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

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModelException;

/**
 * <p>This directive is used to address the problem of browsers caching stale CSS resources. It modifies the 
 * source file requested to include a checksum generated from the file contents to ensure that when a file is
 * updated the browser will be forced to request the updated version (because the browser cached version will be stored
 * against a different URL).</p>
 * 
 * @author David Draper
 */
public class CssDependencyDirective extends JavaScriptDependencyDirective
{
    public CssDependencyDirective(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }
    
    /**
     * <p>Overrides the default implementation to add a CSS dependency to the {@link RequestContext}.</p> 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void addLegacyDependencyRequest(Map params) throws TemplateModelException
    {
        String src = getStringProperty(params, DirectiveConstants.HREF, true);
        String media = getStringProperty(params, DirectiveConstants.MEDIA_PARAM, false);
        if (media == null)
        {
            media = DirectiveConstants.DEFAULT_MEDIA;
        }
        ProcessedDependency pd = processDependency(src);
        this.getRequestContext().addCssDependency(getUpdatedSrc(pd), media);
    }

    /**
     * <p>Overrides the default to return the "href" parameter.</p>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected String getDependencySource(Map params) throws TemplateModelException
    {
        String src = getStringProperty(params, DirectiveConstants.HREF, true);
        return src;
    }

    /**
     * <p>Overrides the default to return the {@link OutputCSSContentModelElement}.</p>
     */
    @Override
    protected DeferredContentTargetModelElement getTargetElement()
    {
        DeferredContentTargetModelElement targetElement = getModel().getDeferredContent(OutputCSSDirective.OUTPUT_DEPENDENCY_DIRECTIVE_ID, OutputCSSDirective.OUTPUT_CSS_DEPENDENCIES_DIRECTIVE_NAME);
        return targetElement;
    }

    /**
     * <p>Overrides the default implementation to instantiate and returns a {@link CssDependencyDirectiveData} instance.</p>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
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
        String media = getStringProperty(params, DirectiveConstants.MEDIA_PARAM, false);
        if (media == null)
        {
            media = DirectiveConstants.DEFAULT_MEDIA;
        }
        DependencyDirectiveData directiveData = new CssDependencyDirectiveData(id, action, target, getDirectiveName(), body, env, dependencyToAdd, group, media, getWebFrameworkConfig().isAggregateDependenciesEnabled(), targetElement);
        return directiveData;
    }
}
