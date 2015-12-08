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

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.resource.ResourceService;

/**
 * <p>Foundation class for factory beans.</p>
 * 
 * @author muzquiano
 * @author David Draper
 */
public abstract class BaseFactoryBean
{
    /**
     * @deprecated Because it was only ever used as a way of obtaining the other <code>WebFrameworkConfigElement</code>, 
     * <code>ModelObjectService</code> and <code>ResourceService</code> references. These are made available when using
     * the non-deprecated constructor. 
     */
    private WebFrameworkServiceRegistry serviceRegistry = null;
    
    /**
     * TODO: Provide a description of the WebFrameworkConfigElement
     */
    private WebFrameworkConfigElement webFrameworkConfigElement;
    
    /**
     * TODO: Provide a description of the ModelObjectService
     */
    private ModelObjectService modelObjectService;
    
    /**
     * TODO: Provide a description of the ResourceService
     */
    private ResourceService resourceService;

    /**
     * <p>This constructor has been deprecated as it relies on the <code>WebFrameworkServiceRegistry</code> argument
     * to access all the required Spring beans rather than using a properly configured Spring application context.</p>
     * @param serviceRegistry WebFrameworkServiceRegistry
     * @deprecated
     */
    public BaseFactoryBean(WebFrameworkServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
        this.webFrameworkConfigElement = serviceRegistry.getWebFrameworkConfiguration();
        this.modelObjectService = serviceRegistry.getModelObjectService();
        this.resourceService = serviceRegistry.getResourceService();
    }
        
    /**
     * <p>This is the preferred constructor for subclasses to call when being instantiated as it sets the 
     * required Spring beans directly rather than relying on them being obtained from the <code>WebFrameworkServiceRegistry</code>
     * supplied to the deprecated constructor.</p>
     * 
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     * @param modelObjectService ModelObjectService
     * @param resourceService ResourceService
     */
    public BaseFactoryBean(WebFrameworkConfigElement webFrameworkConfigElement,
                           ModelObjectService modelObjectService,
                           ResourceService resourceService)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
        this.modelObjectService = modelObjectService;
        this.resourceService = resourceService;
    }
    
    /**
     * Gets the service registry.
     * 
     * @return the service registry
     */
    public WebFrameworkServiceRegistry getServiceRegistry()
    {
        return this.serviceRegistry;
    }

    /**
     * Gets the web framework configuration.
     * 
     * @return the web framework configuration
     */
    public WebFrameworkConfigElement getWebFrameworkConfiguration()
    {
        return this.webFrameworkConfigElement;
    }    
    
    /**
     * Gets the model object service
     * 
     * @return model object service
     */
    public ModelObjectService getObjectService()
    {
        return this.modelObjectService;
    }
    
    /**
     * Gets the resource service.
     * 
     * @return the resource service
     */
    public ResourceService getResourceService()
    {
        return this.resourceService;
    }
}
