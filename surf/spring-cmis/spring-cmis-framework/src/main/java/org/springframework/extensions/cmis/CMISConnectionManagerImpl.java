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
package org.springframework.extensions.cmis;

import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.springframework.extensions.webscripts.Runtime;

public class CMISConnectionManagerImpl extends CMISHelper implements CMISConnectionManager
{
    private CMISScriptParameterFactory factory;
    private Runtime runtime;

    public CMISConnectionManagerImpl(CMISScriptParameterFactory factory, Runtime runtime)
    {
        this.factory = factory;
        this.runtime = runtime;
    }

    public Runtime getRuntime()
    {
        return runtime;
    }

    public CMISConnection createDefaultConnection(CMISServer server)
    {
        return factory.createDefaultConnection(this, server);
    }

    public CMISConnection createUserConnection(CMISServer server, String connectionId)
    {
        return factory.createUserConnection(this, server, connectionId);
    }

    public CMISConnection createSharedConnection(CMISServer server, String connectionId)
    {
        return factory.createSharedConnection(this, server, connectionId);
    }

    public CMISConnection getConnection()
    {
        return factory.getConnection(this);
    }

    public CMISConnection getConnection(String connectionId)
    {
        return factory.getConnection(this, connectionId);
    }

    public List<CMISConnection> getUserConnections()
    {
        return factory.getUserConnections(this);
    }

    public List<CMISConnection> getSharedConnections()
    {
        return factory.getSharedConnections();
    }

    public List<CMISServer> getServerDefinitions()
    {
        return factory.getServerDefinitions();
    }

    public CMISServer getServerDefinition(String serverName)
    {
        return factory.getServerDefinition(serverName);
    }

    public CMISServer createServerDefinition(String serverName, Map<String, String> parameters)
    {
        return factory.createServerDefinition(serverName, parameters);
    }

    public CMISServer createServerDefinition(CMISServer server, String username, String password)
    {
        return factory.createServerDefinition(server, username, password);
    }

    public CMISServer createServerDefinition(CMISServer server, String username, String password, String repositoryId)
    {
        return factory.createServerDefinition(server, username, password, repositoryId);
    }

    public List<Repository> getRepositories(CMISServer server)
    {
        return factory.getRepositories(server);
    }

    public void removeConnection(CMISConnection connection)
    {
        factory.removeConnection(this, connection);
    }
}
