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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>Instances of this class are used to record all of the different Dojo dependencies that are required for any given Dojo JavaScript file.
 * The dependencies are broken down into the following types:
 * <ul><li>JavaScript dependency - typically another Dojo file</li>
 * <li>Text dependency - typically an HTML template for a Dojo widget</li>
 * <li>CSS dependency - typically a CSS file required by a Dojo widget</li>
 * </ul>
 * </p>
 * 
 * @author David Draper
 */
public class DojoDependencies
{
    /**
     * The list of JavaScript dependencies.
     */
    private Set<String> javaScriptDeps = new LinkedHashSet<String>();
    
    /**
     * The list of text dependencies.
     */
    private Set<String> textDeps = new LinkedHashSet<String>();
    
    /**
     * The list of CSS dependencies.
     */
    private Set<CssDependency> cssDeps = new LinkedHashSet<CssDependency>();
    
    /**
     * The list of basic i18n dependencies - these are not the Dojo specific i18n dependencies.
     */
    private Set<I18nDependency> i18nDeps = new LinkedHashSet<I18nDependency>();
    
    /**
     * The list of dependencies that fall outside the Dojo AMD handling remit.
     */
    private Set<String> nonAmdDependencies = new LinkedHashSet<String>();
    
    /**
     * Adds a new JavaScript dependency.
     * @param javaScriptDep String
     */
    public void addJavaScriptDep(String javaScriptDep)
    {
        this.javaScriptDeps.add(javaScriptDep);
    }
    
    /**
     * Adds a new text dependency
     * @param textDep String
     */
    public void addTextDep(String textDep)
    {
        this.textDeps.add(textDep);
    }
    
    /**
     * Adds a new CSS dependency
     * @param path The dependency path
     * @param mediaType The CSS media type
     */
    public void addCssDep(String path, String mediaType)
    {
        this.cssDeps.add(new CssDependency(path, mediaType));
    }
    
    /**
     * Adds a new i18n dependency
     * @param path String
     * @param scope String
     */
    public void addI18nDep(String path, String scope)
    {
        this.i18nDeps.add(new I18nDependency(path, scope));
    }
    
    /**
     * Adds a new non-AMD dependency
     * @param path String
     */
    public void addNonAmdDep(String path)
    {
        this.nonAmdDependencies.add(path);
    }
    
    /**
     * @return The set of JavaScript dependencies.
     */
    public Set<String> getJavaScriptDeps()
    {
        return this.javaScriptDeps;
    }
    
    /**
     * @return The set of text dependencies.
     */
    public Set<String> getTextDeps()
    {
        return this.textDeps;
    }
    
    /**
     * @return The set of CSS dependencies.
     */
    public Set<CssDependency> getCssDeps()
    {
        return this.cssDeps;
    }
    
    /**
     * @return The set of i18n dependencies
     */
    public Set<I18nDependency> getI18nDeps()
    {
        return i18nDeps;
    }

    /**
     * @return The set of non-AMD dependencies
     */
    public Set<String> getNonAmdDependencies()
    {
        return nonAmdDependencies;
    }

    @Override
    public String toString()
    {
        return "JavaScript Deps=" + this.javaScriptDeps.toString() + ", CSS Deps=" + this.cssDeps.toString() + ", Text Deps=" + this.textDeps.toString();
    }

    /**
     * An inner class for defining CSS depdendencies.
     * @author David Draper
     *
     */
    public class CssDependency
    {
        private String path;
        private String mediaType;
        public CssDependency(String path, String mediaType)
        {
            this.path = path;
            this.mediaType = mediaType;
        }
        public String getPath()
        {
            return this.path;
        }
        public String getMediaType()
        {
            return this.mediaType;
        }
        @Override
        public String toString()
        {
            return "Path=" + this.path + ", media type=" + this.mediaType;
        }
    }
    
    /**
     * An inner class for defining CSS depdendencies.
     * @author David Draper
     *
     */
    public class I18nDependency
    {
        private String path;
        private String scope;
        public I18nDependency(String path, String scope)
        {
            this.path = path;
            this.scope = scope;
        }
        public String getPath()
        {
            return this.path;
        }
        public String getScope()
        {
            return this.scope;
        }
        @Override
        public String toString()
        {
            return "Path=" + this.path + ", scope=" + this.scope;
        }
    }
}