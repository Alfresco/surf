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

package org.springframework.extensions.surf.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * Backing bean for Surf Command Console 
 * 
 * @author muzquiano
 */
public class Console extends DeclarativeWebScript
{
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.DeclarativeWebScript#executeImpl(org.alfresco.web.scripts.WebScriptRequest, org.alfresco.web.scripts.WebScriptResponse)
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        model.put("webscripts",  getContainer().getRegistry().getWebScripts());
        model.put("failures", getContainer().getRegistry().getFailures());
        model.put("rooturl", getContainer().getRegistry().getUri("/"));
        model.put("rootpackage", getContainer().getRegistry().getPackage("/"));
        model.put("rootfamily", getContainer().getRegistry().getFamily("/"));
        model.put("rootlifecycle", getContainer().getRegistry().getLifecycle("/"));
        return model;
    }

}
