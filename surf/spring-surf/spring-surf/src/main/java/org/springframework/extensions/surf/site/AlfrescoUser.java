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

package org.springframework.extensions.surf.site;

import java.util.Map;

import org.springframework.extensions.webscripts.connector.User;

/**
 * User object extended to add avatar reference property.
 * 
 * @author Kevin Roast
 */
public class AlfrescoUser extends User
{
    public static String PROP_AVATARREF = "avatar";
    
    protected final Map<String, Boolean> immutability;
    
    
    /**
     * Instantiates a new user.
     * 
     * @param id            The user id
     * @param capabilities  Map of string keyed capabilities given to the user
     * @param immutability  Optional map of property qnames to immutability
     */
    public AlfrescoUser(String id, Map<String, Boolean> capabilities, Map<String, Boolean> immutability)
    {
        super(id, capabilities);
        this.immutability = immutability;
    }
    
    /**
     * @return  the avatarRef
     */
    public String getAvatarRef()
    {
        return getStringProperty(PROP_AVATARREF);
    }

    /**
     * @param avatarRef the avatarRef to set
     */
    public void setAvatarRef(String avatarRef)
    {
        setProperty(PROP_AVATARREF, avatarRef);
    }
    
    /**
     * @param property to test for immutability either full QName or assumed 'cm' namespace
     * @return true if the property is immutable
     */
    public boolean isImmutableProperty(final String property)
    {
        boolean immutable = false;
        if (this.immutability != null && property != null && property.length() != 0)
        {
            if (property.charAt(0) == '{' && property.indexOf('}') != -1)
            {
                // full qname based property
                immutable = this.immutability.containsKey(property);
            }
            else
            {
                // assume 'cm' namespace default
                immutable = this.immutability.containsKey("{http://www.alfresco.org/model/content/1.0}" + property);
            }
        }
        return immutable;
    }
}
