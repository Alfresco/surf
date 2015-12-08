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

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.cache.BasicCache;
import org.springframework.extensions.surf.exception.ResourceLoaderException;

/**
 * Provides caching of resource objects by object id
 * 
 * @author muzquiano
 */
public abstract class AbstractCachingResourceLoader extends AbstractResourceLoader
{
    private BasicCache<Resource> cache = null;
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
    
    /**
     * Instantiates a new abstract resource loader
     * 
     * @param protocolId the protocol id
     * @param endpointId the endpoint id
     */
    public AbstractCachingResourceLoader(String protocolId, String endpointId, FrameworkBean frameworkUtil)
    {
        super(protocolId, endpointId, frameworkUtil);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.ResourceLoader#load(java.lang.String)
     */
    public synchronized Resource load(String objectId)
        throws ResourceLoaderException
    {
        if (cache == null)
        {
            // init with a five minute cache
            cache = new BasicCache<Resource>(this.cacheTimeout);
        }
        
        Resource resource = (Resource) cache.get(objectId);
        if (resource == null)
        {
            resource = buildResource(objectId);
            cache.put(objectId, resource);
        }
        
        return resource;
    }
    
    /**
     * Builds the resource bound to the given object id
     * 
     * @param objectId String
     * 
     * @return resource
     */
    public abstract Resource buildResource(String objectId)
        throws ResourceLoaderException;
}
