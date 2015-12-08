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

import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.ExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.types.TemplateInstance;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * <p>This directive is used to address the problem of browsers caching stale JavaScript resources. It modifies the 
 * source file requested to include an MD5 checksum generated from the file contents to ensure that when a file is
 * updated the browser will be forced to request the updated version (because the browser cached version will be stored
 * against a different URL).</p>
 * 
 * @author David Draper
 */
public class JavaScriptDependencyDirective extends AbstractDependencyExtensibilityDirective
{
    public static final String FORCE_AGGREGATED_OUTPUT_PARAM = "forceAggregation";
    
    public JavaScriptDependencyDirective(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }

    @SuppressWarnings({ "rawtypes" })
    public void execute(Environment env,
                        Map params, 
                        TemplateModel[] loopVars, 
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        if (getModelObject() instanceof TemplateInstance && getRequestContext().isPassiveMode())
        {
            // Don't process this when calculating WebScript dependencies. This checks needs to be done because it is perfectly valid
            // for TemplateInstance FreeMarker templates to use the dependency directives. Because of the double-pass processing to 
            // obtain WebScript dependencies. If we don't do this check then we're guaranteed to import the same dependency twice.
        }
        else
        {
            DeferredContentTargetModelElement targetElement = getTargetElement();
            if (targetElement != null)
            {
                super.execute(env, params, loopVars, body);
            }
            else
            {
                addLegacyDependencyRequest(params);
            }
        }
    }
    
    /**
     * <p>Provides support for making dependency requests when there isn't a {@link DeferredContentTargetModelElement} to 
     * add content to. This will occur when processing the .head.ftl files of a WebScript and is considered the "legacy"
     * approach to making dependency requests. The preferred approach is to move the requests to the *.html.ftl file and 
     * use the new <@script> and <@link> directive implementations.</p>
     * 
     * @param params Map
     * @throws TemplateModelException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void addLegacyDependencyRequest(Map params) throws TemplateModelException
    {
        String src = getStringProperty(params, DirectiveConstants.SRC, true);
        ProcessedDependency pd = processDependency(src);
        this.getRequestContext().addJSDependency(getUpdatedSrc(pd));
    }
    
    /**
     * <p>Gets the parameter that defines the dependency source file. By default this is "src".</p>
     * @param params The map of parameters to search through.
     * @return The parameter defining the dependency source file.
     * @throws TemplateModelException When the target parameter cannot be found.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected String getDependencySource(Map params) throws TemplateModelException
    {
        String src = getStringProperty(params, DirectiveConstants.SRC, true);
        return src;
    }
    
    /**
     * <p>Gets the {@link DeferredContentTargetModelElement} to associate with the directive. By default this is the
     * {@link OutputJavaScriptContentModelElement}.</p>
     * @return DeferredContentTargetModelElement
     */
    protected DeferredContentTargetModelElement getTargetElement()
    {
        DeferredContentTargetModelElement targetElement = getModel().getDeferredContent(OutputJavaScriptDirective.OUTPUT_DEPENDENCY_DIRECTIVE_ID, OutputJavaScriptDirective.OUTPUT_JS_DEPENDENCIES_DIRECTIVE_NAME);
        return targetElement;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ExtensibilityDirectiveData createExtensibilityDirectiveData(String id, 
                                                                       String action, 
                                                                       String target,
                                                                       Map params, 
                                                                       TemplateDirectiveBody body, 
                                                                       Environment env) throws TemplateException
    {
        String src = getDependencySource(params);
        ProcessedDependency pd = processDependency(src);
        String group = getStringProperty(params, DirectiveConstants.GROUP_PARAM, false); // null is acceptable as a group (it is effectively the default group)
        return createDependencyDirectiveData(id, action, target, params, body, env, getUpdatedSrc(pd), group);
    }
    
    /**
     * <p>Creates the {@link DependencyDirectiveData}.</p>
     * @param id String
     * @param action String
     * @param target String
     * @param params Map
     * @param body TemplateDirectiveBody
     * @param env Environment
     * @param dependencyToAdd String
     * @param group String
     * @return DependencyDirectiveData
     */
    @SuppressWarnings("rawtypes")
    protected DependencyDirectiveData createDependencyDirectiveData(String id,
                                                                    String action, 
                                                                    String target,
                                                                    Map params,
                                                                    TemplateDirectiveBody body,
                                                                    Environment env,
                                                                    String dependencyToAdd,
                                                                    String group) throws TemplateModelException
    {
        DeferredContentTargetModelElement targetElement = getTargetElement();
        DependencyDirectiveData directiveData = new DependencyDirectiveData(id, 
                                                                            action, 
                                                                            target, 
                                                                            getDirectiveName(), 
                                                                            body, 
                                                                            env, 
                                                                            dependencyToAdd, 
                                                                            group,
                                                                            getWebFrameworkConfig().isAggregateDependenciesEnabled(),
                                                                            targetElement);
        return directiveData;
    }
}
