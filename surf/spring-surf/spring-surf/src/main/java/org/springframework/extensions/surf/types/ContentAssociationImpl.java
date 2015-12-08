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
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.RequestContext;

/**
 * Default content association implementation
 * 
 * @author muzquiano
 */
public class ContentAssociationImpl extends AbstractModelObject implements ContentAssociation
{
    /**
     * Instantiates a new content association for the given XML document
     * 
     * @param document the document
     */
    public ContentAssociationImpl(String id, ModelPersisterInfo key, Document document)
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
     * @see org.alfresco.web.framework.types.ContentAssociation#getSourceId()
     */
    public String getSourceId()
    {
        return getProperty(PROP_SOURCE_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#setSourceId(java.lang.String)
     */
    public void setSourceId(String sourceId)
    {
        setProperty(PROP_SOURCE_ID, sourceId);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#getDestId()
     */
    public String getDestId()
    {
        return getProperty(PROP_DEST_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#setDestId(java.lang.String)
     */
    public void setDestId(String destId)
    {
        setProperty(PROP_DEST_ID, destId);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#getAssociationType()
     */
    public String getAssociationType()
    {
        return getProperty(PROP_ASSOC_TYPE);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#setSourceType(java.lang.String)
     */
    public void setSourceType(String sourceType)
    {
        setProperty(PROP_SOURCE_TYPE, sourceType);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#getSourceType()
     */
    public String getSourceType()
    {
        return getProperty(PROP_SOURCE_TYPE);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#setAssociationType(java.lang.String)
     */
    public void setAssociationType(String associationType)
    {
        setProperty(PROP_ASSOC_TYPE, associationType);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#getFormatId()
     */
    public String getFormatId()
    {
        return getProperty(PROP_FORMAT_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.ContentAssociation#setFormatId(java.lang.String)
     */
    public void setFormatId(String formatId)
    {
        setProperty(PROP_FORMAT_ID, formatId);
    }

    // Helpers

    public ModelObject getObject(RequestContext context)
    {
        ModelObject modelObject = null;
        
        if (isTemplateAssociation())
        {
            modelObject = context.getObjectService().getTemplate(getDestId());            
        }
        else if (isPageAssociation())
        {
            modelObject = context.getObjectService().getPage(getDestId());
        }
        
        return modelObject;
    }
    
    public boolean isTemplateAssociation()
    {
        return (getAssociationType() == null || "template".equalsIgnoreCase(getAssociationType()));
    }
    
    /**
     * Checks if is page association.
     * 
     * @return true, if is page association
     */
    public boolean isPageAssociation()
    {
        return ("page".equalsIgnoreCase(getAssociationType()));
    }
    
}
