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

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.RequestContextFactory;
import org.springframework.extensions.surf.exception.RequestContextException;
import org.springframework.web.context.request.WebRequest;

/**
 * Abstract base class for RequestContextFactory implementations.  This
 * is provided as a convenience to developers who wish to build their
 * own custom RequestContextFactory variations.
 * 
 * @author muzquiano
 */
public abstract class AbstractRequestContextFactory extends BaseFactory implements RequestContextFactory
{
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.RequestContextFactory#canHandle(org.springframework.web.context.request.WebRequest)
     */
    public abstract boolean canHandle(WebRequest webRequest);
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.RequestContextFactory#newInstance(org.springframework.web.context.request.WebRequest)
     */
    public abstract RequestContext newInstance(WebRequest webRequest)
        throws RequestContextException;
}
