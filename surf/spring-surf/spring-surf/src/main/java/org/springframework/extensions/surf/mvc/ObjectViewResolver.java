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

package org.springframework.extensions.surf.mvc;

import java.util.Locale;
import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkConstants;
import org.springframework.extensions.surf.resource.Resource;
import org.springframework.extensions.surf.resource.ResourceLoader;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.surf.types.ContentAssociation;
import org.springframework.extensions.surf.types.Page;
import org.springframework.extensions.surf.types.TemplateInstance;
import org.springframework.extensions.webscripts.ScriptResource;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * Resolves a URI into an object view
 * 
 * @author muzquiano
 */
public class ObjectViewResolver extends AbstractWebFrameworkViewResolver 
{
    private static final String URI_PREFIX_OBJECT = "obj";
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.UrlBasedViewResolver#canHandle(java.lang.String, java.util.Locale)
     */
    protected boolean canHandle(String viewName, Locale locale) 
    {
        boolean canHandle = false;
        
        RequestContext context = ThreadLocalRequestContext.getRequestContext();
        
        if (viewName.startsWith(URI_PREFIX_OBJECT + "/") || viewName.equals(URI_PREFIX_OBJECT))
        {
            String objectId = (String) context.getParameter("o");
            if (objectId != null)
            {
                // check if we have a resource loader for this resource
                String[] ids = getWebFrameworkResourceService().getResourceDescriptorIds(objectId);
                ResourceLoader resourceLoader = getWebFrameworkResourceService().getResourceLoader(ids[0], ids[1]);
                
                canHandle = (resourceLoader != null);
            }
        }
        
        return canHandle;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.UrlBasedViewResolver#buildView(java.lang.String)
     */
    protected AbstractUrlBasedView buildView(String viewName) throws Exception 
    {
        AbstractUrlBasedView view = null;

        if (viewName.startsWith(URI_PREFIX_OBJECT + "/") || URI_PREFIX_OBJECT.equals(viewName))
        {
            // request context
            RequestContext context = ThreadLocalRequestContext.getRequestContext();        

            // object id
            String resourceId = context.getParameter("o");
            
            // load the object and map into context
            Resource resource = getWebFrameworkResourceService().getResource(resourceId);
            if (resource != null)
            {
                context.setCurrentObject(resource);

                // lookup a template for this object type id
                String objectTypeId = resource.getObjectTypeId();
                
                // Look up which template to use to display this content
                // this must also take into account the current format
                Map<String, ModelObject> objects = getModelObjectService().findContentAssociations(objectTypeId, null, null, null, null);
                if (objects.size() > 0)
                {
                    ContentAssociation association = (ContentAssociation) objects.values().iterator().next();
                    ModelObject o = association.getObject(context);
                    if (o != null)
                    {
                        if (o instanceof TemplateInstance)
                        {
                            // create a TemplateView
                            view = new TemplateView(getWebframeworkConfigElement(), 
                                                    getModelObjectService(), 
                                                    getWebFrameworkResourceService(), 
                                                    getWebFrameworkRenderService(),
                                                    getTemplatesContainer());
                            view.setUrl(o.getId());
                        }
                        if (o instanceof Page)
                        {
                            // create a PageView
                            view = new PageView(getWebframeworkConfigElement(), 
                                                getModelObjectService(), 
                                                getWebFrameworkResourceService(), 
                                                getWebFrameworkRenderService(),
                                                getTemplatesContainer());
                            view.setUrl(o.getId());
                        }
                    }
                }
                else
                {
                    // some data that we can use in the reporting page
                    context.setValue("resource", new ScriptResource(context, resource));

                    // show system page: content association missing
                    view = new SystemPageView(getWebframeworkConfigElement(), 
                                              getModelObjectService(), 
                                              getWebFrameworkResourceService(), 
                                              getWebFrameworkRenderService(),
                                              getTemplatesContainer());
                    view.setUrl(WebFrameworkConstants.SYSTEM_PAGE_CONTENT_ASSOCIATION_MISSING);
                }
            }
        }
        
        return view;
    }
}
