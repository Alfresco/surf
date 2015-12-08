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

import org.springframework.extensions.webscripts.WebScript;

public interface SurfBugData
{
    /**
     * Store the WebScript resolved for this component (this will only be set if the
     * Component is backed by a WebScript).
     * 
     * @param webScript WebScript
     */
    public void setResolvedWebScript(WebScript webScript);
    
    /**
     * Return the WebScript that was resolved to render this component (this will only
     * be available during rendering) it will return null if the component is not backed
     * by a WebScript.
     * 
     * @return WebScript
     */
    public WebScript getResolvedWebScript();
}
