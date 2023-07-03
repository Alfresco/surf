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
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;

/**
 * Store implementation which points to web application root
 * 
 * @author muzquiano
 */
public class WebApplicationStore extends ResourceStore implements ServletContextAware
{
    private static Log logger = LogFactory.getLog(WebApplicationStore.class);
    
    private static String[] forbiddenPaths = new String[] {
        "/classes",
        "/WEB-INF/classes",
        "/lib",
        "/WEB-INF/lib"
    };

    private ServletContext servletContext;

    /* (non-Javadoc)
     * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
     */
    public void setServletContext(ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }
    
    /**
     * Retrieves the servlet context
     * 
     * @return ServletContext
     */
    protected ServletContext getServletContext()
    {
        return servletContext;
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ResourceStore#getResourceResolver()
     */
    public PathMatchingResourcePatternResolver getResourceResolver()
    {
        if (this.resolver == null)
        {
            this.resolver = new ServletContextResourcePatternResolver(getServletContext());
        }
        
        return this.resolver;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ResourceStore#isForbidden(java.lang.String)
     */
    protected boolean isForbidden(String documentPath)
    {
        boolean forbidden = false;
        
        // normalize path structure
        documentPath = documentPath.replace("\\", "/");
        
        if (!documentPath.startsWith("/"))
        {
            documentPath = "/" + documentPath;
        }
        if (documentPath.endsWith("/"))
        {
            documentPath = documentPath.substring(0, documentPath.length() - 1);
        }
        
        // look for baddies
        for (String forbiddenPath : forbiddenPaths)
        {
            if (documentPath.startsWith(forbiddenPath))
            {
                forbidden = true;
            }
        }
        
        return forbidden;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ResourceStore#isSecure()
     */
    public boolean isSecure()
    {
        return true;
    }    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "webapp:" + getBasePath();
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.AbstractStore#isReadOnly()
     */
    public boolean isReadOnly()
    {
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.ResourceStore#matchDocumentPaths(java.lang.String)
     */
    protected List<String> matchDocumentPaths(String documentPathPattern)
        throws IOException
    {
        // all of the matching resource objects
        Resource[] resources = getDocumentResources(documentPathPattern);
        
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < resources.length; i++)
        {
            String resourcePath = ((ServletContextResource)resources[i]).getPathWithinContext();
            
            if (resourcePath.startsWith(getRoot()))
            {
                String documentPath = toDocumentPath(resourcePath);
                if (documentPath != null)
                {
                    if (!isForbidden(documentPath))
                    {
                        list.add(documentPath);
                    }
                }
            }
        }
        
        return list;
    }
}
