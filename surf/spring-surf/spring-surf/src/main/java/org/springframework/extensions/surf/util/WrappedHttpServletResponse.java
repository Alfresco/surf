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

package org.springframework.extensions.surf.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A wrapper class for buffering around HttpServletResponse objects.
 * The output is trapped and retrievable from this object.
 * 
 * @author muzquiano
 */
public class WrappedHttpServletResponse extends HttpServletResponseWrapper
{
    /**
     * Instantiates a new wrapped http servlet response.
     * 
     * @param response the response
     */
    public WrappedHttpServletResponse(HttpServletResponse response)
    {
        super(response);
        this.outputStream = new ByteArrayOutputStream();
        this.printWriter = new PrintWriter(outputStream);
    }

    /** The print writer. */
    private PrintWriter printWriter = null;
    
    /** The output stream. */
    private ByteArrayOutputStream outputStream = null;

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getWriter()
     */
    public PrintWriter getWriter() throws IOException
    {
        // return this instead of the output stream
        return printWriter;
    }

    /**
     * Gets the output.
     * 
     * @return the output
     */
    public String getOutput()
    {
        printWriter.flush();
        printWriter.close();
        return this.outputStream.toString();
    }
}
