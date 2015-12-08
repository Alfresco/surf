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

package org.springframework.extensions.surf.mvc;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.site.CacheUtil;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Default Spring controller for processing Surf remote control calls.
 * 
 * Surf remote control calls are essentially REST-style calls which tell
 * the Surf server to perform operations against its cache or its
 * internal state.
 * 
 * The following URLs are supported:
 * 
 *     /cache/invalidate
 *     /webscripts/reset
 *     
 * These are generally of the form:
 * 
 *     /<system>/<action>
 * 
 * @author muzquiano
 */
public class RemoteController extends AbstractController
{
    private static final String MODE_CACHE = "cache";
    private static final String MODE_CACHE_COMMAND_INVALIDATE = "invalidate";
    
    private static final String MODE_WEBSCRIPTS = "webscripts";
    private static final String MODE_WEBSCRIPTS_COMMAND_RESET = "reset";
    
    /**
     * <p>The <code>FrameworkUtil</code> is needed for resetting WebScripts. It is defined as a Spring Bean and is instantiated
     * and set by the Spring Framework.</p>
     */
    private FrameworkBean frameworkUtil;
        
    /**
     * <p>Setter required by the Spring Framework to set the <code>FrameworkUtil</code> bean used for resetting WebScripts</p>
     * @param frameworkUtil FrameworkBean
     */
    public void setFrameworkUtil(FrameworkBean frameworkUtil)
    {
        this.frameworkUtil = frameworkUtil;
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // get the request context
        RequestContext context = ThreadLocalRequestContext.getRequestContext();
        
        String uri = request.getRequestURI();
        
        // skip server context path and build the path to the resource we are looking for
        uri = uri.substring(request.getContextPath().length());
        
        // validate and return the resource path - stripping the servlet context
        StringTokenizer t = new StringTokenizer(uri, "/");
        String servletName = t.nextToken();
        if (!t.hasMoreTokens())
        {
            throw new ServletException("Invalid URL: " + uri);
        }
        String controller = t.nextToken();
        if (!t.hasMoreTokens())
        {
            throw new ServletException("Invalid URL: " + uri);
        }
        String mode = t.nextToken();
        if (!t.hasMoreTokens())
        {
            throw new ServletException("Invalid URL: " + uri);
        }
        String command = t.nextToken();
        
        // load additional arguments, if any
        ArrayList<String> args = new ArrayList<String>();
        if (t.hasMoreTokens())
        {
            args.add(t.nextToken());            
        }
                
        // CACHE
        if (MODE_CACHE.equals(mode))
        {
            if (MODE_CACHE_COMMAND_INVALIDATE.equals(command))
            {
                // invalidate the model service object cache
                CacheUtil.invalidateModelObjectServiceCache(context);
            }
        }
        
        // WEBSCRIPTS
        if (MODE_WEBSCRIPTS.equals(mode))
        {
            if (MODE_WEBSCRIPTS_COMMAND_RESET.equals(command))
            {
                this.frameworkUtil.resetWebScripts();
            }
        }
        
        return null;
    }
}
