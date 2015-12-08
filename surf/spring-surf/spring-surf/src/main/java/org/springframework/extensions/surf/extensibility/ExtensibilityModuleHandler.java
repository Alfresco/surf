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
package org.springframework.extensions.surf.extensibility;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.types.ExtensionModule;


/**
 * <p>Classes implementing this interface should be able to determine paths to the files provided by
 * a module that extend a {@link ExtensibilityModel} being processed. They should also be able to
 * return a String representing the HTML instructions for importing client side JavaScript and CSS
 * resource dependencies required by the extending modules.</p>
 * 
 * @author David Draper
 */
public interface ExtensibilityModuleHandler
{
    /**
     * <p>Evaluates the {@link ExtensionModule} instances that are applicable for the supplied
     * {@link RequestContext}. 
     * @param context The {@link RequestContext} to evaluate modules for.
     * @return A {@link List} of {@link ExtensionModule} instances.
     */
    public List<ExtensionModule> evaluateModules(RequestContext context);
    
    /**
     * <p>Returns the JavaScript dependencies for the current extension module for the supplied path.</p>
     * @param module The module to retrieve JavaScript dependencies from.
     * @param path The current path being processed. This determines which dependencies are mapped.
     * @return A list of JavaScript dependencies
     */
    public LinkedHashSet<String> getModuleJsDeps(ExtensionModule module, String path);

    /**
     * <p>Returns the CSS dependencies for the current extension module for the supplied path.</p>
     * @param module The module to retrieve CSS dependencies from.
     * @param path The current path being processed. This determines which dependencies are mapped.
     * @return A list of CSS dependencies
     */
    public Map<String, LinkedHashSet<String>> getModuleCssDeps(ExtensionModule module, String path);
    
    /**
     * <p>Returns a {@link List} of the files that should be applied to an {@link ExtensibilityModel}
     * being processed.</p>
     * @param pathBeingProcessed The path of the file being processed. This will typically be a FreeMarker
     * template, JavaScript controller or NLS properties file.
     * 
     * @return A {@link List} of the files that extend the current file being processed.
     */
    public List<String> getExtendingModuleFiles(ExtensionModule module, String pathBeingProcessed);
}
