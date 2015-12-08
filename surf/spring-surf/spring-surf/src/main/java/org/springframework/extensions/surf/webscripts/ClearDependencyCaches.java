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
package org.springframework.extensions.surf.webscripts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.surf.DependencyHandler;
import org.springframework.extensions.surf.DojoDependencyHandler;
import org.springframework.extensions.surf.I18nDependencyHandler;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * This is the Java controller for the clear dependency caches WebScript which allows admins to clear the caches 
 * of running systems.
 *  
 * @author David Draper
 */
public class ClearDependencyCaches extends DeclarativeWebScript
{
    private DependencyHandler dependencyHandler;
    private DependencyAggregator dependencyAggregator;
    private DojoDependencyHandler dojoDependencyHandler;
    private I18nDependencyHandler i18nDependencyHandler;
    public void setDependencyHandler(DependencyHandler dependencyHandler)
    {
        this.dependencyHandler = dependencyHandler;
    }
    public void setDependencyAggregator(DependencyAggregator dependencyAggregator)
    {
        this.dependencyAggregator = dependencyAggregator;
    }
    public void setDojoDependencyHandler(DojoDependencyHandler dojoDependencyHandler)
    {
        this.dojoDependencyHandler = dojoDependencyHandler;
    }
    public void setI18nDependencyHandler(I18nDependencyHandler i18nDependencyHandler)
    {
        this.i18nDependencyHandler = i18nDependencyHandler;
    }
    
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        this.dependencyHandler.clearCaches();
        this.dependencyAggregator.clearCaches();
        this.dojoDependencyHandler.clearCaches();
        this.i18nDependencyHandler.clearCaches();
        return model;
    }
}
