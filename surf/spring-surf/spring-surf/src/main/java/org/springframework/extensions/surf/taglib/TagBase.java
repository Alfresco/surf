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

import java.io.Serializable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.render.RenderContextRequest;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;

/**
 * @author muzquiano
 */
public abstract class TagBase extends BodyTagSupport implements Serializable
{
    private static final long serialVersionUID = -387841473754510763L;

    private PageContext pageContext = null;

    public void setPageContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    protected PageContext getPageContext()
    {
        return this.pageContext;
    }

    public int doEndTag() throws JspException
    {
        return EVAL_PAGE;
    }
            
    /**
     * Gets the request context bound to the current request
     * 
     * @return RequestContext
     * @throws JspException
     */
    protected RequestContext getRequestContext()
        throws JspException
    {
        return ThreadLocalRequestContext.getRequestContext();
    }
    
    protected RequestContext getRenderContext()
        throws JspException
    {
        HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();        
        return (RequestContext) request.getAttribute(RenderContextRequest.ATTRIB_RENDER_CONTEXT);
    }
    
    protected ModelObject getModelObject()
    {
        HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();        
        return (ModelObject) request.getAttribute(RenderContextRequest.ATTRIB_MODEL_OBJECT);        
    }
    
    protected RenderService getRenderService()
    {
        return ThreadLocalRequestContext.getRequestContext().getServiceRegistry().getRenderService();
    }

    protected JspWriter getOut()
    {
        return getPageContext().getOut();
    }

    protected void print(String str)
        throws JspException
    {
        try
        {
            JspWriter jspWriter = getOut();
            jspWriter.clearBuffer();
            jspWriter.print(str);
        }
        catch (Exception ex)
        {
            throw new JspException(ex);
        }
    }
    
    public void release()
    {
        this.pageContext = null;
        super.release();
    }    
}
