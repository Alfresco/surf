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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;

/**
 * Describes a Form that can be bound for the currently rendering
 * component.
 * 
 * @author muzquiano
 */
public final class ScriptForm extends ScriptBase
{
    final private Map<String, FormBinding> bindings;
    final private RequestContext renderContext;
    private ModelObject object;
        
    /**
     * Instantiates a new script form.
     * 
     * @param context the request context
     */
    public ScriptForm(RequestContext context, ModelObject object)
    {
        super(context);
        
        this.renderContext = context; 
        this.object = object;
        this.bindings = new HashMap<String, FormBinding>(16, 1.0f);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.ScriptBase#buildProperties()
     */
    protected ScriptableMap buildProperties()
    {
        return null;
    }
    
    
    // --------------------------------------------------------------
    // JavaScript Properties
    
    /**
     * Binds an element to this form.
     * 
     * @param title the title
     * @param value the value
     */
    public void bind(String title, Object value)
    {
        bind(title, value, null);
    }

    /**
     * Binds an element to this form.
     * 
     * @param id the id
     * @param value the value
     * @param nullValue the null value
     * 
     * @return the object
     */
    public FormBinding bind(String id, Object value, Object nullValue)
    {
        if (value == null)
        {
            value = nullValue;
        }
        
        FormBinding binding = new FormBinding(id, value);
        bindings.put(id, binding);
        
        return binding;
    }
    
    /**
     * Gets an element form binding.
     * 
     * @param id the id
     * 
     * @return the binding
     */
    public FormBinding getBinding(String id)
    {
        return (FormBinding) bindings.get(id);
    }
    
    /**
     * Gets the bindings.
     * 
     * @return the bindings
     */
    public Object[] getBindings()
    {
        Object[] array = new Object[bindings.size()];
        
        int i = 0;
        Iterator it = bindings.keySet().iterator();
        while (it.hasNext())
        {
            String key = (String) it.next();
            FormBinding binding = (FormBinding) bindings.get(key);
            array[i] = binding;
            i++;
        }

        return array;
    }
    
    /**
     * Gets the ids of all element form bindings.
     * 
     * @return the binding ids
     */
    public String[] getBindingIds()
    {
        return bindings.keySet().toArray(new String[bindings.keySet().size()]);
    }    

    public class FormBinding implements Serializable
    {
        private String id;       
        private Object value;
        
        /**
         * Instantiates a new form binding.
         * 
         * @param id the id
         */
        public FormBinding(String id)
        {
            this.id = id;
        }
        
        /**
         * Instantiates a new form binding.
         * 
         * @param id the id
         * @param value the value
         */
        public FormBinding(String id, Object value)
        {
            this(id);

            this.value = value;            
        }
        
        /**
         * Sets the value.
         * 
         * @param value the new value
         */
        public void setValue(Object value)
        {
            this.value = value;
        }
        
        public Object getValue()
        {
            return this.value;
        }
        
        public String getId()
        {
            return ScriptForm.prefix(renderContext, object, id);
        }        
    }
    
    protected static String getPrefix(RequestContext context, ModelObject object)
    {
        return "form_" + object.getId() + "___";
    }
    
    protected static String prefix(RequestContext context, ModelObject object, String id)
    {
        return getPrefix(context, object) + id;
    }
    
    protected static String unprefix(String prefixedId)
    {
        int x = prefixedId.indexOf("___");
        if (x > -1)
        {
            return prefixedId.substring(x+3);
        }
        return null;
    }    
}
