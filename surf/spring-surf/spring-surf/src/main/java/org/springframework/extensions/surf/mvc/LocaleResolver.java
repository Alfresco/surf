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

package org.springframework.extensions.surf.mvc;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * Alfresco specific extension of Spring AcceptHeaderLocaleResolver.
 * 
 * @author kevinr
 */
public class LocaleResolver extends AcceptHeaderLocaleResolver 
{
    @Override
    public Locale resolveLocale(HttpServletRequest request) 
    {
        Locale locale = Locale.getDefault();
        
        // set language locale from browser header if available
        final String acceptLang = request.getHeader("Accept-Language");
        if (acceptLang != null && acceptLang.length() != 0)
        {
           StringTokenizer t = new StringTokenizer(acceptLang, ",; ");
           
           // get language and convert to java locale format
           String language = t.nextToken().replace('-', '_');
           locale = I18NUtil.parseLocale(language);
        }
        
        // set locale onto Alfresco thread local
        I18NUtil.setLocale(locale);           
        
        return locale;
    }
}
