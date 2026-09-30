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
package org.springframework.extensions.surf.webscripts;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * This backs the /surf/xhr/dependencies WebScript and should be used to build resource files containing
 * all the required CSS and JavaScript dependencies for a set of widgets defined in a stringified JSON object
 * passed as a request parameter.
 * 
 * @author David Draper
 *
 */
public class XhrDependencies extends DeclarativeWebScript
{
    private DependencyAggregator dependencyAggregator;
    private WebFrameworkConfigElement webFrameworkConfigElement;

    /**
     * Turns the {@code js} or {@code css} query parameter values into an ordered list of dependency
     * names for the aggregator. A {@link LinkedHashSet} keeps each path once (no duplicates) and
     * preserves the order the browser sent, which matters when files are merged into one bundle.
     * Leading {@code /} on a path is removed so paths match webapp resources (e.g. {@code foo/bar.js}
     * not {@code /foo/bar.js}).
     *
     * @param input repeated request parameter values, or {@code null}
     * @return ordered, de-duplicated paths; empty if {@code input} is {@code null}
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
     * Returns true if any {@code js} or {@code css} query value starts with {@code >>>}
     * ({@link DependencyAggregator#INLINE_AGGREGATION_MARKER}). In plain terms: that prefix means
     * “paste this text into the bundle,” not “load this file from the webapp.”
     * <p>
     * Surf uses inline entries when it builds pages on the server; this XHR endpoint must not accept
     * them from the client. If we see {@code >>>}, {@link #executeImpl} responds with 403 and never
     * calls the aggregator. That complements {@code allowInlineContent=false} on
     * {@link DependencyAggregator}, which skips inline entries if untrusted paths still get through.
     * </p>
     *
     * @param dependencies repeated parameter values from the request, or {@code null}
     * @return {@code true} when at least one value is inline content; {@code false} if {@code null} or all paths are normal
     */
    private boolean containsInlineAggregationMarker(String[] dependencies)
    {
        if (dependencies == null)
        {
            return false;
        }
        for (String dependency : dependencies)
        {
            if (dependency != null && dependency.startsWith(DependencyAggregator.INLINE_AGGREGATION_MARKER))
            {
                return true;
            }
        }
        return false;
    }

    private void forbid(Status status, String message)
    {
        status.setCode(HttpServletResponse.SC_FORBIDDEN);
        status.setMessage(message);
        status.setRedirect(true);
    }
    
    /**
     * Handles {@code GET /surf/xhr/dependencies}: reads {@code js} and {@code css} query parameters
     * (lists of webapp file paths), merges each list into a single cached bundle, and returns the
     * bundle checksums for JSON (for example {@code {"javaScript":"abc.js","css":"def.css"}}).
     * <p>
     * Returns 403 when dependency aggregation is turned off in config, or when any parameter uses
     * the {@code >>>} inline marker. Only normal file paths are aggregated, with inline content
     * disabled on the {@link DependencyAggregator}.
     * </p>
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        // Example: Default surf.xml has <aggregate-dependencies>false</aggregate-dependencies>.
        // Then if client (eg Share) loads many separate .js/.css files and does not need this merge endpoint—return 403.
        // Only when aggregation is true (bundles like /share/res/abc123.js) should we accept js/css lists here.
        if (this.webFrameworkConfigElement == null || !this.webFrameworkConfigElement.isAggregateDependenciesEnabled())
        {
            forbid(status, "Dependency aggregation is not enabled");
            return model;
        }

        // Each ?js=... and ?css=... value should be a webapp file path (may be repeated).
        String[] jsDeps = req.getParameterValues("js");
        String[] cssDeps = req.getParameterValues("css");
        // Reject >>> "inline code" in the URL; only server-rendered pages may supply that marker.
        if (containsInlineAggregationMarker(jsDeps) || containsInlineAggregationMarker(cssDeps))
        {
            forbid(status, "Inline dependency content is not permitted via this endpoint");
            return model;
        }

        // Merge listed paths into one bundle each; false = do not accept >>> inside the aggregator either.
        String jsResource = "";
        String cssResource = "";
        if (jsDeps != null && jsDeps.length > 0)
        {
            jsResource = this.dependencyAggregator.generateJavaScriptDependencies(buildHashSet(jsDeps), false);
        }
        if (cssDeps != null && cssDeps.length > 0)
        {
            cssResource = this.dependencyAggregator.generateCSSDependencies(buildHashSet(cssDeps), false);
        }
        model.put("jsResource", jsResource);
        model.put("cssResource", cssResource);
        return model;
    }
    
    public DependencyAggregator getDependencyAggregator()
    {
        return dependencyAggregator;
    }

    public void setDependencyAggregator(DependencyAggregator dependencyAggregator)
    {
        this.dependencyAggregator = dependencyAggregator;
    }

    public WebFrameworkConfigElement getWebFrameworkConfigElement()
    {
        return webFrameworkConfigElement;
    }

    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }
}
