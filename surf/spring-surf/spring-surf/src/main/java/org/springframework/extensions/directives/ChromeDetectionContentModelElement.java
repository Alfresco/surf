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
package org.springframework.extensions.directives;

import org.springframework.extensions.surf.extensibility.ContentModelElement;
import org.springframework.extensions.surf.extensibility.impl.DefaultContentModelElement;

/**
 * <p>A {@link ContentModelElement} that will wrap its contents in a <{@code}div> element with a unique
 * id when Surf Region Chrome is disabled.</p>
 * 
 * @author David Draper
 */
public class ChromeDetectionContentModelElement extends DefaultContentModelElement 
{
    // Constants used for outputting the the <div> containing the unique id...
    public static final String OPEN_ELEMENT_1 = "<div id=\"";
    public static final String OPEN_ELEMENT_2 = "\">\n";
    public static final String CLOSE_ELEMENT = "</div>\n";
    
    /**
     * <p>Instantiates a new {@link ChromeDetectionContentModelElement}</p>
     * 
     * @param id The id of the directive.
     * @param directiveName The name of the directive.
     * @param htmlId The unique HTML ID that should be used as the "id" attribute of a wrapping <{@code}div> element
     * when Surf Region Chrome is disabled. If this is <code>null</code> then the <{@code}div> will not be output.
     */
    public ChromeDetectionContentModelElement(String id,
                                              String directiveName,
                                              String htmlId)
    {
        super(id, directiveName);
        this.htmlId = htmlId;
    }

    /**
     * <p>The unique HTML ID to use as the "id" attribute of a wrapping <{@code}div> element when Surf Region Chrome is disabled.</p>
     */
    private String htmlId = null;
    
    /**
     * <p>Overrides the default implementation to wrap the content in a <{@code}div> element with a unique "id" attribute when 
     * Surf Region Chrome is disabled.</p>
     */
    @Override
    public String flushContent()
    {
        StringBuilder content = new StringBuilder();
        if (htmlId != null)
        {
            content.append(OPEN_ELEMENT_1);
            content.append(htmlId);
            content.append(OPEN_ELEMENT_2);
        }
        content.append(super.flushContent());
        if (htmlId != null)
        {
            content.append(CLOSE_ELEMENT);
        }
        
        return content.toString();
    }
}
