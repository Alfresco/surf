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
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.yahoo.platform.yui.javascript.ErrorReporter;
import com.yahoo.platform.yui.javascript.EvaluatorException;

/**
 * YUI+RhinoJS based implementation of JavaScript compression handler. This compressor can handle ES3 and
 * a large subset of ES5. Unfortunately it has issues with some ES5 features - including ES3 'reserved' words
 * being used in ES5 code such as 'int' or 'char' as variable names. Therefore it is not suitable for modern
 * ES5 code such as the unmodified source of pdf.js for example.
 * 
 * NOTE: code extracted from {@link DependencyAggregator}
 * 
 * @since 6.7
 * @author Kevin Roast
 */
public class YUIJavaScriptCompressorHandler implements JavaScriptCompressionHandler
{
    private static final Log logger = LogFactory.getLog(YUIJavaScriptCompressorHandler.class);
    
    private String charset = "UTF-8";
    private int linebreak = -1;
    private boolean munge = true;
    private boolean verbose = false;
    private boolean preserveAllSemiColons = false;
    private boolean disableOptimizations = false;
    
    @Override
    public void compress(Reader reader, Writer writer) throws IOException
    {
        JavaScriptCompressor jsc = new JavaScriptCompressor(reader, new YuiCompressorErrorReporter());
        reader.close();
        jsc.compress(writer, linebreak, munge, verbose, preserveAllSemiColons, disableOptimizations);
    }
    
    /* ****************************************************
     *                                                    *
     * SPRING BEAN SETTERS FOR CONFIGURING YUI COMPRESSOR *
     *                                                    *
     ******************************************************/
    
    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    public void setLinebreak(int linebreak)
    {
        this.linebreak = linebreak;
    }

    public void setMunge(boolean munge)
    {
        this.munge = munge;
    }

    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    public void setPreserveAllSemiColons(boolean preserveAllSemiColons)
    {
        this.preserveAllSemiColons = preserveAllSemiColons;
    }

    public void setDisableOptimizations(boolean disableOptimizations)
    {
        this.disableOptimizations = disableOptimizations;
    }
    
    /**
     * Inner class for handling of YUI Compressor errors
     */
    private static class YuiCompressorErrorReporter implements ErrorReporter
    {
        public void warning(String message, String sourceName, int line, String lineSource, int lineOffset)
        {
            if (line < 0)
            {
                logger.warn(message);
            }
            else
            {
                logger.warn(line + ':' + lineOffset + ':' + message);
            }
        }

        public void error(String message, String sourceName, int line, String lineSource, int lineOffset)
        {
            if (line < 0)
            {
                logger.error(message);
            }
            else
            {
                logger.error(line + ':' + lineOffset + ':' + message);
            }
        }

        public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
                int lineOffset)
        {
            error(message, sourceName, line, lineSource, lineOffset);
            return new EvaluatorException(message);
        }
    }
}
