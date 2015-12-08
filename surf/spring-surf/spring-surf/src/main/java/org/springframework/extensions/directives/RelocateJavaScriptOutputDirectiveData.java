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

import org.springframework.extensions.surf.extensibility.ContentModelElement;
import org.springframework.extensions.surf.extensibility.impl.DefaultExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.impl.DiscardUnboundContentModelElementImpl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;

/**
 * <p>This is referenced by the {@link RelocateJavaScriptOutputDirective} but instead of providing its own
 * {@link ContentModelElement} instance for output data to be written to, it uses an existing 
 * {@link OutputJavaScriptContentModelElement} so that the existing JavaScript content is moved to the location
 * in the model where the {@link RelocateJavaScriptOutputDirective} has been used.</p>
 * 
 * @author David Draper
 */
public class RelocateJavaScriptOutputDirectiveData extends DefaultExtensibilityDirectiveData
{
    public RelocateJavaScriptOutputDirectiveData(String id,
                                                 String action, 
                                                 String target, 
                                                 TemplateDirectiveBody body, 
                                                 Environment env,
                                                 OutputJavaScriptContentModelElement javaScriptContent)
    {
        super(id, action, target, "javascript", body, env);
        this.javaScriptContent = javaScriptContent;
    }
    
    /**
     * <p>A reference to the {@link OutputJavaScriptContentModelElement} that should be relocated. This will be
     * set by the constructor.</p>
     */
    private OutputJavaScriptContentModelElement javaScriptContent;
    
    /**
     * <p>Overrides the default implementation to return a <code>JavaScriptContentModelElement</code>
     * which will outputs an instructions to instantiate a widget with merged configuration data.</p>
     */
    @Override
    public ContentModelElement createContentModelElement()
    {
        ContentModelElement content = null;
        if (this.javaScriptContent != null)
        {
            content = this.javaScriptContent;
        }
        else
        {
            // If the JavaScript content model element was not supplied then replace it with a 
            // and element that gets ignored when the model is flushed to the output stream.
            content = new DiscardUnboundContentModelElementImpl();
        }
        return content;
    }
}
