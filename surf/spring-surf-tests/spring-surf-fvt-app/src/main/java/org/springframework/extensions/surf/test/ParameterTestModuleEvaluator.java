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
package org.springframework.extensions.surf.test;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.extensibility.ExtensionModuleEvaluator;

/**
 * Checks that all the evaluation properties exist as request parameters.
 * @author David Draper
 *
 */
public class ParameterTestModuleEvaluator implements ExtensionModuleEvaluator 
{
    @Override
    public boolean applyModule(RequestContext context, Map<String, String> evaluationProperties)
    {
        boolean apply = true;
        for(Entry<String, String> prop: evaluationProperties.entrySet())
        {
            String param = context.getParameters().get(prop.getKey());
            if (param != null && param.equals(prop.getValue()))
            {
                // Found matching parameter, keep going...
            }
            else
            {
                apply = false;
                break;
            }
        }
        return apply;
    }

    @Override
    public String[] getRequiredProperties()
    {
        return new String[] {};
    }
   
}
