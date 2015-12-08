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
 * Default template type implementation
 * 
 * @author muzquiano
 */
public class TemplateTypeImpl extends AbstractRenderableModelObject implements TemplateType
{
    /**
     * Instantiates a new template type for a given XML document
     * 
     * @param document the document
     */
    public TemplateTypeImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.site.model.AbstractModelObject#getTypeName()
     */
    public String getTypeId() 
    {
        return TYPE_ID;
    }    
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.TemplateType#getURI()
     */
    public String getURI()
    {
        return getProperty(PROP_URI);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.TemplateType#setURI(java.lang.String)
     */
    public void setURI(String uri)
    {
        setProperty(PROP_URI, uri);
    }
}
