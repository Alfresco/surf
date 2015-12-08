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

package org.springframework.extensions.surf.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.extensions.surf.ModelPersisterInfo;

/**
 * Default theme implementation
 *
 * @author muzquiano
 * @author David Draper
 * @author Kevin Roast
 */
public class ThemeImpl extends AbstractModelObject implements Theme
{
    private static final long serialVersionUID = -5592730666596308336L;
    
    /** Cache of page type to page Id theme overrides */
    private final Map<String, String> pageTypeCache = new ConcurrentHashMap<String, String>(); 
    
    /**
     * A {@link Map} of CSS tokens to the value that they should be substituted with in CSS source files.
     */
    private Map<String, String> cssTokens = new HashMap<String, String>();
    
    /**
     * @return A {@link Map} of CSS tokens to substitution values. These are used when processing
     * CSS source files so that a common CSS file can be modified per theme.
     */
    public Map<String, String> getCssTokens()
    {
        return this.cssTokens;
    }

    /**
     * Instantiates a new theme for a given XML document.
     *
     * @param document the document
     */
    public ThemeImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
        
        @SuppressWarnings("unchecked")
        List<Element> cssTokenMaps = getDocument().getRootElement().elements(Theme.CSS_TOKENS);
        if (cssTokenMaps != null)
        {
            for (Element cssTokenMapEl: cssTokenMaps)
            {
               @SuppressWarnings("unchecked")
               List<Element> cssTokenEls = cssTokenMapEl.elements();
               for (Element cssTokenEl: cssTokenEls)
               {
                   String subValue = cssTokenEl.getStringValue();
                   if (subValue == null)
                   {
                       subValue = "";
                   }
                   this.cssTokens.put(cssTokenEl.getName(), subValue.trim());
               }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.site.model.AbstractModelObject#getTypeName()
     */
    public String getTypeId()
    {
        return TYPE_ID;
    }

    /**
     * Constant for "page-types"
     */
    private static final String _PAGE_TYPES_ELEMENT = "page-types";

    /**
     * Constant for "page-type"
     */
    private static final String _PAGE_TYPE_ELEMENT = "page-type";

    /**
     * Constant for "id"
     */
    private static final String _ID_ELEMENT = "id";

    /**
     * Constant for "page-instance-id"
     */
    private static final String _PAGE_INSTANCE_ID_ELEMENT = "page-instance-id";

    /**
     * Constant for "page-id"
     */
    private static final String _PAGE_ID_ELEMENT = "page-id";

    /**
     * Sentinel value for a null value mapped page ID
     */
    private static final String SENTINEL_MAPPED_PAGE_ID = "<null>";

    /**
     * Checks the theme to find the id of the page that is mapped to the requested page type. A mapping is defined
     * in a theme configuration file by specifying a <page-type> element containing <id> and <page-id> (or <page-instance-id>)
     * elements.
     *
     * @param pageTypeId The id of the page type to look for
     * @return The id of the page mapped to the page in the theme. Returns null if a mapping is not defined.
     */
    @SuppressWarnings("unchecked")
    public String getPageId(String pageTypeId)
    {
        String mappedPageId = this.pageTypeCache.get(pageTypeId);
        if (mappedPageId == null)
        {
            Element pageTypesEl = getDocument().getRootElement().element(_PAGE_TYPES_ELEMENT);
            if (pageTypesEl != null)
            {
                List<Element> pageTypes = pageTypesEl.elements(_PAGE_TYPE_ELEMENT);
                for(int i = 0; i < pageTypes.size(); i++)
                {
                    Element pageType = (Element) pageTypes.get(i);
                    
                    String id = pageType.elementText(_ID_ELEMENT);
                    if (id != null && id.equals(pageTypeId))
                    {
                        // This was originally coded to return the text from the element "page-instance-id"
                        // but the Professional Alfresco (Online Appendix E) has documented that the way to
                        // define the mapping of PageType to Page in Theme configuration is by using the element
                        // "page-id". To support backwards compatibility this code block has been updated to
                        // check both "page-instance-id" and "page-id" and return whichever value is found first.
                        mappedPageId = pageType.elementText(_PAGE_INSTANCE_ID_ELEMENT);
                        if (mappedPageId == null)
                        {
                            mappedPageId = pageType.elementText(_PAGE_ID_ELEMENT);
                        }
                    }
                }
            }
            this.pageTypeCache.put(pageTypeId, mappedPageId != null ? mappedPageId : SENTINEL_MAPPED_PAGE_ID);
        }
        
        return mappedPageId != SENTINEL_MAPPED_PAGE_ID ? mappedPageId : null;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.framework.types.Theme#setDefaultPageId(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public void setDefaultPageId(String pageTypeId, String pageId)
    {
        // update the cache immediately
        this.pageTypeCache.put(pageTypeId, pageId);
        
        // update the underlying representation of the Model Object
        Document document = getDocument();
        Element pageTypesEl = document.getRootElement().element(_PAGE_TYPES_ELEMENT);
        if (pageTypesEl == null)
        {
            pageTypesEl = document.getRootElement().addElement(_PAGE_TYPES_ELEMENT);
        }
        
        Element theElement = null;
        
        List<Element> pageTypes = pageTypesEl.elements(_PAGE_TYPE_ELEMENT);
        for(int i = 0; i < pageTypes.size(); i++)
        {
            Element pageType = (Element) pageTypes.get(i);

            String id = pageType.elementText(_ID_ELEMENT);
            if (id != null && id.equals(pageTypeId))
            {
                theElement = pageType;
            }
        }
        
        if (theElement != null)
        {
            theElement = pageTypesEl.addElement(_PAGE_TYPE_ELEMENT);
        }
        
        // add the id property
        Element idElement = theElement.addElement(_ID_ELEMENT);
        idElement.setText(pageTypeId);
        
        // add the page instance id property
        Element pageInstanceIdElement = theElement.addElement(_PAGE_INSTANCE_ID_ELEMENT);
        pageInstanceIdElement.setText(pageId);
        
        updateXML(document);
    }
}
