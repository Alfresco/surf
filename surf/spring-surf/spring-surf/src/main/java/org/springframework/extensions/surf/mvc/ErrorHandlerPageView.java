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

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.TemplatesContainer;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.surf.resource.ResourceService;

/**
 * <p>Default view implementation for Surf system pages.</p>
 * 
 * @author muzquiano
 * @author David Draper
 */
public class ErrorHandlerPageView extends AbstractWebFrameworkView 
{
    /**
     * <p>This is the preferred constructor to use for instantiating a new <code>ErrorHandlerPageView</code> because it allows
     * complete flexibility when rendering the view. An <code>AbstractWebFrameworkView</code> is typically instantiated from
     * within a <code>AbstractWebFrameworkViewResolver</code> and all the arguments in the constructor signature should be
     * supplied to the <code>AbstractWebFrameworkViewResolver</code> as beans via the Spring configuration.</p> 
     * 
     * @param webFrameworkConfiguration WebFrameworkConfigElement
     * @param modelObjectService ModelObjectService
     * @param resourceService ResourceService
     * @param renderService RenderService
     * @param templatesContainer TemplatesContainer
     */
    public ErrorHandlerPageView(WebFrameworkConfigElement webFrameworkConfiguration,
                                ModelObjectService modelObjectService,
                                ResourceService resourceService,
                                RenderService renderService,
                                TemplatesContainer templatesContainer)
    {
        super(webFrameworkConfiguration, modelObjectService, resourceService, renderService, templatesContainer);
    }

    /**
     * <p>This constructor should be avoided if possible because it relies on the supplied <code>WebFrameworkServiceRegistry</code>
     * argument to provide all the other Spring beans required to render the view. This means that there is no flexibility via
     * configuration to adapt different views to use different beans.</p>
     * 
     * @param serviceRegistry WebFrameworkServiceRegistry
     * @deprecated
     */
    public ErrorHandlerPageView(WebFrameworkServiceRegistry serviceRegistry)
    {
        super(serviceRegistry);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.mvc.AbstractWebFrameworkView#renderView(org.springframework.extensions.surf.render.RequestContext)
     */
    protected void renderView(RequestContext context) throws Exception 
    {
        String errorHandlerPageId = this.getUrl();
        getRenderService().renderErrorHandlerPage(context, errorHandlerPageId);
    }        
}