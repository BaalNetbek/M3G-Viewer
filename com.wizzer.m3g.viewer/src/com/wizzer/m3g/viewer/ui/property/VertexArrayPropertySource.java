/*
 * VertexArrayPropertySource.java
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

// Import Java Networking I/O classes
import java.nio.*;

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.VertexArray;

/**
 * This class is a Property Source for a M3G VertexArray.
 * 
 * @author Mark Millard
 */
public class VertexArrayPropertySource implements IPropertySource
{
	// The property identifier for the M3G vertex array componentSize field.
	private static final String PROPERTY_VERTEXARRAY_COMPONENTSIZE= "com.wizzer.m3g.viewer.ui.vertexarray.componentsize";
	// The property identifier for the M3G vertex array componentCount field.
	private static final String PROPERTY_VERTEXARRAY_COMPONENTCOUNT = "com.wizzer.m3g.viewer.ui.vertexarray.componentcount";
	// The property identifier for the M3G vertex array encoding field.
	private static final String PROPERTY_VERTEXARRAY_ENCODING = "com.wizzer.m3g.viewer.ui.vertexarray.encoding";
	// The property identifier for the M3G vertex array vertexCount field.
	private static final String PROPERTY_VERTEXARRAY_VERTEXCOUNT = "com.wizzer.m3g.viewer.ui.vertexarray.vertexcount";
	// The property identifier for the M3G vertex array vertices field.
	private static final String PROPERTY_VERTEXARRAY_VERTICES = "com.wizzer.m3g.viewer.ui.vertexarray.vertices";
	
	// The associated M3G object.
	private VertexArray m_array;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private VertexArrayPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param array The associated M3G VertexArray for this property.
	 */
	public VertexArrayPropertySource(VertexArray array)
	{
		m_array = array;
	}
	
	/**
	 * Get the associated VertexArray data.
	 * 
	 * @return A <code>VertexArray</code> is returned.
	 */
	public VertexArray getArray()
	{
		return m_array;
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
			PropertyDescriptor componentSizeDescr = new PropertyDescriptor(
				PROPERTY_VERTEXARRAY_COMPONENTSIZE, "Component Size");
			PropertyDescriptor componentCountDescr = new PropertyDescriptor(
				PROPERTY_VERTEXARRAY_COMPONENTCOUNT, "Component Count");
			PropertyDescriptor encodingDescr = new PropertyDescriptor(
				PROPERTY_VERTEXARRAY_ENCODING, "Encoding");
			PropertyDescriptor vertexCountDescr = new PropertyDescriptor(
				PROPERTY_VERTEXARRAY_VERTEXCOUNT, "Vertex Count");
			PropertyDescriptor verticesDescr = new PropertyDescriptor(
					PROPERTY_VERTEXARRAY_VERTICES, "Vertices");
			
			m_descriptors = new IPropertyDescriptor[] {
				componentSizeDescr,
				componentCountDescr,
				encodingDescr,
				vertexCountDescr,
				verticesDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_VERTEXARRAY_COMPONENTCOUNT))
		{
			return m_array.getComponentCount();
		} else if (id.equals(PROPERTY_VERTEXARRAY_COMPONENTSIZE))
		{
			return m_array.getComponentSize();
		} else if (id.equals(PROPERTY_VERTEXARRAY_ENCODING))
		{
			return m_array.getEncoding();
		} else if (id.equals(PROPERTY_VERTEXARRAY_VERTEXCOUNT))
		{
			return m_array.getVertexCount();
		} else if (id.equals(PROPERTY_VERTEXARRAY_VERTICES))
		{
			StringBuffer buffer = new StringBuffer("[ ");
			if (m_array.getComponentSize() == 1)
			{
				ByteBuffer bBuffer = (ByteBuffer)m_array.getBuffer();
				int numElements = m_array.getVertexCount() * m_array.getComponentSize();
				byte[] data = new byte[numElements];
				bBuffer.get(data);
				for (int i = 0; i < data.length; i++)
				{
					buffer.append(data[i]);
					buffer.append(", ");
					if ((i > 0) && (i%(m_array.getComponentCount()*5) == 0))
						buffer.append("\n");
				}
			} else
			{
				ShortBuffer bBuffer = (ShortBuffer)m_array.getBuffer();
				int numElements = m_array.getVertexCount() * m_array.getComponentSize();
				short[] data = new short[numElements];
				bBuffer.get(data);
				for (int i = 0; i < data.length; i++)
				{
					buffer.append(data[i]);
					buffer.append(", ");
					if (((i > 0) && i%(m_array.getComponentCount()*5) == 0))
						buffer.append("\n");

				}
			}
			buffer.append(" ]");
			return buffer.toString();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_VERTEXARRAY_COMPONENTCOUNT.equals(id) ||
			PROPERTY_VERTEXARRAY_COMPONENTSIZE.equals(id) ||
			PROPERTY_VERTEXARRAY_ENCODING.equals(id) ||
			PROPERTY_VERTEXARRAY_VERTEXCOUNT.equals(id) ||
			PROPERTY_VERTEXARRAY_VERTICES.equals(id))
			return true;
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
