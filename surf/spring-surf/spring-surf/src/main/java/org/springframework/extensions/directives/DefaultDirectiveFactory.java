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

import org.springframework.extensions.config.ConfigService;
import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.surf.DependencyHandler;
import org.springframework.extensions.surf.DojoDependencyHandler;
import org.springframework.extensions.surf.I18nDependencyHandler;
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.render.RenderService;
import org.springframework.extensions.webscripts.LocalWebScriptRuntimeContainer;
import org.springframework.extensions.webscripts.MessagesWebScript;

/**
 * <p>The default Surf bean for instantiating FreeMarker directives.</p>
 * 
 * @author David Draper
 */
public class DefaultDirectiveFactory implements DirectiveFactory
{
    private LocalWebScriptRuntimeContainer webScriptsContainer;
    public void setWebScriptsContainer(LocalWebScriptRuntimeContainer webScriptsContainer)
    {
        this.webScriptsContainer = webScriptsContainer;
    }
    private ConfigService configService;
    public void setConfigService(ConfigService configService)
    {
        this.configService = configService;
    }
    private DojoDependencyHandler dojoDependencyHandler;
    public void setDojoDependencyHandler(DojoDependencyHandler dojoDependencyHandler)
    {
        this.dojoDependencyHandler = dojoDependencyHandler;
    }
    private WebFrameworkConfigElement webFrameworkConfig;
    public void setWebFrameworkConfig(WebFrameworkConfigElement webFrameworkConfig)
    {
        this.webFrameworkConfig = webFrameworkConfig;
    }
    private DependencyHandler dependencyHandler;
    public void setDependencyHandler(DependencyHandler dependencyHandler)
    {
        this.dependencyHandler = dependencyHandler;
    }
    private DependencyAggregator dependencyAggregator;
    public void setDependencyAggregator(DependencyAggregator dependencyAggregator)
    {
        this.dependencyAggregator = dependencyAggregator;
    }
    private ModelObjectService modelObjectService;
    public void setModelObjectService(ModelObjectService modelObjectService)
    {
        this.modelObjectService = modelObjectService;
    }
    private I18nDependencyHandler i18nDependencyHandler;
    public void setI18nDependencyHandler(I18nDependencyHandler i18nDependencyHandler)
    {
        this.i18nDependencyHandler = i18nDependencyHandler;
    }
    private MessagesWebScript messagesWebScript;
    
    /**
     * <p>Returns the message WebScript.
     * @return MessagesWebScript
     */
    public MessagesWebScript getMessagesWebScript()
    {
        return messagesWebScript;
    }
    
    /**
     * <p>Sets the {@link MessagesWebScript}.</p>
     * @param messagesWebScript MessagesWebScript
     */
    public void setMessagesWebScript(MessagesWebScript messagesWebScript)
    {
        this.messagesWebScript = messagesWebScript;
    }

