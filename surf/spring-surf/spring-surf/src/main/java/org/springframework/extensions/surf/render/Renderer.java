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

package org.springframework.extensions.surf.render;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.RendererExecutionException;

public interface Renderer
{
    /**
     * Executes the renderer in the given focus
     *
     * @param renderContext RequestContext
     * @param object ModelObject
     * @param focus RenderFocus
     *
     * @throws RendererExecutionException
     */
    public void render(RequestContext renderContext, ModelObject object, RenderFocus focus)
        throws RendererExecutionException;

    /**
     * Executes the renderer in the "all" mode
     *
     * @param renderContext RequestContext
     * @param object ModelObject
     * @throws RendererExecutionException
     */
    public void all(RequestContext renderContext, ModelObject object)
        throws RendererExecutionException;

    /**
     * Executes the renderer in the "head" mode
     *
     * @param renderContext RequestContext
     * @param object ModelObject
     * @throws RendererExecutionException
     */
    public void header(RequestContext renderContext, ModelObject object)
        throws RendererExecutionException;

    /**
     * Executes the renderer in the "body" mode
     *
     * @param renderContext RequestContext
     * @param object ModelObject
     * @throws RendererExecutionException
     */
    public void body(RequestContext renderContext, ModelObject object)
        throws RendererExecutionException;
}
