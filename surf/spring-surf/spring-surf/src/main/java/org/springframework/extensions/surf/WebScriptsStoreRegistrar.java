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

package org.springframework.extensions.surf;

import org.springframework.extensions.webscripts.SearchPath;
import org.springframework.extensions.webscripts.StoreRegistrar;

/**
 * Registers a store that contains Surf web script files.
 * 
 * The store will be registered into the templates search path and the web scripts search path
 * 
 * @author muzquiano
 */
public class WebScriptsStoreRegistrar extends StoreRegistrar
{
    protected static final String WEBSCRIPTS_SEARCHPATH_ID = "webframework.webscripts.searchpath";
    protected static final String TEMPLATES_SEARCHPATH_ID = "webframework.templates.searchpath";
    
    private SearchPath templatesSearchPath;
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.StoreRegistrar#getSearchPathId()
     */
    protected String getSearchPathId()
    {
        return WEBSCRIPTS_SEARCHPATH_ID;
    }
    
    /**
     * Sets the templates search path
     * 
     * @param templatesSearchPath SearchPath
     */
    public void setTemplatesSearchPath(SearchPath templatesSearchPath)
    {
        this.templatesSearchPath = templatesSearchPath;
    }

    /**
     * Overrides the templates search path id
     * 
     * @return search path id
     */
    protected String getTemplatesSearchPathId()
    {
        return WEBSCRIPTS_SEARCHPATH_ID;
    }
    
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.StoreRegistrar#init()
     */
    public void init()
    {
        initWebScripts();
        initTemplates();
    }
    
    private void initWebScripts()
    {
        if (searchPath == null)
        {
            searchPath = (SearchPath) getApplicationContext().getBean(getSearchPathId());
        }

        plugin(store, searchPath, prepend);                
    }
    
    private void initTemplates()
    {
        if (templatesSearchPath == null)
        {
            templatesSearchPath = (SearchPath) getApplicationContext().getBean(getTemplatesSearchPathId());
        }

        plugin(store, templatesSearchPath, prepend);
    }
}