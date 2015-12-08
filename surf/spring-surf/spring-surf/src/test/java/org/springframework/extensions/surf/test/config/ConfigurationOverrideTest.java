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

package org.springframework.extensions.surf.test.config;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.surf.UserFactory;
import org.springframework.extensions.surf.test.TestCaseSetup;
import org.testng.Assert;

/**
 * Tests the Surf API directly using mock objects
 * 
 * @author muzquiano
 */
public class ConfigurationOverrideTest
{
	public void testConfig()
	{
		// assert default settings
	    WebFrameworkConfigElement config = TestCaseSetup.getServiceRegistry().getWebFrameworkConfiguration();
		Assert.assertEquals("testcomponentchrome1", config.getDefaultComponentChrome());
		Assert.assertEquals("testregionchrome1", config.getDefaultRegionChrome());
		Assert.assertEquals("testsiteconfig1", config.getDefaultSiteConfigurationId());
		Assert.assertEquals("testtheme1", config.getDefaultThemeId());
		Assert.assertEquals("testformat1", config.getDefaultFormatId());
		
		Assert.assertEquals("webframework.factory.user.test1", config.getDefaultUserFactoryId());
		
		UserFactory userFactory = TestCaseSetup.getServiceRegistry().getUserFactory();
		Assert.assertEquals("org.springframework.extensions.surf.config.TestUserFactory", userFactory.getClass().getName());
		
		// check that our formats exist
		Assert.assertNotNull(config.getFormatDescriptor("testformat1"));
		Assert.assertNotNull(config.getFormatDescriptor("testformat2"));
	}
}
