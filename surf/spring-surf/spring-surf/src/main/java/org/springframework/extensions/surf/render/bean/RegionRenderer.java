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
import org.springframework.extensions.surf.exception.RegionRendererExecutionException;
import org.springframework.extensions.surf.exception.RendererExecutionException;
import org.springframework.extensions.surf.render.AbstractRenderer;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.types.Chrome;

/**
 * Bean responsible for rendering a region.
 * 
 * The bean should set up render context state and then hand off to a
 * region chrome renderer.  If no chrome is present, then it call call
 * through to the RenderUtil helper method directly.
 * 
 * @author muzquiano
 * @author David Draper
 */
public class RegionRenderer extends AbstractRenderer
{
    private static final Log logger = LogFactory.getLog(RegionRenderer.class);
    
    private ChromeRenderer chromeRenderer;
    
    public void setChromeRenderer(ChromeRenderer chromeRenderer)
    {
        this.chromeRenderer = chromeRenderer;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractRenderer#header(org.alfresco.web.framework.render.RenderContext)
     */
    public void header(RequestContext context, ModelObject object) throws RendererExecutionException
    {
        if (logger.isDebugEnabled())
        {
            super.header(context, object);
        }
        
        String regionId = (String) context.getValue(WebFrameworkConstants.RENDER_DATA_REGION_ID);
        try
        {
            getRenderService().renderRegionComponents(context, object, false);
        }
        catch (Exception ex)
        {
            throw new RegionRendererExecutionException("Unable to render region: " + regionId, ex);
        }
    }    
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractRenderer#body(org.alfresco.web.framework.render.RendererContext)
     */
    public void body(RequestContext context, ModelObject object) throws RendererExecutionException
    {
        String regionId = (String) context.getValue(WebFrameworkConstants.RENDER_DATA_REGION_ID);
        String regionChromeId = (String) context.getValue(WebFrameworkConstants.RENDER_DATA_REGION_CHROME_ID);
        Boolean chromeless = (Boolean) context.getValue(WebFrameworkConstants.RENDER_DATA_CHROMELESS);

        try
        {
            if (chromeless)
            {
                getRenderService().renderRegionComponents(context, object, chromeless);
            }
            else
            {
                // fetch the appropriate chrome instance
                Chrome chrome = getRenderService().getRegionChrome(regionId, regionChromeId);
                
                // if we have chrome, process it
                if (chrome != null)
                {
                    this.chromeRenderer.render(context, chrome, RenderFocus.BODY);
                }
                else
                {
                    // call through directly to renderRegionComponents - as this is
                    // what the regionInclude tag in the region chrome will do
                    getRenderService().renderRegionComponents(context, object, chromeless);
                }
            }
            
            
            // post process call
            postProcess(context);
        }
        catch (Exception ex)
        {
            throw new RegionRendererExecutionException("Unable to render region: " + regionId, ex);
        }
    }    
    
    /**
     * Post-processing of regions
     */
    public void postProcess(RequestContext context)
        throws IOException
    {
    }
}