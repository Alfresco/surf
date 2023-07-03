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

import jakarta.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.exception.ConnectorProviderException;
import org.springframework.extensions.surf.exception.ConnectorServiceException;
import org.springframework.extensions.surf.site.AuthenticationUtil;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.connector.Connector;
import org.springframework.extensions.webscripts.connector.ConnectorProvider;
import org.springframework.extensions.webscripts.connector.ConnectorService;
import org.springframework.extensions.webscripts.connector.User;

/**
 * An implementation of connector provider that provides access to the
 * Web Framework request context to build connectors
 *
 * @author Kevin Roast
 * @author muzquiano
 */
public class WebFrameworkConnectorProvider implements ConnectorProvider
{    
    private static final Log logger = LogFactory.getLog(WebFrameworkConnectorProvider.class);

    private ConnectorService connectorService;
    
    /**
     * Sets the connector service.
     * 
     * @param connectorService ConnectorService
     */
    public void setConnectorService(ConnectorService connectorService)
    {
        this.connectorService = connectorService;
    }
    
    /**
     * Implementation of the contract to provide a Connector for our remote store.
     * This allows lazy providing of the Connector object only if the remote store actually needs
     * it. Otherwise acquiring the Connector when rarely used is an expensive overhead as most
     * objects are cached by the persister in which case the remote store isn't actually called.
     */
    public Connector provide(String endpoint) throws ConnectorProviderException
    {
        Connector conn = null;
        RequestContext rc = ThreadLocalRequestContext.getRequestContext();
        
        if (rc != null)
        {
            try
            {
                // check whether we have a current user
                User user = rc.getUser();
                if (user == null || rc.getCredentialVault() == null)
                {
                    if (logger.isDebugEnabled())
                        logger.debug("No user was found, creating unauthenticated connector");
                    
                    // return the non-credential'ed connector to this endpoint
                    conn = connectorService.getConnector(endpoint);
                }
                else
                {
                    if (logger.isDebugEnabled())
                        logger.debug("User '" + user.getId() + "' was found, creating authenticated connector");
                    
                    // return the credential'ed connector to this endpoint
                    HttpSession httpSession = ServletUtil.getSession(true);
                    conn = connectorService.getConnector(endpoint, rc.getUserId(), httpSession);
                }
            }
            catch (ConnectorServiceException cse)
            {
                throw new ConnectorProviderException("Unable to provision connector for endpoint: " + endpoint, cse);
            }
        }
        else
        {
            // if we don't have a request context, we can still provision a connector with no credentials
            try
            {
                conn = connectorService.getConnector(endpoint);
            }
            catch (ConnectorServiceException cse)
            {
                throw new ConnectorProviderException("Unable to provision non-credential'd connector for endpoint: " + endpoint, cse);
            }
        }
        
        return conn;
    }
}
