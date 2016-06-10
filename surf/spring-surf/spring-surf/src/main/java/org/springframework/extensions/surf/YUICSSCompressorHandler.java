/*
 * Copyright (C) 2005-2016 Alfresco Software Limited.
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
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import com.yahoo.platform.yui.compressor.CssCompressor;

/**
 * YUI+RhinoJS based implementation of CSS compression handler.
 * 
 * NOTE: code extracted from {@link DependencyAggregator}
 * 
 * @since 6.7
 * @author Kevin Roast
 */
public class YUICSSCompressorHandler implements CSSCompressionHandler
{
    private int linebreak = -1;
    
    @Override
    public void compress(Reader reader, Writer writer) throws IOException
    {
        CssCompressor cssc = new CssCompressor(reader);
        reader.close();
        cssc.compress(writer, linebreak);
    }
    
    public void setLinebreak(int linebreak)
    {
        this.linebreak = linebreak;
    }

}
