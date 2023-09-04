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

package org.springframework.extensions.surf.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * Static helper methods for working with query strings and maps.
 * 
 * @author kevinr
 * @author muzquiano
 */
public final class WebUtil
{
    /**
     * Creates a Map of query string key and value parameters from the
     * given request
     * 
     * @param request the request
     * 
     * @return the query string map
     */
    public static Map<String, String> buildQueryStringMap(HttpServletRequest request)
    {
        return buildQueryStringMap(request.getQueryString());
    }
    
    /**
     * Creates a Map of query string key and value parameters from the
     * given query string.
     * 
     * @param queryString the query string
     * 
     * @return the query string map
     */    
    public static Map<String, String> buildQueryStringMap(String queryString)
    {
        final Map<String, String> map = new HashMap<String, String>(8, 1.0f);
        
        if (queryString != null)
        {
            for (final StringTokenizer t = new StringTokenizer(queryString, "&"); t.hasMoreTokens(); /**/)
            {
                final String combo = t.nextToken();
                final int c = combo.indexOf('=');
                if (c > -1)
                {
                    String value = URLDecoder.decode(combo.substring(c + 1, combo.length()));
                    map.put(combo.substring(0, c), value);
                }
            }
        }
        
        return map;        
    }

    /**
     * Returns the query string for a given map of key and value pairs
     * 
     * @param map the map
     * 
     * @return the query string for map, never null
     */
    public static String getQueryStringForMap(final Map<String, String> map)
    {
        if (map == null)
        {
            return "";
        }
        
        final StringBuilder result = new StringBuilder(32);
        
        for (String key : map.keySet())
        {
            String value = map.get(key);
            
            if (result.length() != 0)
            {
                result.append('&');
            }
            
            result.append(key).append('=').append(value);
        }
        
        return result.toString();
    }
}
