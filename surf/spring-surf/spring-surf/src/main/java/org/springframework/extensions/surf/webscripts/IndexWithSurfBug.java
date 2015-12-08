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

package org.springframework.extensions.surf.webscripts;

import java.util.Map;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.bean.Index;

/**
 * Overridden index of all Web Scripts to include SurfBug information.
 * 
 * @author David Draper
 */
public class IndexWithSurfBug extends Index
{
    /**
     * <p>The <code>WebFrameworkConfigElement</code> is required to enable/disable SurfBug.</p>
     */
    protected WebFrameworkConfigElement webFrameworkConfigElement;
    
    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }

    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status)
    {        
        Map<String, Object> model = super.executeImpl(req, status);
        model.put("surfbugEnabled", this.webFrameworkConfigElement.isSurfBugEnabled());
        return model;
    }

}
