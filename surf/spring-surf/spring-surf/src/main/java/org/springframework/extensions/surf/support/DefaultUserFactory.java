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

import javax.servlet.http.HttpServletRequest;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.UserFactoryException;
import org.springframework.extensions.webscripts.connector.User;

/**
 * The default Web Framework implementation of UserFactory.
 * 
 * This is a very uninteresting user factory which simply returns
 * unauthenticated users.  In other words, all users essentially
 * authenticate which allows a site to run in "guest" mode (NOP users).
 * 
 * @author muzquiano
 */
public class DefaultUserFactory extends AbstractUserFactory
{
    /* (non-Javadoc)
     * @see org.alfresco.web.site.UserFactory#authenticate(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)
     */
    @Override
    public boolean authenticate(HttpServletRequest request, String username, String password)
    {
        return false;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.site.UserFactory#loadUser(org.alfresco.web.site.RequestContext, java.lang.String, java.lang.String)
     */
    @Override
    public User loadUser(RequestContext context, String userId, String endpointId) throws UserFactoryException
    {
        return this.getGuestUser(context);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.site.UserFactory#loadUser(org.alfresco.web.site.RequestContext, java.lang.String)
     */
    @Override
    public User loadUser(RequestContext context, String userId) throws UserFactoryException
    {
        return this.getGuestUser(context);
    }
}