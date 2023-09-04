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

import javax.servlet.jsp.JspException;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.render.RenderService;

/**
 * <p>Renders an HTML anchor tag to a page or page type using the attributes provided. Correct usage of this tag should include
 * a body that will be rendered as the link - if no body is provided then nothing acionable will be rendered on the screen.</p>
 *
 * @author muzquiano
 * @author David Draper
 */
public class ObjectAnchorTag extends AbstractObjectTag
{
    private static final long serialVersionUID = 5263975705338049998L;

    private String target = null;

    public void setTarget(String target)
    {
        this.target = target;
    }

    public String getTarget()
    {
        return this.target;
    }

    /**
     * <p>The life-cycle of a custom JSP tag is that the class is is instantiated when it is first required and
     * then re-used for all subsequent invocations. When a JSP has non-mandatory properties it means that the 
     * setters for those properties will not be called if the properties are not provided and the old values
     * will still be available which can corrupt the behaviour of the code. In order to prevent this from happening
     * we should override the <code>release</code> method to ensure that all instance variables are reset to their
     * initial state.</p>
     */
    @Override
    public void release()
    {
        super.release();
        this.target = null;
    }
    
    /**
     * <p>Generates the URL to the requested resource (either a page or page type with optional object and format
     * request parameters) and then opens an HTML anchor tag using the generated URL as the HREF argument and setting
     * a target if provided. The body is then evaluated (which should render something to display as a link) and the
     * <code>doEndTag</code> method will close the HTML anchor tag.</p>
     *
     * @throws JspException wrapping any <code>IOException</code> that may occur writing the output.
     */    
    @Override
    protected int invokeRenderService(RenderService renderService, RequestContext requestContext, ModelObject object) throws Exception
    {
        try
        {
            String anchorStart = renderService.generateAnchorLink(getPageType(), getPage(), getObject(), getFormat(), target);
            pageContext.getOut().write(anchorStart);
        }
        catch (Exception ex)
        {
            throw new JspException(ex);
        }
        return EVAL_BODY_INCLUDE;
    }
    
    /**
     * <p>Closes the HTML anchor tag opened in <code>doStartTag</code></p>
     */
    public int doEndTag() throws JspException
    {
        try
        {
            pageContext.getOut().write("</A>");
        }
        catch (Exception ex)
        {
            throw new JspException(ex);
        }
        return EVAL_PAGE;
    }
}
