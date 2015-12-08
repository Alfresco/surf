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

package org.springframework.extensions.surf.mvc;

import org.springframework.extensions.webscripts.processor.BaseProcessorExtension;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Script extension helper with utility methods to manipulate ViewResolver caches.
 * This is to allow views to be removed from the cache at runtime e.g. to mirror
 * changes to Surf ModelObject instances such as removed dashboard views.
 * 
 * @author Kevin Roast
 */
public class ViewResolverScriptUtil extends BaseProcessorExtension
{
    private UrlBasedViewResolver viewResolver;
    
    public void setViewResolver(UrlBasedViewResolver viewResolver)
    {
        this.viewResolver = viewResolver;
    }
    
    /**
     * Remove a view from the View Resolver cache.
     * 
     * @param viewName  View to remove
     */
    public void removeFromCache(String viewName)
    {
        this.viewResolver.removeFromCache(viewName, null);
    }
}
