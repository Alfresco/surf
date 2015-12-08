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

package org.springframework.extensions.surf.site;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.FrameworkUtil;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.types.Configuration;
import org.springframework.extensions.surf.types.Page;

/**
 * Helper functions for web sites
 * 
 * @author muzquiano
 */
public class SiteUtil
{
    private static Log logger = LogFactory.getLog(FrameworkBean.class);
    
    private static final String DEFAULT_SITE_CONFIGURATION_ID = "default.site.configuration";

    /**
     * Returns the root page for the current request context
     * 
     * @param context the context
     * 
     * @return the root page
     */
    public static Page getRootPage(RequestContext context)
    {
        return getRootPage(context, getSiteConfiguration(context));        
    }
    
    /**
     * Returns the root page for the given site configuration
     * 
     * @param context RequestContext
     * @param siteConfiguration Configuration
     * 
     * @return the root page instance
     */
    public static Page getRootPage(RequestContext context, Configuration siteConfiguration)
    {
        Page rootPage = null;
        
        // check the site configuration
        if (siteConfiguration != null)
        {
            String rootPageId = siteConfiguration.getProperty("root-page");
            if (rootPageId != null)
            {
                Page page = context.getObjectService().getPage(rootPageId);
                if (page != null)
                {
                    rootPage = page;
                }
            }
        }
                
        return rootPage;
    }

    /**
     * Returns the site configuration object to use for the current request.
     * 
     * At present, this is a very simple calculation since we either look to
     * the current application default site id or we use a default.
     * 
     * In the future, we will seek to support multiple site configurations
     * per web application (i.e. one might be for html, another for wireless,
     * another for print channel).
     * 
     * @param context the context
     * 
     * @return the site configuration
     */
    public static Configuration getSiteConfiguration(RequestContext context)
    {
        // try to load the site configuration id specified by the application default
        String siteConfigId = getConfig().getDefaultSiteConfigurationId();
        
        Configuration configuration = (Configuration) context.getObjectService().getConfiguration(siteConfigId);
        if (configuration == null)
        {
            // if nothing was found, try to load the "stock" configuration id
            if (!DEFAULT_SITE_CONFIGURATION_ID.equals(siteConfigId))
            {
                siteConfigId = DEFAULT_SITE_CONFIGURATION_ID;
                configuration = (Configuration) context.getObjectService().getConfiguration(siteConfigId);
            }
            
            if (configuration == null)
            {
                // if we still haven't found an object, then we can do an exhaustive lookup
                // this is a last resort effort to find the site config object                
                Map<String,ModelObject> configs = context.getObjectService().findConfigurations("site");
                if (configs != null && configs.size() > 0)
                {
                    configuration = (Configuration) configs.values().iterator().next();
                    
                    if (configuration != null && logger.isDebugEnabled())
                        logger.debug("Site configuration '" + configuration.getId() + "' discovered via exhaustive lookup.  Please adjust configuration files to optimize performance.");
                }                
            }
        }
        
        return configuration;
    }
        
    /**
     * Returns the web framework configuration element
     * 
     * @return the config
     */
    protected static WebFrameworkConfigElement getConfig()
    {
        return FrameworkUtil.getConfig();
    }
}