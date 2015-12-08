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

import static org.springframework.extensions.surf.test.basic.Constants.*;

/**
 * <p>This suite tests the custom FreeMarker directives. Many of the tests are very similar to those in 
 * the <code>TagLibTest</code> class... this is because the Directives originally made use of the JSP 
 * TagLib as a means to share code. There are less tests though because some of the directives are heavily
 * used in other test suites or because the tag hasn't been "converted" to a directive. Output between
 * JSP tag and FreeMarker directive should be consistent though.</p>
 *  
 * @author David Draper
 */
public class DirectiveTest extends AbstractJettyTest
{
    /**
     * <p>This test checks that the <{@code}link> directive renders correctly. This was previously only available as a FreeMarker directive
     * but has not been added to the TLD so that it can be used in JSPs. The purpose of the directive is to use as a workaround for an
     * IE bug. It should successfully render all CSS imports into a single <{@code}style> tag.</p>
     */
    @Test
    public void testLinkDirective()
    {
        checkResponseTextOrdering("directive_link_test-page",
                                  _HTTP_GET_METHOD,
                                  "<style type=\"text/css\" media=\"screen\">",
                                  "@import \"/css/test_css_1.css\";",
                                  "@import \"/css/test_css_2.css\";",
                                  "</style>");
    }

    /**
     * <p>This test checks that the <{@code}pagelink> directive renders correctly. The directive should only render the a path and not a complete
     * anchor (that's what the <{@code}anchor> directive does). 8 paths will be rendered in all:
     * <ul>
     * <li>page</li>
     * <li>page type</li>
     * <li>page with format request parameter</li>
     * <li>page type with format request parameter</li>
     * <li>page with object request parameter</li>
     * <li>page type with object request parameter</li>
     * <li>page with format and object request parameters</li>
     * <li>page type with format and object request parameters</li>
     * </ul>
     * </p>
     */
    @Test
    public void testPageLinkDirective()
    {
        checkResponseTextOrdering("directive_pagelink_test-page",
                                  _HTTP_GET_METHOD,
                                  "/basic_template-page",
                                  "/pt/basic-pageType",
                                  "/basic_template-page?f=dummyFormat",
                                  "/pt/basic-pageType?f=dummyFormat",
                                  "/basic_template-page?o=dummyObject",
                                  "/pt/basic-pageType?o=dummyObject",
                                  "/basic_template-page?f=dummyFormat&o=dummyObject",
                                  "/pt/basic-pageType?f=dummyFormat&o=dummyObject");
    }

    /**
     * <p> This test checks that the <{@code}anchor> directive renders correctly. The directive should render an HTML anchor tag to provide the link
     * defined by the arguments provided. It relies on the directive being given a body to actually render something visible. All permutations of
     * the various arguments are used to construct a total of 16 different links.</p>
     */
    @Test
    public void testAnchorDirective()
    {
        checkResponseTextOrdering("directive_anchor_test-page",
                                  _HTTP_GET_METHOD,
                                  "<A href=\"/basic_template-page\">PageLink</A>",
                                  "<A href=\"/basic_template-page\" target=\"dummyTarget\">PageLinkWithTarget</A>",
                                  "<A href=\"/basic_template-page?o=dummyObject\">PageLinkWithObject</A>",
                                  "<A href=\"/basic_template-page?f=dummyFormat\">PageLinkWithFormat</A>",
                                  "<A href=\"/basic_template-page?o=dummyObject\" target=\"dummyTarget\">PageLinkWithTargetAndObject</A>",
                                  "<A href=\"/basic_template-page?f=dummyFormat\" target=\"dummyTarget\">PageLinkWithTargetAndFormat</A>",
                                  "<A href=\"/basic_template-page?f=dummyFormat&o=dummyObject\">PageLinkWithObjectAndFormat</A>",
                                  "<A href=\"/basic_template-page?f=dummyFormat&o=dummyObject\" target=\"dummyTarget\">PageLinkWithTargetAndObjectAndFormat</A>",
                                  "<A href=\"/basic-pageType\">PageTypeLink</A>",
                                  "<A href=\"/basic-pageType\" target=\"dummyTarget\">PageTypeLinkWithTarget</A>",
                                  "<A href=\"/basic-pageType?o=dummyObject\">PageTypeLinkWithObject</A>",
                                  "<A href=\"/basic-pageType?f=dummyFormat\">PageTypeLinkWithFormat</A>",
                                  "<A href=\"/basic-pageType?o=dummyObject\" target=\"dummyTarget\">PageTypeLinkWithTargetAndObject</A>",
                                  "<A href=\"/basic-pageType?f=dummyFormat\" target=\"dummyTarget\">PageTypeLinkWithTargetAndFormat</A>",
                                  "<A href=\"/basic-pageType?f=dummyFormat&o=dummyObject\">PageTypeLinkWithObjectAndFormat</A>",
                                  "<A href=\"/basic-pageType?f=dummyFormat&o=dummyObject\" target=\"dummyTarget\">PageTypeLinkWithTargetAndObjectAndFormat</A>");
    }

    /**
     *
     */
    @Test
    public void testResourceDirective()
    {
        // TODO: Test this directive
    }
}
