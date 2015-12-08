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

import org.springframework.extensions.surf.exception.ResourceLoaderException;

/**
 * Interface for resource loader.
 * 
 * A resource loader is responsible for loading and creating a Resource
 * object for a given object id.
 * 
 * Example: The object id may be a nodeRef to a document in the
 * Alfresco content management system.  Or, it might be a URL to an
 * asset somewhere else in the world.
 * 
 * A resource loader knows how to retrieve the metadata and content for
 * the resource and manufacture a Resource object for use by the Web
 * Framework.
 * 
 * @author muzquiano
 */
public interface ResourceLoader
{
    /**
     * Returns the endpoint id for this resource loader
     * 
     * @return the endpoint id
     */
    public String getEndpointId();
    
    /**
     * Gets the protocol id.
     * 
     * @return the protocol id
     */
    public String getProtocolId();
    
    /**
     * Loads the resource with the given object id
     * 
     * @param objectId String
     * @return Resource
     * @throws ResourceLoaderException
     */
    public Resource load(String objectId)
            throws ResourceLoaderException;
}
