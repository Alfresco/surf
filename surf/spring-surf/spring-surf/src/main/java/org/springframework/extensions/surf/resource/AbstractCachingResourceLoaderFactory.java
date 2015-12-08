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

package org.springframework.extensions.surf.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.cache.BasicCache;

/**
 * Abstract class that provides caching of resource loaders
 * for endpoints
 * 
 * @author muzquiano
 */
public abstract class AbstractCachingResourceLoaderFactory extends AbstractResourceLoaderFactory
{
    private static final Log logger = LogFactory.getLog(AbstractCachingResourceLoaderFactory.class);
    
    private BasicCache<ResourceLoader> cache = null;
    private long cacheTimeout = 1000*60*5; // five minutes
    
    /**
     * Sets the cache timeout.
     * 
     * @param cacheTimeout the new cache timeout
     */
    public void setCacheTimeout(long cacheTimeout)
    {
        this.cacheTimeout = cacheTimeout;         
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.ResourceLoaderFactory#getResourceLoader(java.lang.String, java.lang.String)
     */
    public synchronized ResourceLoader getResourceLoader(String protocolId, String endpointId)
    {
        if (cache == null)
        {
            // init with a five minute cache
            cache = new BasicCache<ResourceLoader>(this.cacheTimeout);
        }
        
        String cacheKey = getCacheKey(protocolId, endpointId);
        
        ResourceLoader resourceLoader = (ResourceLoader) cache.get(cacheKey);
        if (resourceLoader == null)
        {
            resourceLoader = buildResourceLoader(protocolId, endpointId);
            cache.put(cacheKey, resourceLoader);
        }
        
        return resourceLoader;
    }
    
    private String getCacheKey(String protocolId, String endpointId)
    {
        StringBuilder key = new StringBuilder();
        
        key.append((protocolId != null ? protocolId : "null"));
        key.append("_");
        key.append((endpointId != null ? endpointId : "null"));
        
        return key.toString();
    }
    
    public abstract ResourceLoader buildResourceLoader(String protocolId, String endpointId);
}
