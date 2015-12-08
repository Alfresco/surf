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

import org.springframework.extensions.surf.exception.RequestContextException;
import org.springframework.web.context.request.WebRequest;

/**
 * Interface for a RequestContext factory.
 * <p>
 * A request context factory is invoked by the framework at the start of the
 * request chain.  It is responsible for producing a RequestContext object
 * which is bound to the request.  The RequestContext object is a single
 * object instance with which all downstream framework elements can consult.
 * <p>
 * The RequestContext object is scoped to the request.
 * 
 * @author muzquiano
 */
public interface RequestContextFactory
{
    /**
     * Indicates whether the request context factory can produce a request context
     * for the given request object.
     * 
     * @param webRequest web request
     * 
     * @return boolean
     */
    public boolean canHandle(WebRequest webRequest);
    
    /**
     * Produces a new RequestContext instance for a given request. Always returns
     * a new RequestContext instance - or an exception is thrown.
     * 
     * @param webRequest the web request object
     * 
     * @return The RequestContext instance
     * @throws RequestContextException
     */
    public RequestContext newInstance(WebRequest webRequest) throws RequestContextException;
}