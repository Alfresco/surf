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

import java.util.List;
import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.extensibility.Customization;

public interface ModuleDeployment extends ModelObject, Comparable<ModuleDeployment>
{

    public static String TYPE_ID = "module-deployment";

    // properties
    public static String PROP_MODULE_DEPLOYMENT_TYPE = "module-deployment-type";

    public static String PROP_INDEX = "index";
    public static String PROP_EXTENSION_MODULE = "extension-module";
    public static String PROP_EVALUATOR_OVERRIDE = "evaluator-override";
    public static String PROP_EVALUATOR_PROPERTY_OVERRIDES = "evaluator-property-overrides";
    
    public String getModuleDeploymentType();
    
    public void setModuleDeploymentType(String moduleDeploymentType);
    

    public String getExtensionModuleId();
   
    public void setExtensionModuleId(String extensionModule);
    
    public ExtensionModule getExtensionModule();
    
    public void setExtensionModule(ExtensionModule extensionModule);
    
    public int getIndex();
    
    public void setIndex(int index);

    public String getVersion();
    
    public String getAutoDeployIndex();
    
    public String getEvaluator();

    public Map<String, String> getEvaluatorProperties();
    
    public void setEvaluatorOverride(String evaluatorOverride);
    
    public void setEvaluatorPropertyOverrides(Map<String, String> evaluatorProperties);
    
    public String getEvaluatorOverride();
    
    public Map<String, String> getEvaluatorPropertyOverrides();
    
    /**
     * Checks to see if the module declares an extension to the {@link AdvancedComponent} defined by the 
     * supplied id. If the an extension has been declared then it is returned.</p>
     * 
     * @param id The identifier of the {@link AdvancedComponent} extension to retrieve.
     * @return A {@link AdvancedComponent} extension matching the supplied id.
     */
    public AdvancedComponent getAdvancedComponent(String id);
    
    public List<Customization> getCustomizations();
}