    /**
     * <p>Creates a new {@link MessagesDependencyDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return MessagesDependencyDirective
     */
    public MessagesDependencyDirective createMessagesDependencyDirective(String directiveName,
                                                                         ModelObject object,
                                                                         ExtensibilityModel extensibilityModel,
                                                                         RequestContext context)
    {
        MessagesDependencyDirective d = new MessagesDependencyDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link JavaScriptDependencyDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return JavaScriptDependencyDirective
     */
    public JavaScriptDependencyDirective createJavaScriptDependencyDirective(String directiveName,
                                                                             ModelObject object,
                                                                             ExtensibilityModel extensibilityModel, 
                                                                             RequestContext context)
    {
        JavaScriptDependencyDirective d = new JavaScriptDependencyDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link CssDependencyDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return CssDependencyDirective
     */
    public CssDependencyDirective createCssDependencyDirective(String directiveName,
                                                               ModelObject object,
                                                               ExtensibilityModel extensibilityModel, 
                                                               RequestContext context)
    {
        CssDependencyDirective d = new CssDependencyDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link ChecksumResourceDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return ChecksumResourceDirective
     */
    public ChecksumResourceDirective createChecksumResourceDirective(String directiveName,
                                                                     ModelObject object,
                                                                     ExtensibilityModel extensibilityModel, 
                                                                     RequestContext context)
    {
        ChecksumResourceDirective d = new ChecksumResourceDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link AddInlineJavaScriptDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return AddInlineJavaScriptDirective
     */
    public AddInlineJavaScriptDirective createAddInlineJavaScriptDirective(String directiveName,
                                                                           ModelObject object,
                                                                           ExtensibilityModel extensibilityModel, 
                                                                           RequestContext context)
    {
        AddInlineJavaScriptDirective d = new AddInlineJavaScriptDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link CreateWebScriptWidgetsDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return CreateWebScriptWidgetsDirective
     */
    public CreateWebScriptWidgetsDirective createCreateWebScriptsDirective(String directiveName,
                                                                           ModelObject object,
                                                                           ExtensibilityModel extensibilityModel, 
                                                                           RequestContext context)
    {
        CreateWebScriptWidgetsDirective d = new CreateWebScriptWidgetsDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link OutputCSSDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return OutputCSSDirective
     */
    public OutputCSSDirective createOutputCssDirective(String directiveName,
                                                       ModelObject object,
                                                       ExtensibilityModel extensibilityModel, 
                                                       RequestContext context)
    {
        OutputCSSDirective d = new OutputCSSDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link OutputJavaScriptDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return OutputJavaScriptDirective
     */
    public OutputJavaScriptDirective createOutputJavaScriptDirective(String directiveName,
                                                                     ModelObject object,
                                                                     ExtensibilityModel extensibilityModel, 
                                                                     RequestContext context)
    {
        OutputJavaScriptDirective d = new OutputJavaScriptDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link RelocateJavaScriptOutputDirective} directive. </p>
     * @param object The current {@link ModelObject} being processed.
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return RelocateJavaScriptOutputDirective
     */
    public RelocateJavaScriptOutputDirective createRelocateJavaScriptDirective(String directiveName,
                                                                               ModelObject object,
                                                                               ExtensibilityModel extensibilityModel, 
                                                                               RequestContext context)
    {
        RelocateJavaScriptOutputDirective d = new RelocateJavaScriptOutputDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
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
                                                                   RequestContext context)
    {
        ChromeDetectionDirective d = new ChromeDetectionDirective(directiveName, extensibilityModel, webFrameworkConfigElement, context);
        return d;
    }
    
    /**
     * <p>Creates a new {@link ChromeDetectionDirective}.</p>
     * @param directiveName The name of the directive
     * @param object ModelObject
     * @param extensibilityModel The current {@link ExtensibilityModel} being worked on.
     * @param context The current {@link RequestContext}
     * @return StandaloneWebScriptWrapper
     */
    public StandaloneWebScriptWrapper createStandaloneWebScriptWrapperDirective(String directiveName,
            ModelObject object,
            ExtensibilityModel extensibilityModel, 
            RequestContext context)
    {
        StandaloneWebScriptWrapper d = new StandaloneWebScriptWrapper(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        return d;
    }
    
    /**
     * <p>Sets up a directive with the core objects needed.</p>
     * 
     * @param directive AbstractDependencyExtensibilityDirective
     * @param object ModelObject
     * @param context RequestContext
     */
    protected void setupDirective(AbstractDependencyExtensibilityDirective directive,
                                ModelObject object,
                                RequestContext context)
    {
        directive.setModelObject(object);
        directive.setRequestContext(context);
        directive.setDependencyHandler(this.dependencyHandler);
        directive.setDependencyAggregator(this.dependencyAggregator);
        directive.setWebFrameworkConfig(this.webFrameworkConfig);
    }

    public CreateComponentDirective createCreateComponentDirective(String directiveName)
    {
        CreateComponentDirective d = new CreateComponentDirective(directiveName);
        d.setModelObjectService(this.modelObjectService);
        return d;
    }

    public ProcessJsonModelDirective createProcessJsonModelDirective(String directiveName, 
                                                           ModelObject object,
                                                           ExtensibilityModel extensibilityModel, 
                                                           RequestContext context, 
                                                           WebFrameworkConfigElement webFrameworkConfig)
    {
        ProcessJsonModelDirective d = new ProcessJsonModelDirective(directiveName, extensibilityModel);
        setupDirective(d, object, context);
        d.setDojoDependencyHandler(this.dojoDependencyHandler);
        d.setI18nDependencyHandler(this.i18nDependencyHandler);
        d.setConfigService(this.configService);
        d.setWebScriptsContainer(this.webScriptsContainer);
        return d;
    }
    
    public AutoComponentRegionDirective createAutoComponentRegionDirective(String directiveName,
                                                                           RequestContext context, 
                                                                           RenderService renderService)
    {
        AutoComponentRegionDirective d = new AutoComponentRegionDirective(directiveName);
        d.setRenderService(renderService);
        d.setRequestContext(context);
        return d;
    }
}
