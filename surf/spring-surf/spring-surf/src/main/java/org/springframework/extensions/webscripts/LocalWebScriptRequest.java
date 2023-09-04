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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.extensions.config.ServerProperties;
import org.springframework.extensions.surf.ServletUtil;
import org.springframework.extensions.surf.util.Content;

 /**
  * LocalWebScriptRequest represents a locally processed WebScript request object
  * based on a URL.
  * 
  * @author muzquiano
  * @author kevinr
  */
public class LocalWebScriptRequest extends WebScriptRequestURLImpl
{
    final private Map<String, Serializable> parameters;
    final private ServerProperties serverProperties;
    final private LocalWebScriptContext context;
    final private String[] parameterNames;
    
    
    /**
     * Instantiates a new local web script request.
     * 
     * @param runtime the runtime
     * @param scriptUrl the script url
     * @param match the match
     * @param parameters the parameters
     * @param context the web script context
     */
    public LocalWebScriptRequest(Runtime runtime, String scriptUrl,
            Match match, Map<String, Serializable> parameters, ServerProperties serverProps, LocalWebScriptContext context)
    {
        super(runtime, splitURL(context.getRequestContext().getContextPath(), scriptUrl),  match);
        parameters.putAll(queryArgs);
        this.parameters = parameters;
        this.serverProperties = serverProps;
        this.context = context;
        
        // cache parameter names as they are inspected multiple times
        this.parameterNames = this.parameters.keySet().toArray(new String[this.parameters.size()]);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.web.scripts.WebScriptRequest#getParameterNames()
     */
    public String[] getParameterNames()
    {
        return this.parameterNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.web.scripts.WebScriptRequest#getParameter(java.lang.String)
     */
    public String getParameter(String name)
    {
        return (String) this.parameters.get(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.web.scripts.WebScriptRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String name)
    {
        final String[] values = new String[1];
        values[0] = (String) this.parameters.get(name);
        return values;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRequest#getAgent()
     */
    public String getAgent()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRequest#getServerPath()
     */
    public String getServerPath()
    {
        return getServerScheme() + "://" + getServerName() + ":" + getServerPort();
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRequest#getHeaderNames()
     */
    public String[] getHeaderNames()
    {
        return new String[] { };
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRequest#getHeader(java.lang.String)
     */
    public String getHeader(String name)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRequest#getHeaderValues(java.lang.String)
     */
    public String[] getHeaderValues(String name)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRequest#getContent()
     */
    public Content getContent()
    {
        return null;
    }
    
    /**
     * Get Server Scheme
     * 
     * @return  server scheme
     */
    private String getServerScheme()
    {
        HttpServletRequest request = ServletUtil.getRequest();
        
        String scheme = null;
        if (serverProperties != null)
        {
            scheme = serverProperties.getScheme();
        }
        if (scheme == null)
        {
            scheme = request.getScheme();
        }
        return scheme;
    }

    /**
     * Get Server Name
     * 
     * @return  server name
     */
    private String getServerName()
    {
        HttpServletRequest request = ServletUtil.getRequest();
        
        String name = null;
        if (serverProperties != null)
        {
            name = serverProperties.getHostName();
        }
        if (name == null)
        {
            name = request.getServerName();
        }
        return name;
    }

    /**
     * Get Server Port
     * 
     * @return  server name
     */
    private int getServerPort()
    {
        HttpServletRequest request = ServletUtil.getRequest();
        
        Integer port = null;
        if (serverProperties != null)
        {
            port = serverProperties.getPort();
        }
        if (port == null)
        {
            port = request.getServerPort();
        }
        return port;
    }
}
