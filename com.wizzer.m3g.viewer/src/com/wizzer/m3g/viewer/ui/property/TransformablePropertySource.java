/*
 * TransformablePropertySource.java
 * Created on Jul 1, 2008
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
import com.wizzer.m3g.Transformable;
import com.wizzer.m3g.Transform;

/**
 * This class is a Property Source for a M3G Transformable.
 * 
 * @author Mark Millard
 */
public class TransformablePropertySource implements IPropertySource
{
	// The property identifier for the M3G transformable hasComponentTransform field.
	private static final String PROPERTY_TRANSFORMABLE_HASCOMPONENTTRANSFORM = "com.wizzer.m3g.viewer.ui.transformable.hascomponenttransform";
	// The property identifier for the M3G transformable translation field.
	private static final String PROPERTY_TRANSFORMABLE_TRANSLATION = "com.wizzer.m3g.viewer.ui.transformable.translation";
	// The property identifier for the M3G transformable scale field.
	private static final String PROPERTY_TRANSFORMABLE_SCALE = "com.wizzer.m3g.viewer.ui.transformable.scale";
	// The property identifier for the M3G transformable orientationAngle field.
	private static final String PROPERTY_TRANSFORMABLE_ORIENTATIONANGLE = "com.wizzer.m3g.viewer.ui.transformable.orientationangle";
	// The property identifier for the M3G transformable orientationAxis field.
	private static final String PROPERTY_TRANSFORMABLE_ORIENTATIONAXIS = "com.wizzer.m3g.viewer.ui.transformable.orientationaxis";
	// The property identifier for the M3G transformable hasGeneralTransform field.
	private static final String PROPERTY_TRANSFORMABLE_HASGENERALTRANSFORM = "com.wizzer.m3g.viewer.ui.transformable.hasgeneraltransform";
	// The property identifier for the M3G transformable transform field.
	private static final String PROPERTY_TRANSFORMABLE_TRANSFORM = "com.wizzer.m3g.viewer.ui.transformable.transform";
	
	// The associated M3G object.
	private Transformable m_transformable;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private TransformablePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param transformable The associated M3G Transformable for this property.
	 */
	public TransformablePropertySource(Transformable transformable)
	{
		m_transformable = transformable;
	}
	
	/**
	 * Get the associated Transformable data.
	 * 
	 * @return A <code>Transformable</code> is returned.
	 */
	public Transformable getTransformable()
	{
		return m_transformable;
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
			
			PropertyDescriptor hasComponentTransformDescr = new PropertyDescriptor(
					PROPERTY_TRANSFORMABLE_HASCOMPONENTTRANSFORM, "Component Transform");
			descriptors.add(hasComponentTransformDescr);
			
			if (m_transformable.hasComponentTransform())
			{
				PropertyDescriptor translationDescr = new PropertyDescriptor(
					PROPERTY_TRANSFORMABLE_TRANSLATION, "Translation");
				descriptors.add(translationDescr);
				PropertyDescriptor scaleDescr = new PropertyDescriptor(
					PROPERTY_TRANSFORMABLE_SCALE, "Scale");
				descriptors.add(scaleDescr);
				PropertyDescriptor orientationAngleDescr = new PropertyDescriptor(
					PROPERTY_TRANSFORMABLE_ORIENTATIONANGLE, "Orientation Angle");
				descriptors.add(orientationAngleDescr);
				PropertyDescriptor orientationAxisDescr = new PropertyDescriptor(
						PROPERTY_TRANSFORMABLE_ORIENTATIONAXIS, "Orietation Axis");
					descriptors.add(orientationAxisDescr);
			} 

			PropertyDescriptor hasGeneralTransformDescr = new PropertyDescriptor(
					PROPERTY_TRANSFORMABLE_HASGENERALTRANSFORM, "General Transform");
			descriptors.add(hasGeneralTransformDescr);

			if (m_transformable.hasGeneralTransform())
			{
				PropertyDescriptor hasTransformDescr = new PropertyDescriptor(
						PROPERTY_TRANSFORMABLE_TRANSFORM, "Transform");
				descriptors.add(hasTransformDescr);				
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
		if (id.equals(PROPERTY_TRANSFORMABLE_HASGENERALTRANSFORM))
		{
			return m_transformable.hasGeneralTransform();
		} else if (id.equals(PROPERTY_TRANSFORMABLE_ORIENTATIONAXIS))
		{
			float[] angleAxis = new float[4];
			m_transformable.getOrientation(angleAxis);
			StringBuffer buffer = new StringBuffer("x = ");
			buffer.append(angleAxis[1]);
			buffer.append("; y = ");
			buffer.append(angleAxis[2]);
			buffer.append("; z = ");
			buffer.append(angleAxis[3]);
			return buffer.toString();
		} else if (id.equals(PROPERTY_TRANSFORMABLE_HASCOMPONENTTRANSFORM))
		{
			return m_transformable.hasComponentTransform();
		} else if (id.equals(PROPERTY_TRANSFORMABLE_TRANSLATION))
		{
			float translation[] = new float[3];
			m_transformable.getTranslation(translation);
			StringBuffer buffer = new StringBuffer("x = ");
			buffer.append(translation[0]);
			buffer.append("; y = ");
			buffer.append(translation[1]);
			buffer.append("; z = ");
			buffer.append(translation[2]);
			return buffer.toString();
		} else if (id.equals(PROPERTY_TRANSFORMABLE_SCALE))
		{
			float scale[] = new float[3];
			m_transformable.getScale(scale);
			StringBuffer buffer = new StringBuffer("x = ");
			buffer.append(scale[0]);
			buffer.append("; y = ");
			buffer.append(scale[1]);
			buffer.append("; z = ");
			buffer.append(scale[2]);
			return buffer.toString();			
		} else if (id.equals(PROPERTY_TRANSFORMABLE_ORIENTATIONANGLE))
		{
			float[] angleAxis = new float[4];
			m_transformable.getOrientation(angleAxis);
			StringBuffer buffer = new StringBuffer();
			buffer.append(angleAxis[0]);
			return buffer.toString();
		} else if (id.equals(PROPERTY_TRANSFORMABLE_TRANSFORM))
		{
			Transform transform = new Transform();
			m_transformable.getTransform(transform);
			float[] matrix = new float[16];
			transform.get(matrix);
			StringBuffer buffer = new StringBuffer("[ ");
			for (int i = 0; i < 16; i++)
			{
				buffer.append(matrix[i]);
				buffer.append(" ");
			}
			buffer.append("]");
			return buffer.toString();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_TRANSFORMABLE_HASGENERALTRANSFORM.equals(id) ||
			PROPERTY_TRANSFORMABLE_ORIENTATIONAXIS.equals(id) ||
			PROPERTY_TRANSFORMABLE_HASCOMPONENTTRANSFORM.equals(id) ||
			PROPERTY_TRANSFORMABLE_TRANSLATION.equals(id) ||
			PROPERTY_TRANSFORMABLE_SCALE.equals(id) ||
			PROPERTY_TRANSFORMABLE_ORIENTATIONANGLE.equals(id) ||
			PROPERTY_TRANSFORMABLE_TRANSFORM.equals(id))
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
