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

package org.springframework.extensions.surf.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.UserFactory;
import org.springframework.extensions.surf.exception.UserFactoryException;
import org.springframework.extensions.webscripts.connector.User;

/**
 * Abstract base class for UserFactory implementations.  This
 * is provided as a convenience to developers who wish to build their
 * own custom UserFactory variations.
 * 
 * @author muzquiano
 * @author Kevin Roast
 */
public abstract class AbstractUserFactory extends BaseFactory implements UserFactory
{
    /** Guest user cache (no sync required - multiple instance creation will not cause an issue) */
    private User guestUser = null;
    
    /**
     * Retrieve the special "Guest" user instance.
     * 
     * @param context RequestContext
     * 
     * @return Guest User
     * 
     * @throws UserFactoryException
     */
    protected User getGuestUser(RequestContext context) throws UserFactoryException
    {
        if (this.guestUser == null)
        {
            Map<String, Boolean> capabilities = new HashMap<String, Boolean>(4);
            capabilities.put(User.CAPABILITY_ADMIN, false);
            capabilities.put(User.CAPABILITY_GUEST, true);
            capabilities.put(User.CAPABILITY_MUTABLE, false);
            User user = new User(USER_GUEST, capabilities);
            user.setFirstName("Guest");
            
            this.guestUser = user;
        }
        return this.guestUser;
    }
    
    /**
     * Loads a user from the remote user store and store it into the session.
     * 
     * @param context RequestContext
     * @param request HttpServletRequest
     * 
     * @return User
     * 
     * @throws UserFactoryException
     */
    public User initialiseUser(RequestContext context, HttpServletRequest request)
        throws UserFactoryException
    {
        return initialiseUser(context, request, false);
    }
    
    /**
     * Loads a user from the remote user store and store it into the session.
     * 
     * @param context RequestContext
     * @param request HttpServletRequest
     * @param endpoint String
     *
     * @return User
     * 
     * @throws UserFactoryException
     */
    public User initialiseUser(RequestContext context, HttpServletRequest request, String endpoint)
        throws UserFactoryException
    {
        return initialiseUser(context, request, endpoint, false);
    }
    
    /**
     * Loads a user from the remote user store and stores it into the session.
     * 
     * If the force flag is set, the current in-session user
     * object will be purged, forcing the user object to reload.
     * 
     * @param context RequestContext
     * @param request HttpServletRequest
     * @param force boolean
     * 
     * @return User
     * 
     * @throws UserFactoryException
     */
    public User initialiseUser(RequestContext context, HttpServletRequest request, boolean force)
        throws UserFactoryException
    {
        return initialiseUser(context, request, null, force);
    }
    
    /**
     * Loads a user from the remote user store and stores it into the session.
     * 
     * If the force flag is set, the current in-session user
     * object will be purged, forcing the user object to reload.
     * 
     * @param context RequestContext
     * @param request HttpServletRequest
     * @param endpoint String
     * @param force boolean
     * 
     * @return User
     * 
     * @throws UserFactoryException
     */
    public User initialiseUser(RequestContext context, HttpServletRequest request, String endpoint, boolean force)
        throws UserFactoryException
    {
        User user = null;
        HttpSession session = request.getSession(false);
        
        // do we want to force a user fault?
        if (force && session != null)
        {
            // remove the user object from session
            session.removeAttribute(SESSION_ATTRIBUTE_KEY_USER_OBJECT);
        }
        
        // check whether there is a user id marker in the session
        String userId = null;
        if (session != null)
        {
            userId = (String)session.getAttribute(SESSION_ATTRIBUTE_KEY_USER_ID);
        }
        
        // support AppServer based SSO
        boolean externalAuth = false;
        if (userId == null)
        {
            userId = request.getRemoteUser();
            externalAuth = (userId != null);
        }

        if (userId != null)
        {
            try
            {
                if (session == null)
                {
                    // we have a userId for login - ensure we have a session
                    // up till now we avoided session creation so it only occurs -after- login
                    session = request.getSession();
                }
                
                // check whether there is a user object loaded already
                user = (User)session.getAttribute(SESSION_ATTRIBUTE_KEY_USER_OBJECT);
                if (user == null)
                {
                    // load the user from whatever store...
                    user = loadUser(context, userId, endpoint);
                    
                    // if we got the user, set onto session
                    if (user != null)
                    {
                        session.setAttribute(SESSION_ATTRIBUTE_KEY_USER_OBJECT, user);
                        
                        // update the user ID - as the case may be different than used on the login dialog
                        session.setAttribute(SESSION_ATTRIBUTE_KEY_USER_ID, user.getId());
                    }
                    else
                    {
                        // unable to load the user
                        session.removeAttribute(SESSION_ATTRIBUTE_KEY_USER_OBJECT);                	
                    }
                }
            }
            catch (UserFactoryException e)
            {
                if (externalAuth)
                {
                    // Allow for login page failover for an invalid external user ID
                    userId = null;
                }
                else
                {
                    throw e;
                }
            }
        }
        
        // return the guest user
        if (user == null)
        {
            user = getGuestUser(context);
        }
        
        return user;
    }
    
    /**
     * Load the user from a store
     * 
     * @param context RequestContext
     * @param userId String
     * 
     * @return User
     * 
     * @throws UserFactoryException
     */
    public abstract User loadUser(RequestContext context, String userId)
        throws UserFactoryException;
    
    /**
     * Load the user from a store
     * 
     * @param context RequestContext
     * @param userId String
     * @param endpointId String
     * 
     * @return User
     * 
     * @throws UserFactoryException
     */
    public abstract User loadUser(RequestContext context, String userId, String endpointId)
        throws UserFactoryException;
    
    /**
     * Authentication the user given the supplied username/password
     * 
     * @param request HttpServletRequest
     * @param username String
     * @param password String
     * 
     * @return success/failure
     */
    public abstract boolean authenticate(HttpServletRequest request, String username, String password);
}
