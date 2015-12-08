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

package org.springframework.extensions.surf.resource.support;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.resource.AbstractResource;
import org.springframework.extensions.surf.resource.ResourceContent;
import org.springframework.extensions.surf.resource.ResourceContentImpl;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;

/**
 * Virtualized web application resource
 * 
 * Object ids are of the following format:
 * 
 *    images/abc.gif
 *    WEB-INF/config/file.xml
 *    webapp://images/abc.gif
 *    webapp://WEB-INF/config/file.xml 
 * 
 * @author muzquiano
 */
public class VirtualizedWebappResource extends AbstractResource
{
    private static Log logger = LogFactory.getLog(VirtualizedWebappResource.class);
    
    public VirtualizedWebappResource(String protocolId, String endpointId, String objectId, FrameworkBean frameworkUtil)
    {
        super(protocolId, endpointId, objectId, frameworkUtil);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getMetadata()
     */
    public ResourceContent getMetadata() throws IOException
    {
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getMetadataURL()
     */
    public String getMetadataURL()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getContent()
     */
    public ResourceContent getContent() throws IOException
    {
        return new ResourceContentImpl(this, getContentURL(), frameworkUtil);        
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getContentURL()
     */
    public String getContentURL()
    {
        RequestContext context = ThreadLocalRequestContext.getRequestContext();
        
        String contentURL = context.getContextPath() + "/res" + this.getObjectId();
        
        if (logger.isDebugEnabled())
            logger.debug("Formed virtual content url: " + contentURL);
        
        return contentURL;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getObjectTypeId()
     */
    public String getObjectTypeId()
    {
        String extension = null;
        
        // get extension
        int i = getContentURL().lastIndexOf(".");
        if (i > -1)
        {
            extension = getContentURL().substring(i+1);
        }
        
        return extension;        
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getResourceTypeId()
     */
    public String getResourceTypeId()
    {
        return "webapp";        
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.AbstractResource#isContainer()
     */
    public boolean isContainer()
    {
        // TODO: determine whether the resource is a container...
        // assume not
        return false;
    }
    
}
