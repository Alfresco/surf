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

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.FrameworkUtil;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.resource.Resource;
import org.springframework.extensions.surf.resource.ResourceService;

/**
 * <p>Helper object for dealing with the web application's environment.
 * </p><p>
 * This object can be used on both the production and preview tiers to gain access to the correct
 * web application mount points and more.
 * </p>
 * @author muzquiano
 */
public final class ScriptWebApplication extends ScriptBase
{
    private static final long serialVersionUID = -4449467261985787691L;

    private static Log logger = LogFactory.getLog(ScriptWebApplication.class);
    
    /**
     * Constructs a new ScriptWebApplication object.
     * 
     * @param context   The RenderContext instance for the current request
     */
    public ScriptWebApplication(RequestContext context)
    {
        super(context);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.ScriptBase#buildProperties()
     */
    protected ScriptableMap<String, Serializable> buildProperties()
    {
        return null;
    }


    // --------------------------------------------------------------
    // JavaScript Properties
    
    /**
     * Returns the root web application context
     */
    public String getContext()
    {        
        StringBuilder builder = new StringBuilder(512);

        // path to web application
        builder.append(context.getContextPath());
        
        // path to servlet
        // TODO - needs to be tested to resolve how this works
        //builder.append(context.getServletPath());
        
        // use the resource controller
        builder.append("/res");            
        
        return builder.toString();
    }  

    /**
     * Performs a server-side include of a web asset
     * 
     * The result string is returned.
     * 
     * Value paths are:
     * 
     *    /a/b/c.gif
     *    /images/test.jpg
     * 
     * @param relativePath String
     *
     * @return String
     */
    public String include(String relativePath)
    {
        String buffer = null;
        
        try
        {   
            // resource service
            ResourceService resourceService = FrameworkUtil.getServiceRegistry().getResourceService();
            
            // resource
            Resource resource = resourceService.getResource(relativePath);
            if (resource != null)
            {
                buffer = resource.getContent().getStringContent();
            }
            
            // some post treatment of the buffer
            buffer = buffer.replace("${app.context}", this.getContext());
        }
        catch (Exception ex)
        {
            logger.warn("Unable to include: " + relativePath, ex);
        }
        
        return buffer;
    }
}
