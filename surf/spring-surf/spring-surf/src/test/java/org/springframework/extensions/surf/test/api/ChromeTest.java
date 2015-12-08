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

package org.springframework.extensions.surf.test.api;

import org.junit.Assert;
import org.springframework.extensions.surf.test.TestCaseSetup;
import org.springframework.extensions.surf.types.Chrome;
import org.testng.annotations.Test;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class ChromeTest
{
	@Test
	public void testCRUD() throws Exception
    {
    	int c = TestCaseSetup.getObjectService().findChrome().size();
    	
    	// instantiate
    	Chrome chrome1 = TestCaseSetup.getObjectService().newChrome();
    	TestCaseSetup.getObjectService().saveObject(chrome1);
    	Chrome chrome2 = TestCaseSetup.getObjectService().newChrome("chrome2");
    	chrome2.setChromeType("chromeType2");
    	TestCaseSetup.getObjectService().saveObject(chrome2);
    	
    	// verify
    	Assert.assertEquals(c+2, TestCaseSetup.getObjectService().findChrome().size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findChrome("chromeType2").size());
    	
    	// changes + verify
    	chrome2.setChromeType("tempChromeType2");
    	TestCaseSetup.getObjectService().saveObject(chrome2);
    	Assert.assertEquals(0, TestCaseSetup.getObjectService().findChrome("chromeType2").size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findChrome("tempChromeType2").size());
    	
    	// deletion
    	TestCaseSetup.getObjectService().removeObject(chrome2);
    	Assert.assertEquals(c+1, TestCaseSetup.getObjectService().findChrome().size());
    	Assert.assertEquals(0, TestCaseSetup.getObjectService().findChrome("tempChromeType2").size());
    	
    	// deletion
    	TestCaseSetup.getObjectService().removeObject(chrome1);
    	Assert.assertEquals(c, TestCaseSetup.getObjectService().findChrome().size());
    } 
}
