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

package org.springframework.extensions.webscripts;

import java.io.Serializable;
import java.util.Map;

/**
 * Class to represent the template model for a URL.
 * 
 * This class is immutable.
 * 
 * @author Kevin Roast
 */
public interface URLHelper extends Serializable
{
    public String getContext();

    public String getServletContext();

    public String getUri();

    public String getUrl();

    public String getQueryString();
    
    public Map<String, String> getArgs();
    
    public Map<String, String> getTemplateArgs();
}
