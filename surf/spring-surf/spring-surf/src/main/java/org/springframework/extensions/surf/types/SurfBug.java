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
 * <p>A <code>SurfBug</code> object is used for writing debug HTML to the
 * output stream when SurfBug mode is enabled.</p>
 * 
 * @author David Draper
 */
public interface SurfBug extends ModelObject
{
    // type
    public static String TYPE_ID = "surfbug";
    
    // properties
    public static String PROP_SURFBUG_TYPE = "surfbug-type";

    public String getSurfBugType();
    
    public void setSurfBugType(String surfBugType);
    
    /**
     * <p>Should set the <code>Component</code> currently being debugged.</p>
     * @param component Component
     */
    public void setCurrentComponent(Component component);
    
    /**
     * <p>Should return the <code>Component</code> currently being debugged.</p>
     * @return Component
     */
    public Component getCurrentComponent();
    
    /**
     * <p>Should return a boolean value indicating whether or not the supplied <code>Component</code>
     * has already been debugged</p>
     * @param object Component
     * @return boolean
     */
    public boolean hasBeenDebugged(Component object);
}
