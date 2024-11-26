/*
 * KeyframeSequencePropertySource.java
 * Created on Jul 9, 2008
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
 * This class is a Property Source for a M3G KeyframeSequence.
 * 
 * @author Mark Millard
 */
public class KeyframeSequencePropertySource implements IPropertySource
{
	// The property identifier for the M3G keyframesequence interpolation field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_INTERPOLATION = "com.wizzer.m3g.viewer.ui.keyframesequence.interpolation";
	// The property identifier for the M3G keyframesequence repeatMode field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_REPEATMODE = "com.wizzer.m3g.viewer.ui.keyframesequence.repeatmode";
	// The property identifier for the M3G keyframesequence encoding field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_ENCODING = "com.wizzer.m3g.viewer.ui.keyframesequence.encoding";
	// The property identifier for the M3G keyframesequence duration field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_DURATION = "com.wizzer.m3g.viewer.ui.keyframesequence.duration";
	// The property identifier for the M3G keyframesequence validRangeFirst field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_VALIDRANGEFIRST = "com.wizzer.m3g.viewer.ui.keyframesequence.validrangefirst";
	// The property identifier for the M3G keyframesequence validRangeLast field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_VALIDRANGELAST = "com.wizzer.m3g.viewer.ui.keyframesequence.validrangelast";
	// The property identifier for the M3G keyframesequence componentCount field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_COMPONENTCOUNT = "com.wizzer.m3g.viewer.ui.keyframesequence.componentcount";
	// The property identifier for the M3G keyframesequence key frame fields.
	private static final String PROPERTY_KEYFRAMESEQUENCE_FRAME = "com.wizzer.m3g.viewer.ui.keyframesequence.frame";
	// The property identifier for the M3G keyframesequence vectorBias field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_VECTORBIAS = "com.wizzer.m3g.viewer.ui.keyframesequence.vectorbias";
	// The property identifier for the M3G keyframesequence vectorScale field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_VECTORSCALE = "com.wizzer.m3g.viewer.ui.keyframesequence.vectorscale";
	// The property identifier for the M3G keyframesequence keyframeCount field.
	private static final String PROPERTY_KEYFRAMESEQUENCE_KEYFRAMECOUNT = "com.wizzer.m3g.viewer.ui.keyframesequence.keyframecount";
	
	// The associated M3G object.
	private KeyframeSequence m_sequence;
	// The collection of frames.
	private KeyframePropertySource[] m_frames;
	// The collection of vector bias values per frame.
	private float[] m_vectorBias;
	// The collection of vector scale values per frame.
	private float[] m_vectorScale;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private KeyframeSequencePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param keyframesequence The associated M3G KeyframeSequence for this property.
	 */
	public KeyframeSequencePropertySource(KeyframeSequence keyframesequence)
	{
		m_sequence = keyframesequence;
		int keyframeCount = m_sequence.getKeyframeCount();
		int componentCount = m_sequence.getComponentCount();
		m_frames = new KeyframePropertySource[keyframeCount];
		
		if (m_sequence.getEncoding() == 0)
		{
			Float[] vectorValue = new Float[componentCount];
			for (int i = 0; i < keyframeCount; i++)
			{
				for (int j = 0; j < componentCount; j++)
				{
					int index = (i * componentCount) + j;
					vectorValue[j] = new Float(m_sequence.getKeyFrames()[index]);
				}
				long time = m_sequence.getTimes()[i];
				m_frames[i] = new KeyframePropertySource(m_sequence,
					time, vectorValue);
			}
		} else if (m_sequence.getEncoding() == 1)
		{
/*
			Byte[] vectorValue = new Byte[componentCount];
			for (int i = 0; i < keyframeCount; i++)
			{
				for (int j = 0; j < componentCount; j++)
				{
					int index = (i * componentCount) + j;
					vectorValue[j] = new Byte(m_sequence.getKeyFrames()[index]);
				}
				long time = m_sequence.getTimes()[i];
				m_frames[i] = new KeyframePropertySource(m_sequence,
					time, vectorValue);
			}
*/
		} else if (m_sequence.getEncoding() == 2)
		{
/*
			Integer[] vectorValue = new Integer[componentCount];

			for (int i = 0; i < keyframeCount; i++)
			{
				for (int j = 0; j < componentCount; j++)
				{
					int index = (i * componentCount) + j;
					vectorValue[j] = new Integer(m_sequence.getKeyFrames()[index]);
				}
				long time = m_sequence.getTimes()[i];
				m_frames[i] = new KeyframePropertySource(m_sequence,
					time, vectorValue);
			}
*/
	    }
		m_vectorBias = new float[componentCount];
		// XXX - finish filling in array.
		m_vectorScale = new float[componentCount];
		// XXX - finish filling in array.
	}
	
