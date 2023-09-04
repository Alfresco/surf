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

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Tag used at the end of the body section of a page to indicate the end
 * of a page that potentially contains editable Alfresco content.
 * 
 * @author Gavin Cornwell
 */
public class EndTemplateTag extends AbstractTemplateTag
{
    private static final long serialVersionUID = -2917015141188997203L;
    
    protected static final Log logger = LogFactory.getLog(EndTemplateTag.class);

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        if (isEditingEnabled())
        {
            try
            {
                Writer out = pageContext.getOut();

                // get the toolbar location from the request session
                String toolbarLocation = getToolbarLocation();

                // render config required for ribbon and marked content
                out.write("<script type=\"text/javascript\">\n");
                out.write("WEF.ConfigRegistry.registerConfig('org.springframework.extensions.webeditor.ui.ribbon',\n");
                out.write("{ position: \"");
                out.write(toolbarLocation);
                out.write("\" });\n");
                
                // add in custom configuration
                includeCustomConfiguration(out);
                
                // close render config               
                out.write("\n</script>");

                // request all the resources
                out.write("<script type=\"text/javascript\" src=\"");
                out.write(getWebEditorUrlPrefix());
                out.write("/service/wef/resources\"></script>\n");
                
                if (logger.isDebugEnabled())
                    logger.debug("Completed endTemplate rendering");
            }
            catch (IOException ioe)
            {
                throw new JspException(ioe.toString());
            }
        }
        else if (logger.isDebugEnabled())
        {
            logger.debug("Skipping endTemplate rendering as editing is disabled");
        }

        return SKIP_BODY;
    }
    
    /**
     * Extension point for allowing inheriting classes to inject custom
     * configuration script
     * 
     * @param out writer
     */
    public void includeCustomConfiguration(Writer out)
        throws IOException
    {
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release()
    {
        super.release();
    }
}
