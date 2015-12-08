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

import static org.springframework.extensions.surf.WebFrameworkConstants.PAGE_ID;
import static org.springframework.extensions.surf.WebFrameworkConstants.REGION_ID;
import static org.springframework.extensions.surf.WebFrameworkConstants.REGION_SCOPE_GLOBAL;
import static org.springframework.extensions.surf.WebFrameworkConstants.REGION_SCOPE_PAGE;
import static org.springframework.extensions.surf.WebFrameworkConstants.REGION_SCOPE_TEMPLATE;
import static org.springframework.extensions.surf.WebFrameworkConstants.SCOPE_ID;
import static org.springframework.extensions.surf.WebFrameworkConstants.SOURCE_ID;
import static org.springframework.extensions.surf.WebFrameworkConstants.TEMPLATE_ID;

import java.util.Map;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.TemplatesContainer;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.exception.RequestDispatchException;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.surf.resource.ResourceService;
import org.springframework.extensions.surf.types.Page;
import org.springframework.extensions.surf.types.TemplateInstance;

/**
 * <p>View implementation for a Surf page region. URLs are expected to be invoked as shown:</p>
 * <ul>
 * <li>/regionId/{regionId} - displays a globally scoped region</li>
 * <li>/scope/{scopeId}/regionId/{regionId}/sourceId/{sourceId} - displays a page or template scoped region</li>
 * </ul>
 * <p>Most commonly, these are:</p>
 * <ul>
 * <li>scopeId: the scope of the region (i.e. 'page', 'template' or 'global')</li>
 * <li>regionId: the id of the region (i.e. 'footer')</li>
 * <li>sourceId: the id of the template or page instance or 'global' if in the global scope</li>
 * </ul>
 * <p>The region is rendered along with its chrome. If a component is contained in the region, it is also rendered.</p>
 *
 * @author muzquiano
 * @author David Draper
 */
public class RegionView extends AbstractWebFrameworkView
{
    /**
     * <p>This is the preferred constructor to use for instantiating a new <code>RegionView</code> because it allows
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
    public RegionView(WebFrameworkConfigElement webFrameworkConfiguration,
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
    public RegionView(WebFrameworkServiceRegistry serviceRegistry)
    {
        super(serviceRegistry);
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.mvc.AbstractWebFrameworkView#renderView(org.springframework.extensions.surf.render.RenderContext)
     */
    protected void renderView(RequestContext context)
        throws Exception
    {
        // this method assumes that the uri tokens have already been processed through the
        // uri template mappings.
        //
        // the tokens are represented by:
        //
        //    <scopeId>/<regionId>/<sourceId>

        // tokens
        Map<String, String> uriTokens = getUriTokens();
        String scopeId = uriTokens.get(SCOPE_ID);
        String regionId = uriTokens.get(REGION_ID);
        String sourceId = uriTokens.get(SOURCE_ID);

        // defaults
        if (scopeId == null)
        {
            scopeId = REGION_SCOPE_GLOBAL;
        }
        if (sourceId == null)
        {
            sourceId = REGION_SCOPE_GLOBAL;
        }

        // the region id always has to be provided
        if (regionId == null)
        {
            throw new RequestDispatchException("Region ID is missing");
        }

        // populate the model
        context.getModel().put(REGION_ID, regionId);
        context.getModel().put("regionScopeId", scopeId);
        context.getModel().put("regionSourceId", sourceId);

        // page scope
        if (REGION_SCOPE_PAGE.equals(scopeId))
        {
            Page page = (Page) getObjectService().getPage(sourceId);
            if (page != null)
            {
                context.setPage(page);
                context.setTemplate(page.getTemplate(context));
            }
            context.getModel().put(PAGE_ID, sourceId);
        }

        // template scope
        if (REGION_SCOPE_TEMPLATE.equals(scopeId))
        {
            TemplateInstance templateInstance = (TemplateInstance) getObjectService().getTemplate(sourceId);
            if (templateInstance != null)
            {
                context.setTemplate(templateInstance);
            }
            context.getModel().put(TEMPLATE_ID, sourceId);
        }

        // TODO: any setup for other scopes?

        // render the region
        getRenderService().renderRegion(context, RenderFocus.BODY, sourceId, regionId, scopeId, null, false);
    }
}