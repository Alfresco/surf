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
import org.testng.annotations.Test;
import org.springframework.extensions.surf.test.TestCaseSetup;
import org.springframework.extensions.surf.types.Page;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class PageTypeTest
{
	@Test
    public void testCRUD() throws Exception
    {
    	int c = TestCaseSetup.getObjectService().findPages().size();
    	
    	Page page1 = TestCaseSetup.getObjectService().newPage();
    	TestCaseSetup.getObjectService().saveObject(page1);
    	Page page2 = TestCaseSetup.getObjectService().newPage("page2");
    	page2.setPageTypeId("pageType2");
    	TestCaseSetup.getObjectService().saveObject(page2);

    	// verify
    	Assert.assertEquals(c+2, TestCaseSetup.getObjectService().findPages().size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findPages(null, "pageType2").size());
    	
    	// change page types + verify
    	page2.setPageTypeId("tempPageType2");
    	TestCaseSetup.getObjectService().saveObject(page2);
    	Assert.assertEquals(0, TestCaseSetup.getObjectService().findPages(null, "pageType2").size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findPages(null, "tempPageType2").size());
    	
    	// delete
    	TestCaseSetup.getObjectService().removeObject(page2);
    	Assert.assertEquals(c+1, TestCaseSetup.getObjectService().findPages().size());
    }
}
