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

import org.springframework.extensions.surf.ModelObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.render.RenderService;

/**
 * <p>This tag will render the URL to the page or page type specified by the attributes provided. It DOES NOT
 * render an actual link (i.e. it does not render an HTML anchor tag) only the URL that represents a link
 * (relative to the server, i.e. it includes the context root of the application.</p>
 *
 * @author muzquiano
 * @author David Draper
 */
public class ObjectLinkTag extends AbstractObjectTag
{
    private static final long serialVersionUID = 6477844193223944439L;

    @Override
    protected int invokeRenderService(RenderService renderService, 
                                            RequestContext requestContext,
                                            ModelObject object) throws Exception
    {
        String link = renderService.generateLink(getPageType(), getPage(), getObject(), getFormat());
        pageContext.getOut().write(link);
        return SKIP_BODY;
    }
}
