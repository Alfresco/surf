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

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.exception.ResourceLoaderException;
import org.springframework.extensions.surf.resource.AbstractCachingResourceLoader;
import org.springframework.extensions.surf.resource.Resource;

/**
 * Resource loader implementation for the Alfresco repository
 * 
 * @author muzquiano
 */
public class AlfrescoResourceLoader extends AbstractCachingResourceLoader
{
    public AlfrescoResourceLoader(String protocolId, String endpointId, FrameworkBean frameworkUtil)
    {
        super(protocolId, endpointId, frameworkUtil);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.resource.AbstractCachingResourceLoader#buildResource(java.lang.String)
     */
    public Resource buildResource(String objectId)
        throws ResourceLoaderException
    {
        return new AlfrescoResource(getProtocolId(), getEndpointId(), objectId, frameworkUtil);
    }
}
