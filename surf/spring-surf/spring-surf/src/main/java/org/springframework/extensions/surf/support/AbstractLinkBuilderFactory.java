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
import org.springframework.extensions.surf.LinkBuilder;
import org.springframework.extensions.surf.LinkBuilderFactory;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.resource.ResourceService;

/**
 * <p>Abstract base class for LinkBuilderFactory implementations.  This
 * is provided as a convenience to developers who wish to build their
 * own custom LinkBuilderFactory variations.</p>
 * 
 * @author muzquiano
 * @author David Draper
 */
public abstract class AbstractLinkBuilderFactory extends BaseFactory implements LinkBuilderFactory
{
    /**
     * <p>Extending classes must implement this method to instantiate the associated class of <code>LinkBuilder</code>.</p>
     */
    public abstract LinkBuilder newInstance();
    
    /**
     * <p>A <code>WebFrameworkConfigElement</code> is required in the <code>AbstractLinkBuilder</code> methods so 
     * should be provided as to the <code>AbstractLinkBuilderFactory</code> as a Spring bean property.</p>
     */
    private WebFrameworkConfigElement webFrameworkConfigElement;
       
    /**
     * <p>A <code>ModelObjectService</code> is required in the <code>AbstractLinkBuilder</code> methods so 
     * should be provided as to the <code>AbstractLinkBuilderFactory</code> as a Spring bean property.</p>
     */
    private ModelObjectService modelObjectService;
    
    /**
     * <p>A <code>ResourceService</code> is required in the <code>AbstractLinkBuilder</code> methods so 
     * should be provided as to the <code>AbstractLinkBuilderFactory</code> as a Spring bean property.</p>
     */
    private ResourceService resourceService;
    
    /**
     * <p>This method is supplied so that subclasses can get a reference to the <code>WebFrameworkConfigElement</code>
     * that is required to instantiate a <code>AbstractLinkBuilder</code>.</p>
     * @return WebFrameworkConfigElement
     */
    public WebFrameworkConfigElement getWebFrameworkConfigElement()
    {
        return webFrameworkConfigElement;
    }

    /**
     * <p>This method is supplied so that subclasses can get a reference to the <code>ModelObjectService</code>
     * that is required to instantiate a <code>AbstractLinkBuilder</code>.</p>
     * @return ModelObjectService
     */
    public ModelObjectService getModelObjectService()
    {
        return modelObjectService;
    }

    /**
     * <p>This method is supplied so that subclasses can get a reference to the <code>ResourceService</code>
     * that is required to instantiate a <code>AbstractLinkBuilder</code>.</p>
     * @return ResourceService
     */
    public ResourceService getResourceService()
    {
        return resourceService;
    }

    /**
     * This method is provided to allow Spring to set the <code>WebFrameworkConfigElement</code> as a bean property.</p>
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     */
    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }

    /**
     * This method is provided to allow Spring to set the <code>ModelObjectService</code> as a bean property.</p>
     * @param modelObjectService ModelObjectService
     */
    public void setModelObjectService(ModelObjectService modelObjectService)
    {
        this.modelObjectService = modelObjectService;
    }

    /**
     * This method is provided to allow Spring to set the <code>ResourceService</code> as a bean property.</p>
     * @param resourceService ResourceService
     */
    public void setResourceService(ResourceService resourceService)
    {
        this.resourceService = resourceService;
    }
}