	/**
	 * Get the associated KeyframeSequence data.
	 * 
	 * @return A <code>KeyframeSequence</code> is returned.
	 */
	public KeyframeSequence getKeyframeSequence()
	{
		return m_sequence;
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
			PropertyDescriptor interpolationDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_INTERPOLATION, "Interpolation");
			descriptors.add(interpolationDescr);
			PropertyDescriptor repeatModeDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_REPEATMODE, "Repeat Mode");
			descriptors.add(repeatModeDescr);
			PropertyDescriptor encodingDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_ENCODING, "Encoding");
			descriptors.add(encodingDescr);
			PropertyDescriptor durationDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_DURATION, "Duration");
			descriptors.add(durationDescr);
			PropertyDescriptor validRangeFirstDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_VALIDRANGEFIRST, "Valid Range First");
			descriptors.add(validRangeFirstDescr);
			PropertyDescriptor validRangeLastDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_VALIDRANGELAST, "Valid Range Last");
			descriptors.add(validRangeLastDescr);
			PropertyDescriptor componentCountDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_COMPONENTCOUNT, "Component Count");
			descriptors.add(componentCountDescr);
			PropertyDescriptor keyframeCountDescr = new PropertyDescriptor(
				PROPERTY_KEYFRAMESEQUENCE_KEYFRAMECOUNT, "Keyframe Count");
			descriptors.add(keyframeCountDescr);
			
			if (m_sequence.getEncoding() == 0)
			{
				for (int i = 0; i < m_sequence.getKeyframeCount(); i++)
				{
					String frameId = new String(PROPERTY_KEYFRAMESEQUENCE_FRAME + ":" + i);
					PropertyDescriptor frameDescr = new KeyframePropertyDescriptor(
						frameId, "Key Frame " + i);
					descriptors.add(frameDescr);
				}
			} else if ((m_sequence.getEncoding() == 1) || (m_sequence.getEncoding() == 2))
			{
				for (int i = 0; i < m_sequence.getComponentCount(); i++)
				{
					String vectorBiasId = new String(PROPERTY_KEYFRAMESEQUENCE_VECTORBIAS + ":" + i);
					PropertyDescriptor vectorBiasDescr = new PropertyDescriptor(
						vectorBiasId, "Vector Bias");
					descriptors.add(vectorBiasDescr);
					String vectorScaleId = new String(PROPERTY_KEYFRAMESEQUENCE_VECTORSCALE + ":" + i);
					PropertyDescriptor vectorScaleDescr = new PropertyDescriptor(
						vectorScaleId, "Vector Scale");
					descriptors.add(vectorScaleDescr);
				}
				
				for (int i = 0; i < m_sequence.getKeyframeCount(); i++)
				{
					String frameId = new String(PROPERTY_KEYFRAMESEQUENCE_FRAME + ":" + i);
					PropertyDescriptor frameDescr = new PropertyDescriptor(
						frameId, "Key Frame" + i);
					descriptors.add(frameDescr);
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
		if (id.equals(PROPERTY_KEYFRAMESEQUENCE_REPEATMODE))
		{
			return m_sequence.getRepeatMode();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_INTERPOLATION))
		{
			return m_sequence.getInterpolationType();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_ENCODING))
		{
			return m_sequence.getEncoding();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_VALIDRANGEFIRST))
		{
			return m_sequence.getValidRangeFirst();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_DURATION))
		{
			return m_sequence.getDuration();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_COMPONENTCOUNT))
		{
			return m_sequence.getComponentCount();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_VALIDRANGELAST))
		{
			return m_sequence.getValidRangeLast();
		} else if (id.equals(PROPERTY_KEYFRAMESEQUENCE_KEYFRAMECOUNT))
		{
			return m_sequence.getKeyframeCount();
		} else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				
				if (property.equals(PROPERTY_KEYFRAMESEQUENCE_FRAME))
				{
					return m_frames[keyValue.intValue()];
				} else if (property.equals(PROPERTY_KEYFRAMESEQUENCE_VECTORSCALE))
				{
					return m_vectorScale[keyValue.intValue()];
				} else if (property.equals(PROPERTY_KEYFRAMESEQUENCE_VECTORBIAS))
				{
					return m_vectorBias[keyValue.intValue()];
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
		if (PROPERTY_KEYFRAMESEQUENCE_REPEATMODE.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_INTERPOLATION.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_ENCODING.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_VALIDRANGEFIRST.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_DURATION.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_VALIDRANGELAST.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_COMPONENTCOUNT.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE_KEYFRAMECOUNT.equals(id))
			return true;
		else
		{
			String str = (String)id;
			int index = str.indexOf(":");
			
			// Check for PROPERTY_KEYFRAMESEQUENCE_FRAME, PROPERTY_KEYFRAMESEQUENCE_VECTORBIAS,
			// or PROPERTY_KEYFRAMESEQUENCE_VECTORSCALE.
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
