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
package org.springframework.extensions.surf;

import java.util.regex.Matcher;

/**
 * <p>In order to prevent CSS resources being unnecessarily imported we can specify CSS files relevant to a widget in its JavaScript file.
 * This {@link DojoDependencyRule} allows us to check for requested CSS dependencies so that we can dynamically include them when we build
 * the page. The definition should be written as follows:
 * <pre>cssRequirements: [{cssFile:"./css/LeftAndRight.css",mediaType:"screen"}],</pre>
 * 
 * TODO: We could aim to support different aggregation groups as well.  
 * </p>
 * @author David Draper
 */
public class DojoCssDependencyRule extends DojoDependencyRule
{
    /**
     * <p>Overrides the default implementation to retrieve both the CSS dependency path and the
     * media type that the CSS path should be used against.</p>
     */
    @Override
    protected void processDependency(String dependency, 
                                     String sourcePath, 
                                     String sourceContents,
                                     Matcher matcher, DojoDependencies dependencies)
    {
        String cssDep = matcher.group(1);
        String mediaType = matcher.group(3);
        if (mediaType == null || mediaType.equals(""))
        {
            mediaType = "screen";
        }
        String cssPath = getDojoDependencyHandler().getPath(sourcePath, cssDep);
        dependencies.addCssDep(cssPath, mediaType);
    }
}
