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

package org.springframework.extensions.surf.test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.springframework.mock.web.MockServletContext;

/**
 * Servlet Context for Surf Testing
 * 
 * Eliminates JAR file lookups into /WEB-INF/lib by Freemarker
 * 
 * @author muzquiano
 */
public class SurfServletContext extends MockServletContext
{
	protected File rootFolder;
	
	public SurfServletContext(File rootFolder)
	{
		this.rootFolder = rootFolder;
	}
	
	/**
	 * This prevents Freemarker Taglibs from working (during tests)
	 */
	public Set<String> getResourcePaths(String path) 
	{
		if ("/WEB-INF/lib".equals(path))
		{
			return new HashSet<String>();
		}
		
		return super.getResourcePaths(path);
	}
	
	/**
	 * This allows local file system stores to resolve paths to maven test directories
	 */
	public String getRealPath(String path) 
	{
		if (path != null && path.startsWith("/"))
		{
			return this.rootFolder.getPath() + path.substring(1);
		}
		
		return super.getRealPath(path);
	}
	
}
