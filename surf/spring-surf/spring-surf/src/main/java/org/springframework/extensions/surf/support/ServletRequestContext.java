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

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.LinkBuilder;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;

/**
 * Servlet implementation of a request context object for Surf.
 * 
 * @see AbstractRequestContext
 * @see RequestContext
 * 
 * @author muzquiano
 * @author kevinr
 * @author David Draper
 */
public class ServletRequestContext extends AbstractRequestContext
{    
    private static final long serialVersionUID = 2855264613512964578L;

    private LinkBuilder linkBuilder = null;

    /**
     * <p>Constructor for default servlet container request context</p>
     * 
     * @param serviceRegistry WebFrameworkServiceRegistry
     * @param frameworkBean FrameworkBean
     * @param linkBuilder LinkBuilder
     */
    public ServletRequestContext(WebFrameworkServiceRegistry serviceRegistry, 
                                 FrameworkBean frameworkBean, 
                                 LinkBuilder linkBuilder)
    {
        super(serviceRegistry, frameworkBean);
        this.linkBuilder = linkBuilder;
    }
    
    /**
     * <p>Constructor for default servlet container request context.</p>
     * 
     * @param serviceRegistry WebFrameworkServiceRegistry
     * @param linkBuilder LinkBuilder
     * @deprecated
     */
    public ServletRequestContext(WebFrameworkServiceRegistry serviceRegistry, LinkBuilder linkBuilder)
    {
        super(serviceRegistry);
        this.linkBuilder = linkBuilder;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.support.AbstractRequestContext#getLinkBuilder()
     */
    public LinkBuilder getLinkBuilder()    
    {
        return linkBuilder;
    }

    public boolean isExtensibilitySuppressed()
    {
        return false;
    }
}