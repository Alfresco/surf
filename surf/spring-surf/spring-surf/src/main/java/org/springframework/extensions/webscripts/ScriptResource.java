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

import java.io.IOException;
import java.io.Serializable;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.resource.Resource;

/*
 * @author muzquiano
 */
public final class ScriptResource extends ScriptBase
{
    private ScriptResourceContent payloadContent = null;
    private ScriptResourceContent payloadMetadata = null;
    
    final private Resource resource;

    public ScriptResource(RequestContext context, Resource resource)
    {
        super(context);
        
        this.resource = resource;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebFrameworkScriptBase#buildProperties()
     */
    protected ScriptableMap<String, Serializable> buildProperties()
    {
        return null;
    }
    
    
    // --------------------------------------------------------------
    // JavaScript Properties

    public String getId()
    {
        return this.resource.getResourceId();
    }
    
    public String getProtocolId()
    {
        return this.resource.getProtocolId();
    }
    
    public String getEndpointId()
    {
        return this.resource.getEndpointId();
    }
    
    public String getObjectId()
    {
        return this.resource.getObjectId();
    }
    
    public String getObjectTypeId()
    {
        return this.resource.getObjectTypeId();
    }
    
    public String getName()
    {
        return this.resource.getName();
    }
    
    public synchronized ScriptResourceContent getContent()
    {
        if (payloadContent == null)
        {
            try
            {
                payloadContent = new ScriptResourceContent(context, this, resource.getContent());
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        
        return payloadContent;
    }
    
    public String getContentUrl()
    {
        return this.resource.getContentURL();
    }
    
    public synchronized ScriptResourceContent getMetadata()
    {
        if (payloadMetadata == null)
        {
            try
            {
                payloadMetadata = new ScriptResourceContent(context, this, resource.getMetadata());
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        
        return payloadMetadata;
    }
    
    public String getMetadataUrl()
    {
        return this.resource.getMetadataURL();
    }
    
    public boolean getIsContainer()
    {
        return this.resource.isContainer();
    }
}