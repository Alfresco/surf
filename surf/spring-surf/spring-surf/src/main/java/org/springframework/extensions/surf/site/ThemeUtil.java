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

package org.springframework.extensions.surf.site;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.types.Theme;

/**
 * A helper class for working with themes.
 * 
 * This basically assists in synchronizing the current theme between
 * the request and the session.
 * 
 * It is useful for determining the current theme id during the execution
 * of a JSP component, for example, or within a custom Java bean.
 * 
 * @author muzquiano
 */
public class ThemeUtil
{
    /**
     * Gets the current theme id.
     * 
     * @param context the context
     * 
     * @return the current theme id
     */
    public static String getCurrentThemeId(RequestContext context)
    {
        String themeId = null;
        
        if (context.getTheme() != null)
        {
            themeId = context.getTheme().getId();
        }
        
        return themeId;
    }
    
    /**
     * Sets the current theme.
     * 
     * @param context the context
     * @param themeId the theme id
     */
    public static void setCurrentThemeId(RequestContext context, String themeId)
    {
        Theme theme = context.getObjectService().getTheme(themeId);
        if (theme != null)
        {
            context.setTheme(theme);
        }
    }
}
