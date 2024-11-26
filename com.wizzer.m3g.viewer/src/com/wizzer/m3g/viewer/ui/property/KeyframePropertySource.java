/*
 * KeyframePropertySource.java
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

// Import standard Java classes;
import java.util.Vector;

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.KeyframeSequence;
/**
 * This class is a Property Source for a Key Frame referenced
 * by a M3G KeyframeSequence.
 * 
 * @author Mark Millard
 */
public class KeyframePropertySource implements IPropertySource
{
	// The property identifier for the M3G keyframe time field.
	private static final String PROPERTY_KEYFRAME_TIME = "com.wizzer.m3g.viewer.ui.keyframe.time";
	// The property identifier for the M3G keyframe vectorvalue field.
	private static final String PROPERTY_KEYFRAME_VECTORVALUE = "com.wizzer.m3g.viewer.ui.keyframe.vectorvalue";
	
	// The associated KeyframeSequence.
	private KeyframeSequence m_sequence;
	// The key frame time stamp.
	private long m_time;
	// The key frame vector value (one for each component).
	private Object[] m_vectorValue;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private KeyframePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param sequence The associated M3G KeyframeSequence for this property.
	 * @param time The time field for a Key Frame.
	 * @param vectorValue The vector value data for a Key Frame.
	 */
	public KeyframePropertySource(KeyframeSequence sequence, long time, Object[] vectorValue)
	{
		m_sequence = sequence;
		int componentCount = sequence.getComponentCount();
		m_time = time;
		if (sequence.getEncoding() == 0)
		{
			m_vectorValue = new Float[componentCount];
			for (int j = 0; j < componentCount; j++)
			{
				m_vectorValue[j] = new Float((Float) vectorValue[j]);
			}
		} else if (sequence.getEncoding() == 1)
		{
			m_vectorValue = new Byte[componentCount];
			for (int j = 0; j < componentCount; j++)
			{
				m_vectorValue[j] = new Byte((Byte) vectorValue[j]);
			}

		} else if (sequence.getEncoding() == 2)
		{
			m_vectorValue = new Integer[componentCount];
			for (int j = 0; j < componentCount; j++)
			{
				m_vectorValue[j] = new Integer((Integer) vectorValue[j]);
			}

	    }
	}
	
	/**
	 * Get the associated Keyframe time data.
	 * 
	 * @return A <code>long</code> is returned.
	 */
	public long getKeyframeTime()
	{
		return m_time;
	}
	
	/**
	 * Get the associated Keyframe vector value data.
	 * 
	 * @return An array of <code>Object</code> is returned.
	 */
	public Object[] getKeyframeVectorValue()
	{
		return m_vectorValue;
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
		Vector<PropertyDescriptor> descriptors = new Vector<PropertyDescriptor>();
		
		if (m_descriptors == null)
		{
			PropertyDescriptor keyframeTimeDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAME_TIME, "Time");
			descriptors.add(keyframeTimeDescr);
			
			if ((m_sequence.getEncoding() == 0) ||
				(m_sequence.getEncoding() == 1) ||
				(m_sequence.getEncoding() == 2))
			{
				for (int j = 0; j < m_sequence.getComponentCount(); j++)
				{
					String vectorValueId = new String(PROPERTY_KEYFRAME_VECTORVALUE +
						":" + j);
					PropertyDescriptor vectorValueDescr = new PropertyDescriptor(
						vectorValueId, "Vector Value " + j);
					descriptors.add(vectorValueDescr);						
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
		if (id.equals(PROPERTY_KEYFRAME_TIME))
		{
			return m_time;
		} else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				
				if (property.equals(PROPERTY_KEYFRAME_VECTORVALUE))
				{
					return m_vectorValue[keyValue.intValue()].toString();
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
		if (PROPERTY_KEYFRAME_TIME.equals(id))
			return true;
		else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			// Check for PROPERTY_KEYFRAME_VECTORVALUE.
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
