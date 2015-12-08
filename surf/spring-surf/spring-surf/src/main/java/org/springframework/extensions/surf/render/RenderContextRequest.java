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

package org.springframework.extensions.surf.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;

/**
 * Provides an HTTP Servlet Request implementation for the request
 * bundled inside of a render context.  This supports an easy way
 * to retrieve the render context instance.
 * 
 * @author muzquiano
 */
public class RenderContextRequest extends HttpServletRequestWrapper
{
    public static final String ATTRIB_RENDER_CONTEXT = "renderContext";
    public static final String ATTRIB_MODEL_OBJECT = "modelObject";
    
    protected RequestContext renderContext;
    private ModelObject object;
    
    public RenderContextRequest(RequestContext context, ModelObject object, HttpServletRequest request)
    {
        super(request);

        this.renderContext = context;
        this.object = object;
    }
    
    public Object getAttribute(String key)
    {
        Object value = null;
        
        if (ATTRIB_RENDER_CONTEXT.equals(key))
        {
            value = this.renderContext;
        }
        else if (ATTRIB_MODEL_OBJECT.equals(key))
        {
            value = this.object;
        }
        else
        {
            value = super.getAttribute(key);
        }
        
        return value;
    }
}