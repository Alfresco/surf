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

import org.springframework.extensions.surf.extensibility.ContentModelElement;
import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.impl.ModelWriter;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

public class CssDependencyDirectiveData extends DependencyDirectiveData
{
    public CssDependencyDirectiveData(String id, 
                                      String action, 
                                      String target, 
                                      String directiveName,
                                      TemplateDirectiveBody body, 
                                      Environment env, 
                                      String dependency, 
                                      String group,
                                      String media,
                                      boolean aggregate,
                                      DeferredContentTargetModelElement targetElement)
    {
        super(id, action, target, directiveName, body, env, dependency, group, aggregate, targetElement);
        this.media = media;
    }

    /**
     * <p>The media type specified for the CSS dependency request.</p>
     */
    private String media;
    
    
    @Override
    public ContentModelElement createContentModelElement()
    {
        this.modelElement = new CssDependencyContentModelElement(getId(), getDirectiveName(), this.dependency, this.group, this.media, this.aggregate, this.targetElement); 
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
        else
        {
            this.context.addCssDependency(this.dependency, this.media);
        }
    }
}
