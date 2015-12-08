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
import org.springframework.extensions.surf.render.RenderService;

/**
 * <p>This class has been created to solve the problem of components rendering chrome that use
 * the <code>regionInclude</code> tag (resulting in an OutOfMemory exception) and regions rendering
 * chrome that use the <code>componentInclude</code> tag (resulting in an infinite loop).</p>
 * <p>The tags rely on the context being set to indicate the type of rendering being requested
 * (either component or region) and then applies the appropriate logic required. This means that if
 * <code>componentInclude</code> and <code>regionInclude</code> can be used interchangeably without
 * risk of error.</p>
 *
 * @author David Draper
 */
public class IncludeTag extends RenderServiceTag
{
    private static final long serialVersionUID = 7680743024251636060L;

    @Override
    protected int invokeRenderService(RenderService renderService, RequestContext renderContext, ModelObject object)
            throws RequestDispatchException
    {
        renderService.renderChromeInclude(renderContext, object);
        return SKIP_BODY;
    }
}
