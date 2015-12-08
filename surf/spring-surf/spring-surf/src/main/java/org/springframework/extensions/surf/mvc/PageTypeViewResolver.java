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
package org.springframework.extensions.surf.mvc;

import java.util.Locale;

import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.WebFrameworkConstants;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.surf.types.Page;
import org.springframework.extensions.surf.types.PageType;
import org.springframework.extensions.surf.types.Theme;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * Resolver for web framework Page Type views
 *
 * @author muzquiano
 * @author Dave Draper
 */
public class PageTypeViewResolver extends AbstractWebFrameworkViewResolver
{
    /**
     * The prefix "type" was included in this view resolver originally and has
     * been left in as a legitimate prefix for backwards compatibility. However,
     * it is not the documented prefix for resolving page types.
     */
    private static final String PAGE_TYPE_PREFIX_OLD = "type/";

    /**
     * The prefix "pt" is documented in Professional Alfresco for resolving page
     * types and has been added to support the documentation.
     */
    private static final String PAGE_TYPE_PREFIX_DOCUMENTED = "pt/";

    /**
     * The request parameter "pt" is documented in Professional Alfresco for
     * resolving page types so needs to be supported as a valid prefix.
     */
    private static final String _PAGE_TYPE_REQUEST_PARAMETERS = "pt";
    
    public PageTypeViewResolver()
    {
        // Populate the prefixes List with the 2 prefixes that we need to
        // initially support. This code block
        // can be removed if we decide to allow prefixes to be specified via
        // Spring property injection.
        addPrefix(PAGE_TYPE_PREFIX_OLD);
        addPrefix(PAGE_TYPE_PREFIX_DOCUMENTED);

        // Populate the request parameters list with the single request
        // parameter supported.
        addReqParm(_PAGE_TYPE_REQUEST_PARAMETERS);
    }

    /**
     * Determines whether or not this view resolver can be used for the supplied
     * view name. This will return true if the view name is prefixed by any of
     * the prefixes contained in the <code>
     * prefixes</code> property.
     *
     * @param viewName The name of the view to check for page type requests
     * @param locale The locale of the request.
     * @return Returns true if the view name is prefixed by any of the prefixes
     *         contained in the <code>
     * prefixes</code> property.
     */
    protected boolean canHandle(String viewName, Locale locale)
    {

        boolean canHandle = false;

        // Check the requested view name to see whether it begins with any of
        // the legitimate prefixes that can
        // be used to identify a page type request...
        String pageTypeId = processView(viewName);

        // A page type id was retrieved from either the URL or request
        // parameters, check to see if a page type
        // with the specified id actually exists...
        if (pageTypeId != null)
        {
            PageType pageType = getModelObjectService().getPageType(pageTypeId);
            canHandle = (pageType != null);
        }

        return canHandle;
    }

    /**
     * <p>Overrides the default implementation from <code>AbstractCachingViewResolver</code> to ensure that page type view are cached
     * with respect to the requested view. Default caching only takes the basic URL suffix into consideration and does not look at any
     * request parameters that have been provided. This means that if a user attempts to request a page type with a different theme from
     * the cached view then they would not see the page type they've requested. By setting a cache key that includes the requested theme
     * as well as the view name it is possible to switch between themes in a single session.</p>
     */
    @Override
    protected Object getCacheKey(String viewName, Locale locale)
    {
        RequestContext context = ThreadLocalRequestContext.getRequestContext();
        String requestedTheme = context.getParameter("theme");
        StringBuilder cacheKey = new StringBuilder(viewName);
        cacheKey.append("_");
        if (requestedTheme == null)
        {
            // If no theme has been requested then no action is required.
        }
        else
        {
            // If a theme has been requested then use it as part of the cache key.
            cacheKey.append(requestedTheme);
            cacheKey.append("_");
        }

        // Append the locale...
        cacheKey.append(locale);

        return cacheKey.toString();
    }

    /**
     * Constructs the view for a page type request. The view is based on the
     * requested theme, if no specific theme has been requested then the default
     * theme is used. The theme configuration should specify the page id to use
     * for the requested page type.
     *
     * @param viewName The name of the view to build.
     * @return An <code>AbstractUrlBasedView</code>
     */
    protected AbstractUrlBasedView buildView(String viewName) throws Exception
    {
        PageView view = null;

        // request context
        RequestContext context = ThreadLocalRequestContext.getRequestContext();

        String pageTypeId = processView(viewName);
        if (pageTypeId != null)
        {
            // determine which page to use based on requested type
            String pageId = null;

            // theme binding
            String themeId = (String) context.getThemeId();
            if (themeId != null)
            {
                Theme theme = getModelObjectService().getTheme(themeId);
                if (theme != null)
                {
                    pageId = theme.getPageId(pageTypeId);
                }
            }

            // system default page
            if (pageId == null)
            {
                pageId = getWebframeworkConfigElement().getDefaultPageTypeInstanceId(pageTypeId);
            }

            // use a generic page
            if (pageId == null)
            {
                pageId = getWebframeworkConfigElement().getDefaultPageTypeInstanceId(WebFrameworkConstants.GENERIC_PAGE_TYPE_DEFAULT_PAGE_ID);
            }

            // build a page view
            Page page = lookupPage(pageId);
            if (page != null)
            {
                ThreadLocalRequestContext.getRequestContext().setPage(page);
                view = new PageView(getWebframeworkConfigElement(), 
                                    getModelObjectService(), 
                                    getWebFrameworkResourceService(), 
                                    getWebFrameworkRenderService(),
                                    getTemplatesContainer());
                view.setUrl(pageId);
                view.setUrlHelperFactory(getUrlHelperFactory()); // It doesn't matter if this is null, the result will be the DefaultURLHelper gets created
                view.setPage(page);
            }
        }

        return view;
    }
}
