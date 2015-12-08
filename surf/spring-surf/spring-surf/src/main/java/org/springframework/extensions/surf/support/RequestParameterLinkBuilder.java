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

import java.util.Map;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.resource.ResourceService;
import org.springframework.extensions.surf.util.URLEncoder;

/**
 * <p>A link builder which supports linking to dispatch state via
 * request parameters.
 * </p><p>
 * This provides a very simple, request-parameter driven way of creating
 * URLs.  URLs are created according to the following design:
 * </p>
 * For references to a page:
 * <ul>
 * <li>?p=<{@code}pageId></li>
 * <li>?p=<{@code}pageId>&f=<{@code}formatId></li>
 * </ul>    
 * For references to a page type:
 * <ul>
 * <li>?pt=<{@code}pageTypeId></li>
 * <li>?pt=<{@code}pageTypeId>&f=<{@code}formatId></li>
 * </ul>    
 * For references to an object:
 * <ul>
 * <li>?o=<{@code}objectId></li>
 * <li>?o=<{@code}objectId>&f=<{@code}formatId></li>
 * </ul>
 * 
 * @author muzquiano
 * @author David Draper
 */
public class RequestParameterLinkBuilder extends AbstractLinkBuilder
{
    /**
     * 
     * @param serviceRegistry WebFrameworkServiceRegistry
     * @deprecated Because it relies on the supplied <code>WebFrameworkServiceRegistry</code> to obtain the required services.
     */
    protected RequestParameterLinkBuilder(WebFrameworkServiceRegistry serviceRegistry)
    {
        super(serviceRegistry);
    }

    /**
     * <p>This is the preferred constructor to use when instantiating a <code>RequestParameterLinkBuilder</code> because
     * it allows the services to be set directly.</p>
     * 
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     * @param modelObjectService ModelObjectService
     * @param resourceService ResourceService
     */
    public RequestParameterLinkBuilder(WebFrameworkConfigElement webFrameworkConfigElement, 
                                       ModelObjectService modelObjectService,
                                       ResourceService resourceService)
    {
        super(webFrameworkConfigElement, modelObjectService, resourceService);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.support.AbstractLinkBuilder#page(org.alfresco.web.framework.RequestContext, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
     */
    public String page(RequestContext context, String pageId, 
            String formatId, String objectId, Map<String, String> params)
    {
        if (pageId == null)
        {
            throw new IllegalArgumentException("PageId is mandatory.");
        }
        
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());        
        if (formatId != null)
        {
            buffer.append("?f=" + formatId);
        }
        buffer.append("&p=" + pageId);
        if (objectId != null && objectId.length() != 0)
        {
              buffer.append("&o=" + objectId);
        }
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();                
                buffer.append("&" + key + "=" + URLEncoder.encode(value));
            }
        }
        
        return buffer.toString();
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.support.AbstractLinkBuilder#pageType(org.alfresco.web.framework.RequestContext, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
     */
    public String pageType(RequestContext context, String pageTypeId, 
            String formatId, String objectId, Map<String, String> params)
    {
        if (pageTypeId == null)
        {
            throw new IllegalArgumentException("PageTypeId is mandatory.");
        }
        
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());
        if (formatId != null)
        {
            buffer.append("?f=" + formatId);
        }
        buffer.append("&pt=" + pageTypeId);
        if (objectId != null && objectId.length() != 0)
        {
              buffer.append("&o=" + objectId);
        }
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();                
                buffer.append("&" + key + "=" + URLEncoder.encode(value));
            }
        }
        
        return buffer.toString();
    }    

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.support.AbstractLinkBuilder#object(org.alfresco.web.framework.RequestContext, java.lang.String, java.lang.String, java.util.Map)
     */
    public String object(RequestContext context, String objectId,
            String formatId, Map<String, String> params)
    {
        if (objectId == null)
        {
            throw new IllegalArgumentException("ObjectId is mandatory.");
        }
        
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());
        if (formatId != null)
        {
            buffer.append("?f=" + formatId);
        }
        buffer.append("&o=" + objectId);
        
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue();                
                buffer.append("&" + key + "=" + URLEncoder.encode(value));
            }
        }
        
        return buffer.toString();
    }
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.support.AbstractLinkBuilder#resource(org.springframework.extensions.surf.RequestContext, java.lang.String)
     */
    public String resource(RequestContext context, String uri)
    {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(context.getContextPath());
        buffer.append(uri);
                
        return buffer.toString();        
    }    
}
