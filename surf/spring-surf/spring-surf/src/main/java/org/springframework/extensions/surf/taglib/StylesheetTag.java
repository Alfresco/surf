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

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.render.RenderService;

/**
 * The "link" tag is provided so that multiple CSS resources requested by a component
 * can be batched up into a single "style" tag with multiple @import statements.
 * This mechanism is to workaround the MSIE bug described in KB262161 whereby IE browsers
 * will not parse more than 30 separate CSS resource tags.
 * 
 * @author mikeh
 * @author David Draper
 */
public class StylesheetTag extends RenderServiceTag
{
    private static final long serialVersionUID = -2372542871999800148L;

    private String rel = null;
    private String type = null;
    private String href = null;

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
        this.rel = null;
        this.type = null;
        this.href = null;
    }

    public void setRel(String rel)
    {
        this.rel = rel;
    }

    public String getRel()
    {
        return this.rel;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getHref()
    {
        return href;
    }
    
    @Override
    protected int invokeRenderService(RenderService renderService, RequestContext renderContext, ModelObject object)
            throws Exception
    {
        renderService.updateStyleSheetImports(renderContext, href);
        return SKIP_BODY;
    }
}