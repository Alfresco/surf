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

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * Abstract web framework interceptor
 * 
 * @author muzquiano
 */
public abstract class AbstractWebFrameworkInterceptor implements ApplicationContextAware, ServletContextAware, WebRequestInterceptor 
{
    private ApplicationContext applicationContext = null;
    private ServletContext servletContext = null;
    private WebFrameworkServiceRegistry webFrameworkServiceRegistry = null;
    
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
    
    /**
     * Retrieves the application context
     * 
     * @return app context
     */
    public ApplicationContext getApplicationContext()
    {
        return this.applicationContext;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
     */
    public void setServletContext(ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }
    
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
        return this.webFrameworkServiceRegistry.getWebFrameworkConfiguration();
    }    
    
    /**
     * Gets the model object service
     * 
     * @return model object service
     */
    public ModelObjectService getObjectService()
    {
        return this.webFrameworkServiceRegistry.getModelObjectService();
    }
    
    /**
     * Gets the render service.
     * 
     * @return the render service
     */
    public RenderService getRenderService()
    {
        return this.webFrameworkServiceRegistry.getRenderService();
    }
}