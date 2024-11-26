/*
 * MorphingMeshPropertySource.java
 * Created on Jun 17, 2008
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
import com.wizzer.m3g.MorphingMesh;
import com.wizzer.m3g.VertexBuffer;

/**
 * This class is a Property Source for a M3G MorphingMesh.
 * 
 * @author Mark Millard
 */
public class MorphingMeshPropertySource implements IPropertySource
{
	// The property identifier for the M3G morphingmesh morphTargetCount field.
	private static final String PROPERTY_MORPHINGMESH_MORPHTARGETCOUNT = "com.wizzer.m3g.viewer.ui.morphingmesh.morphtargetcount";
	// The property identifier for the M3G morphingmesh morphTarget field.
	private static final String PROPERTY_MORPHINGMESH_MORPHTARGET = "com.wizzer.m3g.viewer.ui.morphingmesh.morphtarget";
	// The property identifier for the M3G morphingmesh initialWeight field.
	private static final String PROPERTY_MORPHINGMESH_INITIALWEIGHT = "com.wizzer.m3g.viewer.ui.morphingmesh.initialweight";
	
	// The associated M3G object.
	private MorphingMesh m_mesh;
	// The collection of morph target property sources.
	private VertexBufferPropertySource[] m_vertexBufferPropertySource;
	// The collection of initial weight property sources.
	private float[] m_initialWeights;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private MorphingMeshPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param mesh The associated M3G MorphingMesh for this property.
	 */
	public MorphingMeshPropertySource(MorphingMesh mesh)
	{
		m_mesh = mesh;
		int count = m_mesh.getMorphTargetCount();
		m_vertexBufferPropertySource = new VertexBufferPropertySource[count];
		for (int i = 0; i < count; i++)
		{
			m_vertexBufferPropertySource[i] = new VertexBufferPropertySource(
				(VertexBuffer) m_mesh.getMorphTarget(i));
		}
		m_initialWeights = new float[count];
		m_mesh.getWeights(m_initialWeights);
	}
	
	/**
	 * Get the associated MorphingMesh data.
	 * 
	 * @return A <code>MorphingMesh</code> is returned.
	 */
	public MorphingMesh getMesh()
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
			
			PropertyDescriptor morphTargetCountDescr = new PropertyDescriptor(
					PROPERTY_MORPHINGMESH_MORPHTARGETCOUNT, "Morph Target Count");
			descriptors.add(morphTargetCountDescr);
			
			for (int i = 0; i < m_mesh.getMorphTargetCount(); i++)
			{
				String morphTargetId = new String(PROPERTY_MORPHINGMESH_MORPHTARGET + ":" + i);
				
				PropertyDescriptor indexBufferDescr = new VertexBufferPropertyDescriptor(
					morphTargetId, "Morph Target");
				descriptors.add(indexBufferDescr);
				
				String initialWeightId = new String(PROPERTY_MORPHINGMESH_INITIALWEIGHT + ":" + i);
				PropertyDescriptor initialWeightDescr = new PropertyDescriptor(
					initialWeightId, "Initial Weight");
				descriptors.add(initialWeightDescr);
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
		if (id.equals(PROPERTY_MORPHINGMESH_MORPHTARGETCOUNT))
		{
			return m_mesh.getMorphTargetCount();
		} else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				if (property.equals(PROPERTY_MORPHINGMESH_MORPHTARGET))
				{
					return m_vertexBufferPropertySource[keyValue.intValue()];
				} else if (property.equals(PROPERTY_MORPHINGMESH_INITIALWEIGHT))
				{
					return m_initialWeights[keyValue.intValue()];
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
		if (PROPERTY_MORPHINGMESH_MORPHTARGETCOUNT.equals(id))
			return true;
		else
		{
			// Check to see if the id is a PROPERTY_MORPHINGMESH_MORPHTARGET or a
			// PROPERTY_MORPHINGMESH_INITIALWEIGHT.
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				// Assume the remaining part of the identifier is correct
				// (since we generated it in the first place).
				return true;
			}
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
