/*
 * MeshPropertySource.java
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
import com.wizzer.m3g.Mesh;
import com.wizzer.m3g.TriangleStripArray;

/**
 * This class is a Property Source for a M3G Mesh.
 * 
 * @author Mark Millard
 */
public class MeshPropertySource implements IPropertySource
{
	// The property identifier for the M3G mesh vertexBuffer field.
	private static final String PROPERTY_MESH_VERTEXBUFFER = "com.wizzer.m3g.viewer.ui.mesh.vertexbuffer";
	// The property identifier for the M3G mesh submeshcount field.
	private static final String PROPERTY_MESH_SUBMESHCOUNT = "com.wizzer.m3g.viewer.ui.mesh.submeshcount";
	// The property identifier for the M3G indexBuffer field.
	private static final String PROPERTY_MESH_INDEXBUFFER = "com.wizzer.m3g.viewer.ui.mesh.indexbuffer";
	// The property identifier for the M3G mesh appearance field.
	private static final String PROPERTY_MESH_APPEARANCE = "com.wizzer.m3g.viewer.ui.mesh.appearance";
	
	// The associated M3G object.
	private Mesh m_mesh;
	// The vertexBuffer property source.
	private VertexBufferPropertySource m_vertexBufferPropertySource;
	// The collection of submesh indexBuffer property sources.
	private IndexBufferPropertySource[] m_indexBufferPropertySource;
	// The collection of submesh appearance property sources.
	private AppearancePropertySource[] m_appearancePropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private MeshPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param mesh The associated M3G Mesh for this property.
	 */
	public MeshPropertySource(Mesh mesh)
	{
		m_mesh = mesh;
		int count = m_mesh.getSubmeshCount();
		m_indexBufferPropertySource = new IndexBufferPropertySource[count];
		for (int i = 0; i < count; i++)
		{
			// Note: currently TriangleStripArray is the only subclass of
			// IndexBuffer. In the future, we will have to account for other
			// types of IndexBuffer.
			if (m_mesh.getIndexBuffer(i) instanceof TriangleStripArray)
				m_indexBufferPropertySource[i] = new TriangleStripArrayPropertySource(
					(TriangleStripArray) m_mesh.getIndexBuffer(i));
		}
		m_appearancePropertySource = new AppearancePropertySource[count];
		for (int i = 0; i < count; i++)
		{
			m_appearancePropertySource[i] = new AppearancePropertySource(
				m_mesh.getAppearance(i));
		}
		m_vertexBufferPropertySource = new VertexBufferPropertySource(
			m_mesh.getVertexBuffer());
	}
	
	/**
	 * Get the associated Mesh data.
	 * 
	 * @return A <code>Mesh</code> is returned.
	 */
	public Mesh getMesh()
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
			
			PropertyDescriptor vertexBufferDescr = new VertexBufferPropertyDescriptor(
					PROPERTY_MESH_VERTEXBUFFER, "VertexBuffer");
			descriptors.add(vertexBufferDescr);
			PropertyDescriptor submeshCountDescr = new PropertyDescriptor(
					PROPERTY_MESH_SUBMESHCOUNT, "Submesh Count");
			descriptors.add(submeshCountDescr);
			
			for (int i = 0; i < m_mesh.getSubmeshCount(); i++)
			{
				if (m_mesh.getIndexBuffer(i) instanceof TriangleStripArray)
				{
					// Note: currently TriangleStripArray is the only subclass of
					// IndexBuffer.
					
					String indexBufferId = new String(PROPERTY_MESH_INDEXBUFFER + ":" + i);
					
					PropertyDescriptor indexBufferDescr = new TriangleStripArrayPropertyDescriptor(
							indexBufferId, "Triangle Strip Array");
					descriptors.add(indexBufferDescr);
				}
				
				String appearanceId = new String(PROPERTY_MESH_APPEARANCE + ":" + i);
				PropertyDescriptor appearanceDescr = new AppearancePropertyDescriptor(
					appearanceId, "Appearance");
				descriptors.add(appearanceDescr);
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
		if (id.equals(PROPERTY_MESH_SUBMESHCOUNT))
		{
			return m_mesh.getSubmeshCount();
		} else if (id.equals(PROPERTY_MESH_VERTEXBUFFER))
		{
			return m_vertexBufferPropertySource;
		} else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				if (property.equals(PROPERTY_MESH_INDEXBUFFER))
				{
					return m_indexBufferPropertySource[keyValue.intValue()];
				} else if (property.equals(PROPERTY_MESH_APPEARANCE))
				{
					return m_appearancePropertySource[keyValue.intValue()];
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
		if (PROPERTY_MESH_SUBMESHCOUNT.equals(id) ||
			PROPERTY_MESH_VERTEXBUFFER.equals(id))
			return true;
		else
		{
			// Check to see if the id is a PROPERTY_MESH_APPEARANCE or a
			// PROPERTY_MESH_INDEXBUFFER.
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
