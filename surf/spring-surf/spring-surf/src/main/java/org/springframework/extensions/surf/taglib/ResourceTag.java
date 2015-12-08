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

package org.springframework.extensions.surf.taglib;

import java.io.IOException;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.RequestDispatchException;
import org.springframework.extensions.surf.render.RenderService;

/**
 * <p>Outputs the requested url representation of a resource. Named Resource Examples:</p>
 * <ul>
 * <li><{@code}alf:resource name="resourceName" /></li>
 * <li><{@code}alf:resource id="<{@code}protocol>://<{@code}endpoint>/<{@code}object id>" /></li>
 * <li><{@code}alf:resource protocol="<{@code}protocol>" endpoint="<{@code}endpoint>" object="<{@code}object id>" /></li>
 * <li><{@code}alf:resource name="resourceName" payload="metadata" /></li>
 * <li><{@code}alf:resource name="resourceName" payload="content" /></li>
 * </ul>
 * 
 * @author David Draper
 * @author muzquiano
 */
public class ResourceTag extends RenderServiceTag
{
    private static final long serialVersionUID = -8143039236653767731L;

    private String name = null;    
    private String id = null;    
    private String protocol = null;
    private String endpoint = null;
    private String object = null;    
    private String payload = null;
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return this.id;
    }
    
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }
    
    public String getProtocol()
    {
        return this.protocol;
    }
    
    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }
    
    public String getEndpoint()
    {
        return this.endpoint;
    }
    
    public void setObject(String object)
    {
        this.object = object;
    }
    
    public String getObject()
    {
        return this.object;
    }

    public void setPayload(String payload)
    {
        this.payload = payload;
    }
    
    public String getPayload()
    {
        return this.payload;
    }
    
    /**
     * <p>The life-cycle of a custom JSP tag is that the class is is instantiated when it is first required and
     * then re-used for all subsequent invocations. When a JSP has non-mandatory properties it means that the 
     * setters for those properties will not be called if the properties are not provided and the old values
     * will still be available which can corrupt the behaviour of the code. In order to prevent this from happening
     * we should override the <code>release</code> method to ensure that all instance variables are reset to their
     * initial state.</p>
     */
    @Override
    public void release()
    {
        super.release();
        this.endpoint = null;
        this.id = null;
        this.name = null;
        this.object = null;
        this.payload = null;
        this.protocol = null;
    }

    @Override
    protected int invokeRenderService(RenderService renderService, RequestContext renderContext, ModelObject modelObject)
            throws RequestDispatchException
    {
        String url = renderService.generateResourceURL(renderContext, modelObject, name, id, protocol, endpoint, object, payload);
        try
        {
            if (url != null)
            {
                pageContext.getOut().write(url);
            }
            else
            {
                // TODO: Output error message.
            }
                
        }
        catch (IOException e)
        {
            // TODO: Handle this gracefully!
        }
        return SKIP_BODY;
    }
}
