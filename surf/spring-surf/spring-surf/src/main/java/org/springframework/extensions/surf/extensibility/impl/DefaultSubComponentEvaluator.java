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
import org.springframework.extensions.surf.extensibility.SubComponentEvaluator;

/**
 * <p>This is the default {@link SubComponentEvaluator} and always evaluates to <code>true</code>. This 
 * is the {@link SubComponentEvaluator} that will be used when no other is configured.</p>
 * 
 * @author David Draper
 */
public class DefaultSubComponentEvaluator implements SubComponentEvaluator
{
    /**
     * @return Always returns <code>true</code> regardless of the context and params arguments supplied.
     */
    public boolean evaluate(RequestContext context, Map<String, String> params)
    {
        return true;
    }

    private String beanName = null;
    
    public String getBeanName()
    {
        return beanName;
    }

    public void setBeanName(String name)
    {
        this.beanName = name;
    }
}
