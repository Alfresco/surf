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
import org.springframework.extensions.surf.DependencyHandler;
import org.springframework.extensions.surf.DependencyHandlerProcessingCallback;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.types.TemplateInstance;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>This directive is used to convert resource URLs into resource URLs containing a checksum that uniquely 
 * matches the resource contents.</p>
 * 
 * @author David Draper
 * @author Kevin Roast
 */
public class ChecksumResourceDirective extends AbstractDependencyExtensibilityDirective
{
    private static final Log logger = LogFactory.getLog(ChecksumResourceDirective.class);

    public ChecksumResourceDirective(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
            String src = getStringProperty(params, DirectiveConstants.SRC, true);
            if (getRequestContext().dependencyAlreadyRequested(src))
            {
                // This dependency has already been requested for the current request, no need to add it again.
                if (logger.isDebugEnabled())
                {
                    logger.debug("A duplicate request was made for \"" + src + "\". This duplicate request has been removed but may potentially cause problems resulting from unexpected ordering");
                }
                env.getOut().write("404_caused_by_duplicate_request: " + src);
            }
            else
            {
                String parm = getStringProperty(params, DirectiveConstants.CHECKSUM_PARM, false);
                ProcessedDependency pd = processDependency(src);
                if (this.dependencyHandler != null)
                {
                    // If no parameter argument has been provided just create a regular checksum path...
                    if (parm == null)
                    {
                        String checksumPath = this.dependencyHandler.getChecksumPath(getUpdatedSrc(pd));
                        if (checksumPath != null)
                        {
                            env.getOut().write(getToInsert(pd) + checksumPath);
                            getRequestContext().markDependencyAsRequested(src);
                        }
                        else
                        {
                            // Handle missing resource...
                            logger.error("It was not possible to generate the resource request for: \"" + src + "\" because the resource could not be found");
                        }
                    }
                    else
                    {
                        // If the checksum has been requested to be placed as a parameter then it is necessary
                        // to build the request slightly differently. This is a non-standard approach that has
                        // been created to solve specific use cases and currently is not as efficient.
                        String checksum = this.dependencyHandler.getChecksum(getUpdatedSrc(pd), new DependencyHandlerProcessingCallback() {
                            @Override
                            public String process(DependencyHandler handler, String path, String contents) throws IOException
                            {
                                String resourceContents = contents;
                                if (path.toLowerCase().endsWith(".css"))
                                {
                                    StringBuilder processedContents = new StringBuilder(resourceContents);
                                    
                                    // If we're generating CSS data images for CSS files then do it now...
                                    if (handler.getWebFrameworkConfigElement().isGenerateCssDataImagesEnabled() && handler.getCssDataImageHandler() != null)
                                    {
                                        handler.getCssDataImageHandler().processCssImages(path, processedContents);                 // works on the StringBuilder directly
                                    }
                                    
                                    // If we are performing LESS CSS processing then do it now...
                                    if (handler.getCssThemeHandler() != null)
                                    {
                                        resourceContents = handler.getCssThemeHandler().processCssThemes(path, processedContents);  // returns a String
                                    }
                                    else
                                    {
                                        resourceContents = processedContents.toString();
                                    }
                                }
                                return resourceContents;
                            }
                        });
                        env.getOut().write(getToInsert(pd) + getUpdatedSrc(pd) + DirectiveConstants.QUESTION_MARK + parm + DirectiveConstants.EQUALS + checksum);
                        getRequestContext().markDependencyAsRequested(src);
                    }
                }
            }
        }
    }
}
