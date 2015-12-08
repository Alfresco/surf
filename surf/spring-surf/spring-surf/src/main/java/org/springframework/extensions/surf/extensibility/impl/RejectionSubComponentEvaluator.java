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
import org.springframework.extensions.surf.types.SubComponent;

/**
 * <p>A sample {@link SubComponentEvaluator} that always evaluates to false. This can be referenced
 * in an <{@code}evaluation> element to ensure that a {@link SubComponent} is not rendered.</p>
 * 
 * @author David Draper
 */
public class RejectionSubComponentEvaluator implements SubComponentEvaluator
{
    /**
     * @return Always returns <code>false</code> regardless of the context and params arguments supplied.
     */
    public boolean evaluate(RequestContext context, Map<String, String> params)
    {
        return false;
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
