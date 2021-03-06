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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.extensions.surf.ModelPersisterInfo;
import org.springframework.extensions.surf.extensibility.BasicExtensionModule;
import org.springframework.extensions.surf.util.XMLUtil;

public class ExtensionImpl extends AbstractModelObject implements Extension
{
    private static final long serialVersionUID = -7611883161758132916L;
    private static final Log logger = LogFactory.getLog(ExtensionImpl.class);
    
    public static final String MODULE = "module";
    public static final String MODULE_ID_XPATH_QUERY_OPEN = "/extension//modules//module//id[.= '";
    public static final String MODULE_ID_XPATH_QUERY_CLOSE = "']";
    
    
    private List<ExtensionModule> modules = new ArrayList<ExtensionModule>();
    
    @Override
    public String getTypeId()
    {
        return Extension.TYPE_ID;
    }

    /**
     * <p>Extends the default constructor by extracting the child elements of the <{@code}modules>
     * element and adds the value of any element with the name <{@code}module> to the <code>modules</code> 
     * <code>java.util.List</code> instance variable.</p>
     * <p>The configuration for the extension point arguments should be similar to:
     * <pre>
     * <{@code}modules>
     *     <{@code}module>mod1<{@code}/module>
     *     <{@code}module>mod2<{@code}/module>
     *     <{@code}module>mod3<{@code}/module>
     * <{@code}/modules>
     * </pre>
     * 
     * @param id String
     * @param key ModelPersisterInfo
     * @param document Document
     */
    @SuppressWarnings("unchecked")
    public ExtensionImpl(String id, ModelPersisterInfo key, Document document)
    {
        super(id, key, document);
        
        // Get all the "modules" elements (there should only be the one)...
        List<Element> modulesDefinitionList = getDocument().getRootElement().elements(Extension.PROP_MODULES);
        for (Element modulesDefinition: modulesDefinitionList)
        {
            List<Element> moduleDefinitionList = (List<Element>) XMLUtil.getChildren(modulesDefinition);
            for (Element moduleDefinition: moduleDefinitionList)
            {
                if (moduleDefinition.getName().equals(MODULE))
                {
                    this.modules.add(new ExtensionModule(moduleDefinition, key));
                }
            }
        }
    }
    
    public List<ExtensionModule> getExtensionModules()
    {
        return this.modules;
    }

    public String getExtensionType()
    {
        return getProperty(PROP_EXTENSION_TYPE);
    }

    public void setExtensionType(String extensionType)
    {
        setProperty(PROP_EXTENSION_TYPE, extensionType);
    }
    
    /**
     * <p>Locates both the {@link ExtensionModule} object in the {@link Extension} instance {@link List} as well as the
     * {@link Node} from the {@link Document} and returns it in a single {@link ModuleObjectAndNode} instance. The
     * <code>getObject</code> and <code>getNode</code> methods should be checked for <code>null</code> values as 
     * a {@link ModuleObjectAndNode} instance will still be returned if only <b>ONE</b> of the objects is found.</p>
     *  
     * @param id The id of the module to find.
     * @return A {@link ModuleObjectAndNode} instance if the module could be found and <code>null</code> if it couldn't.
     */
    private ModuleObjectAndNode findModule(String id, Document document)
    {
        ModuleObjectAndNode result = null;
        if (id != null)
        {
            // Find the module...
            ExtensionModule targetModule = null;
            Node moduleNode = null;
            List<ExtensionModule> modules = getExtensionModules();
            for (ExtensionModule module: modules)
            {
                if (id.equals(module.getId()))
                {
                    targetModule = module;
                    break;
                }
            }

            // Find it in the document...
            // Here we use an XPath query to find all <id> elements within a <module> where
            // the content equals the supplied id...
            String xpathQuery = MODULE_ID_XPATH_QUERY_OPEN + id + MODULE_ID_XPATH_QUERY_CLOSE;
            Node targetIdNode = document.selectSingleNode(xpathQuery);
            if (targetIdNode != null)
            {
                moduleNode = targetIdNode.getParent();
            }
            
            if (targetModule != null && moduleNode != null)
            {
                result = new ModuleObjectAndNode(targetModule, moduleNode);
            }
        }
        else
        {
            // No id was supplied.
        }
        return result;
    }
    
