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
 * <p>This {@link DojoDependencyRule} is used to detect Dojo dependencies that are defined within JavaScript source files
 * as JSON arrays of JSON objects. This rule will detect dependencies that are not explicitly declared through a "define"
 * function call but that will be dynamically requested at runtime.</p>
 *  
 * @author David Draper
 */
public class DojoNonAmdDependencyRule  extends DojoDependencyRule
{
    /**
     * <p>Overrides the default implementation to recurse over the group returned by the main declaration regular expression. This
     * is required because each JSON object that defined a widget can itself declare an array of dependencies.</p>
     */
    @Override
    protected void processRegexRules(String filePath, 
                                     String fileContents, 
                                     DojoDependencies dependencies)
    {
        Matcher m1 = getDeclarationRegexPattern().matcher(fileContents);
        while (m1.find())
        {
            if (m1.groupCount() >= getTargetGroup())
            {
                // The second group in a regex match will contain array of dependencies...
                String deps = m1.group(getTargetGroup());
                if (deps != null)
                {
                    // Recursively look for nested widgets...
                    processRegexRules(filePath, deps, dependencies);
                    
                    // Find the dependencies in the widgets list...
                    Matcher m2 = getDependencyRegexPattern().matcher(deps);
                    while (m2.find())
                    {
                        String dep = m2.group(1);
                        if (dep != null)
                        {
                            String depPath = getDojoDependencyHandler().getPath(filePath, dep);
                            addNonAmdJavaScriptDependency(dependencies, depPath);
                        }
                    }
                }
            }
        }
    }
}
