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

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.extensibility.impl.AbstractExtensibilityDirective;
import org.springframework.extensions.surf.render.RenderService;

public abstract class RenderServiceExtensibilityDirective extends AbstractExtensibilityDirective
{
    /**
     * <p>A <code>RenderService</code> is required to generate the output for this directive. 
     * It should be provided when instantiating the class.</p>
     */
    private RenderService renderService;
    
    /**
     * <p>Returns the <code>RenderService</code> supplied when instantiating the class.</p>
     * @return A <code>RenderService</code>
     */
    public RenderService getRenderService()
    {
        return renderService;
    }

    /**
     * <p>A <code>RequestContext</code> is typically needed as an argument of the <code>PresentationService</code>
     * methods that are used to generate the output for this directive. It should be provided when instantiating 
     * the class.</p>
     */
    private RequestContext requestContext;
    
    /**
     * <p>Returns the <code>RequestContext</code> supplied when instantiating the class</p>
     * @return A <code>RequestContext</code>
     */
    public RequestContext getRequestContext()
    {
        return requestContext;
    }
    
    private ModelObject object;
    
    public ModelObject getObject()
    {
        return object;
    }
    
    public RenderServiceExtensibilityDirective(String directiveName, 
                                               ExtensibilityModel model, 
                                               RequestContext context, 
                                               ModelObject object,  
                                               RenderService renderService)
    {
        super(directiveName, model);
        this.requestContext = context;
        this.object = object;
        this.renderService = renderService;
    }
}
