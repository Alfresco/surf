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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;

/**
 * An abstract Request Context implementation that is responsible for holding the value
 * of the Request Context for the current thread. It supplies a static instance getter
 * that can be used directly to return the current RequestContext object.
 * 
 * @author Kevin Roast
 */
public abstract class ThreadLocalRequestContext extends BaseFactoryBean implements RequestContext
{
    private static final long serialVersionUID = -5110756572122308893L;

    private static Log logger = LogFactory.getLog(ThreadLocalRequestContext.class);
    
    /** The RequestContext holder for the current thread */
    private static ThreadLocal<RequestContext> instance = new ThreadLocal<RequestContext>();
    
    /**
     * Override the default constructor to set the RequestContext value for the current thread
     */
    protected ThreadLocalRequestContext(WebFrameworkServiceRegistry serviceRegistry)
    {
        super(serviceRegistry);
        
        if (logger.isDebugEnabled())
            logger.debug("Setting RequestContext " + this.getId() + " on thread: " + Thread.currentThread().getName());
        instance.set(this);
    }
    
    /**
     * Instance getter to return the RequestContext for the current thread
     * 
     * @return RequestContext
     */
    public static RequestContext getRequestContext()
    {
        return instance.get();
    }

    /**
     * Release resources
     */
    public void release()
    {
        if (logger.isDebugEnabled())
            logger.debug("Releasing RequestContext " + this.getId() + " from thread: " + Thread.currentThread().getName());
        instance.remove();
    }
}