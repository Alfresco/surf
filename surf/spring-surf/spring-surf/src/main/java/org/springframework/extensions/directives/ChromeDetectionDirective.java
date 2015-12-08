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
package org.springframework.extensions.directives;

import java.util.Map;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.extensibility.impl.AbstractExtensibilityDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * <p>This directive is used to determine whether or not Surf Region Chrome is enabled and if not will output the 
 * <{@code}div> element containing the unique HTML ID generated for the Component. This has been created to allow
 * the deprecation of Surf Region Chrome which can be disabled by setting the <{@code}region-chrome> configuration
 * to have no value which will eventually become the default Surf setting.</p>
 *  
 * @author David Draper
 */
public class ChromeDetectionDirective extends AbstractExtensibilityDirective
{
    public ChromeDetectionDirective(String directiveName, 
                                    ExtensibilityModel model, 
                                    WebFrameworkConfigElement webFrameworkConfig,
                                    RequestContext context)
    {
        super(directiveName, model);
        this.webFrameworkConfig = webFrameworkConfig;
    }

    /**
     * <p>The {@link WebFrameworkConfigElement} is required to determine the value of the <{@code}region-chrome> setting.</p>
     */
    private WebFrameworkConfigElement webFrameworkConfig;
    
    /**
     * @return The {@link WebFrameworkConfigElement} that this {@link ChromeDetectionDirective} was instantiated with.
     */
    public WebFrameworkConfigElement getConfig()
    {
        return webFrameworkConfig;
    }

    /**
     * <p>The {@link RequestContext} is required to retrieve the unique HTML ID created for the Component rendering.</p> 
     */
    private RequestContext context;
    
    /**
     * @return The {@link RequestContext} that this {@link ChromeDetectionDirective} was instantiated with.
     */
    public RequestContext getContext()
    {
        return context;
    }

    /**
     * <p>Creates a new {@link ChromeDetectionDirectiveData} instance which will in turn be used to create the
     * {@link ChromeDetectionContentModelElement} that will output the <{@code}div> element containing the 
     * unique HTML ID if required.</p>
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ExtensibilityDirectiveData createExtensibilityDirectiveData(String id, 
                                                                       String action, 
                                                                       String target,
                                                                       Map params, 
                                                                       TemplateDirectiveBody body, 
                                                                       Environment env) throws TemplateException
    {
        return new ChromeDetectionDirectiveData(id, action, target, getDirectiveName(), body, env, webFrameworkConfig, context);
    }
}
