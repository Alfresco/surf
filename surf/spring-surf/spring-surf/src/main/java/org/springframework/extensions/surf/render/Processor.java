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
import org.springframework.extensions.surf.exception.RendererExecutionException;

public interface Processor
{
    /**
     * Executes the given focus of the processor output using the
     * given processor context
     *
     * @param processorContext ProcessorContext
     * @param object ModelObject
     * @param focus RenderFocus
     *
     * @throws RendererExecutionException
     */
    public void execute(ProcessorContext processorContext, ModelObject object, RenderFocus focus)
        throws RendererExecutionException;

    /**
     * Executes the "body" of the processor output using the given
     * processor context.
     *
     * @param processorContext processorContext
     *
     * @throws RendererExecutionException
     */
    public void executeBody(ProcessorContext processorContext, ModelObject object)
        throws RendererExecutionException;

    /**
     * Executes the "header" of the processor output using the given
     * processor context.
     *
     * @param processorContext processorContext
     *
     * @throws RendererExecutionException
     */
    public void executeHeader(ProcessorContext processorContext, ModelObject object)
        throws RendererExecutionException;

    /**
     * Indicates whether the engine responsible for processing
     * the body of the processor exists.
     *
     * @param processorContext ProcessorContext
     * @param object ModelObject
     * @return boolean
     */
    public boolean exists(ProcessorContext processorContext, ModelObject object);
}
