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

package org.springframework.extensions.surf.mvc;

import org.springframework.web.servlet.mvc.UrlFilenameViewController;

/**
 * Default URL View Controller - extends the Spring UrlFilenameViewController to
 * fix the issue where the view name from a URL is determined to be located up to
 * the last index of the "." character - but does not check for further URL elements.
 * 
 * For example:
 *  /products/something = products/something
 *  /page/mrsmith/dashboard = page/mrsmith/dashboard
 *  /page/mr.smith/dashboard = page/mr <-- incorrect! should resolve to: page/mr.smith/dashboard
 * 
 * @author Kevin Roast
 */
public class UrlViewController extends UrlFilenameViewController
{
    /**
     * Extract the URL filename from the given request URI.
     * @param uri the request URI; for example <code>"/index.html"</code>
     * @return the extracted URI filename; for example <code>"index"</code>
     */
    @Override
    protected String extractViewNameFromUrlPath(final String uri)
    {
        return uri.substring((uri.charAt(0) == '/' ? 1 : 0));
    }
}