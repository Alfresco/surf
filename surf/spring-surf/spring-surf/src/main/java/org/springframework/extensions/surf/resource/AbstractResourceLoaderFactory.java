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

import org.springframework.extensions.surf.support.BaseFactory;

/**
 * Abstract class that provides caching of resource loaders
 * for endpoints
 * 
 * @author muzquiano
 */
public abstract class AbstractResourceLoaderFactory extends BaseFactory implements ResourceLoaderFactory
{
    protected int order = 0;
    
    /**
     * Sets the order.
     * 
     * @param order the new order
     */
    public void setOrder(int order)
    {
        this.order = order;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.ResourceLoaderFactory#getOrder()
     */
    public int getOrder()
    {
        return this.order;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.AbstractCachingResourceLoaderFactory#canHandle(java.lang.String)
     */
    public boolean canHandle(String protocolId)
    {
        boolean canHandle = false;

        if (protocolId != null)
        {
            String[] protocols = this.getSupportedProtocols();
            for (int i = 0; i < protocols.length; i++)
            {
                if (protocolId.equalsIgnoreCase(protocols[i]))
                {
                    canHandle = true;
                }
            }
        }

        return canHandle;
    }    
    
    /**
     * Gets the supported protocols.
     * 
     * @return the supported protocols
     */
    public abstract String[] getSupportedProtocols();    
}
