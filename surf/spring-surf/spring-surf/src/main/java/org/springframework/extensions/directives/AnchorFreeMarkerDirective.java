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

import org.springframework.extensions.surf.render.RenderService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>This class uses a <code>RenderService</code> to render an HTML anchor that provides a link
 * to the object defined by the supplied parameters. It is the FreeMarker equivalent of the custom JSP 
 * <code>ObjectAnchorTag</code>.</p>
 * 
 * @author David Draper
 */
public class AnchorFreeMarkerDirective extends RenderServiceFreeMarkerDirective
{
    /**
     * <p>Instantiates a new <code>AnchorFreeMarkerDirective</code>. The <code>RenderService</code> will be 
     * used by calling its <code>generateAnchorLink</code> method to generate the
     * output. The directive name is only needed for generating useful exception messages to assist debugging problems but 
     * an effort should be made to set it correctly</p>
     * 
     * @param directiveName The name of the directive represented by the instance of this class.
     * @param renderService A <code>RenderService</code> used to generate the output of the directive.
     */
    public AnchorFreeMarkerDirective(String directiveName, RenderService renderService)
    {        
        // PLEASE NOTE: We're intentionally supplying null as the RenderContext argument to the super class
        // constructor simply because we know that it is not required. There seemed little point in creating
        // an additional class in the hierarchy to just support PresentationService directives that didn't
        // require a RenderContext.
        super(directiveName, null, null, renderService);
    }
    
    /**
     * <p>This method is implemented to satisfy the <code>TemplateDirectiveModel</code> interface that is 
     * implemented by the <code>AbstractFreeMarkerDirective</code> in the class hierarchy. It retrieves the
     * any parameters that have been supplied when invoking the directive and passes them onto the
     * <code>PresentationService</code> to render the HTML anchor which is then output.</p>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(Environment env, 
                        Map params, 
                        TemplateModel[] loopVars, 
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        // Get the properties - note that none of these are required which is a limitation of the original
        // API. This means that incorrect usage could result in failure to produce an anchor...
        String pageId = getStringProperty(params, "page", false);
        String pageTypeId = getStringProperty(params, "pageType", false);
        String objectId = getStringProperty(params, "object", false);
        String formatId = getStringProperty(params, "format", false);
        String target = getStringProperty(params, "target", false);
        
        // Render the start of the HTML anchor, render the body and then close the HTML anchor. 
        String anchorStart = getRenderService().generateAnchorLink(pageTypeId, pageId, objectId, formatId, target);
        env.getOut().write(anchorStart);
        body.render(env.getOut());        
        env.getOut().write("</A>");        
    }
}
