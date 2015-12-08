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

package org.springframework.extensions.surf.site;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.exception.RendererExecutionException;
import org.springframework.extensions.surf.render.AbstractProcessor;
import org.springframework.extensions.surf.render.ProcessorContext;

/**
 * Processor class which renders a null result.
 * 
 * Used to render vacant "empty" regions when there are no bound components.
 * A common use case for application that are not "design focused".
 * 
 * @author Kevin Roast
 */
public class EmptyRegionRenderer extends AbstractProcessor
{
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractProcessor#executeHeader(org.alfresco.web.framework.render.ProcessorContext)
     */
    public void executeHeader(ProcessorContext pc, ModelObject object)
        throws RendererExecutionException
    {
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.AbstractProcessor#executeBody(org.alfresco.web.framework.render.ProcessorContext)
     */
    public void executeBody(ProcessorContext pc, ModelObject object)
        throws RendererExecutionException
    {
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Processor#exists(org.alfresco.web.framework.render.ProcessorContext)
     */
    public boolean exists(ProcessorContext pc, ModelObject object)
    {
        return true;
    }
}