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
package org.springframework.extensions.surf.extensibility;

import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.types.SubComponent;
import org.springframework.extensions.surf.types.ModuleDeployment;

/**
 * <p>A SubComponentEvaluator is used to help evaluate whether or not a {@link SubComponent} should 
 * be rendered or not. One or more SubComponentEvaluators are called upon by a {@link SubComponentEvaluation}
 * to determine whether a {@link SubComponent} should be rendered. If the <code>evaluate</code> method of
 * every SubComponentEvaluator returns <code>true</code> the the {@link SubComponent} will be rendered.</p>
 * <p>Each SubComponentEvaluator implementation should be configured as a Spring bean in the application context
 * and it's bean id should be referenced in the {@link org.springframework.extensions.surf.types.Component} or {@link ModuleDeployment} configuration (depending
 * upon whether or not it is being defined as part of base or extension configuration.</p>
 * 
 * @author David Draper
 */
public interface SubComponentEvaluator extends BeanNameAware
{
    /**
     * <p>Evaluates whether or not the information in the supplied {@link RequestContext} meets the criteria
     * defined in the supplied parameter {@link Map}.</p>
     * @param context The {@link RequestContext} currently being serviced.
     * @param params A {@link Map} of name/value parameters that define the evaluation criteria.
     * @return <code>true</code> if the evaluation passes and <code>false</code> otherwise.
     */
    public boolean evaluate(RequestContext context, Map<String, String> params);
}
