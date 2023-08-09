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

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.Tag;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * <p>This class provides the "presetToken" custom JSP tag that can only be used as a child tag of the
 * "constructPreset" custom JSP tag. Multiple instances the tag can be used to define token map entries
 * to apply when constructing a Spring Surf preset. It should be used as follows:</p>
 * <pre>
 * <{@code}constructPreset preset="">
 *     <{@code}presetToken key="someKey" value="someValue"><{@code}/presetToken>
 *     <{@code}presetToken key="anotherKey" value="anotherValue"><{@code}/presetToken>
 * <{@code}/constructPreset>
 * </pre>
 *
 * @author David Draper
 */
public class PresetToken extends TagSupport
{
    private static final long serialVersionUID = 2223221597076988546L;

   /**
    * <p>This should be a substitution point in the preset being constructed. For example, if the the
    * preset contains a substitution point <code>${pageid}</code> and the <code>key</code> is set to
    * <code>pageid</code> then that substitution point (and all other with the same name) will be replaced
    * with whatever is set as the <code>value</code> attribute.</p>
    */
    private String key;

    /**
     * <p>This is required to set the <code>key</code> attribute when the custom tag is used.</p>
     * @param key String
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * <p>This should be set to the value used to replace all substitution points with the value set in the
     * <code>key</code> attribute.<p>
     */
    private String value;

    /**
     * <p>This is required to set the <code>value</code> attribute when the custom tag is used.</p>
     * @param value String
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * <p>Checks that the tag has been used as a direct child of a <code>ConstructPreset</code> tag
     * and calls its <code>addToken</code> method with the <code>key</code> and <code>value</code>
     * attributes that have been provided.</p>
     *
     * @throws JspException If the parent of the tag is not a <code>ConstructPreset</code> tag.
     */
    @Override
    public int doStartTag() throws JspException
    {
        Tag parent = getParent();
        if (parent != null && parent instanceof ConstructPreset)
        {
            ConstructPreset preset = (ConstructPreset) parent;
            preset.addToken(key, value);
        }
        else
        {
            throw new JspException("PresetToken tag must be child of ConstructPreset tag");
        }

        return super.doStartTag();
    }
}
