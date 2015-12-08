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
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.resource.ResourceContent;
import org.springframework.extensions.surf.resource.ResourceJSONContent;
import org.springframework.extensions.surf.resource.ResourceXMLContent;
import org.xml.sax.InputSource;

import freemarker.ext.dom.NodeModel;

/**
 * Script wrapper for resource content object.
 * 
 * @author muzquiano
 * @author kevinr
 */
public final class ScriptResourceContent extends ScriptBase
{
    private static Log logger = LogFactory.getLog(ScriptResourceContent.class);
    
    final private ResourceContent resourceContent;
    final private ScriptResource resource;
    
    
    /**
     * Constructor
     * 
     * @param context RequestContext
     * @param resource ScriptResource
     * @param resourceContent ResourceContent
     */
    public ScriptResourceContent(RequestContext context, ScriptResource resource, ResourceContent resourceContent)
    {
        super(context);
        
        this.resourceContent = resourceContent;
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
    
    public ScriptResource getResource()
    {
        return resource;
    }
    
    public String getString()
    {
        String result = null;
        try
        {
            result = this.resourceContent.getStringContent();
        }
        catch (IOException ioe)
        {
            logger.error(ioe);
        }
        return result;
    }
    
    public String getXml()
    {
        String xml = null;
        
        if (resourceContent instanceof ResourceXMLContent)
        {
            try
            {
                xml = ((ResourceXMLContent)resourceContent).getXml();
            }
            catch (IOException ioe)
            {
                logger.error(ioe);
            }
        }
        
        return xml;
    }
    
    public NodeModel getXmlNodeModel()
    {
        NodeModel nodeModel = null;
        
        try
        {
            nodeModel = NodeModel.parse(new InputSource(new StringReader(getXml())));
        }
        catch (Throwable err)
        {
            logger.error(err);
        }
        
        return nodeModel;
    }
    
    public String getJson()
    {
        String jsonString = null;
        
        if (resourceContent instanceof ResourceJSONContent)
        {
            try
            {
                jsonString = ((ResourceJSONContent)resourceContent).getJSONString();
            }
            catch (IOException ioe)
            {
                logger.error(ioe);
            }
        }
        
        return jsonString;
    }
}