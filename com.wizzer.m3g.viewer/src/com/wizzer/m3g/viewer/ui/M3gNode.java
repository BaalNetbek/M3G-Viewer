/*
 * M3gNode.java
 * Created on May 29, 2008
 */

// COPYRIGHT_BEGIN
//
// Copyright (C) 2000-2008  Wizzer Works (msm@wizzerworks.com)
// 
// This file is part of the M3G Viewer.
//
// The M3G Viewer is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published by the Free
// Software Foundation; either version 2 of the License, or (at your option)
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
// more details.
//
// You should have received a copy of the GNU Lesser General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//
// COPYRIGHT_END

// Declare package.
package com.wizzer.m3g.viewer.ui;

// Import standard Java classes.
import java.util.Vector;

// Import Eclipse classes.
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

// Import M3G Toolkit classes.
import com.wizzer.m3g.HeaderObject;
import com.wizzer.m3g.ExternalReference;
import com.wizzer.m3g.Object3D;
import com.wizzer.m3g.SceneSection;

/**
 * This class is a generic scene graph node that manages
 * elements from a Mobile 3D Graphics file.
 * 
 * @author Mark Millard
 */
public class M3gNode implements IAdaptable
{
	// The name of the node.
    private String m_name = null;
    // The M3G object associated with the node.
    private Object m_object;
    // The node's children.
    private Vector<M3gNode> m_children;
    // The node's parent.
    private M3gNode m_parent;
    // A property source for the Properties View.
    private M3gNodePropertySource m_nodePS;

    // Hide the default constructor.
    private M3gNode() {}
    
    public M3gNode(Object obj, M3gNode parent)
    {
        this.m_object = obj;
        this.m_parent = parent;
        if (parent != null)
            parent.addChild(this);
    }

    public M3gNode(Object obj, String name, M3gNode parent)
    {
    	this.m_object = obj;
        this.m_name = name;
        this.m_parent = parent;
        if (parent != null)
            parent.addChild(this);
    }

    public Vector<M3gNode> getChildren()
    {
        return m_children;
    }

    private void addChild(M3gNode child)
    {
        if (m_children == null)
            m_children = new Vector<M3gNode>();
        if (! m_children.contains(child))
            m_children.add(child);
    }

    public String getName()
    {
    	// Use an assigned name if it exists.
    	if (m_name != null)
    		return m_name;

    	// Return a predefined name if necessary.
    	if (m_object instanceof HeaderObject)
    		return new String("Header");
    	else if (m_object instanceof ExternalReference)
    		return new String("External Reference");
    	else if (m_object instanceof SceneSection)
    		return new String("Scene");
    	else if (m_object instanceof Object3D)
    		return new String("Object 3D");
    	else
    		return new String("Unknown");
    }

    public M3gNode getParent()
    {
        return m_parent;
    }
    
    /**
     * Get associated M3G object.
     * 
     * @return A generic <code>Object</code> is returned.
     */
    public Object getM3gObject()
    {
    	return m_object;
    }
    
    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
     */
    public Object getAdapter(Class adapter)
    {
    	if (adapter == IPropertySource.class)
    	{
    		if (m_nodePS == null)
    		{
    			// Cache the node element property source.
    			m_nodePS = new M3gNodePropertySource(this, getName());
    		}
    		return m_nodePS;
    	}
    	return null;
    }
}
