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
import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.test.TestCaseSetup;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class ModelObjectTest
{
	@Test
    public void testProperties() throws Exception
    {
    	ModelObject o = TestCaseSetup.getObjectService().newObject("page");
    	TestCaseSetup.getObjectService().saveObject(o);
    	    	
    	o.setDescription("desc1");
    	Assert.assertEquals("desc1", o.getDescription());
    	
    	o.setDescriptionId("descid2");
    	Assert.assertEquals("descid2", o.getDescriptionId());
    	
    	o.setTitle("title1");
    	Assert.assertEquals("title1", o.getTitle());
    	
    	o.setTitleId("titleId1");
    	Assert.assertEquals("titleId1", o.getTitleId());
    	
    	o.setCustomProperty("customProperty1", "customPropertyValue1");
    	Assert.assertEquals("customPropertyValue1", o.getProperty("customProperty1"));
    	
    	o.setProperty("customProperty2", "customPropertyValue2");
    	Assert.assertEquals("customPropertyValue2", o.getProperty("customProperty2"));
    }
}
