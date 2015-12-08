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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.extensions.surf.exception.PlatformRuntimeException;
import org.springframework.extensions.surf.types.AbstractModelObject;
import org.springframework.extensions.webscripts.GUID;

/**
 * Static Utility class that provides reflection against the public
 * properties of a given object.
 * <p>
 * This class provides methods for checking whether a given property name
 * is declared as a model variable.
 * <p>
 * Model variables are defined on ModelObject derived types as variables
 * that begin with a "PROP_" prefix.
 * <p>
 * The code walks the class chain and picks out which variables are model
 * specific and which ones are custom.  The results are stored in look-up
 * tables so that subsequent lookups will simply hit the cache.  This is
 * perfectly fine since model definitions do not change at runtime.
 * 
 * @author muzquiano
 * @author kevinr
 */
public final class ModelHelper
{
    private static Map<String, Set> classMap = new HashMap<String, Set>();
    
    /** Lock object for class map construction */
    private static ReadWriteLock cacheLock = new ReentrantReadWriteLock();
    
    
    /**
     * Determines whether the given property is a custom property for 
     * the given object
     * 
     * @param object the object
     * @param propertyName the property name
     * 
     * @return true, if is custom property
     */
    public static boolean isCustomProperty(ModelObject object, String propertyName)
    {
        return !(isModelProperty(object, propertyName));
    }

    /**
     * Determines whether the given property is a non-custom (or model)
     * property for the given object.
     * 
     * @param object the object
     * @param propertyName the property name
     * 
     * @return true, if is model property
     */
    public static boolean isModelProperty(ModelObject object, String propertyName)
    {
        if (object == null || propertyName == null)
        {
            throw new IllegalArgumentException("ModelObject and PropertyName are mandatory.");
        }
        
        Class modelClass = object.getClass();
        
        // grab the cache of property keys
        Set<String> propertyMap;
        cacheLock.readLock().lock();
        try
        {
            propertyMap = classMap.get(modelClass.getName());
        }
        finally
        {
            cacheLock.readLock().unlock();
        }
        if (propertyMap == null)
        {
            cacheLock.writeLock().lock();
            try
            {
                // check again - as another thread may have completed a write while we were waiting
                // if more than one thread was waiting on the write lock
                propertyMap = classMap.get(modelClass.getName());
                if (propertyMap == null)
                {
                    // we need to build the property map cache
                    propertyMap = new HashSet<String>();
                    classMap.put(modelClass.getName(), propertyMap);
                    
                    // reflect on the class and its interfaces
                    try
                    {
                        extractProperties(object, propertyMap, modelClass);
                        for (Class klass : modelClass.getInterfaces())
                        {
                            extractProperties(object, propertyMap, klass);
                        }
                    }
                    catch (IllegalAccessException iae)
                    {
                        throw new PlatformRuntimeException("Unable to inspect properties on model object class: " +
                                modelClass.getName());
                    }
                }
            }
            finally
            {
                cacheLock.writeLock().unlock();
            }
        }
        
        // look up property in property map cache
        return propertyMap.contains(propertyName);
    }

    private static void extractProperties(ModelObject object, Set<String> propertyMap, Class klass)
            throws IllegalAccessException
    {
        Field[] fields = klass.getFields();
        for (int i = 0; i < fields.length; i++)
        {
            // is it a declared property?
            if (fields[i].getName().startsWith("PROP_"))
            {
                String fieldValue = (String) fields[i].get(object);
                
                // mark it
                propertyMap.add(fieldValue);
            }
        }
    }

    /**
     * Builds a new GUID
     * 
     * @return the string
     */
    public static String newGUID()
    {
        return GUID.generate();
    }   
    
    /**
     * Allows model object ids to be set manually
     * 
     * @param object ModelObject
     * @param id String
     */
    public static void resetId(ModelObject object, String id)
    {
        if (object instanceof AbstractModelObject)
        {
            ((AbstractModelObject)object).setId(id);
        }        
    }
}
