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

import org.dom4j.Document;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.ModelObjectPersister;
import org.springframework.extensions.surf.ModelPersistenceContext;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.exception.ModelObjectPersisterException;

/**
 * Abstract implementation of a model object persister.
 * <p>
 * Provided to enable ease-of-extension by developers for customer
 * object persisters.  This class serves as a foundation and really
 * only provides essential helper functions.
 * <p>
 * Surf provides persister implementations that are largely file-based.
 * Customizations may seek to incorporate persisters that interact with
 * databases or Alfresco content models directly.
 * 
 * @see StoreObjectPersister
 *
 * @author muzquiano
 * @author kevinr
 */
public abstract class AbstractObjectPersister implements ModelObjectPersister, BeanNameAware
{
    protected static final Class<?>[] MODELOBJECT_CLASSES = new Class[] {
        String.class, ModelPersisterInfo.class, Document.class };    
        
    private String id = null;
    
    private boolean isEnabled = true;
    
    private WebFrameworkConfigElement webFrameworkConfig;
    
    private PersisterService persisterService;
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String name)
    {
        this.id = name;        
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.ModelObjectPersister#init(org.alfresco.web.framework.ModelPersistenceContext)
     */
    public void init(ModelPersistenceContext context) 
    {
        // no default initialization
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.ModelObjectPersister#reset()
     */
    public void reset()
    {
        // no default reset behaviour
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.ModelObjectPersister#removeObject(org.alfresco.web.framework.ModelPersistenceContext, org.alfresco.web.framework.ModelObject)
     */
    public boolean removeObject(ModelPersistenceContext context, ModelObject object)
        throws ModelObjectPersisterException    
    {
        return removeObject(context, object.getTypeId(), object.getId());
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.ModelObjectPersister#hasObject(org.alfresco.web.framework.ModelPersistenceContext, org.alfresco.web.framework.ModelObject)
     */
    public boolean hasObject(ModelPersistenceContext context, ModelObject object)
         throws ModelObjectPersisterException
    {
        return hasObject(context, object.getTypeId(), object.getId());
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.ModelObjectPersister#getId()
     */
    public String getId()
    {
        return this.id;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.framework.ModelObjectPersister#isEnabled()
     */
    public boolean isEnabled()
    {
        return this.isEnabled;
    }
    
    /**
     * Disables the persister
     */
    protected void disable()
    {
        this.isEnabled = false;
    }
        
    /* **************************************
     *                                      *
     * GETTERS AND SETTERS FOR SPRING BEANS *
     *                                      *
     ************************************** */
    
    public void setPersisterService(PersisterService persisterService)
    {
        this.persisterService = persisterService;
    }

    public void setWebFrameworkConfig(WebFrameworkConfigElement webFrameworkConfig)
    {
        this.webFrameworkConfig = webFrameworkConfig;
    }

    protected WebFrameworkConfigElement getWebFrameworkConfiguration()
    {
        // This if block has been included to support the legacy application context configuration
        // which may stay be used.
        if (this.webFrameworkConfig == null)
        {
            this.webFrameworkConfig = this.serviceRegistry.getWebFrameworkConfiguration();
        }
        return this.webFrameworkConfig;
    }

    protected PersisterService getPersisterService()
    {
        // This if block has been included to support the legacy application context configuration
        // which may stay be used.
        if (this.persisterService == null)
        {
            this.persisterService = this.serviceRegistry.getPersisterService();
        }
        return this.persisterService;
    }
    
    /**
     * @deprecated Please configure the Spring application context to set a PersisterService and WebFrameworkConfigElement explicitly
     */
    private WebFrameworkServiceRegistry serviceRegistry;
    
    /**
     * @deprecated Please configure the Spring application context to set a PersisterService and WebFrameworkConfigElement explicitly
     */
    public void setServiceRegistry(WebFrameworkServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
    }
    
    /**
     * @deprecated Please configure the Spring application context to set a PersisterService and WebFrameworkConfigElement explicitly 
     */
    public WebFrameworkServiceRegistry getServiceRegistry()
    {
        return this.serviceRegistry;
    }
}
