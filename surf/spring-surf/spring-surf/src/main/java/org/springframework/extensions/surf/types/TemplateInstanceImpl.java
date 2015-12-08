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
 * Default template instance implementation
 * 
 * @author muzquiano
 */
public class TemplateInstanceImpl extends AbstractModelObject implements TemplateInstance
{
    // cached values
    private String templateTypeId = null;
    
    /**
     * Instantiates a new template instance for a given XML document
     * 
     * @param document the document
     */
    public TemplateInstanceImpl(String id, ModelPersisterInfo key, Document document)
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
     * @see org.alfresco.web.framework.types.TemplateInstance#getTemplateType()
     */
    public String getTemplateTypeId()
    {
        if (this.templateTypeId == null)
        {
            this.templateTypeId = getProperty(PROP_TEMPLATE_TYPE);
            
            // default to freemarker template type
            if (this.templateTypeId == null)
            {
                this.templateTypeId = "freemarker";
            }
        }
        
        return this.templateTypeId;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.TemplateInstance#setTemplateType(java.lang.String)
     */
    public void setTemplateTypeId(String templateType)
    {
        setProperty(PROP_TEMPLATE_TYPE, templateType);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.TemplateInstance#getTemplateType(org.alfresco.web.framework.RequestContext)
     */
    public TemplateType getTemplateType(RequestContext context)
    {
        // either 'global', template or page
        return context.getObjectService().getTemplateType(getTemplateTypeId());
    }
}
