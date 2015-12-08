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

/**
 * Abstract implementation of a resource.  Useful for developers
 * who wish to implement new resource loaders and resource types.
 * 
 * ResourceIds arrive in the following format:
 * 
 * <protocolId>://<endpointId>/<objectId>
 * 
 * @author muzquiano
 */
public abstract class AbstractResource implements Resource
{
    protected String name = null;
    protected String protocolId = null;
    protected String objectId = null;
    protected String endpointId = null;
    protected byte[] bytes = null;
    protected FrameworkBean frameworkUtil;
        
    public AbstractResource(String protocolId, String endpointId, String objectId, FrameworkBean frameworkUtil)
    {
        this.protocolId = protocolId;
        this.endpointId = endpointId;
        this.objectId = objectId;
        this.frameworkUtil = frameworkUtil;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getResourceId()
     */
    public String getResourceId()
    {
        String resourceId = getProtocolId() + "://" + getEndpointId();
        if (getObjectId() != null)
        {
            resourceId = resourceId + '/' + getObjectId();
        }
        
        return resourceId;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getResourceProtocol()
     */
    public String getProtocolId()
    {
        return this.protocolId;
    }
    
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getObjectId()
     */
    public String getObjectId()
    {
        return this.objectId;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#setObjectId(java.lang.String)
     */
    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.web.framework.resource.Resource#setEndpoint(java.lang.String)
     */
    public void setEndpointId(String endpointId)
    {
        this.endpointId = endpointId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.web.framework.resource.Resource#getEndpoint()
     */
    public String getEndpointId()
    {
        return this.endpointId;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getName()
     */
    public String getName()
    {
        return this.name;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#setName(java.lang.String)
     */
    public void setName(String name)
    {
        this.name = name;
    }      
}
