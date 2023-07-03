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

package org.springframework.extensions.webeditor.taglib;

import java.io.IOException;
import java.io.Writer;

import jakarta.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Tag used in the head section of a page to indicate that the page potentially
 * contains in-context editable content.
 * 
 * @author gavinc
 * @author muzquiano
 */
public class StartTemplateTag extends AbstractTemplateTag
{
    private static final long serialVersionUID = -7242916874303242800L;
    
    protected static final Log logger = LogFactory.getLog(StartTemplateTag.class);
    
    private String toolbarLocation = TemplateConstants.TOOLBAR_LOCATION_TOP;

    /**
     * Returns the current value for the toolbar location
     * 
     * @return Toolbar location
     */
    public String getToolbarLocation()
    {
        return this.toolbarLocation;
    }

    /**
     * Sets the toolbar location
     * 
     * @param location Toolbar location
     */
    public void setToolbarLocation(String location)
    {
        if (location.equalsIgnoreCase(TemplateConstants.TOOLBAR_LOCATION_TOP))
        {
            this.toolbarLocation = TemplateConstants.TOOLBAR_LOCATION_TOP;
        }
        else if (location.equalsIgnoreCase(TemplateConstants.TOOLBAR_LOCATION_LEFT))
        {
            this.toolbarLocation = TemplateConstants.TOOLBAR_LOCATION_LEFT;
        }
        else if (location.equalsIgnoreCase(TemplateConstants.TOOLBAR_LOCATION_RIGHT))
        {
            this.toolbarLocation = TemplateConstants.TOOLBAR_LOCATION_RIGHT;
        }
    }
    
    /**
     * @see jakarta.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        if (isEditingEnabled())
        {
            // store the toolbar location into the request session
            this.pageContext.getRequest().setAttribute(TemplateConstants.REQUEST_ATTR_KEY_TOOLBAR_LOCATION, getToolbarLocation());

            try
            {
                Writer out = pageContext.getOut();

                // bootstrap WEF
                out.write("<script type=\"text/javascript\" src=\"");
                out.write(getWebEditorUrlPrefix());
                out.write("/service/wef/bootstrap");
                if (isDebugEnabled())
                {
                    out.write("?debug=true");
                }
                
                // add in custom configuration
                includeCustomConfiguration(out);

				// end of bootstrap                
                out.write("\"></script>\n");
                
                if (logger.isDebugEnabled())
                    logger.debug("Completed startTemplate rendering");                
            }
            catch (IOException ioe)
            {
                throw new JspException(ioe.toString());
            }
        }
        else if (logger.isDebugEnabled())
        {
            logger.debug("Skipping startTemplate rendering as editing is disabled");
        }

        return SKIP_BODY;
    }

    /**
     * Allow tag extensions to insert custom javascript
     */
    public void includeCustomConfiguration(Writer out)
        throws IOException
    {
    }

    /**
     * @see jakarta.servlet.jsp.tagext.TagSupport#release()
     */
    public void release()
    {
        super.release();

        this.toolbarLocation = null;
    }
}
