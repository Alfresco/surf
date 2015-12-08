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
import org.springframework.extensions.surf.types.Component;
import org.springframework.extensions.surf.types.Page;
import org.springframework.extensions.surf.types.TemplateInstance;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class ComponentTest
{
	@Test
    public void testCRUD() throws Exception
    {
    	int c = TestCaseSetup.getObjectService().findComponents().size();
    	
    	Component component1 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component1);
    	
    	Component component2 = TestCaseSetup.getObjectService().newComponent("component2");
    	TestCaseSetup.getObjectService().saveObject(component2);
    	
    	Component component3 = TestCaseSetup.getObjectService().newComponent("global", "test", "global");
    	component3.setComponentTypeId("jsp");
    	TestCaseSetup.getObjectService().saveObject(component3);
    	
    	Assert.assertEquals(c+3, TestCaseSetup.getObjectService().findComponents().size());
    	
    	TestCaseSetup.getObjectService().removeObject(component2);
    	
    	Assert.assertEquals(c+2, TestCaseSetup.getObjectService().findComponents().size());
    	
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("jsp").size());
    }
    
    @Test
    public void testPageScope() throws Exception
    {
    	int psc = TestCaseSetup.getObjectService().findComponents("page", null, null, null).size();

    	Page page4 = TestCaseSetup.getObjectService().newPage("home");
    	TestCaseSetup.getObjectService().saveObject(page4);

    	Component component41 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component41);

    	Component component42 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component42);
    	
    	TestCaseSetup.getObjectService().bindComponent(component41, "page", "test41", page4.getId());
    	TestCaseSetup.getObjectService().bindComponent(component42, "page", "test42", page4.getId());
    	
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("page", "test41", page4.getId(), null).size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("page", "test42", page4.getId(), null).size());
    	Assert.assertEquals(psc+2, TestCaseSetup.getObjectService().findComponents("page", null, page4.getId(), null).size());
    }
    
    @Test
    public void testTemplateScope() throws Exception
    {
    	int tsc = TestCaseSetup.getObjectService().findComponents("template", null, null, null).size();    	
    	
    	TemplateInstance templateInstance5 = TestCaseSetup.getObjectService().newTemplate("layout");
    	TestCaseSetup.getObjectService().saveObject(templateInstance5);
    	
    	Component component51 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component51);

    	Component component52 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component52);
    	
    	TestCaseSetup.getObjectService().bindComponent(component51, "template", "test51", templateInstance5.getId());
    	TestCaseSetup.getObjectService().bindComponent(component52, "template", "test52", templateInstance5.getId());
    	
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("template", "test51", templateInstance5.getId(), null).size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("template", "test52", templateInstance5.getId(), null).size());
    	Assert.assertEquals(tsc+2, TestCaseSetup.getObjectService().findComponents("template", null, templateInstance5.getId(), null).size());
    }
    
    @Test
    public void testGlobalScope() throws Exception
    {
    	int gsc = TestCaseSetup.getObjectService().findComponents("global", null, null, null).size();    	
    	
    	Component component61 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component61);
    	
    	Component component62 = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(component62);
    	
    	TestCaseSetup.getObjectService().bindComponent(component61, "global", "test61", "global");
    	TestCaseSetup.getObjectService().bindComponent(component62, "global", "test62", "global");
    	
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("global", "test61", "global", null).size());
    	Assert.assertEquals(1, TestCaseSetup.getObjectService().findComponents("global", "test62", "global", null).size());
    	Assert.assertEquals(gsc+2, TestCaseSetup.getObjectService().findComponents("global", null, "global", null).size());
    }
    
    @Test
    public void testProperties() throws Exception
    {
    	Component c = TestCaseSetup.getObjectService().newComponent();
    	TestCaseSetup.getObjectService().saveObject(c);
    	
    	c.setChrome("chrome1");
    	Assert.assertEquals("chrome1", c.getChrome());
    	
    	c.setComponentTypeId("ct1");
    	Assert.assertEquals("ct1", c.getComponentTypeId());
    	
    	c.setDescription("desc1");
    	Assert.assertEquals("desc1", c.getDescription());
    	
    	c.setDescriptionId("descid2");
    	Assert.assertEquals("descid2", c.getDescriptionId());
    	
    	c.setRegionId("region1");
    	Assert.assertEquals("region1", c.getRegionId());
    	
    	c.setScope("scope1");
    	Assert.assertEquals("scope1", c.getScope());
    	
    	c.setSourceId("source1");
    	Assert.assertEquals("source1", c.getSourceId());
    	
    	c.setTitle("title1");
    	Assert.assertEquals("title1", c.getTitle());
    }
}
