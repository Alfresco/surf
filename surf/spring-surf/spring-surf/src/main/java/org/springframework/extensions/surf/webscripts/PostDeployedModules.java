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
package org.springframework.extensions.surf.webscripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.surf.ModuleDeploymentService;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * <p>This is the Java controller for the Module Deployment WebScript (/modules/deploy) that handles updates to the module
 * deployment configuration. The modules to deploy are retrieved from a request as an array of "stringified" JSON objects.
 * These are then parsed into {@link JSONObject} instances and passed to the configured {@link ModuleDeploymentService} to
 * be processed.</p>
 * 
 * @author David Draper
 */
public class PostDeployedModules extends DeclarativeWebScript
{
    private static final Log logger = LogFactory.getLog(PostDeployedModules.class);
    
    /**
     * <p> A @link ModuleDeploymentService} is required for processing module deployment configuration updates. This instance
     * variable should be set via the Spring application context.</p> 
     */
    private ModuleDeploymentService moduleDeploymentService;
    
    /**
     * <p>Provided so that the Spring application context can set the {@link ModuleDeploymentService} to use. It is essential that
     * the Spring bean configuration has been set correctly otherwise this controller will not achieve anything.</p>
     * 
     * @param moduleDeploymentService ModuleDeploymentService
     */
    public void setModuleDeploymentService(ModuleDeploymentService moduleDeploymentService)
    {
        this.moduleDeploymentService = moduleDeploymentService;
    }

    /**
     * <p>Processes requests to update the module deployment configuration.</p>
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        
        if (this.moduleDeploymentService != null)
        {
            List<JSONObject> modulesToDeploy = new ArrayList<JSONObject>();
            String[] moduleIdsToDeploy = req.getParameterValues("deployedModules");
            if (moduleIdsToDeploy == null)
            {
                moduleIdsToDeploy = new String[] {};
            }
            for (String modAsJsonStr: moduleIdsToDeploy)
            {
                try
                {
                    JSONObject modAsJson = new JSONObject(modAsJsonStr);
                    modulesToDeploy.add(modAsJson);
                }
                catch (JSONException e)
                {
                    if (logger.isErrorEnabled())
                    {
                        logger.error("The following exception occurred when attempting to update module deployment state", e);
                    }
                }
            }
            
            try
            {
                this.moduleDeploymentService.setDeployedModules(modulesToDeploy);
            }
            catch (JSONException e)
            {
                if (logger.isErrorEnabled())
                {
                    logger.error("The following exception occurred when attempting to update module deployment state", e);
                }
            }
        }
        return model;
    }
}
