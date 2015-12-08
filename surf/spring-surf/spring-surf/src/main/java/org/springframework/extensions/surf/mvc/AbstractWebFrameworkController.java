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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Abstract Spring MVC Controller that produces Surf Views.
 * 
 * Developers who wish to implement custom Spring controllers for use
 * with Alfresco Surf will benefit by extending from this class.  This
 * class provides member functions for accessing the application context
 * as well as important Surf services.
 * 
 * @author muzquiano
 */
public abstract class AbstractWebFrameworkController extends AbstractController implements ServletContextAware
{
    private static final String MIMETYPE_HTML = "text/html;charset=utf-8";
    
    /** The web framework service registry. */
    private WebFrameworkServiceRegistry webFrameworkServiceRegistry;
        
    /**
     * Sets the service registry.
     * 
     * @param webFrameworkServiceRegistry the new service registry
     */
    public void setServiceRegistry(WebFrameworkServiceRegistry webFrameworkServiceRegistry)
    {
        this.webFrameworkServiceRegistry = webFrameworkServiceRegistry;
    }
    
    /**
     * Gets the service registry.
     * 
     * @return the service registry
     */
    public WebFrameworkServiceRegistry getServiceRegistry()
    {
        return this.webFrameworkServiceRegistry;
    }
    
    /**
     * Gets the web framework configuration.
     * 
     * @return the web framework configuration
     */
    public WebFrameworkConfigElement getWebFrameworkConfiguration()
    {
        return this.getServiceRegistry().getWebFrameworkConfiguration();
    }    
    
    /**
     * Gets the model object service
     * 
     * @return model object service
     */
    public ModelObjectService getObjectService()
    {
        return this.getServiceRegistry().getModelObjectService();
    }
    
    /**
     * Gets the render service.
     * 
     * @return the render service
     */
    public RenderService getRenderService()
    {
        return this.getServiceRegistry().getRenderService();
    }    
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // debug logging
        if (getLogger().isDebugEnabled())
        {
            String qs = request.getQueryString();
            getLogger().debug("Processing URL: ("  + request.getMethod() + ") " + request.getRequestURI() + 
                  ((qs != null && qs.length() != 0) ? ("?" + qs) : ""));
        }
        
        // set no cache headers
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        // set response content type and charset
        // TODO: is this the right place to do this?
        response.setContentType(MIMETYPE_HTML);        
        
        // create model and view
        return createModelAndView(request, response);
    }
    
    /**
     * Internal method to be implemented by inheriting class to create
     * model and view for the controller.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ModelAndView
     */
    public abstract ModelAndView createModelAndView(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * Returns a logger for the controller
     * @return logger
     */
    public abstract Log getLogger();
}