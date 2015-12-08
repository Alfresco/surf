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

package org.springframework.extensions.webscripts;

import java.io.Serializable;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;

/**
 * Base class for all Web Framework Root-Scope and Script Model objects
 * 
 * This class can accept a binding to a RenderContext (for convenience).
 * The RenderContext object is the primary interface to the request
 * for the Java API.
 * 
 * @author muzquiano
 */
public abstract class ScriptBase implements Serializable
{
    final protected RequestContext context;
    protected ScriptableMap<String, Serializable> properties;

    /**
     * Instantiates a new web framework script base
     * 
     * @param context the context
     */
    public ScriptBase(RequestContext context)
    {
        // store a reference to the request context
        this.context = context;
    }
    
    public ScriptBase()
    {
        super();
        this.context = null;
    }

    /**
     * Gets the request context.
     * 
     * @return the request context
     */
    final public RequestContext getRequestContext()
    {
        return context;
    }
    
    /**
     * Gets the model object service
     * 
     * @return model object service
     */
    protected ModelObjectService getObjectService()
    {
        return context.getObjectService();
    }
    
    /**
     * Retrieves a model object from the underlying store and hands it back
     * wrapped as a ScriptModelObject.  If the model object cannot be found,
     * null will be returned.
     * 
     * @param objectTypeId the object type id
     * @param objectId the object id
     *
     * @return the script model object
     */
    final public ScriptModelObject getObject(String objectTypeId, String objectId)
    {
        return ScriptHelper.getObject(getRequestContext(), objectTypeId, objectId);
    }        
    
    public ScriptableMap<String, Serializable> getProperties()
    {
        if (this.properties == null)
        {
            this.properties = buildProperties();
        }
        
        return this.properties;
    }
    
    final public WebFrameworkConfigElement getConfig()
    {
        return FrameworkBean.getConfig();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        if (getProperties() != null)
        {
            return getProperties().toString();
        }
        else
        {
            return this.context.toString();
        }
    }
    
    protected abstract ScriptableMap<String, Serializable> buildProperties();
}
