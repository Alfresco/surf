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

import java.util.Map;

import org.springframework.extensions.surf.ModelObject;

/**
 * Interface for a Theme object type
 * 
 * @author muzquiano
 */
public interface Theme extends ModelObject
{
    // type
    public static String TYPE_ID = "theme";
    
    public static String CSS_TOKENS = "css-tokens";
    
    /**
     * Gets the page id given the specified page type. If the theme supplies a
     * specific page for a given page type it will be returned, if not null.
     * 
     * @param pageTypeId the page type id
     * 
     * @return the page id
     */
    public String getPageId(String pageTypeId);
    
    /**
     * Sets the page id for a page type.
     * 
     * @param pageTypeId the page type id
     * @param pageId the page id
     */
    public void setDefaultPageId(String pageTypeId, String pageId);
    
    /**
     * @return A {@link Map} of CSS tokens to substitution values. These are used when processing
     * CSS source files so that a common CSS file can be modified per theme.
     */
    public abstract Map<String, String> getCssTokens();
}
