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
package org.springframework.extensions.surf.test.basic;

import org.springframework.extensions.surf.test.AbstractJettyTest;
import org.testng.annotations.Test;

/**
 * @author David Draper
 *
 * <p>It seems to be possible to configure the a "site" through an XML configuration file. In the sample applications
 * this file defaults to being called "default.site.configuration.xml". The main feature appears to be the ability to
 * set a home page for the web application. This class should be used to write tests that check that the configuration
 * file is found and its settings correctly affect what is returned when requesting just the context root of the web
 * application.</p>
 *
 * TODO: Write unit tests to check that the site configuration works
 * <p>Things to check for:
 * <li>detecting different file names</li>
 * <li>correctly loading default page</li>
 */
public class SiteConfigurationTest extends AbstractJettyTest
{
    @Test
    public void testSiteConfigurations()
    {

    }
}
