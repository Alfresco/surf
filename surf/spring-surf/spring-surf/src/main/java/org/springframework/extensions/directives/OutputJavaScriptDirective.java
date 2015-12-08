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

import org.springframework.extensions.surf.extensibility.ExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>Directive for outputting JavaScript into the FreeMarker template. This directive acts as a placeholder in
 * the extensibility model for other directives (such as {@link JavaScriptDependencyDirective} to use to request
 * resources to be included earlier in the template. This directive should typically be placed in the <{@code}head>
 * element of the page and the {@link RelocateJavaScriptOutputDirective} can then be used to move all JavaScript to
 * the end of the HTML page if required.</p>
 * @author David Draper
 */
public class OutputJavaScriptDirective extends AbstractDependencyExtensibilityDirective
{
    public static final String OUTPUT_DEPENDENCY_DIRECTIVE_ID = "outputJavaScriptDirective";
    public static final String OUTPUT_JS_DEPENDENCIES_DIRECTIVE_NAME = "outputJavaScript";
    
    public OutputJavaScriptDirective(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        ExtensibilityDirectiveData directiveData = new OutputJavaScriptDirectiveData(OUTPUT_DEPENDENCY_DIRECTIVE_ID, 
                                                                                     null, 
                                                                                     null, 
                                                                                     OUTPUT_JS_DEPENDENCIES_DIRECTIVE_NAME, 
                                                                                     params, 
                                                                                     body, 
                                                                                     env,
                                                                                     this.dependencyAggregator,
                                                                                     getWebFrameworkConfig());
        getModel().merge(directiveData);
    }
}
