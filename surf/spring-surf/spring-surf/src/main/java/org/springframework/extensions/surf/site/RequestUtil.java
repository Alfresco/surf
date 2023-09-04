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

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for constructing and binding RequestContext objects.
 * 
 * @author muzquiano
 * @author kevinr
 */
public class RequestUtil
{
    /**
     * Performs a servlet include.  This is the principal means
     * for handling any kind of JSP include that occurs within the framework.
     * 
     * With this method, all dispatch path referencing is relative 
     * to the request.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param dispatchPath String
     * @throws ServletException
     * @throws IOException
     */
    public static void include(HttpServletRequest request,
            HttpServletResponse response, String dispatchPath)
            throws ServletException, IOException
    {
        request.getRequestDispatcher(dispatchPath).include(request, response);
    }

    /**
     * Performs a servlet include.  This is the principal means
     * for handling any kind of JSP include that occurs within the framework.
     * 
     * With this method, all dispatch path referencing is relative 
     * to the servlet context.
     * 
     * @param context ServletContext
     * @param request ServletContext
     * @param response ServletRequest
     * @param dispatchPath String
     *
     * @throws ServletException
     * @throws IOException
     */
    public static void include(ServletContext context, ServletRequest request,
            ServletResponse response, String dispatchPath)
            throws ServletException, IOException
    {
        RequestDispatcher disp = context.getRequestDispatcher(dispatchPath);
        disp.include(request, response);
    }

    /**
     * Performs a servlet forward.  This is the principal means
     * for handling any kind of JSP include that occurs within the framework.
     * 
     * With this method, all dispatch path referencing is relative 
     * to the request.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param dispatchPath String
     * @throws ServletException
     */    
    public static void forward(HttpServletRequest request,
            HttpServletResponse response, String dispatchPath)
            throws ServletException
    {
        try
        {
            request.getRequestDispatcher(dispatchPath).forward(request, response);
        }
        catch (Exception ex)
        {
            throw new ServletException(ex);
        }
    }

    /**
     * Performs a servlet forward.  This is the principal means
     * for handling any kind of JSP include that occurs within the framework.
     * 
     * With this method, all dispatch path referencing is relative 
     * to the servlet context.
     * 
     * @param context ServletContext
     * @param request ServletRequest
     * @param response ServletResponse
     * @param dispatchPath String
     * @throws ServletException
     */    
    public static void forward(ServletContext context, ServletRequest request,
            ServletResponse response, String dispatchPath)
            throws ServletException
    {
        try
        {
            RequestDispatcher disp = context.getRequestDispatcher(dispatchPath);
            disp.forward(request, response);
        }
        catch (Exception ex)
        {
            throw new ServletException(ex);
        }
    }
}
