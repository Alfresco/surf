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

package org.springframework.extensions.surf.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.resource.ResourceService;

/**
 * Foundation for web framework factory objects.
 * 
 * @author muzquiano
 */
public abstract class BaseFactory implements ApplicationContextAware
{
    private ApplicationContext applicationContext;
    protected WebFrameworkServiceRegistry webFrameworkServiceRegistry = null;
    
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
    
    /**
     * Gets the application context.
     * 
     * @return the application context
     */
    public ApplicationContext getApplicationContext()
    {
        return this.applicationContext;
    }
    
    protected FrameworkBean frameworkUtils;
    
    public void setFrameworkUtils(FrameworkBean frameworkUtils)
    {
        this.frameworkUtils = frameworkUtils;
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
        return this.getServiceRegistry().getWebFrameworkConfiguration();
    }    
    
    /**
     * Gets the model object service
     * 
     * @return model object service
     */
    public ModelObjectService getObjectService()
    {
        return getServiceRegistry().getModelObjectService();
    }
    
    /**
     * Gets the resource service.
     * 
     * @return the resource service
     */
    public ResourceService getResourceService()
    {
        return this.getServiceRegistry().getResourceService();
    }    
    
}
