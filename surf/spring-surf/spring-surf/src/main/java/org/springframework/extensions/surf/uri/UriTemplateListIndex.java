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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.extensions.config.ConfigElement;
import org.springframework.extensions.webscripts.UriTemplate;

/**
 * Index of application URI templates.
 *
 * Each template uses a simple form of the JAX-RS JSR-311 URI Template format - only basic variables
 * are specified in the URI template for matching.
 *
 * Example config:
 * <pre>
 *    <uri-templates>
 *       <uri-template>/page/site/{site}/{page}</uri-template>
 *       <uri-template>/page/site/{site}</uri-template>
 *       <uri-template>/page/user/{userid}/{page}</uri-template>
 *       <uri-template>/page/user/{userid}</uri-template>
 *    </uri-templates>
 * </pre>
 *
 * @author Kevin Roast
 */
public class UriTemplateListIndex
{
    private List<UriTemplate> uriTemplates;

    /**
     * Instantiates a new <code>UriTemplateListIndex</code> object using the list of <code>UriTemplates</code>
     * provided.
     */
    public UriTemplateListIndex(List<UriTemplate> uriTemplates)
    {
        this.uriTemplates = uriTemplates;
    }

    /**
     * Instantiates a new <code>UriTemplateList</code> object using the configuration found in the
     * <code>ConfigElement</code> provided. This means that in order to change the default templates
     * it is also necessary to change both the Spring configuration for the resolver and provide a new
     * resolver to cope with the template. The only place this is now used is in the <code>PageViewResolver</code>
     * and as soon as it is no longer required it will be deleted.
     *
     * @param config     ConfigElement pointing to the <uri-templates> sections (see above)
     */
    public UriTemplateListIndex(ConfigElement config)
    {
        List<ConfigElement> uriElements = config.getChildren("uri-template");
        if (uriElements != null)
        {
            this.uriTemplates = new ArrayList<UriTemplate>(uriElements.size());

            for (ConfigElement uriElement : uriElements)
            {
                String template = uriElement.getValue();
                if (template == null || template.trim().length() == 0)
                {
                    throw new IllegalArgumentException("<uri-template> config element must contain a value.");
                }

                // build the object to represent the Uri Template
                UriTemplate uriTemplate = new UriTemplate(template);

                // store the Uri Template
                this.uriTemplates.add(uriTemplate);
            }
        }
        else
        {
            this.uriTemplates = Collections.<UriTemplate>emptyList();
        }
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
        Map<String, String> match = null;
        for (UriTemplate template : this.uriTemplates)
        {
            match = template.match(uri);
            if (match != null)
            {
                break;
            }
        }

        return match;
    }
}
