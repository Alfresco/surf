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

import org.testng.annotations.Test;
import org.springframework.extensions.surf.test.TestCaseSetup;
import org.springframework.extensions.surf.types.TemplateInstance;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class TemplateInstanceTest
{
	@Test
    public void testCRUD() throws Exception
    {    	
    	TemplateInstance templateInstance1 = TestCaseSetup.getObjectService().newTemplate();
    	TestCaseSetup.getObjectService().saveObject(templateInstance1);
    	TemplateInstance templateInstance2 = TestCaseSetup.getObjectService().newTemplate("templateInstance2");
    	TestCaseSetup.getObjectService().saveObject(templateInstance2);
    }
}
