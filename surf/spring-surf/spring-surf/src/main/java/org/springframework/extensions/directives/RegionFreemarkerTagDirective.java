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

import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.render.RenderService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * <p>A FreeMarker directive that uses a PresentationService to render the region
 * specified through properties supplied when invoking it.</p>
 * 
 * @author David Draper
 */
public class RegionFreemarkerTagDirective extends RenderServiceExtensibilityDirective
{
    /**
     * <p>Instantiates a new {@link RegionFreemarkerTagDirective}. The {@link RenderService} will be
     * used by calling its <code>renderRegion</code> method to generate the output. The directive name is only needed
     * for generating useful exception messages to assist debugging problems but an effort should be made to set it 
     * correctly</p>
     * 
     * @param directiveName The name of the directive represented by the instance of this class.
     * @param context A <code>RenderContext</code> required as an argument to the <code>RenderService.renderRegion</code> method.
     * @param renderService A <code>RenderService</code> used to generate the output of the directive by calling 
     * its <code>renderRegion</code> method.
     */
    public RegionFreemarkerTagDirective(String directiveName, 
                                        ExtensibilityModel model, 
                                        RequestContext context, 
                                        ModelObject object, 
                                        RenderService renderService)
    {
        super(directiveName, model, context, object, renderService);  
    }

    /**
     * <p>Overrides the the default directive to create a {@link RegionDirectiveData} object
     * for storing the extensibility directive data. This differs from the default implementation
     * in that it provides an alternative <code>render</code> method which allows us to delegate
     * to the associated {@link RenderService}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ExtensibilityDirectiveData createExtensibilityDirectiveData(String id, 
                                                                       String action,
                                                                       String target,
                                                                       Map params, 
                                                                       TemplateDirectiveBody body, 
                                                                       Environment env) throws TemplateException
    {
        RenderService renderService = getRenderService();
        RequestContext context = getRequestContext();
        return new RegionDirectiveData(id, action, target, getDirectiveName(), params, renderService, context, body, env);
    }
}
