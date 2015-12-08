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
import org.springframework.extensions.surf.exception.RequestDispatchException;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.render.RenderService;

/**
 * @author muzquiano
 * @author David Draper
 */
public class ComponentTag extends RenderServiceTag
{
    private static final long serialVersionUID = -53733105000358307L;

    private String component = null;
    private String chrome = null;
    private boolean chromeless = false;

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
        this.component = null;
        this.chrome = null;
        this.chromeless = false;
    }
    
    public void setComponent(String componentId)
    {
        this.component = componentId;
    }
    
    public String getComponent()
    {
        return this.component;
    }
    
    public void setChrome(String chrome)
    {
        this.chrome = chrome;
    }

    public String getChrome()
    {
        return this.chrome;
    }
    
    public boolean isChromeless()
    {
        return chromeless;
    }

    public void setChromeless(boolean chromeless)
    {
        this.chromeless = chromeless;
    }
    
    @Override
    protected int invokeRenderService(RenderService renderService, RequestContext renderContext, ModelObject object)
            throws RequestDispatchException
    {
        renderService.renderComponent(renderContext, RenderFocus.BODY, this.component, this.chrome, this.chromeless);           
        return SKIP_BODY;
    }
    
}
