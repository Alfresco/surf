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

import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.ExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>This directive is used for outputting the CSS dependencies that have been requested by other directives onto the 
 * output stream. This directive relies on a {@link DeferredContentTargetModelElement} approach to add a place holder into the
 * {@link ExtensibilityModel} which can then be located and added to by subsequently processed directives.</p>
 * 
 * @author David Draper
 */
public class OutputCSSDirective extends AbstractDependencyExtensibilityDirective
{
    public static final String OUTPUT_DEPENDENCY_DIRECTIVE_ID = "outputCSSDependenciesDirective";
    public static final String OUTPUT_CSS_DEPENDENCIES_DIRECTIVE_NAME = "outputCSS";
    
    public OutputCSSDirective(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }

    private DependencyAggregator dependencyAggregator;
    public void setDependencyAggregator(DependencyAggregator dependencyAggregator)
    {
        this.dependencyAggregator = dependencyAggregator;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        ExtensibilityDirectiveData directiveData = new OutputCSSDirectiveData(OUTPUT_DEPENDENCY_DIRECTIVE_ID, 
                                                                              null, 
                                                                              null, 
                                                                              OUTPUT_CSS_DEPENDENCIES_DIRECTIVE_NAME, 
                                                                              params, 
                                                                              body, 
                                                                              env,
                                                                              dependencyAggregator);
        getModel().merge(directiveData);
    }
}
