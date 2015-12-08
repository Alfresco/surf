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

/**
 * Interface for web framework resource loader factories.
 * 
 * @author muzquiano
 */
public interface ResourceLoaderFactory
{
    /**
     * Returns a resource loader that is bound to the given
     * endpoint.
     * 
     * @param protocolId the protocol id
     * @param endpointId the endpoint id
     * 
     * @return the resource loader
     */
    public ResourceLoader getResourceLoader(String protocolId, String endpointId);
    
    /**
     * Returns the order of preference for this resource loader
     * 
     * @return order
     */
    public int getOrder();
    
    /**
     * Identifies whether this factory can produce resource
     * loaders that can handle the given protocol
     * 
     * @param protocolId String
     * 
     * @return whether this factory can produce for the given protocol 
     */
    public boolean canHandle(String protocolId);    
}
