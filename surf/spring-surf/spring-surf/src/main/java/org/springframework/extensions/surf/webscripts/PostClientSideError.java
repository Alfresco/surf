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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * <p>Receives client-side error reports and outputs them via the standard logging mechanisms.</p>
 * 
 * @author David Draper
 */
public class PostClientSideError  extends DeclarativeWebScript
{    
    private static final Log logger = LogFactory.getLog(PostClientSideError.class);
    
    /**
     * <p>Processes an error report and logs it.</p>
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        
        try
        {
            String content = req.getContent().getContent();
            if (content.length() > 0)
            {
                JSONParser jp = new JSONParser();
                Object o = jp.parse(content);
                if (o instanceof JSONObject)
                {
                    JSONObject jsonData = (JSONObject) o;
                    String callerName = (String)jsonData.get("callerName");
                    JSONArray messageArgs = (JSONArray)jsonData.get("messageArgs");
                    String userName = (String)jsonData.get("userName");
                    String location = (String)jsonData.get("location");
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append("The following client-side error has been reported:");
                    sb.append("\n   user: " + userName);
                    sb.append("\n   page: " + location);
                    sb.append("\n   callerName: "  + callerName);
                    sb.append("\n   messageArgs: " + messageArgs.toString());
                    logger.error(sb.toString());
                }
            }
        }
        catch (IOException e)
        {
            
        }
        catch (ParseException e)
        {
            status.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            status.setMessage("An error occurred parsing the client side error");
            status.setException(e);
            status.setRedirect(true);
        }
        
        return model;
    }
}
