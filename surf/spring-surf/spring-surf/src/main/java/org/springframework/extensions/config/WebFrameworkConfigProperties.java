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

package org.springframework.extensions.config;

import org.springframework.extensions.config.WebFrameworkConfigElement.ErrorHandlerDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.FormatDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.PersisterConfigDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.ResourceLoaderDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.ResourceResolverDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.RuntimeConfigDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.SystemPageDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.TagLibraryDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.TypeDescriptor;

/**
 * Web Framework configuration interface
 * 
 * @author muzquiano
 */
public interface WebFrameworkConfigProperties
{
    // formats
    public String[] getFormatIds();
    public FormatDescriptor getFormatDescriptor(String id);
    
    // error handlers
    public String[] getErrorHandlerIds();
    public ErrorHandlerDescriptor getErrorHandlerDescriptor(String id);
    
    // system pages
    public String[] getSystemPageIds();
    public SystemPageDescriptor getSystemPageDescriptor(String id);
    
    // tag libraries
    public String[] getTagLibraryIds();
    public TagLibraryDescriptor getTagLibraryDescriptor(String id);
    
    // resource loaders
    public String[] getResourceLoaderIds();
    public ResourceLoaderDescriptor getResourceLoaderDescriptor(String id);
    
    // resource resolvers
    public String[] getResourceResolverIds();    
    public ResourceResolverDescriptor getResourceResolverDescriptor(String id);    
    
    // debug
    public boolean isTimerEnabled();

    // default services
    public String getDefaultUserFactoryId();

    // default application settings
    public String getDefaultFormatId();
    public String getDefaultRegionChrome();
    public String getDefaultComponentChrome();
    public String[] getDefaultPageTypeIds();
    public String getDefaultPageTypeInstanceId(String id);
    public String getDefaultThemeId();
    public String getDefaultSiteConfigurationId();
    
    // persister
    public String getDefaultPersisterId();
            
    // object types
    public String[] getTypeIds();
    public TypeDescriptor getTypeDescriptor(String id);
    
    // persister configuration
    public PersisterConfigDescriptor getPersisterConfigDescriptor();
    
    /** AUTOWIRE HELPERS **/
    
    // autowire runtime properties
    public String getAutowireRuntimeId();
    
    // autowire mode properties
    public String getAutowireModeId();    
    public boolean isAutowireModeDevelopment();
    public boolean isAutowireModeProduction();
            
    // runtime config
    public RuntimeConfigDescriptor getRuntimeConfigDescriptor(String id);
    
    public static final String MANUAL_MODULE_DEPLOYMENT = "manual";
    public static final String AUTO_MODULE_DEPLOYMENT = "auto";
    
    public String getModuleDeploymentMode();
}
