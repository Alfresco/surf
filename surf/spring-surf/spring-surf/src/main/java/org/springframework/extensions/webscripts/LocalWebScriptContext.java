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

package org.springframework.extensions.webscripts;

import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;

/**
 * The Context for a Local WebScript invocation.
 * 
 * This class holds references to the objects required to invoke a webscript locally.
 * Many local webscripts can be executed and merged into a single response later.
 * 
 * The most common use of this is to represent components within a template on a page.
 * 
 * @author kevinr
 * @author muzquiano
 */
public final class LocalWebScriptContext
{
    private RuntimeContainer runtimeContainer;
    private Map<String, String> tokens;
    private String executeUrl;
    private String scriptUrl;
    
    // Web Framework Elements
    private RequestContext requestContext;
    private ModelObject object;
    
    
    /**
     * @return the runtimeContainer
     */
    public RuntimeContainer getRuntimeContainer()
    {
        return this.runtimeContainer;
    }
    
    /**
     * @param runtimeContainer the runtimeContainer to set
     */
    public void setRuntimeContainer(RuntimeContainer runtimeContainer)
    {
        this.runtimeContainer = runtimeContainer;
    }
    
    /**
     * @return the tokens
     */
    public Map<String, String> getTokens()
    {
        return this.tokens;
    }
    
    /**
     * @param tokens the tokens to set
     */
    public void setTokens(Map<String, String> tokens)
    {
        this.tokens = tokens;
    }
    
    /**
     * @return the executeUrl
     */
    public String getExecuteUrl()
    {
        return this.executeUrl;
    }
    
    /**
     * @param executeUrl the executeUrl to set
     */
    public void setExecuteUrl(String executeUrl)
    {
        this.executeUrl = executeUrl;
    }
    
    /**
     * @return the scriptUrl
     */
    public String getScriptUrl()
    {
        return this.scriptUrl;
    }
    
    /**
     * @param scriptUrl the scriptUrl to set
     */
    public void setScriptUrl(String scriptUrl)
    {
        this.scriptUrl = scriptUrl;
    }
    
    /**
     * @return the renderContext
     */
    public RequestContext getRequestContext()
    {
        return this.requestContext;
    }
    
    /**
     * @param requestContext the renderContext to set
     */
    public void setRequestContext(RequestContext requestContext)
    {
        this.requestContext = requestContext;
    }
    
    /**
     * @return the ModelObject
     */
    public ModelObject getModelObject()
    {
        return this.object;
    }
    
    /**
     * @param object the ModelObject to set
     */
    public void setModelObject(ModelObject object)
    {
        this.object = object;
    }
}
