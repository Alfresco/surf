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

import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;

import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

/**
 * @author Kevin Roast
 * @author Will Abson
 * 
 * @see org.springframework.extensions.webscripts.MessageMethod
 * 
 * Custom FreeMarker Template language method.
 * <p>
 * Returns an I18N message resolved for the current locale and specified message ID.
 * <p>
 * Usage: message(String id)
 */
@ScriptClass 
(
        help="Returns an I18N message resolved for the current locale and specified message ID.\n\nUsage: message(String id)",
        code="${message(\"templates.doc_info.name\")}",
        types=
        {
                ScriptClassType.TemplateAPI
        }
)
public class FreemarkerI18NMessageMethod implements TemplateMethodModelEx
{
    /**
     * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
     */
    public Object exec(List args) throws TemplateModelException
    {
        String result = "";
        int argSize = args.size();
        
        if (argSize != 0)
        {
            String id = "";
            Object arg0 = args.get(0);
            if (arg0 instanceof TemplateScalarModel)
            {
                id = ((TemplateScalarModel)arg0).getAsString();
            }
            
            if (id != null)
            {
                if (argSize == 1)
                {
                    // shortcut for no additional msg params
                    result = I18NUtil.getMessage(id);
                }
                else
                {
                    Object[] params = new Object[argSize - 1];
                    for (int i = 0; i < argSize-1; i++)
                    {
                        // ignore first passed-in arg which is the msg id
                        Object arg = args.get(i + 1);
                        if (arg instanceof TemplateScalarModel)
                        {
                            params[i] = ((TemplateScalarModel)arg).getAsString();
                        }
                        else if (arg instanceof TemplateNumberModel)
                        {
                            params[i] = ((TemplateNumberModel)arg).getAsNumber();
                        }
                        else if (arg instanceof TemplateDateModel)
                        {
                            params[i] = ((TemplateDateModel)arg).getAsDate();
                        }
                        else
                        {
                            params[i] = "";
                        }
                    }
                    result = I18NUtil.getMessage(id, params);
                }
            }
        }
        
        return result;
    }
}