    /**
     * <p>Adds a new {@link ExtensionModule} based on the supplied XML fragment string. The {@link ExtensionModule} will
     * only be created if it is defined with an "id" attribute that is not already present in the {@link Extension}.<p>
     */
    public ExtensionModule addExtensionModule(String xmlFragment) throws DocumentException
    {
        ExtensionModule createdModule = null;
        Document moduleDoc = XMLUtil.parse(xmlFragment);
        Element newModuleRoot = moduleDoc.getRootElement();
        if (newModuleRoot != null && newModuleRoot.getName().equals(MODULE))
        {
            Element idEl = newModuleRoot.element(BasicExtensionModule.ID);
            if (idEl == null)
            {
                // Invalid module - has no id...
                throw new DocumentException("An \"<id>\" element was not present in the XML supplied to create a new ExtensionModule");
            }
            else
            {
                String id = idEl.getTextTrim();
                Document extensionDocument = getDocument();
                ModuleObjectAndNode moan = findModule(id, extensionDocument);
                if (moan != null)
                {
                    // The object already exists. Updates should be done via the update method.
                    // The return object will stay as null.
                    if (logger.isErrorEnabled())
                    {
                        logger.error("An attempt was made to add a new ExtensionModule with the id \"" + id + "\" but an ExtensionModule with that id already exists.");
                    }
                }
                else
                {
                    // Create the module and add it to the list...
                    createdModule = new ExtensionModule(newModuleRoot, getKey());
                    this.getExtensionModules().add(createdModule);
                    
                    // Update the underlying document...
                    Element modulesElement = extensionDocument.getRootElement().element(PROP_MODULES);
                    if (modulesElement == null)
                    {
                        modulesElement = extensionDocument.getRootElement().addElement(PROP_MODULES);
                    }
                    modulesElement.add(newModuleRoot);
                    this.updateXML(extensionDocument);
                }
            }
        }
        else
        {
            // Output an error message because the XML isn't correctly formed
            throw new DocumentException("The root element of the XML supplied to create a new ExtensionModule was not \"<module>\"");
        }
        return createdModule;
    }
    
    /**
     * <p>Updates an existing {@link ExtensionModule} based on the supplied XML fragment string. The {@link ExtensionModule} will
     * only be updated if it is defined with an "id" attribute that is already present in the {@link Extension}.<p>
     */
    public ExtensionModule updateExtensionModule(String xmlFragment) throws DocumentException
    {
        ExtensionModule updatedModule = null;
        Document moduleDoc = XMLUtil.parse(xmlFragment);
        Element moduleRoot = moduleDoc.getRootElement();
        if (moduleRoot != null && moduleRoot.getName().equals(MODULE))
        {
            Element idEl = moduleRoot.element(BasicExtensionModule.ID);
            if (idEl == null)
            {
                // Invalid module - has no id...
                throw new DocumentException("An \"<id>\" element was not present in the XML supplied to update an ExtensionModule");
            }
            else
            {
                String id = idEl.getTextTrim();
                Document extensionDocument = getDocument();
                ModuleObjectAndNode moan = findModule(id, extensionDocument);
                if (moan != null)
                {
                    // Update the module and replace it in the list...
                    updatedModule = new ExtensionModule(moduleRoot, getKey());
                    this.getExtensionModules().remove(moan.getObject());
                    this.getExtensionModules().add(updatedModule);
                    
                    // Update the underlying document...
                    Element modulesElement = extensionDocument.getRootElement().element(PROP_MODULES);
                    modulesElement.remove(moan.getNode());
                    modulesElement.add(moduleRoot);
                    this.updateXML(extensionDocument);
                }
                else
                {
                    // The object does not exists.
                    // The return object will stay as null.
                    if (logger.isErrorEnabled())
                    {
                        logger.error("An attempt was made to add a update ExtensionModule with the id \"" + id + "\" but no ExtensionModule with that id exists.");
                    }
                }
            }
        }
        else
        {
            // Output an error message because the XML isn't correctly formed
            throw new DocumentException("The root element of the XML supplied to create a new ExtensionModule was not \"<module>\"");
        }
        return updatedModule;
    }
   
    /**
     * <p>Deleting an {@link ExtensionModule} is a two stage process. First it is necessary to locate the 
     * {@link ExtensionModule} in the {@link List} and then remove it if it is present. Secondly it is necessary
     * to update the {@link Document} maintained by the {@link ExtensionImpl}.</p>
     * 
     * @param moduleId String
     * @return ExtensionModule
     */
    public ExtensionModule deleteExtensionModule(String moduleId)
    {
        ExtensionModule targetModule = null;
        Document extensionDocument = getDocument();
        ModuleObjectAndNode moan = findModule(moduleId, extensionDocument);
        if (moan != null)
        {
            if (moan.getObject() != null)
            {
                getExtensionModules().remove(moan.getObject());
                targetModule = moan.getObject();
            }
            if (moan.getNode() != null)
            {
                moan.getNode().detach();
                this.updateXML(extensionDocument);
            }
        }
        return targetModule;
    }
    
    /**
     * <p>A basic {@link Class} that allows the {@link ExtensionModule} and {@link Node} representations of the same
     * module to be returned together from a method call.</p>
     * 
     * @author David Draper
     */
    public class ModuleObjectAndNode
    {
        private ExtensionModule object;
        private Node node;
        public ModuleObjectAndNode(ExtensionModule object, Node node)
        {
            this.object = object;
            this.node = node;
        }
        public ExtensionModule getObject()
        {
            return this.object;
        }
        public Node getNode()
        {
            return this.node;
        }
    }
}