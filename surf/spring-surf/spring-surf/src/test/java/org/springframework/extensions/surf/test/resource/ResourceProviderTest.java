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

package org.springframework.extensions.surf.test.resource;

import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.resource.ModelObjectResourceProvider;
import org.springframework.extensions.surf.resource.Resource;
import org.springframework.extensions.surf.test.TestCaseSetup;
import org.testng.Assert;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class ResourceProviderTest
{
    public void testResources() throws Exception
    {
    	ModelObject obj = TestCaseSetup.getObjectService().newObject("page", "page1");
    	TestCaseSetup.getObjectService().saveObject(obj);
    	
    	ModelObjectResourceProvider provider = new ModelObjectResourceProvider(obj);
    	
    	Resource resource1 = provider.addResource("resource1", "http://cmis.alfresco.com/images/logo/AlfrescoLogo200.png");
    	Assert.assertEquals(1, provider.getResources().length);
    	
    	Resource resource2 = provider.addResource("resource2", "http", "cmis.alfresco.com", "/images/logo/AlfrescoLogo200.png");
    	Assert.assertEquals(2, provider.getResources().length);
    	
    	Map<String, Resource> map = provider.getResourcesMap();
    	Assert.assertEquals(resource1, map.get("resource1"));
    	Assert.assertEquals(resource2, map.get("resource2"));
    	
    	Assert.assertEquals(resource1, provider.getResource("resource1"));
    	Assert.assertEquals(resource2, provider.getResource("resource2"));
    	
    	Resource resource3 = TestCaseSetup.getServiceRegistry().getResourceService().getResource("http://www.alfresco.com/about/people/images/dave-caruana_small.jpg");
    	Assert.assertNotNull(resource3);
    	
    	provider.updateResource("resource2", resource3);
    	
    	Assert.assertEquals(resource3, provider.getResource("resource3"));
    }
}
