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
import org.springframework.extensions.surf.extensibility.ExtensibilityModel;
import org.springframework.extensions.surf.mvc.ResourceController;
import org.springframework.extensions.webscripts.MessagesWebScript;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <p>This directive is used to address the problem of browsers caching stale i18n messages for the application. It modifies the 
 * WebScript URL requested to include an checksum generated from the WebScript results to ensure that when those results change
 * that the browser will be forced to request the updated version (because the browser cached version will be stored
 * against a different URL).</p>
 * 
 * @author David Draper
 */
public class MessagesDependencyDirective extends JavaScriptDependencyDirective
{
    private static final Log logger = LogFactory.getLog(MessagesDependencyDirective.class);
    
    public MessagesDependencyDirective(String directiveName, ExtensibilityModel model)
    {
        super(directiveName, model);
    }

    private static final String SERVICE = "/service/";
    private static final String LOCALE = "locale";
    public static final String LOCALE_REQ_PARM = "?locale=";
    
    /**
     * <p>The {@link MessagesWebScript} is required to obtain a checksum for the current
     * i18n messages.</p>
     */
    private MessagesWebScript messagesWebScript;
    
    /**
     * <p>Sets the {@link MessagesWebScript}.</p>
     * @param messagesWebScript MessagesWebScript
     */
    public void setMessagesWebScript(MessagesWebScript messagesWebScript)
    {
        this.messagesWebScript = messagesWebScript;
    }
    
    /**
     * <p>Overrides the default value which is mapped to the {@link ResourceController} and instead
     * returns the value which is mapped to WebScript processing.</p> 
     */
    @Override
    protected String getResourceControllerMapping()
    {
        return SERVICE;
    }
    
    /**
     * <p>Processes a request to generate i18n messages. This is specifically designed to work with the 
     * WebScript backed by {@link MessagesWebScript}. It will output a request to import a JavaScript
     * via the WebScript but will include a checksum as part of the filename to ensure that the browser
     * does not use stale cached data.</p>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void execute(Environment env,
                        Map params, 
                        TemplateModel[] loopVars, 
                        TemplateDirectiveBody body) throws TemplateException, IOException
    {
        String src = getStringProperty(params, DirectiveConstants.SRC, true);
        String locale = getStringProperty(params, LOCALE, true);
        String type = getStringProperty(params, DirectiveConstants.TYPE, false);
        if (type == null)
        {
            type = DirectiveConstants.DEFAULT_TYPE;
        }
        
        // Get the cached checksum, but if it returns null it is most likely because the Surf application 
        // has not been configured correctly to set "useChecksumDependencies" as true.
        String checksum = this.messagesWebScript.generateCachedLocaleChecksum(locale);
        if (checksum != null)
        {
            String checksumPath = this.dependencyHandler.generateCheckSumPath(src, checksum);
            ProcessedDependency pd = processDependency(checksumPath);
            StringBuilder s = new StringBuilder();
            s.append(DirectiveConstants.BEGINNING);
            s.append(type);
            s.append(DirectiveConstants.MIDDLE);
            s.append(getToInsert(pd));
            s.append(getUpdatedSrc(pd));
            s.append(LOCALE_REQ_PARM);
            s.append(locale);
            s.append(DirectiveConstants.END);
            env.getOut().write(s.toString());
        }
        else
        {
            // Handle missing resource...
            logger.error("It was not possible to generate a JavaScript import for: \"" + src + "\" because the resource could not be found");
        }
    }
}
