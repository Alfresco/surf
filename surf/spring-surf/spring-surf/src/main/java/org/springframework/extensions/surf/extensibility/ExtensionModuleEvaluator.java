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

import org.springframework.extensions.surf.RequestContext;

/**
 * <p>Implementations of this interface can be used to determine whether or not an extension module should be applied
 * to an existing extensibility model.
 * 
 * @author David Draper
 *
 */
public interface ExtensionModuleEvaluator
{
    /**
     * <p>Determines whether or not to apply a module. The module being processed will already have been matched to the
     * path being processed but an evaluator can still be used to only apply the module in certain circumstances. These
     * will typically be dictated by whether or not some data in the supplied model matches the criteria defined in the
     * supplied <code>evaluationProperties</code>.</p>  
     * @param context The current {@link RequestContext}
     * @param evaluationProperties The evaluation properties defined in the module.
     * @return <code>true</code> if the module should be applied and <code>false</code> otherwise.
     */
    public boolean applyModule(RequestContext context,
                               Map<String, String> evaluationProperties);
    
    /**
     * <p>Returns the names of the required evaluation properties that are needed to successfully perform an evaluation.
     * This information is used when provided a user-interface that allows a {@link ExtensionModuleEvaluator} to be 
     * dynamically configured for a module.</p>
     * 
     * @return A String array containing the names of the properties that are required by the evaluator.
     */
    public String[] getRequiredProperties();
}
