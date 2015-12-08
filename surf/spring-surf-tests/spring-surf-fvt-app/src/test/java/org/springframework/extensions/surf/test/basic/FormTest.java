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

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.springframework.extensions.surf.test.AbstractJettyTest;
import org.testng.annotations.Test;

/**
 * 
 * @author David Draper
 */
public class FormTest extends AbstractJettyTest
{
    /**
     * <p>This test checks whether or not multipart form submission works.</p>
     * @throws IOException 
     */
    @Test
    public void testMultipartPost() throws IOException
    {
        HashMap<String, String> stringKeyToValueMap = new HashMap<String, String>();
        stringKeyToValueMap.put("name", "testName");
        stringKeyToValueMap.put("title", "testTitle");
        
        HashMap<String, String> fileKeyToLocationMap = new HashMap<String, String>();
        fileKeyToLocationMap.put("file", "src/main/webapp/images/surf32.jpg");
        
        String response = getFilePostResponse("service/testformdatamultipartprocessing2", 
                                              stringKeyToValueMap,
                                              fileKeyToLocationMap);
        
        Assert.assertEquals("SuccessisMultiPart = truearg.name = testNamename = testNametitle = testTitlefilename = surf32.jpgnumber of form fields = 3", response);
    }
}
