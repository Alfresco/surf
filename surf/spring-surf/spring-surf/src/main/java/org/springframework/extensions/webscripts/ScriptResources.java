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

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.resource.Resource;
import org.springframework.extensions.surf.resource.ResourceProvider;

/**
 * Provides an interface to resources as stored on a model object.
 * 
 * var objectId = resources.get("abc").objectId;
 * var endpointId = resources.get("abc").endpointId;
 * 
 * @author muzquiano
 */
public final class ScriptResources extends ScriptBase
{
    private static final long serialVersionUID = -3378946227712931201L;
    
    final private ModelObject modelObject;
        
    /**
     * Instantiates a new resources object
     * 
     * @param context the request context
     * @param modelObject the model object
     */
    public ScriptResources(RequestContext context, ModelObject modelObject)
    {
        super(context);

        this.modelObject = modelObject;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.AbstractScriptableObject#buildProperties()
     */
    protected ScriptableMap buildProperties()
    {
        if (this.properties == null)
        {
            // construct and add in all of our model object properties
            this.properties = new ScriptableLinkedHashMap<String, Serializable>()
            {
                // For now, leave this as a read-only map
            };
            
            // populate the scriptable map
            String[] resourceNames = this.getNames();
            for (int i = 0; i < resourceNames.length; i++)
            {
                ScriptResource scriptResource = this.get(resourceNames[i]);
                this.properties.put(resourceNames[i], scriptResource);
            }
        }
        
        return this.properties;

    }
    
    
    // --------------------------------------------------------------
    // JavaScript Functions
        
    /**
     * Returns the model object
     * 
     * @return ModelObject
     */
    public ModelObject getModelObject()
    {
        return this.modelObject;
    }
    
    public ScriptResource get(String name)
    {
        ScriptResource scriptResource = null;
        
        if (modelObject instanceof ResourceProvider)
        {
            ResourceProvider provider = (ResourceProvider) modelObject;
            
            // now add
            Resource resource = provider.getResource(name);
            if (resource != null)
            {
                scriptResource = new ScriptResource(context, resource);
            }
        }
        
        return scriptResource;        
    }
    
    public void remove(String name)
    {
        if (modelObject instanceof ResourceProvider)
        {
            ResourceProvider provider = (ResourceProvider) modelObject;                        
            provider.removeResource(name);
            
            // update properties
            this.properties.remove(name);
        }
    }
    
    public ScriptResource add(String name, String resourceId)
    {
        ScriptResource scriptResource = null;
        
        if (modelObject instanceof ResourceProvider)
        {
            ResourceProvider provider = (ResourceProvider) modelObject;                        
            
            // now add
            Resource resource = provider.addResource(name, resourceId);
            if (resource != null)
            {
                scriptResource = new ScriptResource(context, resource);
                this.properties.put(name, scriptResource);
            }
        }
        
        return scriptResource;
    }

    public ScriptResource add(String name, String protocolId, String endpointId, String objectId)
    {
        ScriptResource scriptResource = null;
        
        if (modelObject instanceof ResourceProvider)
        {
            ResourceProvider provider = (ResourceProvider) modelObject;                        
            
            // now add
            Resource resource = provider.addResource(name, protocolId, endpointId, objectId);
            if (resource != null)
            {
                scriptResource = new ScriptResource(context, resource);
                this.properties.put(name, scriptResource);
            }
        }
        
        return scriptResource;
    }
    
    public String[] getNames()
    {
        String[] names = new String[0];
        
        if (modelObject instanceof ResourceProvider)
        {        
            ResourceProvider provider = (ResourceProvider) modelObject;        
            Resource[] array = provider.getResources();
            if (array.length > 0)
            {
                names = new String[array.length];
                
                for (int i = 0; i < array.length; i++)
                {
                    names[i] = array[i].getName();
                }
            }
        }
        
        return names;
    }
}
