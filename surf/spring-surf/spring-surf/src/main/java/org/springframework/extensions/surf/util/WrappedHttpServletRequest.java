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

package org.springframework.extensions.surf.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * A wrapper class for buffering around HttpServletRequest objects
 * 
 * @author muzquiano
 */
public class WrappedHttpServletRequest extends HttpServletRequestWrapper
{
    /**
     * Instantiates a new wrapped http servlet request.
     * 
     * @param request the request
     */
    public WrappedHttpServletRequest(HttpServletRequest request)
    {
        super(request);
    }
    
    private String requestUri;
    
    /**
     * Allows for the request URI to be manually overridden
     * 
     * @param requestUri the new request uri
     */
    public void setRequestURI(String requestUri)
    {
        this.requestUri = requestUri;
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getRequestURI()
     */
    public String getRequestURI()
    {
        String value = null;
        
        if (requestUri != null)
        {
            value = requestUri;
        }
        else
        {
            value = super.getRequestURI();
        }
        
        return value;
    }
    
}
