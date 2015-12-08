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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.extensibility.Customization;
import org.springframework.extensions.surf.extensibility.XMLHelper;

public class ModuleDeploymentImpl extends AbstractModelObject implements ModuleDeployment
{
    private static final Log logger = LogFactory.getLog(ModuleDeploymentImpl.class);
    
    private static final long serialVersionUID = -7611883161758132916L;
    
    @Override
    public String getTypeId()
    {
        return ModuleDeployment.TYPE_ID;
    }

    public ModuleDeploymentImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
    }
    
    public String getModuleDeploymentType()
    {
        return getProperty(PROP_MODULE_DEPLOYMENT_TYPE);
    }

    public void setModuleDeploymentType(String extensionType)
    {
        setProperty(PROP_MODULE_DEPLOYMENT_TYPE, extensionType);
    }

    public String getExtensionModuleId()
    {
        return this.getProperty(PROP_EXTENSION_MODULE);
    }

    public void setExtensionModuleId(String extensionModuleId)
    {
        this.setProperty(PROP_EXTENSION_MODULE, extensionModuleId);
    }

    private ExtensionModule extensionModule;
    
    public ExtensionModule getExtensionModule()
    {
        return extensionModule;
    }

    public void setExtensionModule(ExtensionModule extensionModule)
    {
        this.extensionModule = extensionModule;
    }
    
    public int getIndex()
    {
        int index = 0;
        try
        {
            String indexString = getProperty(PROP_INDEX);
            if (indexString != null)
            {
                index = Integer.parseInt(indexString);
            }
        }
        catch (NumberFormatException e)
        {
            // No action required.
        }
        return index;
    }

    public void setIndex(int index)
    {
        setProperty(PROP_INDEX, Integer.toString(index));
    }

    public int compareTo(ModuleDeployment o)
    {
        return this.getIndex() - o.getIndex();
    }

    /**
     * <p>A <code>ModuleDeploymentImpl</code> is considered equal to another object if that object is <b>either</b>
     * another <code>ModuleDeployment</code> with the same ID <b>or</b> an <code>ExtensionModule</code> with the same
     * ID. This implementation is based on the assumption that all module IDs (whether deployed or not) are unique 
     * within the context of the application. The benefit of this is that <code>Collection</code> instances of can 
     * use the .contains() method when comparing lists deployed and undeployed objects.</p>
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof ModuleDeployment)
        {
            String id = ((ModuleDeployment) obj).getId();
            if (id != null && id.equals(this.id))
            {
                equals = true;
            }
        }
        
        if (!equals && obj instanceof ExtensionModule)
        {
            String id = ((ExtensionModule) obj).getId();
            if (id != null && id.equals(this.id))
            {
                equals = true;
            }
        }
        
        return equals;
    }

    public String getVersion()
    {
        return this.extensionModule.getVersion();
    }
    
    public String getAutoDeployIndex()
    {
        return this.extensionModule.getAutoDeployIndex();
    }

    public String getEvaluator()
    {
        String evaluator = getEvaluatorOverride();
        if (evaluator == null)
        {
            evaluator = this.extensionModule.getEvaluator();
        }
        return evaluator;
    }
    
    public Map<String, String> getEvaluatorProperties()
    {
        Map<String, String> properties = getEvaluatorPropertyOverrides();
        if (properties == null || properties.size() == 0)
        {
            properties = this.extensionModule.getEvaluatorProperties();
        }
        return properties;
    }

    public void setEvaluatorOverride(String evaluatorOverride)
    {
        setProperty(PROP_EVALUATOR_OVERRIDE, evaluatorOverride);
    }

    private Map<String, String> evaluatorProperties = null;
    
    public void setEvaluatorPropertyOverrides(Map<String, String> evaluatorProperties)
    {
        // Get the overrides element from the document, if it doesn't exist add it...
        Document document = getDocument();
        Element evalPropOverrideEl = document.getRootElement().element(PROP_EVALUATOR_PROPERTY_OVERRIDES);
        if (evalPropOverrideEl == null)
        {
            evalPropOverrideEl = document.getRootElement().addElement(PROP_EVALUATOR_PROPERTY_OVERRIDES);
        }
        
        // Clear any existing content...
        evalPropOverrideEl.clearContent();
        
        for (Entry<String, String> currProp: evaluatorProperties.entrySet())
        {
            Element newProp = evalPropOverrideEl.addElement(currProp.getKey());
            newProp.setText(currProp.getValue());
        }
        
        updateXML(document);
        
        // clear cached value
        this.evaluatorProperties = null;
    }

    public String getEvaluatorOverride()
    {
        return getProperty(PROP_EVALUATOR_OVERRIDE);
    }

    public Map<String, String> getEvaluatorPropertyOverrides()
    {
        if (this.evaluatorProperties == null)
        {
            Map<String, String> evaluatorProperties = Collections.<String, String>emptyMap();
            Document document = getDocument();
            Element evalPropOverrideEl = document.getRootElement().element(PROP_EVALUATOR_PROPERTY_OVERRIDES);
            if (evalPropOverrideEl != null)
            {
                evaluatorProperties = XMLHelper.getProperties(PROP_EVALUATOR_PROPERTY_OVERRIDES, document.getRootElement());
            }
            this.evaluatorProperties = evaluatorProperties;
        }
        return this.evaluatorProperties;
    }
    
    public Map<String, AdvancedComponent> getAdvancedComponents()
    {
        Map<String, AdvancedComponent> advancedComponents = null;
        if (this.extensionModule == null)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("ModuleDeployment '" + this.id + "' does not have an associated ExtensionModule - returning empty advanced component list");
            }
            advancedComponents = new HashMap<String, AdvancedComponent>();
        }
        else
        {
            advancedComponents = this.extensionModule.getAdvancedComponents();
            if (advancedComponents == null)
            {
                advancedComponents = new HashMap<String, AdvancedComponent>();
            }
        }
        return advancedComponents;
    }
    
    public AdvancedComponent getAdvancedComponent(String id)
    {
        AdvancedComponent component = this.getAdvancedComponents().get(id);
        return component;
    }

    public List<Customization> getCustomizations()
    {
        List<Customization> customizations = null;
        if (this.extensionModule == null)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("ModuleDeployment '" + this.id + "' does not have an associated ExtensionModule - returning empty customizations list");
            }
            customizations = new ArrayList<Customization>();
        }
        else
        {
            customizations = this.extensionModule.getCustomizations();
        }
        return customizations;
    }
}