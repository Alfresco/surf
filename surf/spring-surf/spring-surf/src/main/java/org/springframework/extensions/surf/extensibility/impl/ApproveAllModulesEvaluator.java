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
package org.springframework.extensions.surf.extensibility.impl;

import java.util.Map;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensionModuleEvaluator;

/**
 * <p>This <code>ExtensionModuleEvaluator</code> will always approve each module request. It is designed to be the default
 * Spring Surf evaluator and applications should override the Spring bean configuration to provide a new default evaluator
 * and/or additional evaluators that modules can directly specify</p>
 * 
 * @author David Draper
 */
public class ApproveAllModulesEvaluator implements ExtensionModuleEvaluator
{
    public boolean applyModule(RequestContext context, 
                               Map<String, String> evaluationProperties)
    {
        return true;
    }

    public String[] getRequiredProperties()
    {
        return new String[] {};
    }
    
}
