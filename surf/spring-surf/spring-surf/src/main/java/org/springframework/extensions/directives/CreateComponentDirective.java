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
package org.springframework.extensions.directives;

import java.io.IOException;
import java.util.Map;

import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.exception.ModelObjectPersisterException;
import org.springframework.extensions.surf.extensibility.impl.AbstractFreeMarkerDirective;
import org.springframework.extensions.surf.types.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>This directive is used for creating {@link Component} instances just before they are required by a region directive.
 * This allows developers to quickly reference WebScripts without the overhead of creating additional configuration files.</p>
 * 
 * @author David Draper
 *
 */
public class CreateComponentDirective extends AbstractFreeMarkerDirective
{
    public CreateComponentDirective(String directiveName)
    {
        super(directiveName);
    }

    /**
     * The name to reference this directive by in FreeMarker templates.
     */
    public static final String DIRECTIVE_NAME = "createComponent";
    
    /**
     * A {@link ModelObjectService} is required to create {@link Component} instances.
     */
    private ModelObjectService modelObjectService = null;
    
    /**
     * Setter provided so that the Spring application context can set the {@link ModelObjectService}.
     * @param modelObjectService ModelObjectService
     */
    public void setModelObjectService(ModelObjectService modelObjectService)
    {
        this.modelObjectService = modelObjectService;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void execute(Environment env, 
                        Map params, 
                        TemplateModel[] loopVars, 
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        String scope = getStringProperty(params, "scope", true);
        String regionId = getStringProperty(params, "regionId", true);
        String sourceId = getStringProperty(params, "sourceId", true);
        String uri = getStringProperty(params, "uri", true);
        if (this.modelObjectService.getComponent(scope, regionId, sourceId) == null)
        {
            Component component = this.modelObjectService.newComponent(scope, regionId, sourceId);
            component.setURI(uri);
            component.setURL(uri);
            try
            {
                this.modelObjectService.saveObject(component);
            }
            catch (ModelObjectPersisterException e)
            {
                // No action required if save failed.
            }
        }
    }

}
