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

import javax.servlet.http.HttpServletRequest;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.TemplatesContainer;
import org.springframework.extensions.surf.WebFrameworkConstants;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.exception.RendererExecutionException;
import org.springframework.extensions.surf.exception.RequestDispatchException;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.surf.resource.ResourceService;
import org.springframework.extensions.surf.types.TemplateInstance;

/**
 * Default view implementation for Surf templates
 *
 * @author muzquiano
 * @author David Draper
 */
public class TemplateView extends AbstractWebFrameworkView
{
    /**
     * <p>This is the preferred constructor to use for instantiating a new <code>TemplateView</code> because it allows
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
    public TemplateView(WebFrameworkConfigElement webFrameworkConfiguration,
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
    public TemplateView(WebFrameworkServiceRegistry serviceRegistry)
    {
        super(serviceRegistry);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.mvc.AbstractWebFrameworkView#renderView(org.springframework.extensions.surf.render.RenderContext)
     */
    protected void renderView(RequestContext context) throws Exception
    {
        dispatchTemplate(context);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.dispatcher.AbstractDispatcher#dispatchContext(org.alfresco.web.framework.render.RenderContext)
     */
    public void dispatchTemplate(RequestContext context)
        throws RequestDispatchException
    {
        TemplateInstance templateInstance = context.getTemplate();
        if (templateInstance != null)
        {
            // render content
            getRenderService().renderTemplate(context, RenderFocus.BODY);
        }
        else
        {
            // there was an associated content display template instance
            // however, it appears to be missing or unloadable
            try
            {
                getRenderService().renderSystemPage(context, WebFrameworkConstants.SYSTEM_PAGE_CONTENT_ASSOCIATION_MISSING);
            }
            catch (RendererExecutionException e)
            {
                throw new RequestDispatchException(e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.mvc.AbstractWebFrameworkView#setupRequestContext(org.springframework.extensions.surf.RequestContext, javax.servlet.http.HttpServletRequest)
     */
    protected void validateRequestContext(RequestContext context, HttpServletRequest request)
        throws Exception
    {
        // the template id (from view url)
        String templateId = this.getUrl();

        // bind template
        TemplateInstance templateInstance = getObjectService().getTemplate(templateId);
        if (templateInstance != null)
        {
            context.setTemplate(templateInstance);
        }
    }
}