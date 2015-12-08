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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.impl.DefaultExtensibilityDirectiveData;
import org.springframework.extensions.surf.extensibility.impl.ModelWriter;
import org.springframework.extensions.surf.render.RenderFocus;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.webscripts.ProcessorModelHelper;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * <p>Overrides the default {@link DefaultExtensibilityDirectiveData} to provide a new <code>render</code> method
 * to use the {@link RenderService} to render the specified region</p>
 * 
 * @author David Draper
 */
public class RegionDirectiveData extends DefaultExtensibilityDirectiveData
{
    private static final Log logger = LogFactory.getLog(RegionDirectiveData.class);
    
    public static final String REGION_ID = "id";
    public static final String CHROME_ID = "chrome";
    public static final String SCOPE_ID = "scope";
    public static final String CHROMELESS = "chromeless";
    
    private String regionId;
    private String scope;
    private String chromeId;
    private String templateId;
    private RenderService renderService;
    private RequestContext context;
    private boolean chromeless = false;
    
    public RegionDirectiveData(String id, 
                               String action, 
                               String target,
                               String directiveName,
                               Map<String, Object> params,
                               RenderService renderService,
                               RequestContext context,
                               TemplateDirectiveBody body, 
                               Environment env) throws TemplateException
    {
        super(id, action, target, directiveName, body, env);
        
        this.regionId = DirectiveUtils.getStringProperty(params, REGION_ID, ProcessorModelHelper.REGION_DIRECTIVE_NAME, true);
        this.scope = DirectiveUtils.getStringProperty(params, SCOPE_ID, ProcessorModelHelper.REGION_DIRECTIVE_NAME, false);
        this.templateId = DirectiveUtils.getStringProperty(params, context.getTemplateId(), ProcessorModelHelper.REGION_DIRECTIVE_NAME, false);
        if (this.scope == null)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("A scope was not provided for region: '" + regionId + "' when processing templateId: '" + templateId + "' - setting global scope to prevent NPE");
            }
            this.scope = "global";
        }
        this.chromeId = DirectiveUtils.getStringProperty(params, CHROME_ID, ProcessorModelHelper.REGION_DIRECTIVE_NAME, false);
        this.chromeless = DirectiveUtils.getBooleanProperty(params, CHROMELESS, ProcessorModelHelper.REGION_DIRECTIVE_NAME, false);
        this.renderService = renderService;
        this.context = context;
    }
    
    @Override
    public void render(ModelWriter writer) throws TemplateException, IOException
    {
        renderService.renderRegion(context, RenderFocus.BODY, templateId, regionId, scope, chromeId, chromeless);
    }
}
