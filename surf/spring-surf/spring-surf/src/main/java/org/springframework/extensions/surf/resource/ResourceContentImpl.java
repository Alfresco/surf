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

package org.springframework.extensions.surf.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.ConnectorServiceException;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.connector.Connector;
import org.springframework.extensions.webscripts.connector.ConnectorContext;
import org.springframework.extensions.webscripts.connector.HttpMethod;
import org.springframework.extensions.webscripts.connector.Response;

/**
 * Base resource content implementation
 * 
 * @author muzquiano
 * @author kevinr
 */
public class ResourceContentImpl implements ResourceContent
{
    final protected Resource resource;
    final protected String url;
    
    protected FrameworkBean frameworkUtil;
    
    /**
     * Constructor
     * 
     * @param resource Resource
     * @param url String
     * @param frameworkUtil FrameworkBean
     */
    public ResourceContentImpl(Resource resource, String url, FrameworkBean frameworkUtil)
    {
        this.resource = resource;
        this.url = url;
        this.frameworkUtil = frameworkUtil;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.web.site.Content#getResource()
     */
    public Resource getResource()
    {
        return this.resource;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getReader()
     */
    public Reader getReader() throws IOException
    {
        Response response = getResponse();
        return new StringReader(response.getText());
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getInputStream()
     */
    public InputStream getInputStream() 
        throws IOException
    {
        Response response = getResponse();
        return response != null ? response.getResponseStream() : null;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.resource.ResourceContent#getStringContent()
     */
    public String getStringContent() throws IOException
    {
        Response response = getResponse();
        return response != null ? response.getText() : null;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.ResourceContent#getBytes()
     */
    public byte[] getBytes()
        throws IOException
    {
        Response response = getResponse();
        return response != null ? response.getText().getBytes(response.getEncoding()) : null;
    }
    
    
    /**
     * @return response object for this resource wrapper
     * @throws IOException
     */
    private Response getResponse() 
        throws IOException
    {
        Response response = null;
        
        String endpoint = resource.getEndpointId();
        
        // if there isn't an endpoint specified, we can try to use the http endpoint
        if (endpoint == null)
        {
            endpoint = "http";
        }
        
        Connector connector = null;
        
        // get the current request context
        RequestContext context = ThreadLocalRequestContext.getRequestContext();
        try
        {
            if (context == null)
            {
                connector = frameworkUtil.getConnector(endpoint);
            }
            else
            {
                connector = frameworkUtil.getConnector(context, endpoint);
            }
        }
        catch (ConnectorServiceException cse)
        {
            throw new IOException("Unable to obtain connector to endpoint: " + endpoint);
        }
        
        if (connector != null)
        {
            ConnectorContext connectorContext = new ConnectorContext();
            connectorContext.setMethod(HttpMethod.GET);
            
            response = connector.call(this.url, connectorContext);
        }
        
        return response;
    }
}
