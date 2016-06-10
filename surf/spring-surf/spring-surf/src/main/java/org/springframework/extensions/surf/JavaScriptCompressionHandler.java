/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
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

/**
 * Contract for an implementation of a JavaScript compression library.
 * <p>
 * Typically uses the default YUI+Rhino implementation of ES3(and nearly ES5) compression.
 * 
 * @author Kevin Roast
 * @since 6.7
 */
public interface JavaScriptCompressionHandler
{
    public void compress(Reader reader, Writer writer) throws IOException;
}
