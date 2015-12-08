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

import java.util.Map;

/**
 * Describes a provider of named Resource objects for the 
 * web framework.
 * 
 * A provider of resources should be able to do things like produce a
 * Resource object for a given resource id.
 * 
 * Some model objects, for example, can be thought of as containers of
 * properties.  They are also containers of "resources".  Resource
 * definitions are stored directly into the model object.
 * 
 * These resource containers have a resource provider interface on top
 * of them.
 * 
 * @author muzquiano
 */
public interface ResourceProvider
{
    /**
     * Looks up a resource with the given name
     * 
     * @param name String
     * @return Resource
     */
    public Resource getResource(String name);

    /**
     * Returns the set of all resources
     * 
     * @return Resource[]
     */
    public Resource[] getResources();

    /**
     * Returns the map of resources
     * 
     * @return Map
     */
    public Map<String, Resource> getResourcesMap();

    /**
     * Adds/Creates a resource with the given name and resource id
     * 
     * @param name String
     * @param resourceId String
     * 
     * @return resource
     */
    public Resource addResource(String name, String resourceId);

    /**
     * Adds/Creates a resource with the given name, object id and
     * endpoint id
     * 
     * @param name String
     * @param protocolId String
     * @param endpointId String
     * @param objectId String
     * 
     * @return resource
     */
    public Resource addResource(String name, String protocolId, String endpointId, String objectId);

    /**
     * Updates a resource for the given name
     * 
     * @param name String
     * @param resource Resource
     */
    public void updateResource(String name, Resource resource);

    /**
     * Removes a resource with the given name
     * 
     * @param name String
     */
    public void removeResource(String name);

}
