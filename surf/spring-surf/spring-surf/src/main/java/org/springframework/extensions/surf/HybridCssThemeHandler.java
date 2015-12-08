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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asual.lesscss.LessException;

/**
 * This extends the {@link LessForJavaCssThemeHandler} and adds in support for a hybrid of two differing approaches:
 * <ul><li>The simple token substitution approach used for Alfresco Share 4.2</li>
 * <li>Using LESS for advanced CSS capabilities</li></ul>
 * This hybrid is required during the interim of modifying the widgets that were originally written to just use
 * the simple token substitution with LESS capabilities. This is <b>currently</b> the default Surf CSS Theme handler bean.
 * 
 * @author Dave Draper
 */
public class HybridCssThemeHandler extends LessForJavaCssThemeHandler
{
    private static final Log logger = LogFactory.getLog(HybridCssThemeHandler.class);
    
    
    /**
     * Overrides the default implementation to add LESS processing capabilities.
     * 
     * @param path The path of the file being processed (used only for error output)
     * @param cssContents The CSS to process
     * @throws IOException when accessing file contents.
     */
    @Override
    public String processCssThemes(String path, StringBuilder cssContents) throws IOException
    {
        String compiledCss = null;
        String initialProcessResults = super.processCssThemeTokens(path, cssContents);
        String lessVariables = this.getLessVariables();
        String fullCSS = lessVariables + initialProcessResults;
        try
        {
            compiledCss = this.engine.compile(fullCSS.toString());
        }
        catch (LessException e)
        {
            compiledCss = "/*" + logLessException(e, path) + "*/\n\n " + initialProcessResults;
        }
        catch (ClassCastException e)
        {
            compiledCss = "/*" + logLessException(e, path) + "*/\n\n " + initialProcessResults;
        }
        return compiledCss;
    }
}
