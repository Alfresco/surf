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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.StringBuilderWriter;

import com.google.javascript.jscomp.BasicErrorManager;
import com.google.javascript.jscomp.CheckLevel;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.CompilerOptions.LanguageMode;
import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.LightweightMessageFormatter;
import com.google.javascript.jscomp.MessageFormatter;
import com.google.javascript.jscomp.PropertyRenamingPolicy;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.VariableRenamingPolicy;

/**
 * JavaScript Compressor using the Google Closure compiler library to perform the compression step.
 * 
 * @author Kevin Roast
 */
public class ClosureJavaScriptCompressionHandler implements JavaScriptCompressionHandler
{
    private static final Log logger = LogFactory.getLog(ClosureJavaScriptCompressionHandler.class);
    
    private boolean whitespaceOnly = false;
    /**
     * @param whitespaceOnly    Closure Compiler setting - whitespace processing only no optimizations will be used
     */
    public void setWhitespaceOnly(boolean whitespaceOnly)
    {
        this.whitespaceOnly = whitespaceOnly;
    }
    
    @Override
    public void compress(Reader reader, Writer writer) throws IOException
    {
        Compiler compiler = new Compiler(new ClosureErrorManager());
        
        CompilerOptions options = new CompilerOptions();
        if (whitespaceOnly)
        {
            CompilationLevel.WHITESPACE_ONLY.setOptionsForCompilationLevel(options);
        }
        else
        {
            options.setClosurePass(true);
            options.setRenamingPolicy(VariableRenamingPolicy.LOCAL, PropertyRenamingPolicy.OFF);
            // TODO: examine what other options from CompilationLevel.applySafeCompilationOptions() are safe for us
        }
        
        // ES5 support
        options.setLanguage(LanguageMode.ECMASCRIPT5);
        // Mandatory for the DojoDependencyHandler to process inline dependencies via regex's
        options.setLineLengthThreshold(0);
        
        // The dummy input name "input.js" is used here so that any warnings or
        // errors will cite line numbers in terms of input.js.
        StringBuilderWriter sw = new StringBuilderWriter(512);
        IOUtils.copy(reader, sw);
        reader.close();
        SourceFile input = SourceFile.fromCode("input.js", sw.toString());
        List<SourceFile> inputs = new ArrayList<>(1);
        inputs.add(input);
        
        // compile() returns a Result, but it is not needed here.
        compiler.compile(Collections.<SourceFile>emptyList(), inputs, options);
        
        // The compiler is responsible for generating the compiled code; it is not
        // accessible via the Result.
        String comp = compiler.toSource();
        writer.append(comp);
        writer.close();
    }
    
    private static class ClosureErrorManager extends BasicErrorManager
    {
        private final MessageFormatter formatter;
        
        private ClosureErrorManager()
        {
            formatter = LightweightMessageFormatter.withoutSource();
        }
        
        @Override
        public void println(CheckLevel level, JSError error)
        {
            switch (level) {
              case ERROR:
                logger.error(error.format(level, formatter));
                break;
              case WARNING:
                logger.debug(error.format(level, formatter));
                break;
              case OFF:
                break;
            }
        }

        @Override
        protected void printSummary()
        {
            // no summary required
        }
    }
}
