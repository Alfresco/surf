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

package org.springframework.extensions.surf.taglib;

import java.io.UnsupportedEncodingException;

import jakarta.servlet.jsp.JspException;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.RendererExecutionException;

/**
 * This tag is meant to be used during the processing of Template Instances.
 * 
 * The tag will look for components bound to this template and produce their
 * .head markup.  It will then print this to the output stream.
 * 
 * @author muzquiano
 */
public class HeadTag extends TagBase
{
    private static final long serialVersionUID = -6299508266443500315L;

    public int doStartTag() throws JspException
    {
        RequestContext context = getRequestContext();
        ModelObject object = getModelObject();

        try
        {
            print(getRenderService().renderTemplateHeaderAsString(context, object));
        }
        catch (RendererExecutionException ree)
        {
            throw new JspException("Unable to process downstream component head files", ree);
        }
        catch (UnsupportedEncodingException uee)
        {
            throw new JspException("Unsupported encoding exception", uee);
        }
        
        return SKIP_BODY;
    }
}
