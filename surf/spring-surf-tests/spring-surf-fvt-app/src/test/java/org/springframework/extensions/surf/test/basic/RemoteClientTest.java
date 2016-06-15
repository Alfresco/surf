/*
 * Copyright (C) 2005-2016 Alfresco Software Limited.
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
package org.springframework.extensions.surf.test.basic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.springframework.extensions.surf.test.AbstractJettyTest;
import org.springframework.extensions.surf.test.AbstractTestServerSetup;
import org.springframework.extensions.surf.util.StringBuilderWriter;
import org.springframework.extensions.webscripts.connector.HttpMethod;
import org.springframework.extensions.webscripts.connector.RemoteClient;
import org.springframework.extensions.webscripts.connector.Response;
import org.testng.annotations.Test;

/**
 * @author Kevin Roast
 */
public class RemoteClientTest extends AbstractJettyTest
{
    @Test
    public void testRemoteClientGET() throws IOException
    {
        RemoteClient client = new RemoteClient();
        client.setEndpoint(AbstractTestServerSetup._URL_PREFIX);
        
        // test a basic GET for the webscripts index page
        Response res1 = client.call("index");
        Assert.assertEquals("Expected 200 from testRemoteClientGET", 200, res1.getStatus().getCode());
    }
    
    @Test
    public void testRemoteClientInputStreamPOST() throws IOException
    {
        RemoteClient client = new RemoteClient();
        client.setEndpoint(AbstractTestServerSetup._URL_PREFIX);
        
        // test a POST with an inputstream of data as x-www-form-urlencoded content type
        ByteArrayInputStream bas = new ByteArrayInputStream("reset=on".getBytes("utf-8"));
        client.setRequestContentType("application/x-www-form-urlencoded");
        client.setRequestMethod(HttpMethod.POST);
        Response res = client.call("index", bas);
        Assert.assertEquals(200, res.getStatus().getCode());
        Assert.assertTrue("Expected response from testRemoteClientInputStreamPOST", res.getResponse().indexOf("Reset Web Scripts Registry") != -1);
    }
    
    @Test
    public void testRemoteClientServletRequestParamsPOST() throws IOException
    {
        RemoteClient client = new RemoteClient();
        client.setEndpoint(AbstractTestServerSetup._URL_PREFIX);
        
        Map<String, String[]> params = new HashMap<>();
        String[] val = new String[1];
        val[0] = "on";
        params.put("reset", val);
        client.setRequestContentType("application/x-www-form-urlencoded");
        client.setRequestMethod(HttpMethod.POST);
        StringBuilderWriter out = new StringBuilderWriter();
        Response res = client.call("index", new MockHttpServletRequest(params), new MockHttpServletResponse(out));
        String response = out.toString();
        Assert.assertEquals(200, res.getStatus().getCode());
        Assert.assertTrue("Expected response from testRemoteClientServletRequestParamsPOST", response.indexOf("Reset Web Scripts Registry") != -1);
    }
    
    @Test
    public void testRemoteClientServletRequestInputStreamMultipartPOST() throws IOException
    {
        RemoteClient client = new RemoteClient();
        client.setEndpoint(AbstractTestServerSetup._URL_PREFIX);
        
        ByteArrayInputStream bas = new ByteArrayInputStream(("--ALFFORM\r\n" + "Content-Disposition: form-data; name=\"reset\"\r\n\r\n" + "on" + "\r\n--ALFFORM--").getBytes("utf-8"));
        client.setRequestContentType("multipart/form-data; boundary=ALFFORM");
        client.setRequestMethod(HttpMethod.POST);
        StringBuilderWriter out = new StringBuilderWriter();
        Response res = client.call("index", new MockHttpServletRequest(bas), new MockHttpServletResponse(out));
        String response = out.toString();
        Assert.assertEquals(200, res.getStatus().getCode());
        Assert.assertTrue("Expected response from testRemoteClientServletRequestInputStreamPOST", response.indexOf("Reset Web Scripts Registry") != -1);
    }
    
    class MockHttpServletRequest implements HttpServletRequest
    {
        String encoding;
        String contentType;
        InputStream in;
        Map<String, String[]> requestMap;
        
        private MockHttpServletRequest()
        {
        }
        
        private MockHttpServletRequest(InputStream in)
        {
            this.in = in;
        }
        
        private MockHttpServletRequest(Map<String, String[]> requestMap)
        {
            this.requestMap = requestMap;
        }
        
        @Override
        public Object getAttribute(String name)
        {
            return null;
        }

        @Override
        public Enumeration getAttributeNames()
        {
            return null;
        }

        @Override
        public String getCharacterEncoding()
        {
            return encoding;
        }

        @Override
        public void setCharacterEncoding(String env) throws UnsupportedEncodingException
        {
        }

        @Override
        public int getContentLength()
        {
            return -1;
        }

