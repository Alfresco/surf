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
package org.springframework.extensions.surf.processor;

import java.util.Map;

import org.springframework.extensions.config.ConfigElement;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.ScriptConfigModel;
import org.springframework.extensions.webscripts.processor.JSScriptProcessor;

/**
 * This extends the the default JSScriptProcessor to provide the capability to substitute variables
 * in the requested resource name. This currently only supports the replacing the "aikauVersion" 
 * token in order that page model library files can be accurately located in the version of Aikau
 * that is in use. In the future this could be further enhanced to support additional tokens but
 * was initially created to address a single use-case. 
 *  
 * @author Dave Draper
 */
public class JSScriptWithTokensProcessor extends JSScriptProcessor
{
    /**
     * Reference to the Web Framework configuration that will enable us to access data such as the 
     * "aikau-version" configuration. This will be populated via Spring dependency injection.
     */
    private WebFrameworkConfigElement webFrameworkConfigElement;
    
    /**
     * Returns the configuration for the web framework.
     * 
     * @return WebFrameworkConfigElement
     */
    public WebFrameworkConfigElement getWebFrameworkConfigElement()
    {
        return webFrameworkConfigElement;
    }

    /**
     * Required by Spring to inject the Web Framework configuration.
     * 
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     */
    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }
    
    /**
     * Returns the current Aikau version. This will found in the version of Aikau that is in use.
     * It will typically be the latest version of Aikau found unless module deployment order has
     * been manually set to use a different version.
     * 
     * @return A String containing the current Aikau version.
     */
    @SuppressWarnings("unchecked")
    public String getAikauVersion()
    {
        final RequestContext rc = ThreadLocalRequestContext.getRequestContext();
        String aikauVersion = null;
        ScriptConfigModel config = rc.getExtendedScriptConfigModel(null);
        Map<String, ConfigElement> configs = (Map<String, ConfigElement>)config.getScoped().get("WebFramework");
        if (configs != null)
        {
            WebFrameworkConfigElement wfce = (WebFrameworkConfigElement) configs.get("web-framework");
            aikauVersion = wfce.getAikauVersion();
        }
        else
        {
            aikauVersion = this.getWebFrameworkConfigElement().getAikauVersion();
        }
        return aikauVersion;
    }
    
    /**
     * Extends the inherited method to replace any "{aikauVersion}" tokens with the current version of
     * Aikau that is in use.
     * 
     * @param resource Script resource to load. Supports either classpath: prefix syntax or a resource path within the WebScript stores. 
     * @return The content from the resource, null if not recognised format
     */
    @Override
    public String loadScriptResource(String resource)
    {
        String aikauVersion = this.getAikauVersion();
        if (aikauVersion != null)
        {
            resource = resource.replaceAll("\\{aikauVersion\\}", aikauVersion);
        }
        return super.loadScriptResource(resource);
    }
}
