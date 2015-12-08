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

package org.springframework.extensions.surf.types;

import org.dom4j.Document;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.render.AbstractRenderableModelObject;

/**
 * <p>A <code>SurfBug</code> object is used for writing debug HTML to the
 * output stream when SurfBug mode is enabled.</p>
 * 
 * @author David Draper
 */
public class SurfBugImpl extends AbstractRenderableModelObject implements SurfBug
{
    private static final long serialVersionUID = 8076013453214119283L;

    public SurfBugImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
    }    

    public String getTypeId()
    {
        return SurfBug.TYPE_ID;
    }
    
    public String getSurfBugType()
    {
        return getProperty(PROP_SURFBUG_TYPE);
    }
    
    public void setSurfBugType(String surfbugType)
    {
        setProperty(PROP_SURFBUG_TYPE, surfbugType);
    }

    /**
     * <p>The <code>Component</code> currently being debugged.</p>
     */
    private Component currentComponent = null;
    
    /**
     * <p>Set the <code>Component</code> currently being debugged.</p>
     * @param component Component
     */
    public void setCurrentComponent(Component component)
    {
        this.currentComponent = component;
    }
    
    /**
     * <p>Indicates whether or not the supplied <code>Component</code> has already been debugged.
     * This will return <code>true</code> if the supplied <code>Component</code> matches the 
     * <code>currentComponent</code> instance variable.</p>
     * @param object boolean
     * @return Component
     */
    public boolean hasBeenDebugged(Component object)
    {
        return (this.currentComponent != null && this.currentComponent.equals(object));
    }

    /**
     * <p>Return the <code>Component</code> currently being debugged.</p>
     * @return Component
     */
    public Component getCurrentComponent()
    {
        return this.currentComponent;
    }
}
