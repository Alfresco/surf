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

import org.springframework.extensions.surf.uri.UriUtils;
 
/**
 * Responds to Login POSTs to allow the user to authenticate to the application.
 * 
 * @author kevinr
 */
public class LoginController extends AbstractLoginController
{
    protected static final String PARAM_FAILURE = "failure";
    protected static final String PARAM_SUCCESS = "success";

    /**
     * Sends an HTTP redirect response to the success page provided in the request parameters, if present, falling
     * back to root of the web application.
     * 
     * @see org.springframework.extensions.surf.mvc.AbstractLoginController#onSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	@Override
    protected void onSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String successPage = (String) request.getParameter(LoginController.PARAM_SUCCESS);
        if (successPage != null)
        {
            response.sendRedirect(UriUtils.relativeUri(successPage));
        }
        else
        {
            response.sendRedirect(request.getContextPath());
        }
    }

    /**
     * Sends an HTTP redirect response to the failure page provided in the request parameters, if present, falling
     * back to root of the web application.
     * 
     * @see org.springframework.extensions.surf.mvc.AbstractLoginController#onSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	@Override
	protected void onFailure(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
        String failurePage = (String) request.getParameter(LoginController.PARAM_FAILURE);
        
        // Invalidate the session to ensure any session ID cookies are no longer valid
        // as the auth has failed - mitigates session fixation attacks by ensuring that no
        // valid session IDs are created until after a successful user auth attempt
        request.getSession().invalidate();
        if (failurePage != null)
        {
            response.sendRedirect(UriUtils.relativeUri(failurePage));
        }
        else
        {
            response.sendRedirect(request.getContextPath());
        }
	}
 }