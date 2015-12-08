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

/**
 * Default configuration implementation
 * 
 * @author muzquiano
 */
public class ConfigurationImpl extends AbstractModelObject implements Configuration
{
    /**
     * Instantiates a new configuration for the given xml document.
     * 
     * @param document the document
     */
    public ConfigurationImpl(String id, ModelPersisterInfo key, Document document)
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
     * @see org.alfresco.web.framework.types.Configuration#getSourceId()
     */
    public String getSourceId()
    {
        return getProperty(PROP_SOURCE_ID);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.Configuration#setSourceId(java.lang.String)
     */
    public void setSourceId(String sourceId)
    {
        setProperty(PROP_SOURCE_ID, sourceId);
    }
}
