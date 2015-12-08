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

package org.springframework.extensions.surf.site;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.RequestContext;

/**
 * Utility class that allows for easy invalidation of the cache.
 * 
 * This is used primarly by the CacheServlet which receives calls
 * from the outside world to "refresh" the cache.
 * 
 * It is also invoked from within the scripting layer to force
 * cache refreshes when objects have been changed through scripting.
 * 
 * @author muzquiano
 */
public class CacheUtil
{
    private static Log logger = LogFactory.getLog(CacheUtil.class);
    
    /**
     * Invalidate model object service object cache.
     * 
     * @param context the context
     */
    public static void invalidateModelObjectServiceCache(RequestContext context)
    {
        // invalidate the model object service state for this user context
        context.getServiceRegistry().getObjectPersistenceService().invalidateCache();

        logger.info("Invalidated Object Cache");
    }
}
