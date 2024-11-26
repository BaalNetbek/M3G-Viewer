/*
 * FogPropertySource.java
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
import com.wizzer.m3g.Fog;

/**
 * This class is a Property Source for a M3G Fog.
 * 
 * @author Mark Millard
 */
public class FogPropertySource implements IPropertySource
{
	// The property identifier for the M3G fog color field.
	private static final String PROPERTY_FOG_COLOR = "com.wizzer.m3g.viewer.ui.fog.color";
	// The property identifier for the M3G fog mode field.
	private static final String PROPERTY_FOG_MODE = "com.wizzer.m3g.viewer.ui.fog.mode";
	// The property identifier for the M3G density field.
	private static final String PROPERTY_FOG_DENSITY = "com.wizzer.m3g.viewer.ui.fog.density";
	// The property identifier for the M3G fog near field.
	private static final String PROPERTY_FOG_NEAR = "com.wizzer.m3g.viewer.ui.fog.near";
	// The property identifier for the M3G fog far field.
	private static final String PROPERTY_FOG_FAR = "com.wizzer.m3g.viewer.ui.fog.far";
	
	// The associated M3G object.
	private Fog m_fog;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private FogPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param fog The associated M3G Fog for this property.
	 */
	public FogPropertySource(Fog fog)
	{
		m_fog = fog;
	}
	
	/**
	 * Get the associated Fog data.
	 * 
	 * @return A <code>Fog</code> is returned.
	 */
	public Fog getFog()
	{
		return m_fog;
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
			PropertyDescriptor colorDescr = new PropertyDescriptor(
					PROPERTY_FOG_COLOR, "Color");
			PropertyDescriptor modeDescr = new PropertyDescriptor(
					PROPERTY_FOG_MODE, "Mode");
			
			int mode = m_fog.getMode();
			if (mode == Fog.EXPONENTIAL)
			{
				PropertyDescriptor densityDescr = new PropertyDescriptor(
					PROPERTY_FOG_DENSITY, "Density");
				
				m_descriptors = new IPropertyDescriptor[] {
					colorDescr,
					modeDescr,
					densityDescr
				};
			} else
			{
				PropertyDescriptor nearDescr = new PropertyDescriptor(
					PROPERTY_FOG_DENSITY, "Near");
				PropertyDescriptor farDescr = new PropertyDescriptor(
					PROPERTY_FOG_FAR, "Far");
				
				m_descriptors = new IPropertyDescriptor[] {
					colorDescr,
					modeDescr,
					nearDescr,
					farDescr
				};
		   }	
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_FOG_MODE))
		{
			return m_fog.getMode();
		} else if (id.equals(PROPERTY_FOG_COLOR))
		{
			int color = m_fog.getColor();
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_FOG_DENSITY))
		{
			return m_fog.getDensity();
		} else if (id.equals(PROPERTY_FOG_FAR))
		{
			return m_fog.getFarDistance();
		} else if (id.equals(PROPERTY_FOG_NEAR))
		{
			return m_fog.getNearDistance();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_FOG_MODE.equals(id) ||
			PROPERTY_FOG_COLOR.equals(id) ||
			PROPERTY_FOG_DENSITY.equals(id) ||
			PROPERTY_FOG_FAR.equals(id) ||
			PROPERTY_FOG_NEAR.equals(id))
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
