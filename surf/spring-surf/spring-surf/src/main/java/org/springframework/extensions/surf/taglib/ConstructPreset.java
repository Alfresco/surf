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

import java.util.HashMap;

import javax.servlet.jsp.JspException;

import org.springframework.extensions.surf.PresetsManager;
import org.springframework.extensions.surf.exception.ModelObjectPersisterException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * <p>This class provides the "constructPreset" custom JSP tag that can be used instantiate a Spring Surf
 * preset. It should be used (optionally) with the "presetToken" custom tag as follows:</p>
 * <pre>
 * <{@code}constructPreset preset="">
 *     <{@code}presetToken key="someKey" value="someValue"><{@code}/presetToken>
 *     <{@code}presetToken key="anotherKey" value="anotherValue"><{@code}/presetToken>
 * <{@code}/constructPreset>
 * </pre>
 *
 * @author David Draper
 */
public class ConstructPreset extends RequestContextAwareTag
{
    private static final long serialVersionUID = -8480734697163462239L;

    /**
     * <p>A <code>PresetsManager</code> is in order to actually instantiate presets. This is retrieved from the
     * Spring application context the first time the JSP is used and all subsequent requests use the same instance.</p>
     */
    private PresetsManager presetManager;

    /**
     * <p>This will be set to the id of the preset to construct. It is specified as a required field in the
     * surf.tld file.</p>
     */
    private String preset;

    public void setPreset(String preset)
    {
        this.preset = preset;
    }

    /**
     * <p>This map is used to store all the tokens to apply to the preset. It is initialised when the tag is instantiated
     * and its <code>clear</code> method is called by the <code>release</code> method when the request has completed.
     * Tokens are added to the map by calling the <code>addToken</code> method. This is only intended to be called by
     * child <code>PresetToken</code> tags.</p>
     */
    private HashMap<String, String> tokens = new HashMap<String, String>();

    /**
     * <p>Adds a new token to the <code>tokens</code> map. This method is intended to only be called by child <code>PresetToken</code>
     * tags. This is because it is not possible for a custom tag to have map attributes so they have to be provided through child
     * tags.</p>
     *
     * @param key The key of the token to add. This should be a substitution value in the preset being constructed.
     * @param value The value of the token to add.
     */
    public void addToken(String key, String value)
    {
        tokens.put(key, value);
    }

    /**
     * <p>Extends the super class implementation to ensure that the preset id and token map are reset after each request is processed.</p>
     */
    @Override
    public void release()
    {
        super.release();
        this.preset = null;
        this.tokens.clear();
    }

    /**
     * <p>Retrieves the <code>PresetsManager</code> from the application context the first time the tag is used and at all other
     * times just returns the <code>EVAL_BODY_INCLUDE</code> constant to process any child tags. This is currently hard-coded
     * to retrieve the default Spring Surf <code>PresetsManager</code> bean with an id of "webframework.presets.manager".</p>
     */
    @Override
    protected int doStartTagInternal() throws Exception
    {
        if (this.presetManager != null)
        {
            // No action required. We already have the PresetManager.
        }
        else
        {
            WebApplicationContext applicationContext = getRequestContext().getWebApplicationContext();
            this.presetManager = (PresetsManager) applicationContext.getBean("webframework.presets.manager");

        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * <p>This calls the <code>PresetsManager</code> to actually construct the preset. The <code>PresetsManager</code> will have been
     * retrieved in the <code>doStartTagInternal</code> method the first time the tag was executed and the token map applied will have
     * been populated by executing any child <code>PresetToken</code> tags.
     */
    @Override
    public int doAfterBody() throws JspException
    {        
        this.presetManager.constructPreset(this.preset, this.tokens);
        return EVAL_PAGE;
    }
}
