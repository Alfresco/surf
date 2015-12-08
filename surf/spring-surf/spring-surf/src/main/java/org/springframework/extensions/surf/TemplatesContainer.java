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

import org.springframework.extensions.webscripts.ScriptProcessorRegistry;
import org.springframework.extensions.webscripts.TemplateProcessorRegistry;

/**
 * Simple container to gather together services that support the
 * separation of a template engine within the Web Framework.
 * 
 * Web Scripts have their own script processor and template processor
 * registries.
 * 
 * Templates process outside of this and can have entirely different
 * processors or search paths.
 * 
 * @author muzquiano
 */
public class TemplatesContainer
{
    private TemplateProcessorRegistry templateProcessorRegistry;
    private ScriptProcessorRegistry scriptProcessorRegistry;
    
    /**
     * Sets the template processor registry.
     * 
     * @param templateProcessorRegistry the new template processor registry
     */
    public void setTemplateProcessorRegistry(TemplateProcessorRegistry templateProcessorRegistry)
    {
        this.templateProcessorRegistry = templateProcessorRegistry;
    }
    
    /**
     * Gets the template processor registry.
     * 
     * @return the template processor registry
     */
    public TemplateProcessorRegistry getTemplateProcessorRegistry()
    {
        return this.templateProcessorRegistry;
    }
    
    /**
     * Sets the script processor registry.
     * 
     * @param scriptProcessorRegistry the new script processor registry
     */
    public void setScriptProcessorRegistry(ScriptProcessorRegistry scriptProcessorRegistry)
    {
        this.scriptProcessorRegistry = scriptProcessorRegistry;
    }
    
    /**
     * Gets the script processor registry.
     * 
     * @return the script processor registry
     */
    public ScriptProcessorRegistry getScriptProcessorRegistry()
    {
        return this.scriptProcessorRegistry;
    }
    
    /**
     * Resets the templates container
     */
    public void reset()
    {
        this.scriptProcessorRegistry.reset();
        this.templateProcessorRegistry.reset();
    }
}
