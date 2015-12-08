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

/**
 * Interface that describes basic methods for working with a cache
 * of content objects.  
 * 
 * @author muzquiano
 */
public interface ContentCache<K>
{
    /**
     * Gets content stored in the cache 
     * 
     * @param key the key
     * 
     * @return the object
     */
    public K get(String key);

    /**
     * Places content into the cache (with default timeout)
     * 
     * @param key the key
     * @param obj the obj
     */
    public void put(String key, K obj);
    
    /**
     * Places content into the cache
     * 
     * @param key the key
     * @param obj the obj
     * @param timeout the timeout in milliseconds
     */
    public void put(String key, K obj, long timeout);
    
    /**
     * Removes a content object from the cache.
     * 
     * @param key the key
     */
    public void remove(String key);

    /**
     * Invalidates the cache
     */
    public void invalidate();
}
