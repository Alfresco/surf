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

import org.dom4j.Element;

/**
 * <p>Extends {@link Component} to provide some additional extensibility function. Overriding the default Spring Surf configuration
 * for the Component type to use an implementation of this interface will enable all existing configured components to be treated
 * as AdvancedComponents.</p>
 * <p>An AdvancedComponent is a Component that contains elements that can be individually rendered. This means that extending modules
 * can add additional renderable elements into them, or update the evaluators that determine the WebScript that renders them.</p>
 * 
 * @author David Draper
 */
public interface AdvancedComponent extends Component
{
    public static final String SUB_COMPONENTS = "sub-components";
    public static final String SUB_COMPONENT = "sub-component";
    public static final String COMPONENT = "component";
    public static final String INDEX = "index";
    public static final String ID = "id";
    public static final String EVALUATIONS = "evaluations";
    public static final String EVALUATION = "evaluation";
    public static final String EVALUATORS = "evaluators";
    public static final String EVALUATOR = "evaluator";
    public static final String NEGATE = "negate";
    public static final String URI = "uri";
    public static final String URL = "url";
    public static final String TYPE = "type";
    public static final String PARAMS = "params";
    public static final String PARAM = "param";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String RENDER_IF_EVALUATED = "render";
    public static final String PROPERTIES = "properties";
    
    /**
     * @return A {@link List} of {@link SubComponent} instances directly configured (i.e. not defined in an {@link ExtensionModule}).
     */
    public List<SubComponent> getSubComponents();
    
    public void setSubComponents(List<SubComponent> renderableElements);
    
    public boolean isAdvancedConfig();
    
    public void applyConfig(Element componentEl);
}
