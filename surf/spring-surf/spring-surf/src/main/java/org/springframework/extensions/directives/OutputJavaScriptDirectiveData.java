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

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.surf.extensibility.ContentModelElement;
import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.extensibility.impl.DefaultContentModelElement;
import org.springframework.extensions.surf.extensibility.impl.DefaultExtensibilityDirectiveData;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * <p>This directive is provided to allow nested model directives to update the model outside of the
 * scope that they have been used in. Specifically this has been provided as a means of allowing WebScripts
 * to add JavaScript into the <@{code}head> element of the HTML page. This directive
 * should be placed within the <{@code}head> element in the HTML page and the {@link DefaultContentModelElement}
 * it adds to the {@link ExtensibilityModel} will be updated through the use of the {@link JavaScriptDependencyDirective}
 * directive.
 * 
 * @author David Draper
 */
public class OutputJavaScriptDirectiveData extends DefaultExtensibilityDirectiveData
{
    public OutputJavaScriptDirectiveData(String id, 
                                         String action, 
                                         String target,
                                         String directiveName,
                                         Map<String, Object> params,
                                         TemplateDirectiveBody body, 
                                         Environment env, 
                                         DependencyAggregator dependencyAggregator,
                                         WebFrameworkConfigElement webFrameworkConfig) throws TemplateException
    {
        super(id, action, target, directiveName, body, env);
        this.dependencyAggregator = dependencyAggregator;
        this.webFrameworkConfig = webFrameworkConfig;
    }
    
    private DependencyAggregator dependencyAggregator;
    private WebFrameworkConfigElement webFrameworkConfig;
    
    /**
     * <p>Creates and returns a {@link OutputCSSContentModelElement} which is a type 
     * of {@link DeferredContentTargetModelElement}. This will be added into the {@link ExtensibilityModel}
     * and will allow other directives to add content to it - specifically they will be able to add
     * new JavaScript and CSS dependencies.</p>
     */
    @Override
    public ContentModelElement createContentModelElement()
    {
        return new OutputJavaScriptContentModelElement(getId(),
                                                       getDirectiveName(), 
                                                       dependencyAggregator,
                                                       this.webFrameworkConfig);
    }
}
