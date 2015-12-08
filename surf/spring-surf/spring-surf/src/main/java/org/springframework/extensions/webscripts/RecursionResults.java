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
package org.springframework.extensions.webscripts;

/**
 * This class was created for use in the {@link ScriptWidgetUtils} methods for storing
 * data that would otherwise be lost through recursion. Primarily it keeps track of 
 * whether or not the operation is a success or not but new attributes can be added as 
 * necessary over time to capture more data.  
 * 
 * @author Dave Draper
 */
public class RecursionResults
{
    /**
     * Used to keep track whether or not the operation was a success.
     */
    private boolean success = false;
    
    /**
     * Setter to indicate whether or not the result was a success or not.
     * @param success boolean
     */
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    /**
     * Returns a boolean value indicating whether or not the operation was successful.
     * @return boolean
     */
    public boolean isSuccess()
    {
        return success;
    }
}
