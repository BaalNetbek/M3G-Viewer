/*
 * BonePropertySource.java
 * Created on Aug 5, 2008
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
import com.wizzer.m3g.SkinnedMesh;
import com.wizzer.m3g.Node;

/**
 * This class is a Property Source for a M3G SkinnedMesh Bone.
 * 
 * @author Mark Millard
 */
public class BonePropertySource implements IPropertySource
{
	// The property identifier for the M3G bone transformNode field.
	private static final String PROPERTY_BONE_TRANSFORMNODE = "com.wizzer.m3g.viewer.ui.bone.transformNode";
	// The property identifier for the M3G bone firstVertex field.
	private static final String PROPERTY_BONE_FIRSTVERTEX = "com.wizzer.m3g.viewer.ui.bone.firstvertex";
	// The property identifier for the M3G bone vertexCount field.
	private static final String PROPERTY_BONE_VERTEXCOUNT = "com.wizzer.m3g.viewer.ui.bone.vertexcount";
	// The property identifier for the M3G bone WEIGHT field.
	private static final String PROPERTY_BONE_WEIGHT = "com.wizzer.m3g.viewer.ui.bone.weight";
	
	// The associated M3G object.
	private SkinnedMesh m_mesh;
	// The transform node property sources.
	private NodePropertySource m_transformNodePropertySource;
	// The first vertex.
	private long m_firstVertex;
	// The vertex count.
	private long m_vertexCount;
	// The weight.
	private int m_weight;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private BonePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param mesh The associated M3G SkinnedMesh for this property.
	 */
	public BonePropertySource(SkinnedMesh mesh, Node transformNode, long firstVertex, long vertexCount, int weight)
	{
		m_mesh = mesh;
		m_transformNodePropertySource = new NodePropertySource(transformNode);
		m_firstVertex = firstVertex;
		m_vertexCount = vertexCount;
		m_weight = weight;
	}
	
	/**
	 * Get the associated SkinnedMesh data.
	 * 
	 * @return A <code>SkinnedMesh</code> is returned.
	 */
	public SkinnedMesh getMesh()
	{
		return m_mesh;
	}
	
	public Node getTransformNode()
	{
		return m_transformNodePropertySource.getNode();
	}
	
	public long getFirstVertex()
	{
		return m_firstVertex;
	}
	
	public long getVertexCount()
	{
		return m_vertexCount;
	}
	
	public int getWeight()
	{
		return m_weight;
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
			Vector<PropertyDescriptor> descriptors = new Vector<PropertyDescriptor>();
			
			PropertyDescriptor transformNodeDescr = new NodePropertyDescriptor(
				PROPERTY_BONE_TRANSFORMNODE, "Transform Node");
			descriptors.add(transformNodeDescr);
			
			PropertyDescriptor firstVertexDescr = new PropertyDescriptor(
				PROPERTY_BONE_FIRSTVERTEX, "First Vertex");
			descriptors.add(firstVertexDescr);
			
			PropertyDescriptor vertexCountDescr = new PropertyDescriptor(
				PROPERTY_BONE_VERTEXCOUNT, "Vertex Count");
			descriptors.add(vertexCountDescr);
			
			PropertyDescriptor weightDescr = new PropertyDescriptor(
				PROPERTY_BONE_WEIGHT, "Weight");
			descriptors.add(weightDescr);
			
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
		if (id.equals(PROPERTY_BONE_TRANSFORMNODE))
		{
			return m_transformNodePropertySource;
		} else if (id.equals(PROPERTY_BONE_FIRSTVERTEX))
		{
			return m_firstVertex;
		} else if (id.equals(PROPERTY_BONE_VERTEXCOUNT))
		{
			return m_vertexCount;
		} else if (id.equals(PROPERTY_BONE_WEIGHT))
		{
			return m_weight;
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_BONE_TRANSFORMNODE.equals(id) ||
		    PROPERTY_BONE_FIRSTVERTEX.equals(id) ||
		    PROPERTY_BONE_VERTEXCOUNT.equals(id) ||
		    PROPERTY_BONE_WEIGHT.equals(id))
			return true;
		else
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
