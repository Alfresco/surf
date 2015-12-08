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
 * Default component type implementation
 * 
 * @author muzquiano
 */
public class ComponentTypeImpl extends AbstractRenderableModelObject implements ComponentType
{
    /**
     * Instantiates a new component type for the given XML document.
     * 
     * @param document the document
     */
    public ComponentTypeImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.AbstractModelObject#getTypeId()
     */
    public String getTypeId() 
    {
        return TYPE_ID;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ComponentType#getURI()
     */
    public String getURI()
    {
        return getProperty(PROP_URI);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ComponentType#setURI(java.lang.String)
     */
    public void setURI(String uri)
    {
        setProperty(PROP_URI, uri);
    }
}