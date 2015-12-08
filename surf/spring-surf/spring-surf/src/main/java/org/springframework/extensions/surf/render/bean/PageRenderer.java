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

package org.springframework.extensions.surf.render.bean;

import java.awt.image.renderable.RenderContext;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.PageRendererExecutionException;
import org.springframework.extensions.surf.exception.RendererExecutionException;
import org.springframework.extensions.surf.render.AbstractRenderer;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.types.Page;
import org.springframework.extensions.surf.types.TemplateInstance;

/**
 * The primary duty of this bean is to determine the appropriate
 * template to execute and the begin the processing of that template.
 *
 * It must base this decision off of the given context which will usually
 * be the base render context that lightly wraps the request.
 *
 * @author muzquiano
 * @author David Draper
 */
public class PageRenderer extends AbstractRenderer
{
    private TemplateInstanceRenderer templateRenderer;
   
    public void setTemplateRenderer(TemplateInstanceRenderer templateRenderer)
    {
        this.templateRenderer = templateRenderer;
    }
    
    private ModelObjectService modelObjectService;

    public void setModelObjectService(ModelObjectService modelObjectService)
    {
        this.modelObjectService = modelObjectService;
    }

    /**
     * Renders the current page
     */
    public void body(RequestContext context, ModelObject object)
        throws RendererExecutionException
    {
        // get the page
        Page page = null;
        if (object == null || !(object instanceof Page))
        {
            throw new PageRendererExecutionException("Unable to render page: null");
        }
        else            
        {
            page = (Page) object;
            
            // This code used to attempt to retrieve the code from the RenderContext. Which would ultimately
            // result in asking the Page for the Template. The PageImpl.getTemplate method would ask the 
            // ModelObjectService for the template (once it had retrieved the template id). We should really
            // cut out all the unnecessary code and go straight to the ModelObjectService to get the template.
            String templateId = page.getTemplateId();
            TemplateInstance template = this.modelObjectService.getTemplate(templateId);
            if (template == null)
            {
                throw new PageRendererExecutionException("Unable to locate template for page: " + page.getId());
            }
            this.templateRenderer.render(context, template, RenderFocus.BODY);
        }
    }

    /**
     * Renders the header for the page
     */
    public void header(RenderContext context)
        throws RendererExecutionException
    {
    }
}