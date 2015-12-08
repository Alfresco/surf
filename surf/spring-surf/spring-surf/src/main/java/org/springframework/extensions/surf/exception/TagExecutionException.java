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

package org.springframework.extensions.surf.exception;

/**
 * Class that describes an exception which has occurred while processing
 * tags in a tag executor.
 * 
 * @author muzquiano
 */
public class TagExecutionException extends Exception
{
    
    /**
     * Instantiates a new tag execution exception.
     * 
     * @param message the message
     */
    public TagExecutionException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new tag execution exception.
     * 
     * @param message the message
     * @param ex the ex
     */
    public TagExecutionException(String message, Exception ex)
    {
        super(message, ex);
    }
}