        @Override
        public String getContentType()
        {
            return contentType;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException
        {
            return new ServletInputStream()
            {
                @Override
                public int read() throws IOException
                {
                    return in.read();
                }
            };
        }

        @Override
        public String getParameter(String name)
        {
            return null;
        }

        @Override
        public Enumeration getParameterNames()
        {
            return null;
        }

        @Override
        public String[] getParameterValues(String name)
        {
            return null;
        }

        @Override
        public Map getParameterMap()
        {
            return requestMap;
        }

        @Override
        public String getProtocol()
        {
            return null;
        }

        @Override
        public String getScheme()
        {
            return null;
        }

        @Override
        public String getServerName()
        {
            return null;
        }

        @Override
        public int getServerPort()
        {
            return 0;
        }

        @Override
        public BufferedReader getReader() throws IOException
        {
            return null;
        }

        @Override
        public String getRemoteAddr()
        {
            return null;
        }

        @Override
        public String getRemoteHost()
        {
            return null;
        }

        @Override
        public void setAttribute(String name, Object o)
        {
        }

        @Override
        public void removeAttribute(String name)
        {
        }

        @Override
        public Locale getLocale()
        {
            return null;
        }

        @Override
        public Enumeration getLocales()
        {
            return null;
        }

        @Override
        public boolean isSecure()
        {
            return false;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path)
        {
            return null;
        }

        @Override
        public String getRealPath(String path)
        {
            return null;
        }

        @Override
        public int getRemotePort()
        {
            return 0;
        }

        @Override
        public String getLocalName()
        {
            return null;
        }

        @Override
        public String getLocalAddr()
        {
            return null;
        }

        @Override
        public int getLocalPort()
        {
            return 0;
        }

        @Override
        public String getAuthType()
        {
            return null;
        }

        @Override
        public Cookie[] getCookies()
        {
            return null;
        }

        @Override
        public long getDateHeader(String name)
        {
            return 0;
        }

        @Override
        public String getHeader(String name)
        {
            return null;
        }

        @Override
        public Enumeration getHeaders(String name)
        {
            return null;
        }

        @Override
        public Enumeration getHeaderNames()
        {
            return new Enumeration<String>()
            {
                @Override
                public boolean hasMoreElements()
                {
                    return false;
                }

                @Override
                public String nextElement()
                {
                    return null;
                }
            };
        }

        @Override
        public int getIntHeader(String name)
        {
            return 0;
        }

        @Override
        public String getMethod()
        {
            return null;
        }

        @Override
        public String getPathInfo()
        {
            return null;
        }

        @Override
        public String getPathTranslated()
        {
            return null;
        }

        @Override
        public String getContextPath()
        {
            return null;
        }

        @Override
        public String getQueryString()
        {
            return null;
        }

        @Override
        public String getRemoteUser()
        {
            return null;
        }

        @Override
        public boolean isUserInRole(String role)
        {
            return false;
        }

        @Override
        public Principal getUserPrincipal()
        {
            return null;
        }

        @Override
        public String getRequestedSessionId()
        {
            return null;
        }

        @Override
        public String getRequestURI()
        {
            return null;
        }

        @Override
        public StringBuffer getRequestURL()
        {
            return null;
        }

        @Override
        public String getServletPath()
        {
            return null;
        }

        @Override
        public HttpSession getSession(boolean create)
        {
            return null;
        }

        @Override
        public HttpSession getSession()
        {
            return null;
        }

        @Override
        public boolean isRequestedSessionIdValid()
        {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie()
        {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL()
        {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromUrl()
        {
            return false;
        }
    }
    
    class MockHttpServletResponse implements HttpServletResponse
    {
        MockHttpServletResponse(Writer out)
        {
            this.out = out;
        }

        int statusCode;
        Writer out;
        
        @Override
        public String getCharacterEncoding()
        {
            return null;
        }

        @Override
        public String getContentType()
        {
            return null;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException
        {
            return new ServletOutputStream()
            {
                @Override
                public void write(int b) throws IOException
                {
                    if (out != null)
                    {
                        out.write(b);
                    }
                }
            };
        }

        @Override
        public PrintWriter getWriter() throws IOException
        {
            return null;
        }

        @Override
        public void setCharacterEncoding(String charset)
        {
        }

        @Override
        public void setContentLength(int len)
        {
        }

        @Override
        public void setContentType(String type)
        {
        }

        @Override
        public void setBufferSize(int size)
        {
        }

        @Override
        public int getBufferSize()
        {
            return 0;
        }

        @Override
        public void flushBuffer() throws IOException
        {
        }

        @Override
        public void resetBuffer()
        {
        }

        @Override
        public boolean isCommitted()
        {
            return false;
        }

        @Override
        public void reset()
        {
        }

        @Override
        public void setLocale(Locale loc)
        {
        }

        @Override
        public Locale getLocale()
        {
            return null;
        }

        @Override
        public void addCookie(Cookie cookie)
        {
        }

        @Override
        public boolean containsHeader(String name)
        {
            return false;
        }

        @Override
        public String encodeURL(String url)
        {
            return null;
        }

        @Override
        public String encodeRedirectURL(String url)
        {
            return null;
        }

        @Override
        public String encodeUrl(String url)
        {
            return null;
        }

        @Override
        public String encodeRedirectUrl(String url)
        {
            return null;
        }

        @Override
        public void sendError(int sc, String msg) throws IOException
        {
        }

        @Override
        public void sendError(int sc) throws IOException
        {
        }

        @Override
        public void sendRedirect(String location) throws IOException
        {
        }

        @Override
        public void setDateHeader(String name, long date)
        {
        }

        @Override
        public void addDateHeader(String name, long date)
        {
        }

        @Override
        public void setHeader(String name, String value)
        {
        }

        @Override
        public void addHeader(String name, String value)
        {
        }

        @Override
        public void setIntHeader(String name, int value)
        {
        }

        @Override
        public void addIntHeader(String name, int value)
        {
        }

        @Override
        public void setStatus(int sc)
        {
            statusCode = sc;
        }
        
        int getStatus()
        {
            return statusCode;
        }

        @Override
        public void setStatus(int sc, String sm)
        {
            statusCode = sc;
        }
    }
}
