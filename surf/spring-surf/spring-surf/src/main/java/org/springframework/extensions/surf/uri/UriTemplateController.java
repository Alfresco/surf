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

package org.springframework.extensions.surf.uri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.config.Config;
import org.springframework.extensions.config.ConfigElement;
import org.springframework.extensions.config.ConfigService;
import org.springframework.extensions.surf.exception.PlatformRuntimeException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * URI template remapping controller.
 * 
 * Using the facilities of the UriTemplateIndex and a configured list of URI Template mappings.
 *
 * Each URI Template maps to one a page resource urls. The page resource URL is then forwarded
 * to the PageRendererServlet. 
 *
 * @author kevinr
 * @author muzquiano
 */
public class UriTemplateController extends AbstractController
{
    private static Log logger = LogFactory.getLog(UriTemplateController.class);
         
    public static final String CONFIG_ELEMENT = "UriTemplate";
    
    private ConfigService configService;
    
    /** URI Template index - Application url mappings */
    private UriTemplateMappingIndex uriTemplateIndex;
    
    public void setConfigService(ConfigService configService)
    {
        this.configService = configService;
    }
    
    /**
     * Spring init method
     */
    public void init()
    {
        initUriIndex(this.configService);
    }

    /**
     * Initialise the list of URL Mapper objects for the PageRenderer
     */
    private void initUriIndex(ConfigService configService)
    {
        Config config = configService.getConfig(CONFIG_ELEMENT);
        if (config == null)
        {
            throw new PlatformRuntimeException("Cannot find required config element 'UriTemplate'.");
        }
        ConfigElement uriConfig = config.getConfigElement("uri-mappings");
        if (uriConfig == null)
        {
            throw new PlatformRuntimeException("Missing required config element 'uri-mappings' under 'UriTemplate'.");
        }
        this.uriTemplateIndex = new UriTemplateMappingIndex(uriConfig);
    }

    /**
     * Match the specified URI against the URI template index
     * 
     * @param uri to match
     * 
     * @return the resource URL to use
     */
    private String matchUriTemplate(String uri)
    {
        String resource = this.uriTemplateIndex.findMatchAndReplace(uri);
        if (resource == null)
        {
            resource = uri;
        }
        return resource;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.mvc.AbstractController#createModelAndView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        // get the URI (after the controller)
        String uri = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        // query string
        String qs = req.getQueryString();

        // build the uri ready for URI Template match
        uri = uri + (qs != null ? ("?" + qs) : "");

        if (logger.isDebugEnabled())
            logger.debug("Matching application URI template: " + uri);

        String resource = matchUriTemplate(uri);

        if (logger.isDebugEnabled())
            logger.debug("Resolved uri template to resource: " + resource);

        // rebuild page servlet URL to perform forward too
        req.getRequestDispatcher(resource).forward(req, res);
        
        return null;
    }
}