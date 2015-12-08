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

package org.springframework.extensions.surf.persister;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.ModelPersistenceContext;
import org.springframework.extensions.surf.cache.ContentCache;

/**
 * Extends the abstract implementation by offering the basics of a caching layer.
 *
 * @author muzquiano
 * @author kevinr
 */
public abstract class AbstractCachedObjectPersister extends AbstractObjectPersister implements CachedPersister
{
    private final static Log logger = LogFactory.getLog(AbstractCachedObjectPersister.class);

    protected final static String GLOBAL_STORE_ID_SUFFIX = ":_global";

    final protected Map<String, ContentCache<ModelObject>> caches;

    protected boolean useCache = true;
    protected long cacheDelay = -1L;
    protected int cacheMaxSize = 10240;


    /**
     * Constructor
     */
    public AbstractCachedObjectPersister()
    {
        this.caches = new HashMap<String, ContentCache<ModelObject>>(128);
    }

    /**
     * Gets the cache for a particular model persistence context
     *
     * @param context       ModelPersistenceContext
     * @param bucket        Cache bucket to pick
     *
     * @return the cache
     */
    protected abstract ContentCache<ModelObject> getCache(ModelPersistenceContext context, String bucket);

    /**
     * Creates a new cache.
     *
     * @return the cache
     */
    protected abstract ContentCache<ModelObject> createCache();

    /**
     * Places an object into this persister's cache.
     *
     * @param context the context
     * @param obj the obj
     */
    protected void cachePut(ModelPersistenceContext context, ModelObject obj)
    {
        if (this.useCache)
        {
            if (logger.isDebugEnabled())
                logger.debug("Put into cache: " + obj.getId());

            String typeId = obj.getTypeId();
            String objId = obj.getId();
            ContentCache<ModelObject> cache = getCache(context, typeId);
            cache.put(objId, obj);
        }
    }

    /**
     * Removes an object from the cache
     *
     * @param context the context
     * @param obj ModelObject
     */
    protected void cacheRemove(ModelPersistenceContext context, ModelObject obj)
    {
        if (this.useCache)
        {
            if (logger.isDebugEnabled())
                logger.debug("Remove from cache: " + obj.getId());

            getCache(context, obj.getTypeId()).remove(obj.getId());
        }
    }

    /**
     * @see org.springframework.extensions.surf.persister.CachedPersister#setCache(boolean)
     */
    public void setCache(boolean useCache)
    {
        this.useCache = useCache;
    }

    /**
     * @see org.springframework.extensions.surf.persister.CachedPersister#setCacheCheckDelay(int)
     */
    public void setCacheCheckDelay(int cacheCheckDelay)
    {
        this.cacheDelay = (cacheCheckDelay * 1000L);
    }
    
    /**
     * @see org.springframework.extensions.surf.persister.CachedPersister#setCacheMaxSize(int)
     */
    public void setCacheMaxSize(int cacheMaxSize)
    {
        this.cacheMaxSize = cacheMaxSize;
    }

    /**
     * @see org.springframework.extensions.surf.persister.CachedPersister#invalidateCache()
     */
    public synchronized void invalidateCache()
    {
        for (ContentCache<ModelObject> cache: caches.values())
        {
            cache.invalidate();
        }
    }
}
