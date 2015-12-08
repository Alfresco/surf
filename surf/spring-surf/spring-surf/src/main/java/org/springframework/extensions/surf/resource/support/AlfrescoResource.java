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
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.resource.AbstractResource;
import org.springframework.extensions.surf.resource.ResourceContent;
import org.springframework.extensions.surf.resource.ResourceContentImpl;
import org.springframework.extensions.surf.resource.ResourceJSONContent;
import org.springframework.extensions.surf.resource.ResourceJSONContentImpl;

/**
 * Alfresco resource
 * 
 * Content = heavy asset
 * Metadata = Alfresco information
 * 
 * Object ids are of the following format:
 *
 *    alfresco://<endpointId>/<objectId>
 *    alfresco://<endpointId>/workspace/SpacesStore/39627322-c892-49fe-bcbb-a5c72bdc2a9b
 *    
 *    where <objectId> is <repoProtocol>/<repoStore>/<repoNodeRefId>
 *    
 * Example:
 *    
 *    alfresco://<endpointId>/workspace/SpacesStore/790ccbc3-a3ee-45ba-b169-0926ad77c2c8 
 *     
 * @author muzquiano
 */
public class AlfrescoResource extends AbstractResource
{
    private static Log logger = LogFactory.getLog(AlfrescoResource.class);
    
    private String objectTypeId = null;
    
    public AlfrescoResource(String protocolId, String endpointId, String objectId, FrameworkBean frameworkUtil)
    {
        super(protocolId, endpointId, objectId, frameworkUtil);
        if (objectId.indexOf('/') == -1)
        {
            throw new IllegalArgumentException(
                    "Resource URL of the format <protocolId>://<endpointId>/<objectId> for example: " +
                    "alfresco://alfresco-admin/workspace/SpacesStore/1234567890");
        }
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getMetadata()
     */
    public ResourceContent getMetadata() throws IOException
    {
        return new ResourceJSONContentImpl(this, getMetadataURL(), frameworkUtil);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getMetadataURL()
     */
    public String getMetadataURL()
    {
        String metadataURL = "/webframework/content/metadata";
        
        if (getObjectId() != null)
        {
            metadataURL += "?id=" + this.getNodeRefId();
        }
        
        if (logger.isDebugEnabled())
            logger.debug("Alfresco Resource metadata URL: " + metadataURL);
        
        return metadataURL;
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
        String contentURL = null;
        
        if (getObjectId() != null)
        {
            contentURL = "/api/node/" + getObjectId() + "/content";
        }
        
        if (logger.isDebugEnabled())
            logger.debug("Alfresco Resource content URL: " + contentURL);
        
        return contentURL;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.Resource#getObjectTypeId()
     */
    public synchronized String getObjectTypeId()
    {
        if (objectTypeId == null)
        {
            JSONObject json = null;
            
            try
            {
                json = ((ResourceJSONContent) getMetadata()).getJSON();
                objectTypeId = json.getString("type");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return objectTypeId;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.AbstractResource#isContainer()
     */
    public boolean isContainer()
    {
        boolean isContainer = false;
        
        try
        {
            ResourceJSONContent jsonContent = (ResourceJSONContent) getMetadata();
            isContainer = jsonContent.getJSON().getBoolean("isContainer");
        }
        catch (Exception e) { }
        
        return isContainer;
    }
    
    
    /*
     * Gets the node ref version of the object id
     */
    protected String getNodeRefId()
    {
        String nodeRefId = null;
        
        String objectId = getObjectId();
        if (objectId != null)
        {        
            String workspaceId = null;
            String storeId = null;
            String nodeId = null;
            
            StringTokenizer tokenizer = new StringTokenizer(objectId, ":/");
            workspaceId = tokenizer.nextToken();
            storeId = tokenizer.nextToken();
            nodeId = tokenizer.nextToken();
            
            nodeRefId = workspaceId + "://" + storeId + "/" + nodeId;
        }
        
        return nodeRefId;
    }
}