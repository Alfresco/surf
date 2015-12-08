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
package org.springframework.extensions.surf.extensibility.impl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletOutputStream;

public class ExtensibilityServletOutputStream extends ServletOutputStream
{
    private Writer modelWriter = null;
    
    public ExtensibilityServletOutputStream(Writer modelWriter)
    {
        super();
        this.modelWriter = modelWriter;
    }

    @Override
    public void write(int b) throws IOException
    {
        this.modelWriter.write(b);
    }

    @Override
    public void print(String s) throws IOException
    {
        this.modelWriter.write(s);
    }

    @Override
    public void print(char c) throws IOException
    {
        this.modelWriter.write(c);
    }

    @Override
    public void flush() throws IOException
    {
        this.modelWriter.flush();
    }

    @Override
    public void close() throws IOException
    {
        this.modelWriter.close();
    }

}
