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

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Provides helper methods for encoding and decoding strings into a given
 * encoding scheme.
 * 
 * @author muzquiano
 */
public class EncodingUtil
{
    
    /** The DEFAULT encoding. */
    public static final String DEFAULT_ENCODING = "utf-8";

    /**
     * Encodes the given String into the default encoding. The default encoding
     * is specified by DEFAULT_ENCODING.
     * 
     * If the encoding is unable to be performed, null is returned.
     * 
     * @param input
     *            The String to be encoded
     * 
     * @return The encoded String
     */
    public static String encode(String input)
    {
        return encode(input, DEFAULT_ENCODING);
    }

    /**
     * Encodes the given String into the given encoding.
     * 
     * If the encoding is unable to be performed, null is returned.
     * 
     * @param input
     *            The String to be encoded
     * @param encoding
     *            The encoding to be used
     * 
     * @return The encoded String
     */
    public static String encode(String input, String encoding)
    {
        String output = null;
        try
        {
            output = URLEncoder.encode(input, encoding);
        }
        catch (Exception ex)
        {
        }
        return output;
    }

    /**
     * Decode.
     * 
     * @param input
     *            the input
     * 
     * @return the string
     */
    public static String decode(String input)
    {
        return decode(input, DEFAULT_ENCODING);
    }

    /**
     * Decode.
     * 
     * @param input
     *            the input
     * @param encoding
     *            the encoding
     * 
     * @return the string
     */
    public static String decode(String input, String encoding)
    {
        String output = null;
        try
        {
            output = URLDecoder.decode(input, encoding);
        }
        catch (Exception ex)
        {
        }
        return output;
    }

}
