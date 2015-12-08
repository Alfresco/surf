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

package org.springframework.extensions.surf.persister;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.config.WebFrameworkConfigElement.PersisterConfigDescriptor;
import org.springframework.extensions.config.WebFrameworkConfigElement.TypeDescriptor;
import org.springframework.extensions.surf.AutowireService;
import org.springframework.extensions.surf.ModelObjectPersister;
import org.springframework.extensions.surf.exception.ModelObjectPersisterException;

/**
 * Persistence Service
 *
 * Responsible for managing the state and interaction with persisters in the Surf registry.
 *
 * @author David Draper
 * @author kevinr
 */
public class PersisterService
{
    private static final Log logger = LogFactory.getLog(PersisterService.class);

    /**
     * <p>This property should be configured through the Spring application context to
     * to be the complete list of <code>ModelObjectPerister</code> instances that should be
     * used to <b>search</b> for Spring Surf <code>ModelObjects</code>. Only the 
     * <code>ModelObjectPersister</code> configured for each type will be used to create and
     * save that type.</p>
     */
    private List<ModelObjectPersister> persisters;
    
    /**
     * <p>An <code>AutowireService</code> should be provided to the <code>PersisterService</code>
     * so that the Spring application context can be updated to support the configured mode and 
     * runtime. If the <code>persisters</code> property contains an element that is a
     * <code>MultiObjectPersister</code> then it will be used to update its own list of persisters
     * to support the configured runtime.</p> 
     */
    private AutowireService autowireService;
    
    /**
     * <p>The <code>WebFrameworkConfigElement</code> is required to retrieve both the declared
     * object type information (used when constructing the <code>typeToPersisterMap</code>) as
     * well as obtaining the configured mode and runtime to use when invoking the <code>autowireService</code>.</p>
     */
    private WebFrameworkConfigElement webFrameworkConfig;
    
    /**
     * <p>This is populated during the <code>init</code> method and can be accessed by consumers
     * of the <code>PersisterService</code> via the <code>getTypeToPersisterMap</code> method
     * as a convenience for quick access to the <code>ModelObjectPersister</code> instances.</p>
     */
    private HashMap<String, ModelObjectPersister> typeToPersisterMap = new HashMap<String, ModelObjectPersister>();
    
    /**
     * <p>This is populated during the <code>init</code> method and can be accessed by consumers
     * of the <code>PersisterService</code> via the <code>getPersisterIdToPersisterMap</code> method
     * as a convenience for quick access to the <code>ModelObjectPersister</code> instances.</p>
     */
    private HashMap<String, ModelObjectPersister> persisterIdToPersisterMap = new HashMap<String, ModelObjectPersister>();

    /**
     * @return A map of type IDs to the <code>ModelObjectPersister</code> instance that has been configured to
     * create and save that type. 
     */
    public HashMap<String, ModelObjectPersister> getTypeToPersisterMap()
    {
        return typeToPersisterMap;
    }
    
    /**
     * @return A map of the persister IDs to the associated <code>ModelObjectPersister</code> instance.
     */
    public HashMap<String, ModelObjectPersister> getPersisterIdToPersisterMap()
    {
        return persisterIdToPersisterMap;
    }

