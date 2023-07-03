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
import java.io.PrintWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import org.springframework.extensions.surf.extensibility.ExtensibilityModel;

public class ExtensibilityHttpResponse extends HttpServletResponseWrapper
{
    private ExtensibilityModel model = null;
    
    public ExtensibilityHttpResponse(HttpServletResponse response, ExtensibilityModel model)
    {
        super(response);
        this.model = model;
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        return new PrintWriter(model.getWriter());
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        ExtensibilityServletOutputStream os = new ExtensibilityServletOutputStream(model.getWriter());
        return os;
    }

    @Override
    public void flushBuffer() throws IOException
    {
        this.model.getWriter().flush();
    }

    @Override
    public void reset()
    {
        super.reset();
    }

    @Override
    public void resetBuffer()
    {
        super.resetBuffer();
    }
}
