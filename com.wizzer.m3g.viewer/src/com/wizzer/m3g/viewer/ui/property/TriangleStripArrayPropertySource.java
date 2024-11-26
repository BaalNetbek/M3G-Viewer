/*
 * TriangleStripArrayPropertySource.java
 * Created on Jun 28, 2008
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

// Import Eclipse classes
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.TriangleStripArray;

/**
 * This class is a Property Source for a M3G TriangleStripArray.
 * 
 * @author Mark Millard
 */
public class TriangleStripArrayPropertySource extends IndexBufferPropertySource
{
	// The property identifier for the M3G triangle strip array encoding field.
	private static final String PROPERTY_TRIANGLESTRIPARRAY_ENCODING = "com.wizzer.m3g.viewer.ui.trianglestriparray.encoding";
	// The property identifier for the M3G triangle strip array startIndex field.
	private static final String PROPERTY_TRIANGLESTRIPARRAY_STARTINDEX = "com.wizzer.m3g.viewer.ui.trianglestriparray.startindex";
	// The property identifier for the M3G triangle strip array indices field.
	private static final String PROPERTY_TRIANGLESTRIPARRAY_INDICES = "com.wizzer.m3g.viewer.ui.trianglestriparray.indices";
	// The property identifier for the M3G triangle strip array stripLengths field.
	private static final String PROPERTY_TRIANGLESTRIPARRAY_STRIPLENGTHS = "com.wizzer.m3g.viewer.ui.trianglestriparray.striplengths";

    // The parameterID field. A Uint32.
	TriangleStripArray m_array;
	
	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private TriangleStripArrayPropertySource() {}
	
	/**
	 * Create a new property source.
	 * 
	 * @param array The associated M3G TriangleStripArray for this property.
	 */
	public TriangleStripArrayPropertySource(TriangleStripArray array)
	{
		m_array = array;
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
		PropertyDescriptor encodingDesc = new PropertyDescriptor(
			PROPERTY_TRIANGLESTRIPARRAY_ENCODING, "Encoding");
		PropertyDescriptor indexDesc;
		if ((m_array.getEncoding() >= 0) && (m_array.getEncoding() < 3))
		{
			indexDesc = new PropertyDescriptor(
				PROPERTY_TRIANGLESTRIPARRAY_STARTINDEX, "Start Index");
		} else
		{
			indexDesc = new PropertyDescriptor(
				PROPERTY_TRIANGLESTRIPARRAY_INDICES, "Indices");
		}
		PropertyDescriptor stripLengthsDesc = new PropertyDescriptor(
				PROPERTY_TRIANGLESTRIPARRAY_STRIPLENGTHS, "Strip Lengths");
		
		m_descriptors = new IPropertyDescriptor[] {
			encodingDesc,
			indexDesc,
			stripLengthsDesc
		};
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_TRIANGLESTRIPARRAY_ENCODING))
		{
			return m_array.getEncoding();
		} else if (id.equals(PROPERTY_TRIANGLESTRIPARRAY_STARTINDEX))
		{
			return m_array.getStartIndex();
		} else if (id.equals(PROPERTY_TRIANGLESTRIPARRAY_INDICES))
		{
			int[] array = m_array.getIndices();
			StringBuffer buffer = new StringBuffer("[ ");
			for (int i = 0; i < array.length; i++)
			{
				buffer.append(array[i]);
				buffer.append(", ");
				if ((i > 0) && (i%10 == 0))
					buffer.append("\n");
			}
			return buffer.toString();
		} else if (id.equals(PROPERTY_TRIANGLESTRIPARRAY_STRIPLENGTHS))
		{
			int[] array = m_array.getStripLengths();
			StringBuffer buffer = new StringBuffer("[ ");
			for (int i = 0; i < array.length; i++)
			{
				buffer.append(array[i]);
				buffer.append(", ");
				if ((i > 0) && (i%10 == 0))
					buffer.append("\n");
			}
			return buffer.toString();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_TRIANGLESTRIPARRAY_ENCODING.equals(id) ||
			PROPERTY_TRIANGLESTRIPARRAY_STARTINDEX.equals(id) ||
			PROPERTY_TRIANGLESTRIPARRAY_INDICES.equals(id) ||
			PROPERTY_TRIANGLESTRIPARRAY_STRIPLENGTHS.equals(id))
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
