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

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.RendererExecutionException;
import org.springframework.extensions.surf.render.AbstractRenderer;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.surf.types.Component;
import org.springframework.extensions.surf.types.TemplateInstance;

/**
 * Bean responsible for rendering a template instance.
 *
 * @author muzquiano
 * @author kevinr
 * @author David Draper
 */
public class TemplateInstanceRenderer extends AbstractRenderer
{
    /**
     * <p>The {@link WebFrameworkConfigElement} is used to determine whether or <code>calculate-webscript-dependencies</code> has
     * been enabled or not (it is enabled by default to ensure backwards compatibility). If it is disabled then the 
     * <code>calculateComponentDependencies</code> method will not be called. The result of this is that all .head.ftl WebScript
     * files will not be processed.</p>
     */
    private WebFrameworkConfigElement webFrameworkConfigElement;
    
    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }

    /**
     * Pre-process the template - with no actually output capture or component rendering -
     * to capture which components are bound into regions. This information can then be
     * used to render the "head" templates up-front before the main template executes.
     *
     * @param context RequestContext
     * @param object ModelObject
     * @throws RendererExecutionException
     */
    private void calculateComponentDependencies(RequestContext context, ModelObject object)
        throws RendererExecutionException
    {
        TemplateInstance template = (TemplateInstance) object;

        // We need to preprocess the template to calculate the component dependencies
        // - component dependencies are resolved only when they have all executed.
        // First pass is very fast as template pages themselves have very little implicit content and
        // any associated behaviour logic is executed only once, with the result stored for the 2nd pass.
        // The critical performance path is in executing the WebScript components - which is only
        // performed during the second pass of the template - once component references are all resolved.
        try
        {
            context.setPassiveMode(true);
            getRenderService().processTemplate(context, RenderFocus.BODY, template);
        }
        finally
        {
            context.setPassiveMode(false);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractRenderer#header(org.alfresco.web.framework.render.RenderContext)
     */
    public void header(RequestContext context, ModelObject object)
        throws RendererExecutionException
    {
        RenderService renderService = getRenderService();
        if (object instanceof TemplateInstance)
        {
            TemplateInstance template = (TemplateInstance) object;
            if (template != null)
            {
                // Render "head" output of components
                Component component = null;
                Component[] components = context.getRenderingComponents();
                if (components != null)
                {
                    for (int i = 0; i < components.length; i++)
                    {
                        component = components[i];

                        // Note that when we render the component we render it "chromeless". The reason is
                        // that chrome is intended for adding visual elements and the template header is
                        // for use in the <head> HTML element so there is no point in rendering the component
                        // chrome. This improves performance by not requiring chrome to loaded.
                        renderService.renderComponent(context, RenderFocus.HEADER, component, null, true);
                        print(context, RenderService.NEWLINE);
                    }
                }
            }
        }
        else if (object instanceof Component)
        {
            renderService.renderComponent(context, RenderFocus.HEADER, (Component) object, null, true);
            print(context, RenderService.NEWLINE);
        }

        postHeaderProcess(context);
    }

    /**
     * Renders the current template
     */
    public void body(RequestContext context, ModelObject object)
        throws RendererExecutionException
    {
        TemplateInstance template = (TemplateInstance) object;

        if (this.webFrameworkConfigElement == null || this.webFrameworkConfigElement.isCalculateWebScriptDependenciesEnabled())
        {
            // FIRST PASS - calculate component dependencies - only if configured.
            calculateComponentDependencies(context, object);
        }

        // SECOND PASS - render output of template
        // get the template processor and process it
        // this commits the template output to output stream
        getRenderService().processTemplate(context, RenderFocus.BODY, template);
    }

    public void postHeaderProcess(RequestContext context)
        throws RendererExecutionException
    {
    }
}