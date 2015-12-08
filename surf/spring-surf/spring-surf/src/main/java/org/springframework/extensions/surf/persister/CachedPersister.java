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


/**
 * Indicates that the persister implementation provides caching.
 * 
 * @author muzquiano
 * @author Kevin Roast
 */
public interface CachedPersister
{
    /**
     * Enables or disables the caching mechanics
     * 
     * @param cache boolean
     */
    public void setCache(boolean cache);
    
    /**
     * Sets the number of seconds to wait between cache checks, -1 for never.
     * 
     * @param cacheCheckDelay int
     */
    public void setCacheCheckDelay(int cacheCheckDelay);
    
    /**
     * Sets the maximum size of the underlying cache, -1 for no max size.
     * 
     * @param cacheMaxSize int
     */
    public void setCacheMaxSize(int cacheMaxSize);
    
    /**
     * Invalidates the cache
     */
    public void invalidateCache();
}
