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

import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.extensions.surf.ModelObject;

public interface Extension extends ModelObject
{
    public static String TYPE_ID = "extension";

    // properties
    public static String PROP_EXTENSION_TYPE = "extension-type";

    public String getExtensionType();
    
    public void setExtensionType(String extensionType);
    
    public static String PROP_MODULES = "modules";
    
    public List<ExtensionModule> getExtensionModules(); 
    
    public ExtensionModule addExtensionModule(String xmlFragment) throws DocumentException;
    
    public ExtensionModule updateExtensionModule(String xmlFragment) throws DocumentException;
    
    public ExtensionModule deleteExtensionModule(String moduleId) throws DocumentException;
}
