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

package org.springframework.extensions.surf.test.groovy;

import org.springframework.extensions.surf.test.TestCaseSetup;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;

/**
 * Tests to ensure that script processor extensions work in Surf
 * 
 * @author muzquiano
 */
public class GroovyTest
{
	public void testGroovy() throws Exception
	{
		MockHttpServletRequest req = new MockHttpServletRequest(TestCaseSetup.getServletContext(), "GET", "/test/groovy1");
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		TestCaseSetup.getDispatcherServlet().service(req, res);
		
		String result = res.getContentAsString();
		Assert.assertEquals("VALUE: SUCCESS", result);
	}
}
