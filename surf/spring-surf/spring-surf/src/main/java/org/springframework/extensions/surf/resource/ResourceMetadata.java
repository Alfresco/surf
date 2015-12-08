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
 * Interface to the describe the metadata of a resource.
 *  
 * @author muzquiano
 */
public interface ResourceMetadata
{
    /**
     * A link back to the resource of which this content is a part
     * 
     * @return Resource
     */
    public Resource getResource();

    /**
     * Gets the id of the object
     * 
     * @return the id
     */
    public String getId();
    
    /**
     * Returns the bytes of the metadata
     */
    public byte[] getBytes();

    /**
     * Gets the timestamp of the metadata retrieval
     * 
     * @return long
     */
    public long getTimestamp();
    
    /**
     * Reloads the metadata
     */
    public void reload();
}
