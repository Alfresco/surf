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

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.extensions.surf.ModuleDeploymentService;
import org.springframework.extensions.surf.exception.ModelObjectPersisterException;
import org.springframework.extensions.surf.types.Extension;
import org.springframework.extensions.surf.types.ExtensionModule;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * <p>Handles the WebScript request to delete an {@link ExtensionModule} from a persisted {@link Extension}.</p>
 *  
 * @author David Draper
 */
public class DeleteExtensionModule  extends DeclarativeWebScript
{
    /**
     * <p> A @link ModuleDeploymentService} is required as it is used to refresh the configured module list.</p> 
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
     * <p>Processes requests to delete a module from an extension.</p>
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        if (this.moduleDeploymentService != null)
        {
            String moduleId = req.getParameter("moduleId");
            try
            {
                if (!this.moduleDeploymentService.deleteModuleFromExtension(moduleId))
                {
                    // The only explanation for not being able to delete the requested module (that
                    // is not handled by the wrapped extensions) is that the module could not be found
                    // so issue a 404 response...
                    status.setCode(HttpServletResponse.SC_NOT_FOUND);
                    status.setMessage("The requested module was not found.");
                    status.setRedirect(true);
                }
            }
            catch (ModelObjectPersisterException e)
            {
                // An error occurred saving the extension...
                status.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                status.setMessage("An error occurred saving the Extension");
                status.setException(e);
                status.setRedirect(true);
            }
            catch (DocumentException e)
            {
                // An error occurred processing the existing XML (this shouldn't really ever happen!)
                status.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                status.setMessage("An error occurred parsing the existing Extension XML");
                status.setException(e);
                status.setRedirect(true);
            }
        }
        return model;
    }
}
