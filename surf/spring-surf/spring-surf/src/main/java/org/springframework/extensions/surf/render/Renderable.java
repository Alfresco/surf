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

package org.springframework.extensions.surf.render;

import java.util.Map;

/**
 * Implemented by object types which wish to expose a set of renderer
 * configurations.  Each renderer configuration identifies a processor
 * id and provides the processor with information about how to render.
 * <p>
 * A renderable object is one that has renderer processors
 * defined on it for one or more render modes. 
 * 
 * @author muzquiano
 */
public interface Renderable 
{
    /*
    JSP EXAMPLE:

    <processor mode="view">
       <id>jsp</id>
       <url>/abc/view.jsp</url>
    </processor>    
    <processor mode="edit">
       <id>jsp</id>
       <url>/abc/edit.jsp</url>
    </processor>
    
    
    WEBSCRIPT:

    <processor mode="view">
       <id>webscript</id>
    </processor>
    <processor mode="edit">
       <id>webscript</id>
       <uri>${mode.view.uri}/edit</uri>
    </processor>
     */
    
    /**
     * The list of defined render modes
     * 
     * @return an array of render modes
     */
    public RenderMode[] getRenderModes();

    /**
     * Gets the default 'view' processor id
     * 
     * @return the processor id
     */
    public String getProcessorId();
    
    /**
     * Gets the processor id
     * 
     * @param renderMode RenderMode
     * 
     * @return the processor id
     */
    public String getProcessorId(RenderMode renderMode);

    /**
     * Gets a default 'view' processor property
     * 
     * @param propertyName String
     * 
     * @return the processor property value
     */
    public String getProcessorProperty(String propertyName);
    
    /**
     * Gets a processor property
     * 
     * @param renderMode RenderMode
     * @param propertyName String
     * 
     * @return the processor property value
     */
    public String getProcessorProperty(RenderMode renderMode, String propertyName);
    
    /**
     * Gets a map of default 'view' processor properties
     *  
     * @return the map
     */
    public Map<String, String> getProcessorProperties();

    /**
     * Gets a map of processor properties for the given mode
     *  
     * @param renderMode the render mode
     * 
     * @return the map
     */    
    public Map<String, String> getProcessorProperties(RenderMode renderMode);  
}