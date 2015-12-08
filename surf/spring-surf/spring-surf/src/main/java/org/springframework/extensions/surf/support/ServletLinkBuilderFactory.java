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

package org.springframework.extensions.surf.support;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.LinkBuilder;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.resource.ResourceService;

/**
 * The servlet implementation of LinkBuilderFactory
 * 
 * @author muzquiano
 */
public class ServletLinkBuilderFactory extends AbstractLinkBuilderFactory
{
    protected String pageUri;
    protected String pageTypeUri;
    protected String objectUri;
    
    /* (non-Javadoc)
     * @see org.springframework.extensions.surf.support.AbstractLinkBuilderFactory#newInstance()
     */
    public LinkBuilder newInstance()
    {
        WebFrameworkConfigElement webFrameworkConfigElement = getWebFrameworkConfigElement();
        ModelObjectService modelObjectService = getModelObjectService();
        ResourceService resourceService = getResourceService();
        ServletLinkBuilder linkBuilder = new ServletLinkBuilder(webFrameworkConfigElement, modelObjectService, resourceService);
        linkBuilder.setPageTypeUri(pageTypeUri);
        linkBuilder.setPageUri(pageUri);        
        return linkBuilder;
    }
    
    /**
     * Specifies the uri base for dispatching to pages
     * 
     * @param pageUri String
     */
    public void setPageUri(String pageUri)
    {
        this.pageUri = pageUri;
    }
    
    /**
     * Specifies the uri base for dispatching to page types
     * 
     * @param pageTypeUri String
     */
    public void setPageTypeUri(String pageTypeUri)
    {
        this.pageTypeUri = pageTypeUri;
    }
    
    /**
     * Specifies the uri base for dispatching to objects
     * 
     * @param objectUri String
     */
    public void setObjectUri(String objectUri)
    {
        this.objectUri = objectUri;
    }
}
