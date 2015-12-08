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

package org.springframework.extensions.config;

import org.dom4j.Element;
import org.springframework.extensions.config.ConfigElement;
import org.springframework.extensions.config.xml.elementreader.ConfigElementReader;

/**
 * Responsible for loading Web Framework configuration settings from
 * the web-framework-config*.xml files that are loaded via the configuration
 * service.
 * 
 * @author muzquiano
 */
public class WebFrameworkConfigElementReader implements ConfigElementReader
{   
   /**
    * Called from the configuration service to handle the loading of the
    * Web Framework configuration XML.
    * 
    * @param elem the element
    * 
    * @return the config element
    * 
    * @see org.springframework.extensions.config.xml.elementreader.ConfigElementReader#parse(org.dom4j.Element)
    */
   public ConfigElement parse(Element elem)
   {
       ConfigElement configElement = null;
       if (elem != null)
       {
           configElement = WebFrameworkConfigElement.newInstance(elem);
       }
       return configElement;
   }
}
