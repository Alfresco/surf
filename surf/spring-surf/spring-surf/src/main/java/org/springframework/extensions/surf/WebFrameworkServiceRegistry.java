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

import org.springframework.extensions.config.ConfigService;
import org.springframework.extensions.config.RemoteConfigElement;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.persister.PersisterService;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.surf.resource.ResourceService;
import org.springframework.extensions.webscripts.Container;
import org.springframework.extensions.webscripts.ScriptRemote;
import org.springframework.extensions.webscripts.connector.ConnectorService;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.ServletContextHashModel;

/**
 * Service Registry for Web Framework
 * 
 * This service provides getters for all Web Framework services and
 * helper beans.
 * 
 * @author muzquiano
 * @author Dave Draper
 */
public class WebFrameworkServiceRegistry
{
    // web framework service registry singleton
    public static final String WEB_FRAMEWORK_SERVICE_REGISTRY_ID = "webframework.service.registry";
    
    // core service beans
    private ConfigService configService;

    public void setConfigService(ConfigService configService)
    {
        this.configService = configService;
    }
    
    private RenderService webFrameworkRenderService;
    
    public void setWebFrameworkRenderService(RenderService webFrameworkRenderService)
    {
        this.webFrameworkRenderService = webFrameworkRenderService;
    }
    
    private ResourceService webFrameworkResourceService;
    
    public void setWebFrameworkResourceService(ResourceService webFrameworkResourceService)
    {
        this.webFrameworkResourceService = webFrameworkResourceService;
    }
    
    private ConnectorService connectorService;
    
    public void setConnectorService(ConnectorService connectorService)
    {
        this.connectorService = connectorService;
    }
    
    private PresetsManager presetsManager;
    
    public void setPresetsManager(PresetsManager presetsManager)
    {
        this.presetsManager = presetsManager;
    }
    
    private ScriptRemote scriptRemote;
    
    public void setScriptRemote(ScriptRemote scriptRemote)
    {
        this.scriptRemote = scriptRemote;
    }
    
    private PersisterService persisterService;
    
    public void setPersisterService(PersisterService persisterService)
    {
        this.persisterService = persisterService;
    }
    
    private ObjectPersistenceService objectPersistenceService;
    
    public void setObjectPersistenceService(ObjectPersistenceService objectPersistenceService)
    {
        this.objectPersistenceService = objectPersistenceService;
    }
    
    private ModelObjectService modelObjectService;
    
    public void setModelObjectService(ModelObjectService modelObjectService)
    {
        this.modelObjectService = modelObjectService;
    }

    public void setTemplatesContainer(TemplatesContainer templatesContainer)
    {
        this.templatesContainer = templatesContainer;
    }
    
    // templates container
    private TemplatesContainer templatesContainer;
    
    /**
     * Gets the config service.
     * 
     * @return the config service
     */
    public ConfigService getConfigService()
    {
        return this.configService;
    }
    
    /**
     * Gets the web framework render service.
     * 
     * @return the web framework render service
     */
    public RenderService getRenderService()
    {
        return this.webFrameworkRenderService;
    }
    
    /**
     * Gets the resource service.
     * 
     * @return the resource service
     * @deprecated
     */
    public ResourceService getResourceService()
    {
        return this.webFrameworkResourceService;
    }
    
    /**
     * Gets the connector service.
     * 
     * @return the connector service
     */
    public ConnectorService getConnectorService()
    {
        return this.connectorService;
    }

    /**
     * Gets the persister service
     * 
     * @return the persister service
     * @deprecated
     */
    public PersisterService getPersisterService()
    {
        return this.persisterService;
    }
    
    /**
     * Gets the object persistence service
     * 
     * @return the object persistence service
     * @deprecated
     */
    public ObjectPersistenceService getObjectPersistenceService()
    {
        return this.objectPersistenceService;
    }
    
    /**
     * Gets the model object service
     * 
     * @return the model object service
     * @deprecated
     */
    public ModelObjectService getModelObjectService()
    {
        return this.modelObjectService;
    }
    
    /**
     * Gets the presets manager.
     * 
     * @return the presets manager
     */
    public PresetsManager getPresetsManager()
    {
        return this.presetsManager;
    }
    
    /**
     * Gets the script remote.
     * 
     * @return the script remote
     */
    public ScriptRemote getScriptRemote()
    {
        return this.scriptRemote;
    }
    
