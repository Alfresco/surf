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

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.webscripts.MessagesWebScript;

public interface DirectiveFactory
{
    /**
     * <p>The {@link MessagesWebScript} is required for setting up i18n messages so will be needed by the <code>createMessagesDependencyDirective</code>
     * method. It should be set through Spring bean configuration so that it can easily be reconfigured without changing the Surf source code.</p>
     * @return MessagesWebScript
     */
    public MessagesWebScript getMessagesWebScript();
    
    public MessagesDependencyDirective createMessagesDependencyDirective(String directiveName,
                                                                         ModelObject object,
                                                                         ExtensibilityModel extensibilityModel,
                                                                         RequestContext context);
    
    /**
     * <p>Creates a new {@link JavaScriptDependencyDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return JavaScriptDependencyDirective
     */
    public JavaScriptDependencyDirective createJavaScriptDependencyDirective(String directiveName,
                                                                             ModelObject object,
                                                                             ExtensibilityModel extensibilityModel, RequestContext context);
    
    /**
     * <p>Creates a new {@link CssDependencyDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return CssDependencyDirective
     */
    public CssDependencyDirective createCssDependencyDirective(String directiveName,
                                                               ModelObject object,
                                                               ExtensibilityModel extensibilityModel, RequestContext context);

    /**
     * <p>Creates a new {@link ChecksumResourceDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return ChecksumResourceDirective
     */
    public ChecksumResourceDirective createChecksumResourceDirective(String directiveName,
                                                                     ModelObject object,
                                                                     ExtensibilityModel extensibilityModel, RequestContext context);
    
    /**
     * <p>Creates a new {@link AddInlineJavaScriptDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return AddInlineJavaScriptDirective
     */
    public AddInlineJavaScriptDirective createAddInlineJavaScriptDirective(String directiveName,
                                                                           ModelObject object,
                                                                           ExtensibilityModel extensibilityModel, RequestContext context);
    
    /**
     * <p>Creates a new {@link CreateWebScriptWidgetsDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return CreateWebScriptWidgetsDirective
     */
    public CreateWebScriptWidgetsDirective createCreateWebScriptsDirective(String directiveName,
                                                                           ModelObject object,
                                                                           ExtensibilityModel extensibilityModel, RequestContext context);
    
    /**
     * <p>Creates a new {@link OutputCSSDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return OutputCSSDirective
     */
    public OutputCSSDirective createOutputCssDirective(String directiveName,
                                                       ModelObject object,
                                                       ExtensibilityModel extensibilityModel, RequestContext context);
    
    /**
     * <p>Creates a new {@link OutputJavaScriptDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return OutputJavaScriptDirective
     */
    public OutputJavaScriptDirective createOutputJavaScriptDirective(String directiveName,
                                                                     ModelObject object,
                                                                     ExtensibilityModel extensibilityModel, 
                                                                     RequestContext context);
    
    /**
     * <p>Creates a new {@link RelocateJavaScriptOutputDirective} directive. </p>
     * @param directiveName The name of the directive
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return RelocateJavaScriptOutputDirective
     */
    public RelocateJavaScriptOutputDirective createRelocateJavaScriptDirective(String directiveName,
                                                                               ModelObject object,
                                                                               ExtensibilityModel extensibilityModel, RequestContext context);

    /**
     * <p>Creates a new {@link ChromeDetectionDirective}.</p>
     * @param directiveName The name of the directive
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param webFrameworkConfigElement The {@link WebFrameworkConfigElement} for the application.
     * @param context The current {@link RequestContext}
     * @return ChromeDetectionDirective
     */
    public ChromeDetectionDirective createChromeDetectionDirective(String directiveName,
                                                                   ExtensibilityModel extensibilityModel,
                                                                   WebFrameworkConfigElement webFrameworkConfigElement,
                                                                   RequestContext context);
    
    public StandaloneWebScriptWrapper createStandaloneWebScriptWrapperDirective(String directiveName,
            ModelObject object,
            ExtensibilityModel extensibilityModel, 
            RequestContext context);
    
    /**
     * <p>Creates a new {@link CreateComponentDirective}.</p>
     * @param directiveName The name of the directive
     * @return CreateComponentDirective
     */
    public CreateComponentDirective createCreateComponentDirective(String directiveName);
    
    /**
     * <p>Creates a new {@link ProcessJsonModelDirective}.</p>
     * @param directiveName The name of the directive
     * @param object ModelObject
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @param webFrameworkConfig TODO
     * @return ProcessJsonModelDirective
     */
    public ProcessJsonModelDirective createProcessJsonModelDirective(String directiveName,
                                                                     ModelObject object,
                                                                     ExtensibilityModel extensibilityModel, 
                                                                     RequestContext context, 
                                                                     WebFrameworkConfigElement webFrameworkConfig);
    
    /**
     * <p>Creates a new {@link AutoComponentRegionDirective}.</p>
     * @param directiveName The name of the directive
     * @param context The current {@link RequestContext}
     * @param renderService RenderService
     * @return AutoComponentRegionDirective
     */
    public AutoComponentRegionDirective createAutoComponentRegionDirective(String directiveName,
                                                                           RequestContext context, 
                                                                           RenderService renderService);
}
