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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.util.WebUtil;

/**
 * Class to represent the template model for a URL.
 * 
 * @author Kevin Roast
 */
public class DefaultURLHelper implements Serializable, URLHelper
{
    private static final long serialVersionUID = -966485798540601444L;

    private final String context;
    private final String pageContext;
    private final String uri;
    private final String queryString;
    private final Map<String, String> args;
    private final Map<String, String> templateArgs = new HashMap<String, String>(4, 1.0f);

    /**
     * Construction
     * 
     * @param context   Request Context to build URL model helper from
     */
    @SuppressWarnings("unchecked")
    public DefaultURLHelper(RequestContext context)
    {
        this.context = context.getContextPath();
        this.uri = context.getUri();
        
        String uriNoContext = context.getUri().substring(this.context.length());
        StringTokenizer t = new StringTokenizer(uriNoContext, "/");
        if (t.hasMoreTokens())
        {
            this.pageContext = this.context + "/" + t.nextToken();
        }
        else
        {
            this.pageContext = this.context;
        }
        
        this.queryString = WebUtil.getQueryStringForMap(context.getParameters());
        
        this.args = Collections.unmodifiableMap((HashMap<String, String>)((HashMap<String, String>)context.getParameters()).clone());
    }
    
    /**
     * Construction
     * 
     * @param context   Request Context to build URL model helper from
     */
    public DefaultURLHelper(RequestContext context, Map<String, String> templateArgs)
    {
        this(context);
        if (templateArgs != null)
        {
            this.templateArgs.putAll(templateArgs);
        }
    }
    
    public String getContext()
    {
        return context;
    }

    public String getServletContext()
    {
        return pageContext;
    }

    public String getUri()
    {
        return uri;
    }

    public String getUrl()
    {
        return uri + (this.queryString.length() != 0 ? ("?" + this.queryString) : "");
    }

    public String getQueryString()
    {
        return this.queryString;
    }
    
    public Map<String, String> getArgs()
    {
        return this.args;
    }
    
    public Map<String, String> getTemplateArgs()
    {
        return Collections.unmodifiableMap(this.templateArgs);
    }
}
