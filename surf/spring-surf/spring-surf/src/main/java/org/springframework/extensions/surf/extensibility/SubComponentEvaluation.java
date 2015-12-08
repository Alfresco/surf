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

import org.springframework.context.ApplicationContext;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.types.SubComponent;

/**
 * <p>A ComponentElementEvaluation is used to evaluate whether or not a {@link SubComponent} should be
 * rendered or not and if it should be rendered the URI of the WebScript that should be used to perform
 * the rendering.</p>
 * 
 * @author David Draper
 */
public interface SubComponentEvaluation
{
    /**
     * An evaluation should define an ID to help with debugging. This will return it.
     * @return String
     */
    public String getId();
    
    /**
     * @return The configured URI to use to render the {@link SubComponent} if all configured
     * {@link SubComponentEvaluator} instances pass successfully.
     */
    public String getUri();

    /**
     * <p>Processes all the configured {@code evaluator} instances and returns the overall
     * result. This is effectively an AND gate such that it will only return <code>true</code> if 
     * every {@link SubComponentEvaluator} returns true.</p>
     * @param context The current {@link RequestContext}
     * @param applicationContext The {@link ApplicationContext} to use to look up the {@link SubComponentEvaluator}
     * instances from.
     * @return boolean
     */
    public boolean evaluate(RequestContext context, ApplicationContext applicationContext);
    
    /**
     * <p>Indicates whether or not the successful evaluation means that the {@link SubComponent} should
     * be rendered or not. This should return <code>false</code> if the the <{@code}evaluation> XML configuration
     * includes has the attribute <code>renderIfEvaluated</code> set to the value "false".</p>
     *  
     * @return boolean
     */
    public boolean renderIfEvaluated();
    
    
    public Map<String, String> getProperties();
}
