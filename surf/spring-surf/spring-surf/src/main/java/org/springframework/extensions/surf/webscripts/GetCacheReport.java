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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.extensions.surf.util.CacheReport;
import org.springframework.extensions.surf.util.CacheReporter;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * GET method implementation for the Cache Report webscript.
 * <p>
 * /caches/report API with admin authentication.
 * 
 * @author Kevin Roast
 */
public class GetCacheReport extends DeclarativeWebScript
{
    private static String INFO = "Since last refresh, cache entries have {0} by {1} items and the total size has {2} by {3} bytes.";
    
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>();
        
        // aquire the web application context from Spring
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(
                ((WebScriptServletRequest)req).getHttpServletRequest().getSession().getServletContext() );
        
        // get the beans that implement the CacheReporter index
        Map<String, CacheReporter> beans = context.getBeansOfType(CacheReporter.class);
        
        // clear the caches if appropriate request arg is present
        boolean clear = Boolean.parseBoolean(req.getParameter("clear"));
        
        // ask each bean supporting the interface to return information on its internal cache state and size
        // we then construct a model of those reports for each bean with some simple meta information on each cache
        long totalSize = 0L;
        long totalCount = 0L;
        Map<String, List<Map<String, Object>>> modelBeans = new LinkedHashMap<>();
        for (String bean: beans.keySet())
        {
            CacheReporter reporter = beans.get(bean);
            
            // clear cache if requested
            if (clear) reporter.clearCaches();
            
            List<Map<String, Object>> modelBeanReporter = new ArrayList<>();
            List<CacheReport> reports = reporter.report();
            for (CacheReport report : reports)
            {
                Map<String, Object> modelBeanReporterReport = new HashMap<>();
                modelBeanReporterReport.put("name", report.getCacheName());
                int entryCount = report.getEntryCount();
                modelBeanReporterReport.put("count", entryCount);
                totalCount += entryCount;
                long entrySize = report.getValueSizeEstimate();
                modelBeanReporterReport.put("size", entrySize);
                totalSize += entrySize;
                modelBeanReporter.add(modelBeanReporterReport);
            }
            modelBeans.put(bean, modelBeanReporter);
        }
        model.put("totalcount", totalCount);
        model.put("totalsize", totalSize);
        model.put("reports", modelBeans);
        
        // last report size and entry count may be available
        String strLastSize = req.getParameter("ls");
        long lastSize = strLastSize != null && strLastSize.length() != 0 ? Long.parseLong(strLastSize) : -1;
        String strLastCount = req.getParameter("lc");
        long lastCount = strLastCount != null && strLastCount.length() != 0 ? Long.parseLong(strLastCount) : -1;
        if (lastSize != -1 && lastCount != -1)
        {
            // build info on the size differences from the last invocation of the cache report screen
            String info = MessageFormat.format(
                    INFO,
                    totalCount >= lastCount ? "increased" : "decreased",
                    Math.abs(totalCount - lastCount),
                    totalSize >= lastSize ? "increased" : "decreased",
                    Math.abs(totalSize - lastSize));
            model.put("info", info);
        }
        
        return model;
    }
}