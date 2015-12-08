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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkConstants;
import org.springframework.extensions.surf.exception.ComponentRendererExecutionException;
import org.springframework.extensions.surf.exception.RendererExecutionException;
import org.springframework.extensions.surf.render.AbstractRenderer;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.types.Chrome;
import org.springframework.extensions.surf.types.Component;

/**
 * Bean responsible for rendering a component.
 * 
 * This entails rendering Chrome and then placing the component inside of that chrome.
 * 
 * @author muzquiano
 * @author kevinr
 * @author David Draper
 */
public class ComponentRenderer extends AbstractRenderer
{
    private static final Log logger = LogFactory.getLog(ComponentRenderer.class);
    
    private ChromeRenderer chromeRenderer;
    
    public void setChromeRenderer(ChromeRenderer chromeRenderer)
    {
        this.chromeRenderer = chromeRenderer;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractRenderer#header(org.alfresco.web.framework.render.RequestContext)
     */
    public void header(RequestContext context, ModelObject object) throws RendererExecutionException
    {
        if (logger.isDebugEnabled())
        {
            super.header(context, object);
        }
        
        Component component = (Component) object;
        try
        {
            // Render the component WITHOUT chrome.
            getRenderService().processComponent(context, RenderFocus.HEADER, component, true);
        }
        catch (Exception ex)
        {
            throw new ComponentRendererExecutionException("Unable to render component: " + component.getId(), ex);
        }
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractRenderer#body(org.alfresco.web.framework.render.RendererContext)
     */
    public void body(RequestContext context, ModelObject object) throws RendererExecutionException
    {
        Component component = (Component) object;
        Chrome chrome = (Chrome) context.getValue(WebFrameworkConstants.RENDER_DATA_COMPONENT_CHROME);
        Boolean chromeless = (Boolean) context.getValue(WebFrameworkConstants.RENDER_DATA_CHROMELESS);
        try
        {
            // if we have chrome, render it
            if ((chromeless == null || !chromeless) && chrome != null)
            {            
                this.chromeRenderer.render(context, chrome, RenderFocus.BODY);
            }
            else
            {
                getRenderService().processComponent(context, RenderFocus.BODY, component, chromeless);
            }
            
            // post process call
            postProcess(context);
        }
        catch (Exception ex)
        {
            throw new ComponentRendererExecutionException("Unable to render component: " + component.getId(), ex);
        }
    }
    
    /**
     * Post-processing of components
     */
    public void postProcess(RequestContext context)
        throws IOException
    {
    }
}