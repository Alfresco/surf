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
package org.springframework.extensions.surf.persister;

import org.springframework.extensions.config.WebFrameworkConfigElement;
import org.springframework.extensions.config.WebFrameworkConfigElement.PersisterConfigDescriptor;
import org.springframework.extensions.surf.ModelObjectPersister;
import org.springframework.extensions.surf.persister.CachedPersister;
import org.springframework.extensions.surf.persister.PersisterService;


public class CustomPersisterService extends PersisterService
{
    private ModelObjectPersister persister;

    public void setPersister(ModelObjectPersister persister)
    {
        this.persister = persister;
    }

    private WebFrameworkConfigElement webFrameworkConfig;

    public void setWebFrameworkConfig(WebFrameworkConfigElement webFrameworkConfig)
    {
        this.webFrameworkConfig = webFrameworkConfig;
    }

    /**
     * <p>This overrides the default implementation to just return the <code>ModelObjectPersister</code>
     * configured via the Spring application context.</p>
     */
    public ModelObjectPersister getPersisterById(String persisterId)
    {
        return this.persister;
    }

    /**
     * <p>This overrides the default implementation to just return the <code>ModelObjectPersister</code>
     * configured via the Spring application context.</p>
     */
    public ModelObjectPersister getPersisterByTypeId(String objectTypeId)
    {
        return this.persister;
    }

    public void initPersisters()
    {
        PersisterConfigDescriptor config =  this.webFrameworkConfig.getPersisterConfigDescriptor();
        if (persister instanceof CachedPersister)
        {
            if (config != null)
            {
                // global cache settings
                boolean cache = config.getCacheEnabled();
                int cacheCheckDelay = config.getCacheCheckDelay();

                // set onto persister
                ((CachedPersister)persister).setCache(cache);
                ((CachedPersister)persister).setCacheCheckDelay(cacheCheckDelay);
            }
        }

        // init the persister
        persister.init(null);
    }
}
