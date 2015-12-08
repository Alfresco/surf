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

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.ScriptProcessor;
import org.springframework.extensions.webscripts.ScriptProcessorFactory;
import org.springframework.extensions.webscripts.ScriptProcessorRegistry;
import org.springframework.extensions.webscripts.SearchPath;
import org.springframework.extensions.webscripts.processor.AbstractScriptProcessor;

/**
 * Registers a script processor with Surf
 * 
 * @author muzquiano
 */
public class ScriptProcessorRegistrar implements ApplicationContextAware
{
    private static final String WEBSCRIPTS_SCRIPT_REGISTRY_ID = "webframework.webscripts.registry.scriptprocessor";
    private static final String TEMPLATES_SCRIPT_REGISTRY_ID = "webframework.templates.registry.scriptprocessor";
    
    private static final String WEBSCRIPTS_SEARCHPATH_ID = "webframework.webscripts.searchpath";
    private static final String TEMPLATES_SEARCHPATH_ID = "webframework.templates.searchpath";
    
    private ApplicationContext applicationContext;  
    private ScriptProcessorRegistry webscriptsRegistry;
    private ScriptProcessorRegistry templatesRegistry;
    private ScriptProcessorFactory factory;
    private SearchPath webscriptsSearchPath;
    private SearchPath templatesSearchPath;
    private String name;
    private String extension;
    
    
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    public void setWebScriptsRegistry(ScriptProcessorRegistry webscriptsRegistry)
    {
        this.webscriptsRegistry = webscriptsRegistry;
    }
    
    public void setTemplatesRegistry(ScriptProcessorRegistry templatesRegistry)
    {
        this.templatesRegistry = templatesRegistry;
    }
    
    public void setFactory(ScriptProcessorFactory factory)
    {
        this.factory = factory;
    }
    
    public void setWebscriptsSearchPath(SearchPath webscriptsSearchPath)
    {
        this.webscriptsSearchPath = webscriptsSearchPath;
    }
    
    public void setTemplatesSearchPath(SearchPath templatesSearchPath)
    {
        this.templatesSearchPath = templatesSearchPath;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setExtension(String extension)
    {
        this.extension = extension;
    }
    
    public void init()
    {
        if (webscriptsRegistry == null)
        {
            webscriptsRegistry = (ScriptProcessorRegistry) applicationContext.getBean(WEBSCRIPTS_SCRIPT_REGISTRY_ID);
        }
        
        if (templatesRegistry == null)
        {
            templatesRegistry = (ScriptProcessorRegistry) applicationContext.getBean(TEMPLATES_SCRIPT_REGISTRY_ID);
        }
        
        if (this.factory != null)
        {
            // set up the web scripts script processor
            ScriptProcessor scriptProcessor1 = factory.newInstance();
            if (webscriptsSearchPath == null)
            {
                webscriptsSearchPath = (SearchPath) applicationContext.getBean(WEBSCRIPTS_SEARCHPATH_ID);
            }
            if (scriptProcessor1 instanceof AbstractScriptProcessor)
            {
                ((AbstractScriptProcessor)scriptProcessor1).setSearchPath(webscriptsSearchPath);
            }
            webscriptsRegistry.registerScriptProcessor(scriptProcessor1, extension, name);
            
            // set up the templates script processor
            ScriptProcessor scriptProcessor2 = factory.newInstance();
            if (templatesSearchPath == null)
            {
                templatesSearchPath = (SearchPath) applicationContext.getBean(TEMPLATES_SEARCHPATH_ID);
            }
            if (scriptProcessor2 instanceof AbstractScriptProcessor)
            {
                ((AbstractScriptProcessor)scriptProcessor2).setSearchPath(templatesSearchPath);
            }
            templatesRegistry.registerScriptProcessor(scriptProcessor2, extension, name);
        }
    }
}