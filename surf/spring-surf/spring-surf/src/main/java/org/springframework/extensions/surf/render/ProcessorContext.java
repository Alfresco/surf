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

import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.surf.RequestContext;

/**
 * @author muzquiano
 */
final public class ProcessorContext
{
    final private RequestContext context;
    final private Map<RenderMode, ProcessorDescriptor> descriptors = new HashMap<RenderMode, ProcessorDescriptor>(4, 1.0f);

    public ProcessorContext(RequestContext context)
    {
        this.context = context;
    }

    public RequestContext getRequestContext()
    {
        return this.context;
    }

    public ProcessorDescriptor getDescriptor(RenderMode renderMode)
    {
    	ProcessorDescriptor processorDescriptor = this.descriptors.get(renderMode);
        return  processorDescriptor;
    }

    public void putDescriptor(RenderMode renderMode, ProcessorDescriptor descriptor)
    {
        this.descriptors.put(renderMode, descriptor);
    }

    public void removeDescriptor(RenderMode renderMode)
    {
        this.descriptors.remove(renderMode);
    }

    public void addDescriptor(RenderMode renderMode, Map<String, String> properties)
    {
        ProcessorDescriptor descriptor = new ProcessorDescriptor(properties);
        putDescriptor(renderMode, descriptor);
    }

    public void load(Renderable renderable)
    {
        RenderMode[] renderModes = renderable.getRenderModes();

        for (int i = 0; i < renderModes.length; i++)
        {
            Map<String, String> properties = renderable.getProcessorProperties(renderModes[i]);
            ProcessorDescriptor descriptor = new ProcessorDescriptor(properties);

            putDescriptor(renderModes[i], descriptor);
        }
    }

    public final static class ProcessorDescriptor
    {
        public final Map<String, String> properties;

        public ProcessorDescriptor()
        {
            this.properties = new HashMap<String, String>(4, 1.0f);
        }

        public ProcessorDescriptor(Map<String, String> properties)
        {
            this.properties = properties;
        }

        public void put(String key, String value)
        {
            this.properties.put(key, value);
        }

        public String get(String key)
        {
            return (String) this.properties.get(key);
        }

        public void remove(String key)
        {
            this.properties.remove(key);
        }

        public Map<String, String> map()
        {
            return this.properties;
        }
    }
}
