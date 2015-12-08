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

import java.io.Serializable;

/**
 * The ModelPersisterInfo structure class holds the values that represent the binding
 * of a model object instance to a persistance store and it's path in that store.
 * 
 * @author muzquiano
 * @author David Draper
 */
public final class ModelPersisterInfo implements Serializable
{
    private static final long serialVersionUID = -2684766253715296643L;

    private String persisterId;
    private String storagePath;
    
    /**
     * <p>This contains the details of the file where the <code>ModelObject</code> is defined. This is different
     * from the <code>storagePath</code> because when a <code>ModelObject</code> is defined within another (e.g. 
     * when a <code>Component</code> is defined inside a <code>Page</code>) then a storage path is generated for 
     * internal use. The sourcePath is useful for debugging purposes when we want to determine not where the 
     * <code>ModelObject</code> is stored but where it is defined.</p>
     */
    private String sourcePath = null;
    
    /**
     * <p>This should be used to set the location where the <code>ModelObject</code> is originally defined.</p>
     * @param sourcePath The path to the definition of he <code>ModelObject</code>
     */
    public void setSourcePath(String sourcePath)
    {
        this.sourcePath = sourcePath;
    }

    /**
     * <p>Retrieves the path to the original definition of the <code>ModelObject</code>.
     * @return The path to the original definition of the <code>ModelObject</code>.
     */
    public String getSourcePath()
    {
        return sourcePath;
    }

    private boolean saved;

    /**
     * Instantiates a new model object key.
     * 
     * @param persisterId the persister id
     * @param storagePath the storage path
     */
    public ModelPersisterInfo(String persisterId, String storagePath, boolean saved)
    {
        this.persisterId = persisterId;
        this.storagePath = storagePath;
        this.saved = saved;
    }
    
    /**
     * Instantiates a new model object key.
     * 
     * @param persisterId the persister id
     * @param storagePath the storage path
     */
    public ModelPersisterInfo(String persisterId, String storagePath, String sourcePath, boolean saved)
    {
        this.persisterId = persisterId;
        this.storagePath = storagePath;
        this.saved = saved;
        this.sourcePath = sourcePath;
    }
    
    /**
     * Gets the persister id.
     * 
     * @return the persister id
     */
    public String getPersisterId()
    {
        return this.persisterId;
    }
    
    /**
     * Sets the storage path
     * 
     * @param storagePath String
     */
    public void setStoragePath(String storagePath)
    {
        this.storagePath = storagePath;
    }
    
    /**
     * Gets the storage path.
     * 
     * @return the storage path
     */
    public String getStoragePath()
    {
        return this.storagePath;
    }
    
    /**
     * Returns whether the object is currently saved or not
     * 
     * @return whether saved
     */
    public boolean isSaved()
    {
        return this.saved;
    }
    
    /**
     * Marks the saved flag on the key
     * 
     * @param saved boolean
     */
    public void setSaved(boolean saved)
    {
        this.saved = saved;
    }
}
