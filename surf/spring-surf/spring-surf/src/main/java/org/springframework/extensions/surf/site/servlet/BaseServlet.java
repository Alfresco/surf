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

package org.springframework.extensions.surf.site.servlet;

import java.util.StringTokenizer;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.extensions.surf.util.I18NUtil;

/**
 * @author kevinr
 * @author muzquiano
 */
public abstract class BaseServlet extends HttpServlet
{
    /**
     * Apply the headers required to disallow caching of the response in the browser
     */
    public static void setNoCacheHeaders(HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
    }
    
   /**
    * Apply Client and Repository language locale based on the 'Accept-Language' request header
    */
   public static void setLanguageFromRequestHeader(HttpServletRequest req)
   {
      // set language locale from browser header
      String acceptLang = req.getHeader("Accept-Language");
      if (acceptLang != null && acceptLang.length() != 0)
      {
         StringTokenizer t = new StringTokenizer(acceptLang, ",; ");
         // get language and convert to java locale format
         String language = t.nextToken().replace('-', '_');
         I18NUtil.setLocale(I18NUtil.parseLocale(language));
      }
   }
}