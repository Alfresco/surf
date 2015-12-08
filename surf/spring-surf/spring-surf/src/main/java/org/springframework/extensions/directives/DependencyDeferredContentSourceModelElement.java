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
package org.springframework.extensions.directives;

import org.springframework.extensions.surf.extensibility.DeferredContentSourceModelElement;
import org.springframework.extensions.surf.extensibility.DeferredContentTargetModelElement;
import org.springframework.extensions.surf.extensibility.ExtensibilityDirective;
import org.springframework.extensions.surf.extensibility.impl.DefaultContentModelElement;

/**
 * <p>This {@link DeferredContentSourceModelElement} defines the common behaviour for adding CSS and JavaScript dependency requests
 * to the associated {@link DeferredContentTargetModelElement}.</p>
 * @author David Draper
 */
public class DependencyDeferredContentSourceModelElement extends DefaultContentModelElement implements DeferredContentSourceModelElement
{
    /**
     * <p>Indicates whether or not the element has been removed from the model that it was originally added to.</p>
     */
    private boolean removed = false;
    
    /**
     * <p>Updates the <code>removed</code> attribute to indicate that the element is no longer part of the model it 
     * was originally added to. By default there is no other cleanup action necessary.</p>
     */
    public void markAsRemoved()
    {
        this.removed = true;
    }
    
    /**
     * <p>Returns the value of the <code>removed</code> attribute.</p>
     * @return <code>true</code> if the element has been removed and <code>false</code> otherwise.
     */
    public boolean hasBeenRemoved()
    {
        return this.removed;
    }
    
    public DependencyDeferredContentSourceModelElement(String id,
                                                       String directiveName,
                                                       String dependency,
                                                       String group,
                                                       boolean aggregate,
                                                       DeferredContentTargetModelElement targetElement)
    {
        super(id, directiveName);
        this.group = group;
        this.targetElement = targetElement;
        this.dependency = dependency;
        this.aggregate = aggregate;
    }

    /**
     * <p>The dependency represented by this {@link DeferredContentSourceModelElement}</p>
     */
    private String dependency;
    
    /**
     * @return The dependency represented by this {@link DeferredContentSourceModelElement} 
     */
    public String getDependency()
    {
        return this.dependency;
    }

    /**
     * <p>The group to which this {@link DeferredContentSourceModelElement} belongs.</p>
     */
    private String group;
    
    /**
     * @return The name of the group that the dependency has been assigned to.
     */
    public String getGroup()
    {
        return group;
    }
    
    /**
     * Indicates where or not the dependency should be aggregated
     */
    private boolean aggregate = false;
    
    /**
     * @return Whether or not to aggregate the dependency 
     */
    public boolean isAggregate()
    {
        return aggregate;
    }

    /**
     * <p>The {@link DeferredContentTargetModelElement} instance that this {@link DeferredContentSourceModelElement} has
     * registered with.</p> 
     */
    private DeferredContentTargetModelElement targetElement;
    
    /**
     * <p>Returns the the {@link DeferredContentTargetModelElement} associated with this {@link DeferredContentSourceModelElement}</p>
     * 
     * @return DeferredContentTargetModelElement
     */
    public DeferredContentTargetModelElement getTargetElement()
    {
        return this.targetElement;
    }
    
    /**
     * <p>Update the associated {@link DeferredContentTargetModelElement} that edit mode has been entered. If the
     * edit action is either to remove or replace the element then the element should be marked as having been 
     * removed.</p>
     */
    public void enterEditMode(String mode)
    {
        // Mark the element as removed if it has been either removed or replaced - this will ensure that the
        // deferred content element doesn't process it...
        if (mode == ExtensibilityDirective.ACTION_REMOVE || mode == ExtensibilityDirective.ACTION_REPLACE)
        {
            this.markAsRemoved();
        }
        
        // Update the deferred target that we're in edit mode...
        DeferredContentTargetModelElement targetElement = getTargetElement();
        if (targetElement != null)
        {
            targetElement.enterEditMode(mode, this);
        }
    }

    /**
     * <p>Update the associated {@link DeferredContentTargetModelElement} that edit mode has been
     * exited.</p>
     */
    public void exitEditMode()
    {
        // Update the deferred target that we're out of edit mode...
        DeferredContentTargetModelElement targetElement = getTargetElement();
        if (targetElement != null)
        {
            targetElement.exitEditMode();
        }
    }

    @Override
    public String toString()
    {
        return "DependencyDeferredContentSourceModelElement [dependency=" + dependency + ", group=" + group
                + ", targetElement=" + targetElement + "]";
    }
}
