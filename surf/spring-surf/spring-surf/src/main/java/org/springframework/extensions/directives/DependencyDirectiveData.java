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

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ContentModelElement;
import org.springframework.extensions.surf.extensibility.DeferredContentSourceModelElement;
import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.impl.DefaultExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.impl.ModelWriter;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

public class DependencyDirectiveData extends DefaultExtensibilityDirectiveData
{
    public DependencyDirectiveData(String id, 
                                   String action, 
                                   String target, 
                                   String directiveName,
                                   TemplateDirectiveBody body, 
                                   Environment env,
                                   String dependency,
                                   String group,
                                   boolean aggregate,
                                   DeferredContentTargetModelElement targetElement)
    {
        super(id, action, target, directiveName, body, env);
        this.dependency = dependency;
        this.group = group;
        this.aggregate = aggregate;
        this.targetElement = targetElement;
    }

    /**
     * <p>The dependency requested by the directive.</p>
     */
    protected String dependency;
    
    /**
     * <p>The group that the dependency should be added to (as requested by the directive)</p>
     */
    protected String group;
    
    /**
     * <p>This is the {@link DeferredContentSourceModelElement} that will get created when the <code>createContentModelElement</code>
     * method is called. A reference to it is required so that when the <code>render</code> method is called it can be passed to its
     * associated {@link DeferredContentTargetModelElement} instance.</p>
     */
    protected DeferredContentSourceModelElement modelElement;
    
    /**
     * <p>This is required for backwards compatibility to support WebScripts that are still using *.head.ftl files to add dependencies.
     * When a "head" file is processed the main model (and therefore any target {@link DeferredContentTargetModelElement}) will not yet
     * exist. In order to ensure that the dependency is not lost we need to add the dependency via this {@link RequestContext}.</p>
     */
    protected RequestContext context;
    
    /**
     * Indicates whether or not to aggregate the dependency.
     */
    protected boolean aggregate = false;
    
    /**
     * <p>The {@link DeferredContentTargetModelElement} to associate any created {@link DeferredContentSourceModelElement} with.</p>
     */
    protected DeferredContentTargetModelElement targetElement;
    
    @Override
    public ContentModelElement createContentModelElement()
    {
        this.modelElement = new DependencyDeferredContentSourceModelElement(getId(), getDirectiveName(), this.dependency, this.group, this.aggregate, this.targetElement); 
        return this.modelElement; 
    }

    @Override
    public void render(ModelWriter writer) throws TemplateException, IOException
    {
        // This will get called during merge, before, after and replace operations...
        // This is when we need to find the targeted DeferredContentTargetModelElement and register ourselves
        // as content to be included...
        if (this.targetElement != null)
        {
            this.targetElement.registerDeferredSourceElement(this.modelElement);
        }
    }
}
