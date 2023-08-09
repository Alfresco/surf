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

package org.springframework.extensions.surf;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.extensions.surf.exception.UserFactoryException;
import org.springframework.extensions.webscripts.connector.User;

/**
 * Defines the user factory interface
 * 
 * @author muzquiano
 */
public interface UserFactory
{
    /** Guest user name key*/
    public static final String USER_GUEST = "guest";
    
    /** User object key in the session */
    public static String SESSION_ATTRIBUTE_KEY_USER_OBJECT = "_alf_USER_OBJECT";
    
    /** User name id key in the session */
    public static String SESSION_ATTRIBUTE_KEY_USER_ID = "_alf_USER_ID";
    
    /** flag to set in the user Session when an external authentication mechanism is used
     *  this informs the framework that user cannot Change Password or Logout in the usual way */
    public static final String SESSION_ATTRIBUTE_EXTERNAL_AUTH= "_alfExternalAuth";

    /** flag to set in the user Session when AIMS authentication mechanism is used
     *  this informs the framework that user cannot Change Password or Logout in the usual way
     *  and preserve the original SESSION_ATTRIBUTE_EXTERNAL_AUTH (used for Kerberos, Ldap,etc)*/
    public static final String SESSION_ATTRIBUTE_EXTERNAL_AUTH_AIMS= "_alfExternalAuthAIMS";
    
    /**
     * Authenticates the given user credentials against the user provider
     * 
     * @param request HttpServletRequest
     * @param username String
     * @param password String
     * @return boolean
     */
    public boolean authenticate(HttpServletRequest request, String username, String password);

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
        throws UserFactoryException;
    
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
        throws UserFactoryException;
    
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
        throws UserFactoryException;
    
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
        throws UserFactoryException;

    /**
     * Loads a user object from the default endpoint.
     * 
     * @param context RequestContext
     * @param userId String
     * @return User
     * @throws UserFactoryException
     */
    public User loadUser(RequestContext context, String userId)
        throws UserFactoryException;

    /**
     * Loads a user object from the given endpoint.
     * 
     * @param context RequestContext
     * @param userId String
     * @param endpointId String
     * @return User
     * @throws UserFactoryException
     */
    public User loadUser(RequestContext context, String userId, String endpointId)
        throws UserFactoryException;
}
