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
import java.util.LinkedHashSet;

import org.springframework.extensions.surf.DependencyAggregator;

public class ScriptResourceUtils extends ScriptBase
{
    private static final long serialVersionUID = 1L;

    private DependencyAggregator dependencyAggregator = null;
    
    public ScriptResourceUtils(DependencyAggregator dependencyAggregator)
    {
        this.dependencyAggregator = dependencyAggregator;
    }
    
    @Override
    protected ScriptableMap<String, Serializable> buildProperties()
    {
        return null;
    }
    
    /**
     * Builds a new {@link LinkedHashSet} from the supplied {@link String} array.
     * @param input The {@link String} array to convert to a {@link LinkedHashSet}
     * @return The populated {@link LinkedHashSet}
     */
    protected LinkedHashSet<String> buildHashSet(String[] input)
    {
        LinkedHashSet<String> output = new LinkedHashSet<String>();
        if (input != null)
        {
            for (String value: input)
            {
                // Trim off any initial forward slash otherwise the resource won't be found
                if (value.startsWith("/"))
                {
                    value = value.substring(1);
                }
                output.add(value);
            }
        }
        return output;
    }
    
    /**
     * 
     * @param jsResources String[]
     * @return String
     */
    public String getAggregratedJsResources(String[] jsResources)
    {
        String aggregatedResource = null;
        if (jsResources != null)
        {
            aggregatedResource = this.dependencyAggregator.generateJavaScriptDependencies(buildHashSet(jsResources));
        }
        return aggregatedResource;
    }
    
    /**
     * 
     * @param cssResources String[]
     * @return String
     */
    public String getAggregratedCssResources(String[] cssResources)
    {
        String aggregatedResource = null;
        if (cssResources != null)
        {
            aggregatedResource = this.dependencyAggregator.generateJavaScriptDependencies(buildHashSet(cssResources));
        }
        return aggregatedResource;
    }
    
}
