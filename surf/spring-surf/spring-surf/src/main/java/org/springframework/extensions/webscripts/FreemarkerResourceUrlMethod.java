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

import java.util.List;

import org.springframework.extensions.surf.FrameworkBean;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * Custom FreeMarker Template language method.
 * <p>
 * Render resource url independent of script hosting environment e.g. render inside / outside
 * a portal or within a Surf app.
 * <p>
 * Usage: resourceurl(String url)
 * 
 * @author muzquiano
 */
public final class FreemarkerResourceUrlMethod implements TemplateMethodModelEx
{
    /**
     * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
     */
    public Object exec(List args) throws TemplateModelException
    {
        RequestContext context = ThreadLocalRequestContext.getRequestContext();
        
        String result = "";
        
        if (args.size() != 0)
        {
            Object arg0 = args.get(0);
            
            if (arg0 instanceof TemplateScalarModel)
            {
                String url = ((TemplateScalarModel)arg0).getAsString();
                
                result = context.getLinkBuilder().resource(context, url);
            }
        }
        
        return result;
    }
}
