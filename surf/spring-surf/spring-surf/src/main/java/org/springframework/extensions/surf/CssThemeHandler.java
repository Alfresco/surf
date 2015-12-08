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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.surf.types.Theme;

/**
 * This class handles the detection of tokens defined in themes and handles their substitution within 
 * CSS files.
 * 
 * @author David Draper
 */
public class CssThemeHandler
{

    /**
     * This is the Regular Expression token used to find tokens within a CSS source file
     */
    private String tokenRegex = null;
    
    /**
     * Gets the Regular Expression used to find tokens within a CSS source file.
     * @return String
     */
    public String getTokenRegex()
    {
        return tokenRegex;
    }

    /**
     * Allows the Regular Expression used to find tokens within a CSS source file to be set. This setter
     * also compiles the Regular Expression for use within the bean.
     * 
     * @param tokenRegex String
     */
    public void setTokenRegex(String tokenRegex)
    {
        this.tokenRegex = tokenRegex;
        this.sourceTokenPattern = Pattern.compile(this.tokenRegex);
    }

    /**
     * The target {@link Matcher} group that identifies the group of dependencies detected by the <code>tokenRegex</code>
     */
    private int targetGroup;
    
    /**
     * The target group matched by the <code>tokenRegex</code>
     * @return int
     */
    public int getTargetGroup()
    {
        return this.targetGroup;
    }

    /**
     * Allows the target group matched by the <code>tokenRegex</code> to be set.
     * 
     * @param targetGroup int
     */
    public void setTargetGroup(int targetGroup)
    {
        this.targetGroup = targetGroup;
    }
    
    /**
     * The configuration that will enable us to access any default LESS configuration.
     */
    private WebFrameworkConfigElement webFrameworkConfigElement;
    
    /**
     * Returns the configuration for the web framework.
     * 
     * @return WebFrameworkConfigElement
     */
    public WebFrameworkConfigElement getWebFrameworkConfigElement()
    {
        return webFrameworkConfigElement;
    }

    /**
     * Required by Spring to inject the web framework configuration.
     * 
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     */
    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }
    
    private DependencyHandler dependencyHandler;
    
    public DependencyHandler getDependencyHandler()
    {
        return dependencyHandler;
    }

    public void setDependencyHandler(DependencyHandler dependencyHandler)
    {
        this.dependencyHandler = dependencyHandler;
    }

    /**
     * The compiled Regular Expression defined by the tokenRegex variable.
     */
    private Pattern sourceTokenPattern = null;
    
    public void determineThemeTokens()
    {
        Theme currentTheme = ThreadLocalRequestContext.getRequestContext().getTheme();
        if (currentTheme == null)
        {
            currentTheme = ThreadLocalRequestContext.getRequestContext().getObjectService().getTheme("default");
        }
        this.tokenMap.putAll(currentTheme.getCssTokens());
    }
    
    /**
     * A map of the tokens for the theme to the values they should be replaced with
     */
    Map<String, String> tokenMap = new HashMap<String, String>();
    
    /**
     * 
     * @return Map
     */
    public Map<String, String> getTokenMap()
    {
        this.determineThemeTokens();
        return this.tokenMap;
    }
    
    protected String processCssThemeTokens(String path, StringBuilder cssContents) throws IOException
    {
        StringBuffer processedOutput = new StringBuffer();
        Matcher m = sourceTokenPattern.matcher(cssContents);
        while(m.find())
        {
            // Get the matched token and see if it exists in the map...
            String token = m.group(this.getTargetGroup());
            String replacement = this.getTokenMap().get(token);
            if (replacement == null)
            {
                // No replacement available - change to a comment to indicate what's happened...
                m.appendReplacement(processedOutput, "/*TOKEN NOT FOUND*/");
                
            }
            else
            {
                // Replace the token with the matched value...
                m.appendReplacement(processedOutput, replacement);
            }
        }
        m.appendTail(processedOutput);
        return processedOutput.toString();
    }
    
    /**
     * 
     * @param path String
     * @param cssContents StringBuilder
     * @throws IOException
     */
    public String processCssThemes(String path, StringBuilder cssContents) throws IOException
    {
        return processCssThemeTokens(path, cssContents);
    }
    
}
