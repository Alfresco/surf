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
 * Default chrome implementation
 * 
 * @author muzquiano
 */
public class ChromeImpl extends AbstractRenderableModelObject implements Chrome
{
    private static final long serialVersionUID = -2095286118767991371L;

    /**
     * Instantiates a new chrome for a given XML document
     * 
     * @param document the document
     */
    public ChromeImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
    }    

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.AbstractModelObject#getTypeId()
     */
    public String getTypeId()
    {
        return Chrome.TYPE_ID;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.Chrome#getChromeType()
     */
    public String getChromeType()
    {
        return getProperty(PROP_CHROME_TYPE);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.Chrome#setChromeType(java.lang.String)
     */
    public void setChromeType(String chromeType)
    {
        setProperty(PROP_CHROME_TYPE, chromeType);
    }
}
