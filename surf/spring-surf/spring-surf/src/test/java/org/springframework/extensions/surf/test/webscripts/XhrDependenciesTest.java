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
package org.springframework.extensions.surf.test.webscripts;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.surf.webscripts.XhrDependencies;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for {@link XhrDependencies} security and aggregation behaviour.
 */
public class XhrDependenciesTest
{
    private RecordingDependencyAggregator aggregator;
    private TestableXhrDependencies script;
    private Status status;

    @BeforeMethod
    public void setUp()
    {
        aggregator = new RecordingDependencyAggregator();
        script = new TestableXhrDependencies();
        script.setDependencyAggregator(aggregator);
        status = new Status();
    }

    @Test
    public void buildHashSet_stripsLeadingSlashAndPreservesOrder()
    {
        LinkedHashSet<String> set = script.buildPathSet(new String[] { "/a.js", "b.js", "/a.js" });
        Assert.assertEquals(set.size(), 2);
        Object[] ordered = set.toArray();
        Assert.assertEquals(ordered[0], "a.js");
        Assert.assertEquals(ordered[1], "b.js");
    }

    @Test
    public void executeImpl_returns403WhenAggregationDisabled() throws Exception
    {
        script.setWebFrameworkConfigElement(webFrameworkConfigWithAggregation(false));
        Map<String, Object> model = script.run(webScriptRequestWithParams("css", "foo/bar.css"), status, null);
        Assert.assertEquals(status.getCode(), HttpServletResponse.SC_FORBIDDEN);
        Assert.assertEquals(aggregator.cssCalls, 0);
        Assert.assertEquals(aggregator.jsCalls, 0);
        Assert.assertNull(model.get("cssResource"));
    }

    @Test
    public void executeImpl_returns403WhenWebFrameworkConfigMissing()
    {
        script.setWebFrameworkConfigElement(null);
        script.run(webScriptRequestWithParams("css", "foo/bar.css"), status, null);
        Assert.assertEquals(status.getCode(), HttpServletResponse.SC_FORBIDDEN);
        Assert.assertEquals(aggregator.cssCalls, 0);
    }

    @Test
    public void executeImpl_returns403WhenCssParameterUsesInlineMarker() throws Exception
    {
        script.setWebFrameworkConfigElement(webFrameworkConfigWithAggregation(true));
        script.run(webScriptRequestWithParams("css", ">>>.evil { color: red; }"), status, null);
        Assert.assertEquals(status.getCode(), HttpServletResponse.SC_FORBIDDEN);
        Assert.assertEquals(aggregator.cssCalls, 0);
    }

    @Test
    public void executeImpl_returns403WhenJsParameterUsesInlineMarker() throws Exception
    {
        script.setWebFrameworkConfigElement(webFrameworkConfigWithAggregation(true));
        script.run(webScriptRequestWithParams("js", ">>>alert(1)"), status, null);
        Assert.assertEquals(status.getCode(), HttpServletResponse.SC_FORBIDDEN);
        Assert.assertEquals(aggregator.jsCalls, 0);
    }

    @Test
    public void executeImpl_aggregatesFilePathsWithInlineContentDisabled() throws Exception
    {
        script.setWebFrameworkConfigElement(webFrameworkConfigWithAggregation(true));
        WebScriptRequest req = webScriptRequestWithParams(
                new String[] { "js", "css" },
                new String[][] { new String[] { "scripts/a.js" }, new String[] { "/themes/x.css" } });
        Map<String, Object> model = script.run(req, status, null);
        Assert.assertTrue(status.getCode() != HttpServletResponse.SC_FORBIDDEN);
        Assert.assertEquals(aggregator.jsCalls, 1);
        Assert.assertEquals(aggregator.cssCalls, 1);
        Assert.assertFalse(aggregator.lastJsAllowInline);
        Assert.assertFalse(aggregator.lastCssAllowInline);
        Assert.assertEquals(model.get("jsResource"), "checksum-a.js");
        Assert.assertEquals(model.get("cssResource"), "checksum-b.css");
        Assert.assertTrue(aggregator.lastCssPaths.contains("themes/x.css"));
    }

    private static WebFrameworkConfigElement webFrameworkConfigWithAggregation(boolean enabled) throws Exception
    {
        WebFrameworkConfigElement config = new WebFrameworkConfigElement();
        Field field = WebFrameworkConfigElement.class.getDeclaredField("aggregateDependencies");
        field.setAccessible(true);
        field.set(config, Boolean.valueOf(enabled));
        return config;
    }

    private static WebScriptRequest webScriptRequestWithParams(String paramName, String... values)
    {
        Map<String, String[]> params = new HashMap<String, String[]>(2);
        params.put(paramName, values);
        return webScriptRequestWithParams(new String[] { paramName }, new String[][] { values });
    }

    private static WebScriptRequest webScriptRequestWithParams(String[] names, String[][] values)
    {
        final Map<String, String[]> params = new HashMap<String, String[]>(4);
        for (int i = 0; i < names.length; i++)
        {
            params.put(names[i], values[i]);
        }
        return (WebScriptRequest) Proxy.newProxyInstance(
                WebScriptRequest.class.getClassLoader(),
                new Class<?>[] { WebScriptRequest.class },
                (proxy, method, args) -> {
                    if ("getParameterValues".equals(method.getName()))
                    {
                        return params.get(args[0]);
                    }
                    return null;
                });
    }

    /** Exposes {@link XhrDependencies#executeImpl} to this test package. */
    static final class TestableXhrDependencies extends XhrDependencies
    {
        LinkedHashSet<String> buildPathSet(String[] input)
        {
            return buildHashSet(input);
        }

        Map<String, Object> run(WebScriptRequest req, Status status, Cache cache)
        {
            return executeImpl(req, status, cache);
        }
    }

    static final class RecordingDependencyAggregator extends DependencyAggregator
    {
        int jsCalls;
        int cssCalls;
        boolean lastJsAllowInline;
        boolean lastCssAllowInline;
        LinkedHashSet<String> lastCssPaths;

        @Override
        public String generateJavaScriptDependencies(LinkedHashSet<String> paths, boolean allowInlineContent)
        {
            jsCalls++;
            lastJsAllowInline = allowInlineContent;
            return "checksum-a.js";
        }

        @Override
        public String generateCSSDependencies(LinkedHashSet<String> paths, boolean allowInlineContent)
        {
            cssCalls++;
            lastCssAllowInline = allowInlineContent;
            lastCssPaths = new LinkedHashSet<String>(paths);
            return "checksum-b.css";
        }
    }
}
