/*
 * Copyright (C) 2005-2015 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */

package org.springframework.extensions.surf.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.types.AbstractModelObject;
import org.springframework.extensions.surf.types.Component;
import org.springframework.extensions.surf.util.CacheReport;
import org.springframework.extensions.surf.util.CacheReporter;
import org.springframework.extensions.webscripts.Store;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * Cache for ModelObject instances. Based on an underlying ConcurrentHashMap
 * implementation that is resposible for handling multi-threaded access to
 * the cache.
 * <p>
 * The delay between checks against the underlying store is configurable.
 * 
 * @since 1.1
 * Improved version using ConcurrentLinkedHashmap to discard old items.
 * 
 * @author kevinr
 * @author dward
 */
public class ModelObjectCache implements ContentCache<ModelObject>, CacheReporter
{
    private static final int MAX_SIZE = -1;
    
    protected final Store store;
    protected final long delay;
    protected final int maxSize;
    protected final Map<String, CacheItem<ModelObject>> cache;
    
    
    /**
     * Instantiates a new model object cache.
     * 
     * @param store     the store
     * @param delay     the delay to check modified dates for items in the cache
     */
    public ModelObjectCache(Store store, long delay)
    {
        this(store, MAX_SIZE, delay);
    }
    
    /**
     * Instantiates a new model object cache.
     * 
     * @param store     the store
     * @param maxSize   the maxSize
     * @param delay     the delay to check modified dates for items in the cache
     */
    public ModelObjectCache(Store store, int maxSize, long delay)
    {
        this.delay = delay;
        this.store = store;
        this.maxSize = maxSize;
        if (maxSize != -1)
        {
            // maximum size of the cache means we use a Linked Concurrent HashMap which is
            // responsible for discarding items from the cache when it hits capacity
            cache = new ConcurrentLinkedHashMap.Builder<String, CacheItem<ModelObject>>()
                 .maximumWeightedCapacity(maxSize)
                 .weigher(Weighers.singleton())
                 .listener(new EvictionListener<String, CacheItem<ModelObject>>() {
                        /**
                         * Listener called when a key/value pair is evicted from the cache - we use this to ensure parent of
                         * any child model object from a definition containing sub-components is also evicted see MNT-16495
                         */
                        @Override
                        public void onEviction(String key, CacheItem<ModelObject> value)
                        {
                            if (value.object instanceof Component)
                            {
                                Component c = ((Component)value.object);
                                c.onEviction();
                            }
                        }
                    })
                 .build();
        }
        else
        {
            // no maximum size specified - this cache uses a standard Concurrent HashMap
            cache = new ConcurrentHashMap<String, CacheItem<ModelObject>>(1024, 0.75f);
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.cache.ContentCache#get()
     */
    public ModelObject get(String key)
    {
        ModelObject obj = null;
        CacheItem<ModelObject> item = this.cache.get(key);
        if (item != null)
        {
            obj = item.object;
            
            // check for validity of the object
            if (this.delay >= 0L)
            {
                synchronized (item)
                {
                    // get the content item from the cache
                    long now = System.currentTimeMillis();
                    if (this.delay < now - item.lastChecked)
                    {
                        // delay hit - check cached item
                        // modification time of our model object
                        // check the modification time in the store
                        item.lastChecked = now;
                        if (obj != ModelObjectSentinel.instance)
                        {
                            String path = obj.getStoragePath();
                            try
                            {
                                if (this.store.lastModified(path) > obj.getModificationTime())
                                {
                                    // the in-memory copy is stale, remove from cache
                                    remove(key);
                                    obj = null;
                                }
                            }
                            catch (IOException ex)
                            {
                                // unable to access the timestamp in the store
                                // could be many reasons but lets assume the worst case
                                // the file may have been deleted, so remove from the cache
                                remove(key);
                                obj = null;
                            }
                        }
                        else
                        {
                            // no point checking the store as this was a sentinel
                            remove(key);
                            obj = null;
                        }
                    }
                }
            }
        }
        return obj;
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.cache.ContentCache#invalidate()
     */
    public void invalidate()
    {
        cache.clear();
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.cache.ContentCache#put(java.lang.String, java.lang.Object)
     */
    public void put(String key, ModelObject obj)
    {
        put(key, obj, -1L);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.cache.ContentCache#put(java.lang.String, java.lang.Object, long)
     */
    public void put(String key, ModelObject obj, long timeout)
    {
        // create the cache item
        CacheItem<ModelObject> item = new CacheItem<ModelObject>(key, obj, timeout);
        cache.put(key, item);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.cache.ContentCache#remove(java.lang.String)
     */
    public void remove(String key)
    {
        if (key == null)
        {
            return;
        }
        cache.remove(key);
    }
    
    /**
     * Returns the values for the items in the cache
     * 
     * @return the collection
     */
    public Collection<CacheItem<ModelObject>> values()
    {
        return this.cache.values();
    }
    
    /**
     * Returns keys for the items in the cache
     * 
     * @return the set
     */
    public Set<String> keys()
    {
        return this.cache.keySet();
    }
    
    @Override
    public List<CacheReport> report()
    {
        List<CacheReport> reports = new ArrayList<>(1);
        
        long size = 0;
        for (String key : this.cache.keySet())
        {
            CacheItem<ModelObject> v = this.cache.get(key);
            if (v != null)
            {
                size += key.length() * 2 + 128;
                String xml = v.object.toXML();
                // careful to avoid Sentinel reference in cache!
                if (xml != null)
                {
                    size += xml.length() * 2 + 64;
                    size += v.object.getModelProperties().size()*128 + 32;
                    size += v.object.getCustomProperties().size()*128 + 32;
                    size += 1024;
                }
            }
        }
        reports.add(new CacheReport("modelObjectCache:" + store.toString(), this.cache.size(), size));
        
        return reports;
    }

    @Override
    public void clearCaches()
    {
        this.invalidate();
    }


    /**
     * Singleton sentinel class used to represent a 'null' value in some cache implementations
     *  
     * @author Kevin Roast
     */
    public static class ModelObjectSentinel extends AbstractModelObject
    {
        private static ModelObjectSentinel instance = new ModelObjectSentinel();
        
        private ModelObjectSentinel()
        {
        }
        
        public static ModelObjectSentinel getInstance()
        {
            return instance;
        }
        
        /* (non-Javadoc)
         * @see org.alfresco.web.framework.AbstractModelObject#getTypeId()
         */
        @Override
        public String getTypeId()
        {
            return "ModelObjectSentinel";
        }
    }
}
