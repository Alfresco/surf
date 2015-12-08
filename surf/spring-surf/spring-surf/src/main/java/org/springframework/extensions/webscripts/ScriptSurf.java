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

package org.springframework.extensions.webscripts;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkConstants;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;

/**
 * A root-scoped Java object that represents the framework configuration
 * 
 * @author muzquiano
 */
@ScriptClass 
(
        help="Root-scoped Java object that represents the configuration of Spring Surf",
        types=
        {
                ScriptClassType.JavaScriptRootObject,
                ScriptClassType.TemplateRootObject
        }
)
public final class ScriptSurf extends ScriptBase
{
    public ScriptSurf(RequestContext context)
    {
        super(context);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebFrameworkScriptBase#buildProperties()
     */
    protected ScriptableMap buildProperties()
    {
        return null;
    }
    
    
    // --------------------------------------------------------------
    // JavaScript Properties
    
    @ScriptMethod 
    (
            help="Indicates whether Spring Surf has a non-default User Factory configured for it",
            output="Whether a user factory is configured"
    )
    public boolean getLoginEnabled()
    {
        boolean enabled = false;
        
        String defaultUserFactoryId =context.getServiceRegistry().getWebFrameworkConfiguration().getDefaultUserFactoryId();
        if (defaultUserFactoryId != null)
        {
            if (!WebFrameworkConstants.DEFAULT_USER_FACTORY_ID.equals(defaultUserFactoryId))
            {
                enabled = true;
            }
        }
        
        return enabled;       
    }
}