    /**
     * <p>This method is configured in the default Spring Surf application context to execute when the
     * <code>PersisterService</code> is instantiated. The initialisation consists of 4 parts:</p>
     * <ul><li>Create a map of types to persisters</li>
     * <li>Create a map of persisters from their ids</li>
     * <li>Run the autowire service</li>
     * <li>Initialise the persisters</li>
     * </ul>
     * <p>The maps are created for the convenience of consumers of the <code>PersisterService</code>. 
     * The list of persisters initialised will be those defined in the application context as the 
     * <code>persisters</code> property, along with any additional persisters that are defined by any
     * <code>MultiObjectPersisters</code> found in that list. The <code>AutowireService.configureRuntime</code>
     * method is also run against any <code>MultiObjectPersisters</code> found to ensure that they
     * are updated to contain the correct persisters for the configured runtime.</p>
     * 
     * @throws Exception 
     */
    public void init() throws Exception
    { 
        // Check that this persister service can actually cater for all the object types. This is
        // a courtesy check to make sure that the consuming application has provided complementary
        // application context and Spring Surf configurations.
        for (TypeDescriptor type: this.webFrameworkConfig.getTypes())
        {
            boolean found = false;
            Iterator<ModelObjectPersister> _persisters = this.persisters.iterator();
            while (!found && _persisters.hasNext())
            {
                String targetPersisterId = type.getPersisterId();
                ModelObjectPersister persister = _persisters.next();
                found = persister.getId().equals(targetPersisterId);
                this.typeToPersisterMap.put(type.getId(), persister);
            }
            
            if (!found)
            {
                // The persister configured for the current type does not exist, throw an exception
                throw new ModelObjectPersisterException("Object type: \"" + type.getId() + "\" is configured to use a persister \"" + type.getPersisterId() + "\" that is not configured in the application context.");
            }
        }
        
        // Run the autowire service...
        this.autowireService.configureMode(webFrameworkConfig);
        
        // Initialise the persisters...
        PersisterConfigDescriptor config =  this.webFrameworkConfig.getPersisterConfigDescriptor();
        for (ModelObjectPersister persister: this.persisters)
        {
            this.persisterIdToPersisterMap.put(persister.getId(), persister);
            
            // If the current persister is a MultiObjectPersister then we need to add all of its
            // delegated persisters to the map for easy access.
            if (persister instanceof MultiObjectPersister)
            {
                MultiObjectPersister moPersister = (MultiObjectPersister) persister;
                this.autowireService.configureRuntime(webFrameworkConfig, moPersister);
                for(ModelObjectPersister p: moPersister.getPersisters())
                {
                    this.persisterIdToPersisterMap.put(p.getId(), p);    
                }                
            }
            
            if (persister instanceof CachedPersister)
            {
                if (config != null)
                {
                    boolean cache = config.getCacheEnabled();
                    int cacheCheckDelay = config.getCacheCheckDelay();
                    
                    if (logger.isDebugEnabled())
                        logger.debug("Setting global cache setting: " + cache + " delay: " + cacheCheckDelay + " onto persister: " + persister.getId());
                    
                    ((CachedPersister)persister).setCache(cache);
                    ((CachedPersister)persister).setCacheCheckDelay(cacheCheckDelay);
                }
            }
            
            if (logger.isDebugEnabled())
                logger.debug("Initalising persister: " + persister.getId());
            
            persister.init(null);
            
            if (logger.isDebugEnabled())
                logger.debug("Finished initalising persister: " + persister.getId());
        }
    }
    
    /* *******************************************************************
     *                                                                   *
     * GETTERS AND SETTERS FOR THE SPRING APPLICATION CONTEXT PROPERTIES *
     *                                                                   *
     *********************************************************************/
    
    public void setAutowireService(AutowireService autowireService)
    {
        this.autowireService = autowireService;
    }

    public List<ModelObjectPersister> getPersisters()
    {
        return persisters;
    }

    public void setPersisters(List<ModelObjectPersister> persisters)
    {
        this.persisters = persisters;
    }
    /**
     * <p>This method will be invoked by the Spring framework if this class has been correctly
     * configured as a bean.</p>
     * 
     * @param webFrameworkConfig WebFrameworkConfigElement
     */
    public void setWebFrameworkConfig(WebFrameworkConfigElement webFrameworkConfig)
    {
        this.webFrameworkConfig = webFrameworkConfig;
    }

    /**
     * <p>Returns the <code>WebFrameworkConfigElement</code> that was supplied to the class
     * upon instantiation.</p>
     *
     * @return A <code>WebFrameworkConfigElement</code> instance.
     */
    public WebFrameworkConfigElement getWebFrameworkConfig()
    {
        return this.webFrameworkConfig;
    }
}
