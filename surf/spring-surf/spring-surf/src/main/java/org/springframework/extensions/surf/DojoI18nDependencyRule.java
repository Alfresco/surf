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
import java.util.regex.Pattern;

import org.springframework.extensions.config.WebFrameworkConfigElement;

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
public class DojoI18nDependencyRule extends DojoDependencyRule
{
    /**
     * <p>Overrides the default implementation to retrieve both the CSS dependency path and the
     * media type that the CSS path should be used against.</p>
     */
    @Override
    protected void processDependency(String dependency, 
                                     String sourcePath, 
                                     String sourceContents,
                                     Matcher matcher, 
                                     DojoDependencies dependencies)
    {
        // Try to find the widget name in the source...
        String widgetName = null;
        Matcher m1 = this.widgetNamePattern.matcher(sourceContents);
        if (m1.find())
        {
            if (m1.groupCount() >= this.widgetNameTargetGroup)
            {
                widgetName = m1.group(this.widgetNameTargetGroup);
            }    
        }
        
        // Get the i18n dependency path and use the widgetName (or default scope) for scoping the i18n properties 
        String i18nPath = getDojoDependencyHandler().getPath(sourcePath, matcher.group(1));
        if (widgetName == null || widgetName.equals(""))
        {
            widgetName = this.webFrameworkConfigElement.getDojoMessagesDefaultScope();
        }
        
        dependencies.addI18nDep(i18nPath, widgetName);
    }

    /**
     * This String should be set by the Spring application context when this rule is instantiated. It will be
     * used to compile the <code>widgetNamePattern</code> and should be the Regular Expression to use to 
     * identify the name assigned to the widget. The widget name is used in this case for scoping i18n messages
     * associated with the widget. If no widget name is provided then the value assigned to the <code>defaultI18nScope</code>
     * will be used.
     */
    private String widgetNamePatternString;
    
    /**
     * The {@link Pattern} compiled from the <code>widgetNamePatternString</code> attribute.
     */
    private Pattern widgetNamePattern;
    
    /**
     * The group that identifies the widgetName from the processing the regular expression for finding the widget name.
     * This should be set via the Spring application context.
     */
    private int widgetNameTargetGroup;
    
    private WebFrameworkConfigElement webFrameworkConfigElement;
    
    public String getWidgetNamePatternString()
    {
        return widgetNamePatternString;
    }
    
    public void setWidgetNamePatternString(String widgetNamePatternString)
    {
        this.widgetNamePatternString = widgetNamePatternString;
        this.widgetNamePattern = Pattern.compile(widgetNamePatternString);
    }
    
    public int getWidgetNameTargetGroup()
    {
        return widgetNameTargetGroup;
    }
    
    public void setWidgetNameTargetGroup(int widgetNameTargetGroup)
    {
        this.widgetNameTargetGroup = widgetNameTargetGroup;
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
