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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Kevin Roast
 */
public class ExternalJavaScriptCompressionHandler implements JavaScriptCompressionHandler
{
    private static final Log logger = LogFactory.getLog(ExternalJavaScriptCompressionHandler.class);
    
    private String cmd;
    
    /**
     * @param cmd   The external cmd to execute. For example Node uglifyjs this would be "uglifyjs -".
     *              The command must be able to accept JavaScript source as stdin and return output from stdout.
     */
    public void setCmd(String cmd)
    {
        this.cmd = cmd;
    }
    
    @Override
    public void compress(Reader reader, Writer writer) throws IOException
    {
        if (this.cmd == null || cmd.length() == 0)
        {
            throw new IllegalArgumentException("External compressor 'cmd' not set correctly in bean config.");
        }
        
        // setup our external process and retrieve streams - IO exception is handled in caller
        Process proc = Runtime.getRuntime().exec(this.cmd);
        
        // if we get here, retrieve the streams for processing
        BufferedWriter stdIn = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
        BufferedReader stdOut = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        
        try
        {
            // push javascript source file to stdIn
            IOUtils.copy(reader, stdIn);
            stdIn.close();
            
            // read the output from the command
            String s;
            while ((s = stdOut.readLine()) != null) {
                writer.append(s);
            }
            stdOut.close();
            
            // read any errors from the attempted command
            if ((s = stdError.readLine()) != null)
            {
                // error occured, collect information and throw exception with the message
                StringBuilder buf = new StringBuilder("Error during external JavaScript compilation:\r\n");
                do {
                    buf.append(s);
                } while ((s = stdError.readLine()) != null);
                stdError.close();
                throw new IOException(buf.toString());
            }
        }
        finally
        {
            stdError.close();
            stdOut.close();
            stdIn.close();
        }
    }
}
