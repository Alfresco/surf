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

import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.webscripts.Description.RequiredAuthentication;

/**
 * Interface for a Page object type
 * 
 * @author muzquiano
 */
public interface Page extends ModelObject
{
    // type
    public static String TYPE_ID = "page";
    
    // properties
    public static String PROP_TEMPLATE_INSTANCE = "template-instance";
    public static String ATTR_FORMAT_ID = "format-id";
    public static String PROP_PAGE_TYPE_ID = "page-type-id";
    public static String PROP_AUTHENTICATION = "authentication";
    public static String DEFAULT_PAGE_TYPE_ID = "generic";
        
    /**
     * Gets the template id.
     * 
     * @return the template id
     */
    public String getTemplateId();

    /**
     * Gets the template id.
     * 
     * @param formatId the format id
     * 
     * @return the template id
     */
    public String getTemplateId(String formatId);

    /**
     * Sets the template id.
     * 
     * @param templateId the new template id
     */
    public void setTemplateId(String templateId);

    /**
     * Sets the template id.
     * 
     * @param templateId the template id
     * @param formatId the format id
     */
    public void setTemplateId(String templateId, String formatId);

    /**
     * Removes the template id.
     * 
     * @param formatId the format id
     */
    public void removeTemplateId(String formatId);

    /**
     * Gets the templates.
     * 
     * @param context the context
     * 
     * @return the templates
     */
    public Map<String, TemplateInstance> getTemplates(RequestContext context);

    /**
     * Gets the template.
     * 
     * @param context the context
     * 
     * @return the template
     */
    public TemplateInstance getTemplate(RequestContext context);

    /**
     * Gets the template.
     * 
     * @param context the context
     * @param formatId the format id
     * 
     * @return the template
     */
    public TemplateInstance getTemplate(RequestContext context, String formatId);

    /**
     * Gets the child pages.
     * 
     * @param context the context
     * 
     * @return the child pages
     */
    public Page[] getChildPages(RequestContext context);
    
    /**
     * Gets the page type id.
     * 
     * @return the page type id
     */
    public String getPageTypeId();
    
    /**
     * Sets the page type id.
     * 
     * @param pageTypeId the new page type id
     */
    public void setPageTypeId(String pageTypeId);
    
    /**
     * @return the Authentication required for this page
     */
    public RequiredAuthentication getAuthentication();

    /**
     * @param authentication    the authentication level to set
     */
    public void setAuthentication(String authentication);

    /**
     * Gets the page type.
     * 
     * @param context the context
     * 
     * @return the page type
     */
    public PageType getPageType(RequestContext context);
}
