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

package org.springframework.extensions.surf.resource;

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;

/**
 * Abstract class that developers can extend to build their own custom resource
 * loaders.
 * 
 * @author muzquiano
 */
public abstract class AbstractResourceLoader implements ResourceLoader
{
    private WebFrameworkServiceRegistry serviceRegistry;
    
    protected String endpointId;
    protected String protocolId;
    protected FrameworkBean frameworkUtil;
    
    /**
     * Instantiates a new abstract resource loader
     * 
     * @param protocolId the protocol id
     * @param endpointId the endpoint id
     */
    public AbstractResourceLoader(String protocolId, String endpointId, FrameworkBean frameworkUtil)
    {
        this.protocolId = protocolId;
        this.endpointId = endpointId;
        this.frameworkUtil = frameworkUtil;
    }
    
    /**
     * Sets the service registry.
     * 
     * @param serviceRegistry the new service registry
     */
    public void setServiceRegistry(WebFrameworkServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
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
     * Spring initialization method
     */
    public void init()
    {
    }    

    /**
     * Gets the endpoint id.
     * 
     * @return the endpoint id
     */
    public String getEndpointId()
    {
        return this.endpointId;
    }
    
    /**
     * Gets the protocol id.
     * 
     * @return the protocol id
     */
    public String getProtocolId()
    {
        return this.protocolId;
    }
}
