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

package org.springframework.extensions.surf.resource.support;

import org.springframework.extensions.surf.resource.AbstractCachingResourceLoaderFactory;
import org.springframework.extensions.surf.resource.ResourceLoader;

/**
 * Resource loader factory for general http urls.
 * 
 * @author muzquiano
 */
public class URLResourceLoaderFactory extends AbstractCachingResourceLoaderFactory
{
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.AbstractCachingResourceLoaderFactory#buildResourceLoader(java.lang.String, java.lang.String)
     */
    public ResourceLoader buildResourceLoader(String protocolId, String endpointId)
    {
        return new URLResourceLoader(protocolId, endpointId, frameworkUtils);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.AbstractResourceLoaderFactory#getSupportedProtocols()
     */
    public String[] getSupportedProtocols()
    {
        return new String[] { "http", "https" };
    }
}