    /**
     * Gets the templates container.
     * 
     * @return the templates container
     */
    public TemplatesContainer getTemplatesContainer()
    {
        return this.templatesContainer;
    }
    

    
    /* 
     * PLEASE NOTE:
     * The following fields/methods are deprecated because they perform function which can be 
     * achieved through sensible Spring Bean configuration.
     */
    
    /**
     * Web framework webscripts container
     * @deprecated
     */
    private Container webFrameworkContainer;
    
    /**
     * Gets the web framework container.
     * 
     * @return the web framework container
     * @deprecated Use Spring configuration to access the Web Framework Container (Bean ref "webscripts.container")
     */
    public Container getWebFrameworkContainer()
    {
        return this.webFrameworkContainer;
    }
    
    /**
     * @param webFrameworkContainer Container
     * @deprecated
     */
    public void setWebFrameworkContainer(Container webFrameworkContainer)
    {
        this.webFrameworkContainer = webFrameworkContainer;
    }
    
    /**
     * @deprecated
     */
    private RemoteConfigElement remoteConfigElement;
    
    /**
     * @deprecated
     * @param remoteConfigElement RemoteConfigElement
     */
    public void setRemoteConfigElement(RemoteConfigElement remoteConfigElement)
    {
        this.remoteConfigElement = remoteConfigElement;
    }

    /**
     * Gets the remote configuration.
     * 
     * @return the remote configuration
     * @deprecated Use Spring configuration to access the RemoteConfigElement (Bean ref "remote.config.element")
     */
    public RemoteConfigElement getRemoteConfigElement()
    {
        return this.remoteConfigElement;
    }
    
    /**
     * @deprecated
     */
    private WebFrameworkConfigElement webFrameworkConfigElement;

    /**
     * @deprecated
     * @param webFrameworkConfigElement WebFrameworkConfigElement
     */
    public void setWebFrameworkConfigElement(WebFrameworkConfigElement webFrameworkConfigElement)
    {
        this.webFrameworkConfigElement = webFrameworkConfigElement;
    }
    
    /**
     * Gets the web framework configuration.
     * 
     * @return the web framework configuration
     * @deprecated Use Spring configuration to access the WebFrameworkConfigElement (Bean ref "webframework.config.element") 
     */
    public WebFrameworkConfigElement getWebFrameworkConfiguration()
    {
        return this.webFrameworkConfigElement;
    }
    
    /**
     * @deprecated
     */
    private UserFactory userFactory;
    
    /**
     * Gets the user factory.
     * 
     * @return the user factory
     * @deprecated Use Spring configuration to access the UserFactory (Bean ref "user.factory")
     */
    public UserFactory getUserFactory()
    {
        return this.userFactory;
    }
    
    /**
     * Sets the user factory.
     * 
     * @param userFactory the new user factory
     * @deprecated
     */
    public void setUserFactory(UserFactory userFactory)
    {
        this.userFactory = userFactory;
    }
    
    /**
     * @deprecated
     */
    private static TaglibFactory taglibFactory;
    
    /**
     * Gets the taglib factory.
     * 
     * @return the taglib factory
     * @deprecated Use Spring configuration to access the TabLibFactory (Bean ref "taglib.factory")
     */
    public TaglibFactory getTaglibFactory()
    {
        return taglibFactory;
    }
    
    /**
     * @deprecated 
     * @param taglibFactory TaglibFactory
     */
    public static void setTaglibFactory(TaglibFactory taglibFactory)
    {
        WebFrameworkServiceRegistry.taglibFactory = taglibFactory;
    }
    
    /**
     * @deprecated
     */
    private static ServletContextHashModel servletContextHashModel;
    
    /**
     * @deprecated
     * @param servletContextHashModel ServletContextHashModel
     */
    public static void setServletContextHashModel(ServletContextHashModel servletContextHashModel)
    {
        WebFrameworkServiceRegistry.servletContextHashModel = servletContextHashModel;
    }

    /**
     * Gets the servlet context hash model.
     * 
     * @return the servlet context hash model
     * @deprecated Use Spring configuration to access the ServletContextHashModel (Bean ref "servletContext.hashModel")
     */
    public ServletContextHashModel getServletContextHashModel()
    {
        return servletContextHashModel;
    }        
    
}
