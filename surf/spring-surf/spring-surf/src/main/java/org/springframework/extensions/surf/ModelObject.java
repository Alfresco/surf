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
import java.util.Map;

import org.dom4j.Document;

/**
 * @author muzquiano
 */
public interface ModelObject extends Serializable
{
    /** IMPORTANT - public fields starting with PROP_ are inspected by the ModelHelper */
    public static String PROP_ID = "id";
    public static String PROP_TITLE = "title";
    public static String PROP_TITLE_ID = "title-id";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_DESCRIPTION_ID = "description-id";
    
    /**
     * Returns the model object key instance
     * 
     * @return ModelPersisterInfo
     */
    public ModelPersisterInfo getKey();
    
    /**
     * Returns the id of the model object.
     * 
     * @return The id
     */
    public String getId();
    
    /**
     * Returns the type id of the model object.
     * 
     * @return The type id
     */
    public String getTypeId();
    
    /**
     * Returns the title property of the model object.
     * 
     * @return The title
     */
    public String getTitle();
    
    /**
     * Sets the title property of the model object
     * 
     * @param value The new title
     */
    public void setTitle(String value);
    
    /**
     * Returns the title id property of the model object.
     * 
     * @return The title id
     */
    public String getTitleId();
    
    /**
     * Sets the title id property of the model object
     * 
     * @param value The new title id
     */
    public void setTitleId(String value);
    
    /**
     * Returns the description property of the model object
     * 
     * @return The description
     */
    public String getDescription();
    
    /**
     * Sets the description property of the model object
     * 
     * @param value The description
     */
    public void setDescription(String value);
    
    /**
     * Returns the description id property of the model object
     * 
     * @return The description id
     */
    public String getDescriptionId();
    
    /**
     * Sets the description id property of the model object
     * 
     * @param value The description id
     */
    public void setDescriptionId(String value);
        
    /**
     * Indicates whether the object is currently persisted (saved)
     * or not.  A new object will have this flag set to false prior
     * to a save and true once the save operation has completed.
     * 
     * @return Whether the object is currently saved
     */
    public boolean isSaved();

    /**
     * Serializes the object to XML.  By default, this uses a 
     * pretty XML renderer so that the resulting XML is
     * human readable.
     * 
     * @return The XML string
     */
    public String toXML();

    // general property accessors
    public boolean getBooleanProperty(String propertyName);
    public String getProperty(String propertyName);
    public void setProperty(String propertyName, String propertyValue);
    public void removeProperty(String propertyName);
    public Map<String, Serializable> getProperties();

    // model properties
    public String getModelProperty(String propertyName);
    public void setModelProperty(String propertyName, String propertyValue);
    public void removeModelProperty(String propertyName);
    public Map<String, Serializable> getModelProperties();
    
    // custom properties
    public String getCustomProperty(String propertyName);
    public void setCustomProperty(String propertyName, String propertyValue);
    public void removeCustomProperty(String propertyName);
    public Map<String, Serializable> getCustomProperties();
    
    // persistence
    public String getStoragePath();
    public String getPersisterId();
    public long getModificationTime();
    public void touch();
    
    // allow xml retrieval via document
    public Document getDocument();    
}
