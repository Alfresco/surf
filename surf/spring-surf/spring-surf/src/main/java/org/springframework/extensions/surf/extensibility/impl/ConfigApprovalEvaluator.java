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
 * @author David Draper
 */
public class ConfigApprovalEvaluator implements ExtensionModuleEvaluator
{
    public static final String APPLY = "apply";
    
    public boolean applyModule(RequestContext context, 
                               Map<String, String> evaluationProperties)
    {
        boolean apply = false;
        if (evaluationProperties.containsKey(APPLY))
        {
            apply = Boolean.valueOf(evaluationProperties.get(APPLY));
        }
        return apply;
    }

    public String[] getRequiredProperties()
    {
        return new String[] { APPLY };
    }
}
