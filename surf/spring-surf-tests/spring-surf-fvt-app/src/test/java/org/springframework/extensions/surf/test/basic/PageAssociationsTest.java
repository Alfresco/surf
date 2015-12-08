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
 * <p>It's possible to create parent/child relationship associations between pages for the purposes of navigation.
 * These associations don't do anything by themselves but are available in WebScript JavaScript code to be used to
 * create navigation artifacts on the screen. This means that providing that the component you use for navigation
 * is rendered by a WebScript (i.e. it's not just part of a template) and is coded to make use of this data then
 * you can automatically add to it through configuration.</p>
 *
 * <p>This class should be used to check that associations are correctly populated and made available to WebScripts</p>
 *
 * TODO: Write tests for checking associations.
 *
 * @author David Draper
 */
public class PageAssociationsTest extends AbstractJettyTest
{
    @Test
    public void testPageAssociations()
    {

    }
}
