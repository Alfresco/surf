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

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.surf.FrameworkBean;

/**
 * JSON resource content implementation
 * 
 * @author muzquiano
 */
public class ResourceJSONContentImpl extends ResourceContentImpl implements ResourceJSONContent
{
    protected JSONObject jsonObject = null;
    
    public ResourceJSONContentImpl(Resource resource, String url, FrameworkBean frameworkUtil)
    {
        super(resource, url, frameworkUtil);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.ResourceJSONContent#getJSONString()
     */
    public String getJSONString() throws IOException
    {
        return getStringContent();
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.ResourceJSONContent#getJSON()
     */
    public synchronized JSONObject getJSON() throws JSONException, IOException
    {
        if (jsonObject == null)
        {
            jsonObject = new JSONObject(getJSONString());
        }
        
        return jsonObject;
    }
}
