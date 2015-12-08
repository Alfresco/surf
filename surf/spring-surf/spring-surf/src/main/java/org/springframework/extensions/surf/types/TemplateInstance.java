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

package org.springframework.extensions.surf.types;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;

/**
 * Interface for a TemplateInstance object type
 * 
 * @author muzquiano
 */
public interface TemplateInstance extends ModelObject
{
    // type
    public static String TYPE_ID = "template-instance";
    
    // properties
    public static String PROP_TEMPLATE_TYPE = "template-type";
    
    /**
     * Gets the template type.
     * 
     * @return the template type
     */
    public String getTemplateTypeId();

    /**
     * Sets the template type.
     * 
     * @param templateTypeId the new template type
     */
    public void setTemplateTypeId(String templateTypeId);

    /**
     * Gets the template type.
     * 
     * @param context the context
     * 
     * @return the template type
     */
    public TemplateType getTemplateType(RequestContext context);
}
