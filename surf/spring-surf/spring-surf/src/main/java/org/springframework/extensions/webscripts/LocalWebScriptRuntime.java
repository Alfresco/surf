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
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.extensions.config.ServerProperties;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.WebFrameworkConstants;
import org.springframework.extensions.surf.uri.UriUtils;

/**
 * WebScript Runtime for rendering as Web Framework components.
 * 
 * @author kevinr
 * @author muzquiano
 */
public class LocalWebScriptRuntime extends AbstractRuntime
{
    public static final String DEFAULT_METHOD_GET = "GET";
    
    private ServerProperties serverProperties;
    private LocalWebScriptContext context;
    private Writer out;
    private String method;

    
    /**
     * Constructor
     * 
     * @param out Writer
     * @param container RuntimeContainer
     * @param serverProps ServerProperties
     * @param context LocalWebScriptContext
     */
    public LocalWebScriptRuntime(
            Writer out, RuntimeContainer container, ServerProperties serverProps, LocalWebScriptContext context) 
    {
        super(container);
        this.out = out;
        this.serverProperties = serverProps;
        this.context = context;
        this.method = DEFAULT_METHOD_GET;
    }
    
    /**
     * @return context object for this webscript runtime
     */
    public LocalWebScriptContext getLocalContext()
    {
        return context;
    }

    /* (non-Javadoc)
     * @see org.springframework.extensions.webscripts.Runtime#getName()
     */
    public String getName()
    {
        return "SURF Web Framework Runtime";
    }

    @Override
    protected String getScriptUrl()
    {
        return context.getScriptUrl();
    }

    @Override
    protected WebScriptRequest createRequest(Match match)
    {
        // this includes all elements of the xml
        Map<String, Serializable> properties = context.getModelObject().getProperties();
        String scriptUrl = context.getExecuteUrl();
        
        // component ID is always available to the component
        ModelObject modelObject = context.getModelObject();
        properties.put("id", modelObject.getId());
        
        // Merge in the custom and evaluated properties...
        addProperties(properties, modelObject.getCustomProperties());
        addProperties(properties, context.getRequestContext().getEvaluatedProperties());
        
        // add the html binding id
        String htmlBindingId = (String) context.getRequestContext().getValue(WebFrameworkConstants.RENDER_DATA_HTMLID);
        if (htmlBindingId != null)
        {
            properties.put(ProcessorModelHelper.PROP_HTMLID, htmlBindingId);
        }
        
        return new LocalWebScriptRequest(this, scriptUrl, match, properties, serverProperties, context);
    }

    /**
     * <p>Merges one set of supplied properties into another, but performs replacement on the uri tokens.</p>
     * @param base The properties to add to
     * @param toMerge The properties to add
     */
    private void addProperties(Map<String, Serializable> base, Map<String, Serializable> toMerge)
    {
        for (Entry<String, Serializable> prop: toMerge.entrySet())
        {
            base.put(prop.getKey(), UriUtils.replaceTokens((String)prop.getValue(), context.getRequestContext(), null, null, ""));
        }
    }
    
    @Override
    protected LocalWebScriptResponse createResponse()
    {
        return new LocalWebScriptResponse(this, context, out);
    }

    @Override
    protected String getScriptMethod()
    {
        return method;
    }

    @Override
    protected Authenticator createAuthenticator()
    {
        return null;
    }

    @Override
    public WebScriptSessionFactory createSessionFactory()
    {
        return null;
    }

    public void setScriptMethod(String method)
    {
        this.method = method;
    }

    /**
     * @see org.springframework.extensions.webscripts.AbstractRuntime#beforeProcessError(org.springframework.extensions.webscripts.Match, java.lang.Throwable)
     * 
     * Override this hook to add special handling for "missing" WebScript components.
     * The page renderer can safely ignore components that no longer map to a URL. It
     * is recommended that that the debug flag is used to view missing webscript URLs.
     */
    @Override
    protected boolean beforeProcessError(Match match, Throwable e)
    {
        if (e instanceof WebScriptException && ((WebScriptException)e).getStatus() == HttpServletResponse.SC_NOT_FOUND)
        {
            // log info on server if we are debugging
            if (logger.isDebugEnabled())
            {
                logger.debug(e.getMessage());
            }
            return false;
        }
        else
        {
            return super.beforeProcessError(match, e);
        }
    }
}
