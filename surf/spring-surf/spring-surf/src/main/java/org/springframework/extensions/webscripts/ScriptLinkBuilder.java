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

import java.util.Map;

import org.springframework.extensions.surf.RequestContext;

public final class ScriptLinkBuilder extends ScriptBase
{
    /**
     * Constructs a new ScriptLinkBuilder object.
     * 
     * @param context   The request context instance for the current request
     */
    public ScriptLinkBuilder(RequestContext context)
    {
        super(context);
    }
        
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebFrameworkScriptBase#buildProperties()
     */
    protected ScriptableMap buildProperties()
    {
        if (this.properties == null)
        {
            this.properties = new ScriptableWrappedMap(context.getValuesMap());
        }
        
        return this.properties;
    }

    
    // --------------------------------------------------------------
    // JavaScript Properties
        
    /**
     * Constructs a link to a given page instance.
     * This will automatically use the default format.
     * 
     * @param pageId The id of the page instance
     */
    public String page(String pageId)
    {
        return context.getLinkBuilder().page(context, pageId);
    }

    /**
     * Constructs a link to a given page for a given format.
     * 
     * @param pageId The id of the page instance
     * @param formatId The id of the format to render
     */
    public String page(String pageId, String formatId)
    {
        return context.getLinkBuilder().page(context, pageId, formatId);
    }

    /**
     * Constructs a link to a given page for a given format.
     * The provided object is passed in as context.
     * 
     * @param pageId The id of the page instance
     * @param formatId The id of the format to render
     * @param objectId The id of the object
     */
    public String page(String pageId, String formatId, String objectId)
    {
        return context.getLinkBuilder().page(context, pageId, formatId, objectId);      
    }

    /**
     * Constructs a link to a given page for a given format.
     * The provided object is passed in as context.
     * The provided parameters are appended to the URL.
     * 
     * @param pageId The id of the page instance
     * @param formatId The id of the format to render
     * @param objectId The id of the object
     * @param params A map of name/value pairs to be appended to the URL
     */
    public String page(RequestContext context, String pageId, 
            String formatId, String objectId, Map<String, String> params)
    {
        return context.getLinkBuilder().page(context, pageId, formatId, objectId, params);      
    }

    /**
     * Constructs a link to a given page for the default format.
     * The provided parameters are appended to the URL.
     * 
     * @param pageId The id of the page instance
     * @param params A map of name/value pairs to be appended to the URL
     */
    public String page(RequestContext context, String pageId, Map<String, String> params)
    {
        return context.getLinkBuilder().page(context, pageId, null, null, params);      
    }
    
    /**
     * Constructs a link to a given page type.
     * This will automatically use the default format.
     * 
     * @param pageTypeId The type of the page
     */
    public String pageType(String pageTypeId)
    {
        return context.getLinkBuilder().pageType(context, pageTypeId);      
    }

    /**
     * Constructs a link to a given page type for a given format.
     * 
     * @param pageTypeId The type of the page
     * @param formatId The id of the format to render
     */    
    public String pageType(String pageTypeId, String formatId)
    {
        return context.getLinkBuilder().pageType(context, pageTypeId, formatId);
    }

    /**
     * Constructs a link to a given page type for a given format.
     * The provided object is passed in as context.
     * 
     * @param pageTypeId The type of the page
     * @param formatId The id of the format to render
     * @param objectId The id of the object
     */    
    public String pageType(String pageTypeId, String formatId, String objectId)
    {
        return context.getLinkBuilder().pageType(context, pageTypeId, formatId, objectId);      
    }

    /**
     * Constructs a link to a given page type for a given format.
     * The provided object is passed in as context.
     * The provided parameters are appended to the URL.
     * 
     * @param pageTypeId The type of the page
     * @param formatId The id of the format to render
     * @param objectId The id of the object
     * @param params A map of name/value pairs to be appended to the URL
     */
    public String pageType(String pageTypeId, 
            String formatId, String objectId, Map<String, String> params)
    {
        return context.getLinkBuilder().pageType(context, pageTypeId, formatId, objectId, params);      
    }
    
    /**
     * Constructs a link to a given object.
     * This will automatically use the default format.
     * 
     * @param objectId The id of the object
     */    
    public String object(String objectId)
    {
        return context.getLinkBuilder().object(context, objectId);      
    }

    /**
     * Constructs a link to a given object.
     * This will automatically use the default format.
     * 
     * @param objectId The id of the object
     * @param formatId The id of the format to render
     */        
    public String object(String objectId, String formatId)
    {
        return context.getLinkBuilder().object(context, objectId, formatId);        
    }

    /**
     * Constructs a link to a given object.
     * The provided object is passed in as context.
     * The provided parameters are appended to the URL.
     * 
     * @param objectId The id of the object
     * @param formatId The id of the format to render
     * @param params A map of name/value pairs to be appended to the URL
     */    
    public String object(String objectId, String formatId, Map<String, String> params)
    {
        return context.getLinkBuilder().object(context, objectId, formatId, params);
    }
    
    /**
     * Constructs a link to a resources at a given relative uri.
     * 
     * @param uri String
     * 
     * @return the 
     */
    public String resource(String uri)
    {
        return context.getLinkBuilder().resource(context, uri);        
    }
    
}