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

package org.springframework.extensions.surf.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.TemplatesContainer;
import org.springframework.extensions.surf.WebFrameworkServiceRegistry;
import org.springframework.extensions.surf.site.CacheUtil;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

/**
 * Handles POST processing for console reset commands
 * 
 * @author muzquiano
 */
public class ConsoleUpdate extends DeclarativeWebScript implements ApplicationContextAware
{
    private WebFrameworkServiceRegistry serviceRegistry;
    private ApplicationContext applicationContext;
    
    public void setServiceRegistry(WebFrameworkServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        this.applicationContext = applicationContext;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.DeclarativeWebScript#executeImpl(org.alfresco.web.scripts.WebScriptRequest, org.alfresco.web.scripts.WebScriptResponse)
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status)
    {
        List<String> tasks = new ArrayList<String>();
        
        // actions
        boolean resetWebscripts = false;
        boolean resetTemplates = false;
        boolean resetObjects = false;

        // reset index
        String reset = req.getParameter("reset");
        
        if ("webscripts".equalsIgnoreCase(reset))
        {
            resetWebscripts = true;
        }
        if ("templates".equalsIgnoreCase(reset))
        {
            resetTemplates = true;
        }
        if ("objects".equalsIgnoreCase(reset))
        {
            resetObjects = true;
        }
        if ("all".equalsIgnoreCase(reset))
        {
            resetWebscripts = true;
            resetTemplates = true;
            resetObjects = true;
        }
        
        // web script resets
        if (resetWebscripts)
        {
            // reset list of web scripts
            int previousCount = getContainer().getRegistry().getWebScripts().size();
            int previousFailures = getContainer().getRegistry().getFailures().size();
            getContainer().reset();
            tasks.add("Reset Web Scripts Registry; registered " + getContainer().getRegistry().getWebScripts().size() + " Web Scripts.  Previously, there were " + previousCount + ".");
            int newFailures = getContainer().getRegistry().getFailures().size();
            if (newFailures != 0 || previousFailures != 0)
            {
                tasks.add("Warning: found " + newFailures + " broken Web Scripts.  Previously, there were " + previousFailures + ".");
            }
        }
        
        // template resets
        if (resetTemplates)
        {
            TemplatesContainer container = serviceRegistry.getTemplatesContainer();
            container.reset();
            
            tasks.add("Reset Templates Registry.");
        }
        
        // surf registry
        if (resetObjects)
        {
            RequestContext rc = ThreadLocalRequestContext.getRequestContext();
            CacheUtil.invalidateModelObjectServiceCache(rc);
            
            // we must reset the SpringMVC view resolvers - as they maintain a reference to View
            // object which could themselves reference pages or templates by ID
            Map<String, ViewResolver> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                    applicationContext, ViewResolver.class, true, false);
            for (ViewResolver resolver : matchingBeans.values())
            {
                if (resolver instanceof AbstractCachingViewResolver)
                {
                    ((AbstractCachingViewResolver)resolver).clearCache();
                }
            }
            
            tasks.add("Reset Surf Objects Registry.");
        }
        
        // create model for rendering
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        model.put("tasks", tasks);
        model.put("webscripts", getContainer().getRegistry().getWebScripts());
        model.put("failures", getContainer().getRegistry().getFailures());
        return model;
    }

}
