/*
 * GroupPropertySource.java
 * Created on Jul 3, 2008
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
package com.wizzer.m3g.viewer.ui.property;

// Import standard Java classes.
import java.util.Vector;

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Group;
import com.wizzer.m3g.Light;
import com.wizzer.m3g.Camera;
import com.wizzer.m3g.Mesh;
import com.wizzer.m3g.World;
import com.wizzer.m3g.Sprite3D;

/**
 * This class is a Property Source for a M3G Group.
 * 
 * @author Mark Millard
 */
public class GroupPropertySource implements IPropertySource
{
	// The property identifier for the M3G group children field.
	private static final String PROPERTY_GROUP_CHILD = "com.wizzer.m3g.viewer.ui.group.child";
	
	// The associated M3G object.
	private Group m_group;
	// The collection of node property sources.
	private NodePropertySource[] m_nodePropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private GroupPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param group The associated M3G Group for this property.
	 */
	public GroupPropertySource(Group group)
	{
		m_group = group;
		int count = m_group.getChildCount();
		m_nodePropertySource = new NodePropertySource[count];
		for (int i = 0; i < count; i++)
		{
			if (m_group.getChild(i) instanceof Group)
				m_nodePropertySource[i] = new NodePropertySource(
					(Group) m_group.getChild(i));
			else if (m_group.getChild(i) instanceof Camera)
				m_nodePropertySource[i] = new NodePropertySource(
					(Camera) m_group.getChild(i));
			else if (m_group.getChild(i) instanceof Mesh)
				m_nodePropertySource[i] = new NodePropertySource(
					(Mesh) m_group.getChild(i));
			else if (m_group.getChild(i) instanceof Light)
				m_nodePropertySource[i] = new NodePropertySource(
					(Light) m_group.getChild(i));
			else if (m_group.getChild(i) instanceof World)
				m_nodePropertySource[i] = new NodePropertySource(
					(World) m_group.getChild(i));
			else if (m_group.getChild(i) instanceof Sprite3D)
				m_nodePropertySource[i] = new NodePropertySource(
					(Sprite3D) m_group.getChild(i));
		}
	}
	
	/**
	 * Get the associated Group data.
	 * 
	 * @return A <code>Group</code> is returned.
	 */
	public Group getGroup()
	{
		return m_group;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue()
	{
		return this;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors()
	{
		if (m_descriptors == null)
		{
			Vector<PropertyDescriptor> descriptors = new Vector<PropertyDescriptor>();;
			
			for (int i = 0; i < m_group.getChildCount(); i++)
			{
				String id = new String(PROPERTY_GROUP_CHILD + ":" + i);
				
				if (m_group.getChild(i) instanceof Group)
				{
					PropertyDescriptor groupDescr = new NodePropertyDescriptor(
						id, "Group Node");
					descriptors.add(groupDescr);
				} else if (m_group.getChild(i) instanceof Camera)
				{
					PropertyDescriptor cameraDescr = new NodePropertyDescriptor(
						id, "Camera Node");
					descriptors.add(cameraDescr);
				} else if (m_group.getChild(i) instanceof Mesh)
				{
					PropertyDescriptor meshDescr = new NodePropertyDescriptor(
						id, "Mesh Node");
					descriptors.add(meshDescr);
				} else if (m_group.getChild(i) instanceof Light)
				{
					PropertyDescriptor lightDescr = new NodePropertyDescriptor(
						id, "Light Node");
					descriptors.add(lightDescr);
				} else if (m_group.getChild(i) instanceof Sprite3D)
				{
					PropertyDescriptor spriteDescr = new NodePropertyDescriptor(
						id, "Sprite3D Node");
					descriptors.add(spriteDescr);
				}
			}
			
			Object[] objs = descriptors.toArray();
			m_descriptors = new IPropertyDescriptor[objs.length];
			for (int i = 0; i < m_descriptors.length; i++)
			{
				m_descriptors[i] = (IPropertyDescriptor)objs[i];
			}
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		String str = (String)id;
		int index = str.indexOf(":");
		
		if (index != -1)
		{
			String key = str.substring(index+1);
			Integer keyValue = Integer.valueOf(key);
			String property = str.substring(0, index);
			if (property.equals(PROPERTY_GROUP_CHILD))
			{
				return m_nodePropertySource[keyValue.intValue()];
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		// Check to see if the id is a PROPERTY_GROUP_CHILDR.
		String str = (String)id;
		int index = str.indexOf(":");
		
		if (index != -1)
		{
			// Assume the remaining part of the identifier is correct
			// (since we generated it in the first place).
			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
	 */
	public void resetPropertyValue(Object id)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value)
	{
		// TODO Auto-generated method stub

	}
}
