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

package org.springframework.extensions.surf.uri;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.extensions.config.ConfigElement;
import org.springframework.extensions.webscripts.UriTemplate;

/**
 * Index of application URI template mappings. One or more URI templates can map to a single entry url.
 * 
 * Each template uses a simple form of the JAX-RS JSR-311 URI Template format - only basic variables
 * are specified in the URI template for matching.
 * 
 * Example config:
 * <pre>
 *    <!-- simple mapping from a friendly url template to a similarly named page -->
 *    <uri-mapping>
 *       <uri-template>/site/{site}/dashboard</uri-template>
 *       <url-entry>/page/sites/{site}/dashboard</url-entry>
 *    </uri-mapping>
 *    <!-- multiple friendly url templates can map to a single page -->
 *    <uri-mapping>
 *       <uri-template>/user/{user}</uri-template>
 *       <uri-template>/user/{user}/mydashboard</uri-template>
 *       <url-entry>/page/users/{user}/dashboard</url-entry>
 *    </uri-mapping>
 *    <!-- reusable user tool page - note the arbitrary url path suffix mapped to an argument -->
 *    <uri-mapping>
 *       <uri-template>/user/{user}/wiki/{path}</uri-template>
 *       <url-entry>/page/users/tools/wiki?user={user}&article={path}</url-entry>
 *    </uri-mapping>
 * </pre>
 * 
 * @author Kevin Roast
 */
public class UriTemplateMappingIndex
{
    private Map<UriTemplate, String> mappings;

    /**
     * Constructor
     * 
     * @param config     ConfigElement pointing to the <uri-mapping> sections (see above)
     */
    public UriTemplateMappingIndex(ConfigElement config)
    {
        List<ConfigElement> mappingElements = config.getChildren("uri-mapping");
        if (mappingElements != null)
        {
            this.mappings = new LinkedHashMap<UriTemplate, String>(mappingElements.size());

            for (ConfigElement mappingElement : mappingElements)
            {
                String entry = mappingElement.getChildValue("url-entry");
                if (entry == null || entry.trim().length() == 0)
                {
                    throw new IllegalArgumentException("<uri-mapping> config element must contain <url-entry> element value.");
                }

                List<ConfigElement> templateElements = mappingElement.getChildren("uri-template");
                if (templateElements.size() == 0)
                {
                    throw new IllegalArgumentException("<uri-mapping> config element must contain <uri-template> element(s).");
                }
                for (ConfigElement templateElement : templateElements)
                {
                    String template = templateElement.getValue();
                    if (template == null || template.trim().length() == 0)
                    {
                        throw new IllegalArgumentException("<uri-template> config element must contain a value.");
                    }

                    // build the object to represent the Uri Template
                    UriTemplate uriTemplate = new UriTemplate(template);

                    // store the mapping between the Uri Template and the url entry pattern
                    this.mappings.put(uriTemplate, entry);
                }
            }
        }
        else
        {
            this.mappings = Collections.<UriTemplate, String>emptyMap();
        }
    }

    /**
     * Search the URI index to locate a match for the specified URI.
     * If found, return the matched URL with the tokens replaced as per the
     * pattern and supplied URI value.
     * 
     * @param uri  URI to match against the URI Templates in the index
     * 
     * @return URI match with tokens replaced as per the URI Template pattern or
     *         null if no match was found.
     */
    public String findMatchAndReplace(String uri)
    {
        for (UriTemplate template : this.mappings.keySet())
        {
            Map<String, String> match = template.match(uri);
            if (match != null)
            {
                // found a uri template match
                // so replace the tokens in the matched page with those from the uri
                return UriUtils.replaceUriTokens(this.mappings.get(template), match);
            }
        }
        
        // if we get here, no match was found
        return null;
    }
    
    /**
     * Search the URI index to locale a match for the specified URI.
     * If found, return the args that represent the matched URI pattern tokens
     * and the values as per the supplied URI value.
     * 
     * @param uri  URI to match against the URI Templates in the index
     * 
     * @return Map of token args to values or null if no match was found.
     */
    public Map<String, String> findMatch(String uri)
    {
        for (UriTemplate template : this.mappings.keySet())
        {
            Map<String, String> match = template.match(uri);
            if (match != null)
            {
                return match;
            }
        }
        
        // if we get here, no match was found
        return null;
    }
}
