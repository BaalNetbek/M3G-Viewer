/*
 * SkinnedMeshPropertySource.java
 * Created on Jul 10, 2008
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

/**
 * This class is a Property Source for a M3G SkinnedMesh.
 * 
 * @author Mark Millard
 */
public class SkinnedMeshPropertySource implements IPropertySource
{
	// The property identifier for the M3G skinnedmesh skeleton field.
	private static final String PROPERTY_SKINNEDMESH_SKELETON = "com.wizzer.m3g.viewer.ui.skinnedmesh.skeleton";
	// The property identifier for the M3G skinnedmesh transformReferenceCount field.
	private static final String PROPERTY_SKINNEDMESH_TRANSFORMREFERENCECOUNT = "com.wizzer.m3g.viewer.ui.skinnedmesh.transformreferencecount";
	// The property identifier for the M3G skinnedmesh bone field.
	private static final String PROPERTY_SKINNEDMESH_BONE = "com.wizzer.m3g.viewer.ui.skinnedmesh.bone";
	
	// The associated M3G object.
	private SkinnedMesh m_mesh;
	// The skeleton property source.
	private GroupPropertySource m_skeletonPropertySource;
	// The collection of bone property sources.
	private BonePropertySource[] m_bonePropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private SkinnedMeshPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param mesh The associated M3G SkinnedMesh for this property.
	 */
	public SkinnedMeshPropertySource(SkinnedMesh mesh)
	{
		m_mesh = mesh;
		m_skeletonPropertySource = new GroupPropertySource(m_mesh.getSkeleton());
		int count = m_mesh.getTransformReferenceCount();
		m_bonePropertySource = new BonePropertySource[count];
		for (int i = 0; i < count; i++)
		{
			m_bonePropertySource[i] = new BonePropertySource(m_mesh,
				m_mesh.getTransformNode(i), m_mesh.getFirstVertex(i),
				m_mesh.getVertexCount(i), m_mesh.getWeight(i));
		}
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
			
			PropertyDescriptor skeletonDescr = new GroupPropertyDescriptor(
				PROPERTY_SKINNEDMESH_SKELETON, "Skeleton");
			descriptors.add(skeletonDescr);
			PropertyDescriptor transformReferenceCountDescr = new PropertyDescriptor(
				PROPERTY_SKINNEDMESH_TRANSFORMREFERENCECOUNT, "Transform Reference Count");
			descriptors.add(transformReferenceCountDescr);
			
			for (int i = 0; i < m_mesh.getTransformReferenceCount(); i++)
			{
				String boneId = new String(PROPERTY_SKINNEDMESH_BONE + ":" + i);
				PropertyDescriptor boneDescr = new BonePropertyDescriptor(
						boneId, "Bone " + i);
				descriptors.add(boneDescr);
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
		if (id.equals(PROPERTY_SKINNEDMESH_SKELETON))
		{
			return m_skeletonPropertySource;
		} else if (id.equals(PROPERTY_SKINNEDMESH_TRANSFORMREFERENCECOUNT))
		{
			return m_mesh.getTransformReferenceCount();
		} else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				if (property.equals(PROPERTY_SKINNEDMESH_BONE))
				{
					return m_bonePropertySource[keyValue.intValue()];
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_SKINNEDMESH_SKELETON.equals(id) ||
		    PROPERTY_SKINNEDMESH_TRANSFORMREFERENCECOUNT.equals(id) ||
		    PROPERTY_SKINNEDMESH_BONE.equals(id))
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
