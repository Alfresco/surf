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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.SearchPath;
import org.springframework.extensions.webscripts.Store;

public class RemoteResourcesHandler
{
    private static final Log logger = LogFactory.getLog(RemoteResourcesHandler.class);
    
    /**
     * <p>This a reference to the {@link SearchPath} that identifies the {@link Store} objects to search through. This
     * should be configured via the Spring bean application context.</p>
     */
    private SearchPath searchPath;
    
    /**
     * <p>This defines a list of the acceptable path filters that can be searched on. Paths are filtered to prevent
     * unnecessary remote requests being made. Although the {@link RemoteResourcesHandler} is searched first the requests
     * it is allowed to make needs to be carefully managed to avoid negative performance impacts.</p>
     */
    private List<String> filters;
    
    /**
     * <p>Attempts to retrieve the supplied path from a remote resource. A remote request will only be made if the path
     * starts with one of the filters defined. This ensures that unnecessary requests are not made that will negatively
     * impact performance.</p>
     * 
     * @param path The path to find.
     * @return An {@link InputStream} to the requested path or <code>null</code> if it cannot be found.
     */
    public InputStream getRemoteResource(String path)
    {
        InputStream in = null;
        if (this.filters != null)
        {
            for (String filter: this.filters)
            {
                if (path.startsWith(filter))
                {
                    for (Store store : this.searchPath.getStores())
                    {
                        try
                        {
                            in = store.getDocument(processPath(path));
                            break;
                        }
                        catch (IOException e)
                        {
                            // This log is commented out to prevent verbose output...
                            //logger.error("Error occurred obtaining remote resource: '" + path + "'", e);
                        }
                    }
                }
            }
        }
        return in;
    }

    /**
     * By default this method simply returns the path provided. The method is provided for extensions
     * to have the opportunity to manipulate the supplied path before any resource resolving is attempted.
     * 
     * @return String
     */
    protected String processPath(String path)
    {
        return path;
    }
    
    public SearchPath getSearchPath()
    {
        return searchPath;
    }

    public void setSearchPath(SearchPath searchPath)
    {
        this.searchPath = searchPath;
    }

    public List<String> getFilters()
    {
        return filters;
    }

    public void setFilters(List<String> filters)
    {
        this.filters = filters;
    }
}
