/*
 * LightPropertySource.java
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

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Light;

/**
 * This class is a Property Source for a M3G Light.
 * 
 * @author Mark Millard
 */
public class LightPropertySource implements IPropertySource
{
	// The property identifier for the M3G light attenuationConstant field.
	private static final String PROPERTY_LIGHT_ATTENUATIONCONSTANT = "com.wizzer.m3g.viewer.ui.light.attenuationconstant";
	// The property identifier for the M3G light attenuationLinear field.
	private static final String PROPERTY_LIGHT_ATTENUATIONLINEAR = "com.wizzer.m3g.viewer.ui.light.attenuationlinear";
	// The property identifier for the M3G light attenuationQuadratic field.
	private static final String PROPERTY_LIGHT_ATTENUATIONQUADRATIC = "com.wizzer.m3g.viewer.ui.light.attenuationquadratic";
	// The property identifier for the M3G light color field.
	private static final String PROPERTY_LIGHT_COLOR = "com.wizzer.m3g.viewer.ui.light.color";
	// The property identifier for the M3G light mode field.
	private static final String PROPERTY_LIGHT_MODE = "com.wizzer.m3g.viewer.ui.light.mode";
	// The property identifier for the M3G light intensity field.
	private static final String PROPERTY_LIGHT_INTENSITY = "com.wizzer.m3g.viewer.ui.light.intensity";
	// The property identifier for the M3G light spot angle field.
	private static final String PROPERTY_LIGHT_SPOTANGLE= "com.wizzer.m3g.viewer.ui.light.spotangle";
	// The property identifier for the M3G light spot exponent field.
	private static final String PROPERTY_LIGHT_SPOTEXPONENT = "com.wizzer.m3g.viewer.ui.light.spotexponent";
	
	// The associated M3G object.
	private Light m_light;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private LightPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param camera The associated M3G Light for this property.
	 */
	public LightPropertySource(Light camera)
	{
		m_light = camera;
	}
	
	/**
	 * Get the associated Light data.
	 * 
	 * @return A <code>Light</code> is returned.
	 */
	public Light getLight()
	{
		return m_light;
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
			PropertyDescriptor attenuationConstantDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_ATTENUATIONCONSTANT, "Attenuation Constant");
			PropertyDescriptor attenuationLinearDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_ATTENUATIONLINEAR, "Attenuation Linear");
			PropertyDescriptor attenuationQuadraticDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_ATTENUATIONQUADRATIC, "Attenuation Quadratic");
			PropertyDescriptor colorDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_COLOR, "Color");
			PropertyDescriptor modeDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_MODE, "Mode");
			PropertyDescriptor intensityDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_INTENSITY, "Intensiity");
			PropertyDescriptor spotAngleDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_SPOTANGLE, "Spot Angle");
			PropertyDescriptor spotExponentDescr = new PropertyDescriptor(
				PROPERTY_LIGHT_SPOTEXPONENT, "Spot Exponent");
			
			m_descriptors = new IPropertyDescriptor[] {
				attenuationConstantDescr,
				attenuationLinearDescr,
				attenuationQuadraticDescr,
				colorDescr,
				modeDescr,
				intensityDescr,
				spotAngleDescr,
				spotExponentDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_LIGHT_INTENSITY))
		{
			return m_light.getIntensity();
		} else if (id.equals(PROPERTY_LIGHT_MODE))
		{
			return m_light.getMode();
		} else if (id.equals(PROPERTY_LIGHT_ATTENUATIONCONSTANT))
		{
			return m_light.getConstantAttenuation();
		} else if (id.equals(PROPERTY_LIGHT_ATTENUATIONLINEAR))
		{
			return m_light.getLinearAttenuation();
		} else if (id.equals(PROPERTY_LIGHT_ATTENUATIONQUADRATIC))
		{
			return m_light.getQuadraticAttenuation();
		} else if (id.equals(PROPERTY_LIGHT_COLOR))
		{
			int color = m_light.getColor();
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_LIGHT_SPOTANGLE))
		{
			return m_light.getSpotAngle();
		} else if (id.equals(PROPERTY_LIGHT_SPOTEXPONENT))
		{
			return m_light.getSpotExponent();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_LIGHT_INTENSITY.equals(id) ||
			PROPERTY_LIGHT_MODE.equals(id) ||
			PROPERTY_LIGHT_ATTENUATIONCONSTANT.equals(id) ||
			PROPERTY_LIGHT_ATTENUATIONLINEAR.equals(id) ||
			PROPERTY_LIGHT_ATTENUATIONQUADRATIC.equals(id) ||
			PROPERTY_LIGHT_COLOR.equals(id) ||
			PROPERTY_LIGHT_SPOTANGLE.equals(id) ||
			PROPERTY_LIGHT_SPOTEXPONENT.equals(id))
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
