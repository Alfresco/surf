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

package org.springframework.extensions.webeditor.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Base class for all Web Editor tag implementations.
 * 
 * @author gavinc
 */
public class AbstractTemplateTag extends TagSupport
{
	public static final long serialVersionUID = 3251970922970982753L;

    protected String urlPrefix = null;
    protected Boolean editingEnabled = null;
    protected Boolean debugEnabled = null;
        
    /**
     * Determines whether editing is currently enabled
     * 
     * @return true if editing is enabled
     */
    protected boolean isEditingEnabled()
    {
        if (this.editingEnabled == null)
        {
            Object enabledKey = this.pageContext.getRequest().getAttribute(TemplateConstants.REQUEST_ATTR_KEY_WEF_ENABLED);
            if (enabledKey != null && enabledKey instanceof Boolean)
            {
                this.editingEnabled = (Boolean) enabledKey;
            }
            else
            {
                // try to load from properties file
                this.editingEnabled = TemplateProps.isEditingEnabled();
                if (this.editingEnabled == null)
                {
                    // set default value
                    this.editingEnabled = Boolean.FALSE;
                }
            }
        }

        return this.editingEnabled;
    }

    /**
     * Returns the URL prefix for the web editor application.
     * 
     * @return The WEF prefix URL
     */
    protected String getWebEditorUrlPrefix()
    {
        if (this.urlPrefix == null)
        {
            String prefix = (String)this.pageContext.getRequest().getAttribute(TemplateConstants.REQUEST_ATTR_KEY_URL_PREFIX);
            if (prefix != null && prefix.length() > 0)
            {
                this.urlPrefix = prefix;
            }
            else
            {
                // try to load from properties file
                this.urlPrefix = TemplateProps.getUrlPrefix();
                if (this.urlPrefix == null)
                {
                    // assume a default
                    this.urlPrefix = ((HttpServletRequest) this.pageContext.getRequest()).getContextPath();
                }
            }
        }

        return this.urlPrefix;
    }

    /**
     * Determines whether debug is enabled for the web editor application
     * <p>
     * This is the value of the <code>debug</code> init
     * parameter of the Web Editor filter definition in web.xml.
     * If the init parameter is not present false will be returned.
     * </p>
     * 
     * @return true if debug is enabled
     */
    protected boolean isDebugEnabled()
    {
        if (this.debugEnabled == null)
        {
            Object debug = this.pageContext.getRequest().getAttribute(TemplateConstants.REQUEST_ATTR_KEY_DEBUG_ENABLED);
            if (debug != null && debug instanceof Boolean)
            {
                this.debugEnabled = (Boolean)debug;
            }
            else
            {
                // load from properties file
                this.debugEnabled = TemplateProps.isDebugEnabled();
                if (this.debugEnabled == null)
                {
                    // set default value
                    this.debugEnabled = Boolean.FALSE;
                }
            }

        }

        return this.debugEnabled.booleanValue();
    }

    /**
     * Determins the location of the toolbar
     * 
     * @return string location
     */
    protected String getToolbarLocation()
    {
        return (String) this.pageContext.getRequest().getAttribute(TemplateConstants.REQUEST_ATTR_KEY_TOOLBAR_LOCATION);
    }
        
    /**
     * Encodes the given string, so that it can be used within an HTML page.
     * 
     * @param string     the String to convert
     */
    protected String encode(String string)
    {
        if (string == null)
        {
            return "";
        }

        StringBuilder sb = null;      // create on demand
        String enc;
        char c;
        for (int i = 0; i < string.length(); i++)
        {
            enc = null;
            c = string.charAt(i);
            switch (c)
            {
                case '"': enc = "&quot;"; break;    //"
                case '&': enc = "&amp;"; break;     //&
                case '<': enc = "&lt;"; break;      //<
                case '>': enc = "&gt;"; break;      //>

                case '\u20AC': enc = "&euro;";  break;
                case '\u00AB': enc = "&laquo;"; break;
                case '\u00BB': enc = "&raquo;"; break;
                case '\u00A0': enc = "&nbsp;"; break;

                default:
                    if (((int)c) >= 0x80)
                    {
                        //encode all non basic latin characters
                        enc = "&#" + ((int)c) + ";";
                    }
                    break;
            }

            if (enc != null)
            {
                if (sb == null)
                {
                    String soFar = string.substring(0, i);
                    sb = new StringBuilder(i + 16);
                    sb.append(soFar);
                }
                sb.append(enc);
            }
            else
            {
                if (sb != null)
                {
                    sb.append(c);
                }
            }
        }

        if (sb == null)
        {
            return string;
        }
        else
        {
            return sb.toString();
        }
    }

    @Override
    public void release()
    {
        super.release();

        this.urlPrefix = null;
        this.debugEnabled = null;
    }
}
