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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.extensions.surf.site.AuthenticationUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import org.springframework.extensions.surf.util.UserInputValidator;

/**
 * Listen for call from a client to log the user out from the current session.
 * 
 * @author kevinr
 * @author muzquiano
 */
public class LogoutController extends AbstractController
{
    public static final String REDIRECT_URL_PARAMETER = "redirectURL";
    public static final String REDIRECT_URL_PARAMETER_QUERY_KEY = "redirectURLQueryKey";
    public static final String REDIRECT_URL_PARAMETER_QUERY_VALUE = "redirectURLQueryValue";
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.mvc.AbstractController#createModelAndView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        AuthenticationUtil.logout(request, response);
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // Check for a redirect URL - this should only be used when login is not required...
        String redirectURL = request.getParameter(REDIRECT_URL_PARAMETER);
        if (redirectURL != null)
        {
            String[] keys = request.getParameterValues(REDIRECT_URL_PARAMETER_QUERY_KEY);
            String[] values = request.getParameterValues(REDIRECT_URL_PARAMETER_QUERY_VALUE);
            
            if (keys != null && 
                values != null && 
                keys.length > 0 && 
                keys.length == values.length)
            {
                for (int i=0; i<keys.length; i++)
                {
                    String delim = (i == 0) ? "?" : "&";
                    redirectURL = redirectURL + delim + keys[i] + "=" + values[i];
                }
            }
            //response.setHeader("Location", redirectURL);
            // MNT-20202:  Add a header to the response after ensuring that there are no encoded or illegal characters in the name and name and value
            // LM_2019-01-30
            ESAPI.httpUtilities().addHeader(response, "Location", redirectURL);
        }
        else
        {
            // redirect to the root of the website
            response.setHeader("Location", request.getContextPath());
        }
        
        return null;
    }
}