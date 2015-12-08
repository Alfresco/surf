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
 * Interface for a Configuration object type
 * 
 * @author muzquiano
 */
public interface Configuration extends ModelObject
{
    // type
    public static String TYPE_ID = "configuration";
    
    // properties
    public static String PROP_SOURCE_ID = "source-id";
    public static String VALUE_SOURCE_ID_SITE = "site";    
    
    /**
     * Gets the source id.
     * 
     * @return the source id
     */
    public String getSourceId();

    /**
     * Sets the source id.
     * 
     * @param sourceId the new source id
     */
    public void setSourceId(String sourceId);
}
