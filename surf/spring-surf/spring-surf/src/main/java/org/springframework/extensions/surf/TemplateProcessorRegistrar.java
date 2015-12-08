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
import org.springframework.extensions.webscripts.SearchPath;
import org.springframework.extensions.webscripts.TemplateProcessor;
import org.springframework.extensions.webscripts.TemplateProcessorFactory;
import org.springframework.extensions.webscripts.TemplateProcessorRegistry;
import org.springframework.extensions.webscripts.processor.AbstractScriptProcessor;

/**
 * Registers a template processor with the web script framework
 * 
 * @author muzquiano
 */
public class TemplateProcessorRegistrar implements ApplicationContextAware
{
    private static final String WEBSCRIPTS_TEMPLATE_REGISTRY_ID = "webframework.webscripts.registry.templateprocessor";
    private static final String TEMPLATES_TEMPLATE_REGISTRY_ID = "webframework.templates.registry.templateprocessor";

    private static final String WEBSCRIPTS_SEARCHPATH_ID = "webframework.webscripts.searchpath";
    private static final String TEMPLATES_SEARCHPATH_ID = "webframework.templates.searchpath";
    
    private ApplicationContext applicationContext;
    private TemplateProcessorRegistry webscriptsRegistry;
    private TemplateProcessorRegistry templatesRegistry;
    private TemplateProcessorFactory factory;
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

    public void setWebScriptsRegistry(TemplateProcessorRegistry webscriptsRegistry)
    {
        this.webscriptsRegistry = webscriptsRegistry;
    }
    
    public void setTemplatesRegistry(TemplateProcessorRegistry templatesRegistry)
    {
        this.templatesRegistry = templatesRegistry;
    }
    
    public void setFactory(TemplateProcessorFactory factory)
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
            webscriptsRegistry = (TemplateProcessorRegistry) applicationContext.getBean(WEBSCRIPTS_TEMPLATE_REGISTRY_ID);
        }
        
        if (templatesRegistry == null)
        {
            templatesRegistry = (TemplateProcessorRegistry) applicationContext.getBean(TEMPLATES_TEMPLATE_REGISTRY_ID);
        }
        
        if (this.factory != null)
        {
            TemplateProcessor templateProcessor1 = factory.newInstance();           
            if (webscriptsSearchPath == null)
            {
                webscriptsSearchPath = (SearchPath) applicationContext.getBean(WEBSCRIPTS_SEARCHPATH_ID);
            }
            if (templateProcessor1 instanceof AbstractScriptProcessor)
            {
                ((AbstractScriptProcessor)templateProcessor1).setSearchPath(webscriptsSearchPath);
            }
            webscriptsRegistry.registerTemplateProcessor(templateProcessor1, extension, name);
            
            TemplateProcessor templateProcessor2 = factory.newInstance();
            if (templatesSearchPath == null)
            {
                templatesSearchPath = (SearchPath) applicationContext.getBean(TEMPLATES_SEARCHPATH_ID);
            }
            if (templateProcessor2 instanceof AbstractScriptProcessor)
            {
                ((AbstractScriptProcessor)templateProcessor2).setSearchPath(templatesSearchPath);
            }
            templatesRegistry.registerTemplateProcessor(templateProcessor2, extension, name);
        }
    }
}