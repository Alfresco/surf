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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Describes the context within which a persister should execute.
 * 
 * The object principally stores the user id which may eventually be used
 * by our store implementations to check for user rights against the
 * accessed object.
 * 
 * The object also describes the web framework's currently bound
 * repository store id and web application id (to support Alfresco
 * web projects).
 * 
 * The object may also eventually store role and rights information 
 * regarding who they are in the end application.
 * 
 * @author muzquiano
 */
public final class ModelPersistenceContext
{
    public static String REPO_STOREID = "REPO_STOREID";
    public static String REPO_WEBAPPID = "REPO_WEBAPPID";
    
    private final String userId;
    private final Map<String, Object> values;
        
    /**
     * Instantiates a new persister context.
     * 
     * @param userId the user id
     */
    public ModelPersistenceContext(String userId)
    {
        this.userId = userId;
        this.values = new HashMap<String, Object>(2, 1.0f);
    }

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public String getUserId()
    {
        return this.userId;
    }
    
    /**
     * Returns the stored value with the given key
     * 
     * @param key the key
     * 
     * @return the value
     */
    public Object getValue(String key)
    {
        return values.get(key);
    }
    
    /**
     * Stores a value with the given key
     * 
     * @param key the key
     * @param value the value
     */
    public void putValue(String key, Object value)
    {
        this.values.put(key, value);
    }
    
    /**
     * Returns the set of keys
     * 
     * @return the set
     */
    public Set<String> keys()
    {
        return this.values.keySet();
    }
    
    /**
     * Returns the collection of values
     * 
     * @return the collection
     */
    public Collection<Object> values()
    {
        return this.values.values();
    }
    
    /**
     * Sets the store id.
     * 
     * @param storeId the new store id
     */
    public void setStoreId(String storeId)
    {
        this.putValue(REPO_STOREID, storeId);
    }
    
    /**
     * Gets the store id.
     * 
     * @return the store id
     */
    public String getStoreId()
    {
        return (String) this.getValue(REPO_STOREID);
    }
    
    /**
     * Sets the webapp id.
     * 
     * @param webappId the new webapp id
     */
    public void setWebappId(String webappId)
    {
        this.putValue(REPO_WEBAPPID, webappId);
    }
    
    /**
     * Gets the webapp id.
     * 
     * @return the webapp id
     */
    public String getWebappId()
    {
        return (String) this.getValue(REPO_WEBAPPID);
    }

    @Override
    public String toString()
    {
        return "ModelPersistenceContext-" + userId + "-" + values.toString();
    } 
}
