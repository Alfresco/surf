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

import org.springframework.extensions.surf.extensibility.CloseModelElement;
import org.springframework.extensions.surf.extensibility.ExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.extensibility.OpenModelElement;
import org.springframework.extensions.surf.extensibility.impl.CloseModelElementImpl;
import org.springframework.extensions.surf.extensibility.impl.OpenModelElementImpl;
import org.springframework.extensions.surf.types.Page;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * <p>This FreeMarker Directive can be used as the outer-wrapper in a WebScript FreeMarker template to add the the 
 * {@link OutputCSSContentModelElement} and {@link OutputJavaScriptContentModelElement} instances into the {@link ExtensibilityModel}
 * when the WebScript is not processed within the context of a {@link Page}. This ensures that any dependency files are 
 * loaded into the page.</p>
 * <p>TODO: Currently this only outputs the JS and CSS deferred content model elements. This could be further enhanced to add
 * additional content elements that set up the structure of a page</p> 
 * 
 * @author David Draper
 */
public class StandaloneWebScriptWrapper extends AbstractDependencyExtensibilityDirective
{
    public StandaloneWebScriptWrapper(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ExtensibilityDirectiveData createExtensibilityDirectiveData(String id, 
                                                                       String action, 
                                                                       String target,
                                                                       Map params, 
                                                                       TemplateDirectiveBody body, 
                                                                       Environment env) throws TemplateException
    {
        if (getRequestContext().getPage() == null)
        {
            // Add the JavaScript target content model element...
            OpenModelElement jsOpen = new OpenModelElementImpl(OutputJavaScriptDirective.OUTPUT_DEPENDENCY_DIRECTIVE_ID, 
                                                               OutputJavaScriptDirective.OUTPUT_JS_DEPENDENCIES_DIRECTIVE_NAME);
            OutputJavaScriptContentModelElement jsTarget = 
                new OutputJavaScriptContentModelElement(id, 
                                                        OutputJavaScriptDirective.OUTPUT_JS_DEPENDENCIES_DIRECTIVE_NAME, 
                                                        this.dependencyAggregator, 
                                                        getWebFrameworkConfig());
            CloseModelElement jsClose = new CloseModelElementImpl(OutputJavaScriptDirective.OUTPUT_DEPENDENCY_DIRECTIVE_ID, 
                                                                  OutputJavaScriptDirective.OUTPUT_JS_DEPENDENCIES_DIRECTIVE_NAME);
            this.getModel().insertDeferredContentTarget(1, jsOpen, jsTarget, jsClose);
            
            // Add the CSS target content model element...
            OpenModelElement cssOpen = new OpenModelElementImpl(OutputCSSDirective.OUTPUT_DEPENDENCY_DIRECTIVE_ID, 
                                                                OutputCSSDirective.OUTPUT_CSS_DEPENDENCIES_DIRECTIVE_NAME);
            OutputCSSContentModelElement cssTarget = 
                new OutputCSSContentModelElement(id, 
                                                 OutputCSSDirective.OUTPUT_CSS_DEPENDENCIES_DIRECTIVE_NAME, 
                                                 this.dependencyAggregator);
            CloseModelElement cssClose = new CloseModelElementImpl(OutputCSSDirective.OUTPUT_DEPENDENCY_DIRECTIVE_ID, 
                                                                   OutputCSSDirective.OUTPUT_CSS_DEPENDENCIES_DIRECTIVE_NAME);
            this.getModel().insertDeferredContentTarget(1, cssOpen, cssTarget, cssClose);
        }
        return super.createExtensibilityDirectiveData(id, action, target, params, body, env);
    }
}
