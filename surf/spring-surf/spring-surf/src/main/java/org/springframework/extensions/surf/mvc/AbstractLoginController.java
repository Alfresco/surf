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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.UserFactory;
import org.springframework.extensions.surf.site.AuthenticationUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Superclass for login controller implementations, using the Template Method design pattern.
 * 
 * Authenticates the user by using userFactory, and then calls 
 * {@link #onSuccess(HttpServletRequest, HttpServletResponse)} if authentication is successful, or
 * {@link #onFailure(HttpServletRequest, HttpServletResponse)} if authentication fails.
 *    
 * @author muzquiano
 * @author kevinr
 */
public abstract class AbstractLoginController extends AbstractController
{
    /* password parameter */
    protected static final String PARAM_PASSWORD = "password";
    
    /* username parameter */
    protected static final String PARAM_USERNAME = "username";
    
    
    /**
     * <p>A <code>UserFactory</code> is required to authenticate requests. It will be supplied by the Spring Framework
     * providing that the controller is configured correctly - it requires that a "userFactory" is set with an instance
     * of a <code>UserFactory</code>. The <code>ConfigBeanFactory</code> can be used to generate <code>UserFactory</code>
     * Spring Beans</p>
     */
    private UserFactory userFactory;
    private WebFrameworkConfigElement webFrameworkConfiguration;

    /**
     * <p>This method is provided to allow the Spring framework to set a <code>UserFactory</code> required for authenticating
     * requests</p>
     * 
     * @param userFactory UserFactory
     */
    public void setUserFactory(UserFactory userFactory) 
    {
        this.userFactory = userFactory;
    }

    public void setWebFrameworkConfiguration(WebFrameworkConfigElement webFrameworkConfiguration)
    {
        this.webFrameworkConfiguration = webFrameworkConfiguration;
    }


    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        request.setCharacterEncoding("UTF-8");
        
        String username = (String) request.getParameter(AbstractLoginController.PARAM_USERNAME);
        String password = (String) request.getParameter(AbstractLoginController.PARAM_PASSWORD);
        
        boolean success = false;
        try
        {
            // check whether there is already a user logged in
            HttpSession session = request.getSession(false);
            
            // destroy old session and log out the current user
              AuthenticationUtil.logout(request, response);
           
            // see if we can authenticate the user
            boolean authenticated = this.userFactory.authenticate(request, username, password);
            if (authenticated)
            {
                AuthenticationUtil.login(request, response, username, false, webFrameworkConfiguration.isLoginCookiesEnabled());
                
                // mark the fact that we succeeded
                success = true;
            }
        }
        catch (Throwable err)
        {
            throw new ServletException(err);
        }
        
        // If they succeeded in logging in, redirect to the success page
        // Otherwise, redirect to the failure page
        if (success)
        {
            onSuccess(request, response);
        }
        else
        {
            onFailure(request, response);
        }
        
        return null;
    }

    /**
     * Template method. 
     * 
     * Called after failed authentication.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @throws Exception in case of errors
     */
    protected abstract void onFailure(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Template method. 
     * 
     * Called after successful authentication.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @throws Exception in case of errors
     */
    protected abstract void onSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
