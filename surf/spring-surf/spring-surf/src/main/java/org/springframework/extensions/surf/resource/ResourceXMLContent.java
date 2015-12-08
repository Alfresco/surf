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

package org.springframework.extensions.surf.resource;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.extensions.surf.FrameworkBean;

/**
 * XML Content for a Resource
 *  
 * @author muzquiano
 */
public interface ResourceXMLContent extends ResourceContent
{   
    /**
     * Gets the xml for a resource
     * 
     * @return the xml
     */
    public String getXml() throws IOException;
    
    /**
     * Gets the document.
     * 
     * @return the document
     */
    public Document getDocument() throws DocumentException, IOException;
}
