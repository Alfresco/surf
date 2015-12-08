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

package org.springframework.extensions.surf.types;

import org.springframework.extensions.surf.ModelObject;

/**
 * Interface for a ComponentType object type
 * 
 * @author muzquiano
 */
public interface ComponentType extends ModelObject
{
    // type
    public static String TYPE_ID = "component-type";
    
    // properties
    public static String PROP_URI = "uri";
    
    /**
     * Gets the URI.
     * 
     * @return the uRI
     */
    public String getURI();

    /**
     * Sets the URI.
     * 
     * @param uri the new uRI
     */
    public void setURI(String uri);
}