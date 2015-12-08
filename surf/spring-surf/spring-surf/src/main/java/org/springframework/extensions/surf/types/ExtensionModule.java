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
package org.springframework.extensions.surf.types;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.dom4j.tree.DefaultDocument;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.extensibility.BasicExtensionModule;
import org.springframework.extensions.surf.extensibility.Customization;
import org.springframework.extensions.surf.extensibility.ExtensionModuleEvaluator;
import org.springframework.extensions.surf.extensibility.XMLHelper;
import org.springframework.extensions.surf.render.RenderUtil;

/**
 * <p>Representation of the configuration of a module that provides an extension. Modules consist
 * of {@link Customization} and {@link AdvancedComponent} instances. They can optionally be configured
 * with a {@link ExtensionModuleEvaluator} that determines whether or not the module should be 
 * applied to a request.</p>
 * 
 * @author David Draper
 */
public class ExtensionModule extends BasicExtensionModule
{
    private static final Log logger = LogFactory.getLog(ExtensionModule.class);
        
    private ModelPersisterInfo key = null;
    public ModelPersisterInfo getKeyPath()
    {
        return key;
    }

    public HashMap<String, AdvancedComponent> advancedComponents = new HashMap<String, AdvancedComponent>();
    
    @SuppressWarnings("unchecked")
    public ExtensionModule(Element element, ModelPersisterInfo key)
    {
        // Get the id and description...
        super(element);
        this.key = key;
        if (getId() != null)
        {
            // Parse the component extension configuration...
            List<Element> componentsList = element.elements(COMPONENTS);
            for (Element componentsEl: componentsList)
            {
                List<Element> componentList = componentsEl.elements(COMPONENT);
                for (Element componentEl: componentList)
                {
                    // Get the id from the id attribute...
                    String componentId = componentEl.attributeValue("id");
                    if (componentId == null)
                    {
                        // ...but if not provided, try to generate it...
                        String scope = XMLHelper.getStringData(Component.PROP_SCOPE, componentEl, false);
                        String region = XMLHelper.getStringData(Component.PROP_REGION_ID, componentEl, false);
                        String source = XMLHelper.getStringData(Component.PROP_SOURCE_ID, componentEl, false);
                        componentId = RenderUtil.generateComponentId(scope, region, source);
                    }
                    
                    if (componentId != null)
                    {
                        // Create a new AdvancedComponent object for this configured element, we don't need to provide
                        // info or document objects as this data will not exist outside the lifecycle of the server...
                        AdvancedComponentImpl advancedComponent = new AdvancedComponentImpl(componentId, this.key, new DefaultDocument(new BaseElement("dummy")));
                        advancedComponent.applyConfig(componentEl);
                        advancedComponents.put(componentId, advancedComponent);
                    }
                    else
                    {
                        if (logger.isErrorEnabled())
                        {
                            logger.error("A <" + COMPONENT + "> element was found with no identification in <" + ExtensionImpl.MODULE + "> '" + getId() + "'");
                        }
                    }
                }
            }
        }
        else
        {
            if (logger.isErrorEnabled())
            {
                logger.error("A <" + ExtensionImpl.MODULE + "> was found with no identification");
            }
        }
    }
    
    public HashMap<String, AdvancedComponent> getAdvancedComponents()
    {
        return this.advancedComponents;
    }
}
