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

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.surf.DependencyAggregator;
import org.springframework.extensions.surf.DojoDependencies;
import org.springframework.extensions.surf.DojoDependencyHandler;
import org.springframework.extensions.surf.webscripts.XHRDojoDependencies;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for {@link XHRDojoDependencies} aggregation gate and inline-marker rejection.
 */
public class XHRDojoDependenciesTest
{
    private TestableXHRDojoDependencies script;
    private Status status;

    @BeforeMethod
    public void setUp()
    {
        script = new TestableXHRDojoDependencies();
        status = new Status();
    }

    @Test
    public void executeImpl_returns403WhenAggregationDisabled() throws Exception
    {
        script.setWebFrameworkConfig(webFrameworkConfigWithAggregation(false));
        script.setDojoDependencyHandler(new DojoDependencyHandler());
        script.run(webScriptRequestWithJson("{\"jsonContent\":{\"services\":[],\"widgets\":[]}}"), status, null);
        Assert.assertEquals(status.getCode(), HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    public void executeImpl_returns403WhenResolvedCssUsesInlineMarker() throws Exception
    {
        script.setWebFrameworkConfig(webFrameworkConfigWithAggregation(true));
        script.setDojoDependencyHandler(inlineCssDojoHandler());
        script.setI18nDependencyHandler(new org.springframework.extensions.surf.I18nDependencyHandler());
        script.run(webScriptRequestWithJson(
                "{\"jsonContent\":{\"services\":[\"MyService\"],\"widgets\":[]}}"), status, null);
        Assert.assertEquals(status.getCode(), HttpServletResponse.SC_FORBIDDEN);
    }

    private static DojoDependencyHandler inlineCssDojoHandler()
    {
        return new DojoDependencyHandler()
        {
            @Override
            public String getPath(String base, String name)
            {
                return name;
            }

            @Override
            public DojoDependencies getDependencies(String path)
            {
                DojoDependencies deps = new DojoDependencies();
                deps.addCssDep(DependencyAggregator.INLINE_AGGREGATION_MARKER + ".x { color: red; }", "screen");
                return deps;
            }

            @Override
            public void recursivelyProcessDependencies(DojoDependencies deps,
                    Map<String, DojoDependencies> dependenciesForCurrentRequest)
            {
                // no-op for test
            }
        };
    }

    private static WebFrameworkConfigElement webFrameworkConfigWithAggregation(boolean enabled) throws Exception
    {
        WebFrameworkConfigElement config = new WebFrameworkConfigElement();
        Field field = WebFrameworkConfigElement.class.getDeclaredField("aggregateDependencies");
        field.setAccessible(true);
        field.set(config, Boolean.valueOf(enabled));
        return config;
    }

    private static WebScriptRequest webScriptRequestWithJson(final String json)
    {
        final Content body = (Content) Proxy.newProxyInstance(
                Content.class.getClassLoader(),
                new Class<?>[] { Content.class },
                (proxy, method, args) -> {
                    if ("getContent".equals(method.getName()))
                    {
                        return json;
                    }
                    if ("getMimetype".equals(method.getName()))
                    {
                        return "application/json";
                    }
                    if ("getEncoding".equals(method.getName()))
                    {
                        return "UTF-8";
                    }
                    if ("getSize".equals(method.getName()))
                    {
                        return Long.valueOf(json.length());
                    }
                    return null;
                });
        return (WebScriptRequest) Proxy.newProxyInstance(
                WebScriptRequest.class.getClassLoader(),
                new Class<?>[] { WebScriptRequest.class },
                (proxy, method, args) -> {
                    if ("getContent".equals(method.getName()))
                    {
                        return body;
                    }
                    return null;
                });
    }

    static final class TestableXHRDojoDependencies extends XHRDojoDependencies
    {
        Map<String, Object> run(WebScriptRequest req, Status status, Cache cache)
        {
            return executeImpl(req, status, cache);
        }
    }
}
