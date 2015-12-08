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
public class PageAssociationTest
{
	@Test
    public void testCRUD() throws Exception
    {
    	int c = TestCaseSetup.getObjectService().findPageAssociations().size();
    	
    	Page childPage1 = TestCaseSetup.getObjectService().newPage("childPage1");
    	TestCaseSetup.getObjectService().saveObject(childPage1);
    	Page childPage2 = TestCaseSetup.getObjectService().newPage("childPage2");
    	TestCaseSetup.getObjectService().saveObject(childPage2);
    	Page childPage3 = TestCaseSetup.getObjectService().newPage("childPage3");
    	TestCaseSetup.getObjectService().saveObject(childPage3);
    	
    	TestCaseSetup.getObjectService().associatePage("page1", "childPage1");
    	TestCaseSetup.getObjectService().associatePage("page1", "childPage2", "dummyAssociationType");
    	TestCaseSetup.getObjectService().associatePage("page2", "childPage3");

    	Assert.assertEquals(c+3, TestCaseSetup.getObjectService().findPageAssociations().size());
    	Assert.assertEquals(2, TestCaseSetup.getObjectService().findPageAssociations("page1", null, null).size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findPageAssociations("page1", "childPage2", null).size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findPageAssociations("page1", null, "dummyAssociationType").size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findPageAssociations("page2", null, null).size());
    	
    	// remove association + verify
    	TestCaseSetup.getObjectService().unassociatePage("page2", "childPage3");
    	Assert.assertEquals(0, TestCaseSetup.getObjectService().findPageAssociations("page2", null, null).size());
    	
    	// remove + verify
    	TestCaseSetup.getObjectService().unassociatePage("page1", "childPage1");
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findPageAssociations("page1", null, null).size());
    }
}
