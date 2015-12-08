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

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;

/**
 * This is a pure LESS driven CSS Theme Handler. Uses the Java+Rhino implementation of LESS.
 * 
 * @author Dave Draper
 */
public class LessForJavaCssThemeHandler extends LessCssThemeHandler
{
    /**
     * The engine to use for LESS processing.
     */
    protected LessEngine engine;
    
    /**
     * Sets up a new {@link LessEngine} instance.
     */
    public LessForJavaCssThemeHandler()
    {
        this.engine = new LessEngine();
    }

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
        String fullCSS = this.getLessVariables() + cssContents;
        try
        {
            compiledCss = this.engine.compile(fullCSS);
        }
        catch (LessException e)
        {
            compiledCss = "/*" + logLessException(e, path) + "*/\n\n " + cssContents;
            
        }
        catch (ClassCastException e)
        {
            compiledCss = "/*" + logLessException(e, path) + "*/\n\n " + cssContents;
        }
        return compiledCss;
    }
}
