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

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.render.RenderService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>A FreeMarker directive that uses a <code>PresentationService</code> to render the component 
 * specified through properties supplied when invoking it.</p>
 * 
 * @author David Draper
 * @author muzquiano
 */
public class ComponentFreemarkerTagDirective extends RenderServiceFreeMarkerDirective
{
    /**
     * <p>Instantiates a new <code>ComponentFreemarkerTagDirective</code>. The <code>RenderService</code> will be 
     * used by calling its <code>renderComponent</code> or <code>renderChromelessComponent</code> methods to generate the
     * output. The directive name is only needed for generating useful exception messages to assist debugging problems but 
     * an effort should be made to set it correctly</p>
     * 
     * @param directiveName The name of the directive represented by the instance of this class.
     * @param context A <code>RequestContext</code> required as an argument to the <code>RenderService</code> methods.
     * @param renderService A <code>RenderService</code> used to generate the output of the directive.
     */
    public ComponentFreemarkerTagDirective(String directiveName, RequestContext context, ModelObject object, RenderService renderService)
    {        
        super(directiveName, context, object, renderService);
    }

    /**
     * <p>This method is declared by the <code>TemplateDirectiveModel</code> interface that the abstract
     * superclass <code>AbstractFreeMarkerDirective</code> implements. It retrieves any properties supplied
     * when invoking the directive and passes them onto the <code>RenderService</code> methods.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(Environment env, 
                        Map params, 
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        String componentId = getStringProperty(params, "componentId", true);
        String chrome = getStringProperty(params, "chrome", false);
        boolean chromeless = getBooleanProperty(params, "chromeless", false);
        getRenderService().renderComponent(getRequestContext(), RenderFocus.BODY, componentId, chrome, chromeless);
    }
}
