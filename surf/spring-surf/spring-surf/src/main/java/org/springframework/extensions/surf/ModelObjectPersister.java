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

import java.util.List;
import java.util.Map;

import org.springframework.extensions.surf.exception.ModelObjectPersisterException;

/**
 * @author muzquiano
 * @author kevinr
 */
public interface ModelObjectPersister
{
    /**
     * Initializes the persister by preloading the object cache
     * 
     * @param context the persistence context
     */
    public void init(ModelPersistenceContext context);
    
    /**
     * Resets the persister, clearing cache and starting anew.
     */
    public void reset();
    
    /**
     * Returns a unique id for this persister
     * 
     * If this persister is wrapped around a ClassPath store,
     * a LocalFileSystem store or a Repository store, this will return
     * the value provided getBasePath()
     * 
     * If this is wrapped around a RemoteStore, this will return the
     * AVM Store ID to which this persister is bound
     * 
     * @return String
     */
    public String getId();
    
    /**
     * Gets an object from persisted storage by id
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * @param objectId String
     * 
     * @return object instance
     */
    public ModelObject getObject(ModelPersistenceContext context, String objectTypeId, String objectId)
        throws ModelObjectPersisterException;
    
    /**
     * Saves an object to persisted storage
     * 
     * @param context ModelPersistenceContext
     * @param object ModelObject
     * 
     * @return whether the object was saved
     */
    public boolean saveObject(ModelPersistenceContext context, ModelObject object)
        throws ModelObjectPersisterException;
    
    /**
     * Saves a collection of objects to persisted storage
     *
     * @param context ModelPersistenceContext
     * @param objects List<ModelObject>
     * @return boolean
     * @throws ModelObjectPersisterException
     */
    public boolean saveObjects(ModelPersistenceContext context, List<ModelObject> objects) 
        throws ModelObjectPersisterException;
    
    /**
     * Removes an object from persisted storage
     * 
     * @param context ModelPersistenceContext
     * @param object ModelObject
     * 
     * @return whether the object was removed
     */
    public boolean removeObject(ModelPersistenceContext context, ModelObject object)
        throws ModelObjectPersisterException;
    
    /**
     * Removes an object from persisted storage
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * @param objectId String
     * 
     * @return whether the object was removed
     */
    public boolean removeObject(ModelPersistenceContext context, String objectTypeId, String objectId)
        throws ModelObjectPersisterException;
    
    /**
     * Checks whether the given object is persisted
     * 
     * @param context ModelPersistenceContext
     * @param object ModelObject
     * 
     * @return whether the object is persisted
     */
    public boolean hasObject(ModelPersistenceContext context, ModelObject object)
        throws ModelObjectPersisterException;
    
    /**
     * Checks whether an object with the given path is persisted
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * @param objectId String
     * 
     * @return whether the object is persisted
     */
    public boolean hasObject(ModelPersistenceContext context, String objectTypeId, String objectId)
        throws ModelObjectPersisterException;
    
    /**
     * Creates a new object
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * @param objectId String
     * 
     * @return the object
     */
    public ModelObject newObject(ModelPersistenceContext context, String objectTypeId, String objectId)
        throws ModelObjectPersisterException;
    
    /**
     * Returns a map of all of the objects referenced by this persister.
     * <p>
     * In general, this is a very expensive call and should be avoided. Each object
     * descriptor referenced by the persister is loaded into the model object cache. 
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * 
     * @return Map of object IDs to ModelObject instances
     * 
     * @throws ModelObjectPersisterException
     */
    public Map<String, ModelObject> getAllObjects(ModelPersistenceContext context, String objectTypeId)
        throws ModelObjectPersisterException;
    
    /**
     * Returns a map of all of the objects referenced by this persister filtered by
     * the given ID filter.
     * <p>
     * In general, this is an expensive call but less expensive than getAllObjects().
     * Each object descriptor referenced by the persister found using the filter is
     * loaded into the model object cache. 
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * @param objectIdPattern String
     * 
     * @return Map of object IDs to ModelObject instances
     * 
     * @throws ModelObjectPersisterException
     */
    public Map<String, ModelObject> getAllObjectsByFilter(ModelPersistenceContext context, String objectTypeId, String objectIdPattern)
        throws ModelObjectPersisterException;

    /**
     * Returns the timestamp of the given object in the underlying store
     * 
     * @param context ModelPersistenceContext
     * @param objectTypeId String
     * @param objectId String
     * @return long
     * @throws ModelObjectPersisterException
     */
    public long getTimestamp(ModelPersistenceContext context, String objectTypeId, String objectId)
        throws ModelObjectPersisterException;
    
    /**
     * Indicates whether this persisted is currently enabled.
     * Disabled persisters continue to work but do not modify data.
     * 
     * @return flag
     */
    public boolean isEnabled();
    
    /**
     * Indicates whether or not the persister uses a store that is read only.
     * @return boolean
     */
    public boolean hasReadOnlyStore();
}
