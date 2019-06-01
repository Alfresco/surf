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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Defines key information required for handling dependency resources.</p> 
 * 
 * @author David Draper
 * @author Kevin Roast
 */
public final class DependencyResource
{
    private static Log logger = LogFactory.getLog(DependencyResource.class);

    private final String mimetype;
    private final byte[] content;
    private final String charset;
    private int length;
    
    /**
     * Create a Dependency Resource - compressing the given content before storing it.
     */
    public DependencyResource(String mimetype, String content, String charset)
    {
        this.mimetype = mimetype;
        this.charset = charset;
        this.length = content.length();
        try
        {
            // GZIP the content string into an array of compressed bytes
            ByteArrayOutputStream bao = new ByteArrayOutputStream(this.length >> 2 > 32 ? this.length >> 2 : 32);
            GZIPOutputStream gzos = new GZIPOutputStream(bao);
            gzos.write(content.getBytes(charset));
            gzos.close();
            this.content = bao.toByteArray();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String getMimetype()
    {
        return mimetype;
    }

    public byte[] getContent()
    {
        try
        {
            // unzip our byte array and return the raw byte data - this is generally streamed back to a client
            GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(this.content));
            byte[] buf = new byte[4096*2];
            ByteArrayOutputStream bos = new ByteArrayOutputStream(this.length);
            int len;
            while ((len=gzip.read(buf, 0, buf.length)) != -1)
            {
               bos.write(buf, 0, len);
            }
            bos.close();
            gzip.close();
            return bos.toByteArray();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /*package*/ int getStoredSize()
    {
        return this.content.length;
    }
    
    @Override
    public String toString()
    {
        try
        {
            return new String(getContent(), this.charset);
        }
        catch (IOException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(e.getMessage(), e);
            }
        }

        return "";
    }
}
