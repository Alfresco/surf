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
import org.springframework.extensions.surf.RequestContext;

/**
 * Default page association implementation
 * 
 * @author muzquiano
 */
public class PageAssociationImpl extends AbstractModelObject implements PageAssociation
{
    /**
     * Instantiates a new page association for a given XML document
     * 
     * @param document the document
     */
    public PageAssociationImpl(String id, ModelPersisterInfo key, Document document)
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
     * @see org.alfresco.web.framework.types.PageAssociation#getSourceId()
     */
    public String getSourceId()
    {
        return getProperty(PROP_SOURCE_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#setSourceId(java.lang.String)
     */
    public void setSourceId(String sourceId)
    {
        setProperty(PROP_SOURCE_ID, sourceId);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#getDestId()
     */
    public String getDestId()
    {
        return getProperty(PROP_DEST_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#setDestId(java.lang.String)
     */
    public void setDestId(String destId)
    {
        setProperty(PROP_DEST_ID, destId);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#getAssociationType()
     */
    public String getAssociationType()
    {
        return getProperty(PROP_ASSOC_TYPE);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#setAssociationType(java.lang.String)
     */
    public void setAssociationType(String associationType)
    {
        setProperty(PROP_ASSOC_TYPE, associationType);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#getOrderId()
     */
    public String getOrderId()
    {
        return getProperty(PROP_ORDER_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#setOrderId(java.lang.String)
     */
    public void setOrderId(String orderId)
    {
        setProperty(PROP_ORDER_ID, orderId);
    }

    // Helpers

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#getSourcePage(org.alfresco.web.framework.RequestContext)
     */
    public Page getSourcePage(RequestContext context)
    {
        return context.getObjectService().getPage(getSourceId());
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.PageAssociation#getDestPage(org.alfresco.web.framework.RequestContext)
     */
    public Page getDestPage(RequestContext context)
    {
        return context.getObjectService().getPage(getDestId());
    }
}
