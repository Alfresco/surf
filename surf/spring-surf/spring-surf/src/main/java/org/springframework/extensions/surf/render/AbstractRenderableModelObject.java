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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.types.AbstractModelObject;
import org.springframework.extensions.surf.util.XMLUtil;

/**
 * Abstract base class for a renderable model object.
 * <p>
 * A renderable model object is one that has renderer processors
 * defined on it for one or more render modes.
 * 
 * @author muzquiano
 * @author Kevin Roast
 */
public abstract class AbstractRenderableModelObject extends AbstractModelObject implements Renderable
{
    /** IMPORTANT - public fields starting with PROP_ are inspected by the ModelHelper */
    public static String PROP_PROCESSOR = "processor";
    
    private static final String PROP_PROCESSOR_ID = "id";
    private static final String ATTR_RENDER_MODE = "mode";
    
    /** cache of RenderMode to Processor properties
        NOTE: there is no locking around this map as it is created during the constructor and then read only operations */
    private final Map<RenderMode, Map<String, String>> processorPropertyCache;
    
    /** the render modes available to this renderable object */
    private final RenderMode[] renderModes;
    
    
    /**
     * Constructs a new model object
     * 
     * @param document the document
     */
    @SuppressWarnings("unchecked")
    public AbstractRenderableModelObject(String id, ModelPersisterInfo info, Document document)
    {
        super(id, info, document);
        
        // first cache the render modes available for this renderable object
        if (document != null && document.getRootElement().elements(PROP_PROCESSOR).size() != 0)
        {
            List<Element> processorElements = document.getRootElement().elements(PROP_PROCESSOR);
            this.renderModes = new RenderMode[processorElements.size()];
            this.processorPropertyCache = new HashMap<RenderMode, Map<String,String>>(this.renderModes.length + 1);
            for (int i = 0; i < processorElements.size(); i++)
            {
                Element processorElement = processorElements.get(i);
                this.renderModes[i] = RenderMode.valueOf(processorElement.attributeValue(ATTR_RENDER_MODE).toUpperCase());
                
                // for each render mode, retrieve all processor properties
                List<Element> children = XMLUtil.getChildren(processorElement);
                HashMap<String, String> props = new HashMap<String, String>(children.size() + 1);
                for (int n = 0; n < children.size(); n++)
                {
                    Element child = children.get(n);
                    String name = child.getName();
                    String value = XMLUtil.getChildValue(processorElement, name);
                    
                    props.put(name, value);
                }
                
                // store the properties in the cache against the rendermode
                this.processorPropertyCache.put(this.renderModes[i], props);
            }
        }
        else
        {
            // If no document is provided then no render modes should be defined. This code has been added
            // to support the AdvancedComponent paradigm. AdvancedComponents only support WebScript processors.
            this.renderModes = new RenderMode[0];
            this.processorPropertyCache = Collections.<RenderMode, Map<String,String>>emptyMap();
        }
    }
        
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getProcessorId()
     */
    public String getProcessorId()
    {
        return getProcessorId(null);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getProcessorId(java.lang.String)
     */
    public String getProcessorId(final RenderMode mode)
    {
        return getProcessorProperty(mode, PROP_PROCESSOR_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getProcessorProperty(java.lang.String)
     */
    public String getProcessorProperty(final String propertyName)
    {
        return getProcessorProperty(null, propertyName);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getProcessorProperty(java.lang.String, java.lang.String)
     */
    public String getProcessorProperty(RenderMode mode, final String propertyName)
    {
        String processorProperty = null;
        if (mode == null)
        {
            mode = RenderMode.VIEW;
        }
        
        Map<String, String> modeData = this.processorPropertyCache.get(mode);
        if (modeData != null)
        {
            processorProperty = modeData.get(propertyName);
        }
        
        return processorProperty;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getProcessorProperties()
     */
    public Map<String, String> getProcessorProperties()
    {
        return getProcessorProperties(null);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getProcessorProperties(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getProcessorProperties(RenderMode renderMode)
    {
        if (renderMode == null)
        {
            renderMode = RenderMode.VIEW;
        }
        
        // return a clone of the available properties - as it can be modified later
        return (Map<String, String>)((HashMap<String, String>)this.processorPropertyCache.get(renderMode)).clone();
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.render.Renderable#getRenderModes()
     */
    public RenderMode[] getRenderModes()
    {
        return this.renderModes;
    } 
}
