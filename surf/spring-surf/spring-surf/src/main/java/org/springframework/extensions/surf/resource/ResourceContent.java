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

package org.springframework.extensions.surf.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.springframework.extensions.surf.FrameworkBean;

/**
 * Interface to describe content around a Resource.
 * 
 * @author muzquiano
 * @author kevinr
 */
public interface ResourceContent
{
    /**
     * A link back to the resource of which this content is a part.
     * 
     * @return Resource
     */
    public Resource getResource();
    
    /**
     * Gets the bytes. Use with caution only, if you know the content encoding.
     * 
     * @return the bytes
     */
    public byte[] getBytes() throws IOException;
        
    /**
     * Retrieves an input stream to the resource content.
     * Use with caution only, if you know the content encoding.
     * 
     * @return the input stream
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public InputStream getInputStream() throws IOException;
    
    /**
     * Gets the reader for the resource content. Reader will use the
     * appropriate character encoding as specified in the resource response.
     * 
     * @return the reader
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Reader getReader() throws IOException;
    
    /**
     * Returns the String content for the resource. Will use the appropriate
     * character encoding as specified in the resource response.
     * 
     * @return String content for the resource
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getStringContent() throws IOException;
}
