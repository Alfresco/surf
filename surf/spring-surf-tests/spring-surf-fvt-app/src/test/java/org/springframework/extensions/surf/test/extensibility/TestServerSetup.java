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
package org.springframework.extensions.surf.test.extensibility;

import org.springframework.extensions.surf.test.AbstractTestServerSetup;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

/**
 * <p>Required to exist at the test package level.</p>
 * @author David Draper
 *
 */
public class TestServerSetup extends AbstractTestServerSetup
{
    @Parameters({"configFolder"})
    @BeforeSuite
    public static void setupJettyServer(String configFolder) throws Exception
    {
        AbstractTestServerSetup.setupJettyServer(configFolder);
    }
    
    @AfterSuite
    public static void tearDownJettyServer() throws Exception
    {
        AbstractTestServerSetup.tearDownJettyServer();
    }

}
