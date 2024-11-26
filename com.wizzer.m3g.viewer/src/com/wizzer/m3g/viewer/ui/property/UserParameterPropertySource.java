/*
 * UserParameterPropertySource.java
 * Created on Jun 14, 2008
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
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * This class is a Property Source for a M3G Object3D User Parameter.
 * 
 * @author Mark Millard
 */
public class UserParameterPropertySource implements IPropertySource
{
	// The property identifier for the M3G object3D parameterID field.
	private static final String PROPERTY_PARAMETER_ID = "com.wizzer.m3g.viewer.ui.object3d.parameterid";
	// The property identifier for the M3G object3d parameterValue fields.
	private static final String PROPERTY_PARAMETER_VALUE = "com.wizzer.m3g.viewer.ui.object3d.parametervalue";

    // The parameterID field. A Uint32.
	long m_parameterID;
	// The parameterValue field.
	byte[] m_parameterValue;
	
	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private UserParameterPropertySource() {}
	
	/**
	 * Create a Property Source for the user parameter referenced by a M3G Object3D class.
	 * 
	 * @param id The parameter identifier.
	 * @param value The parameter value.
	 */
	public UserParameterPropertySource(long id, byte[] value)
	{
		m_parameterID = id;
		m_parameterValue = value;
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
		PropertyDescriptor parameterIdDesc = new PropertyDescriptor(
			PROPERTY_PARAMETER_ID, "Parameter Identifier");
		PropertyDescriptor parameterValueDesc = new PropertyDescriptor(
			PROPERTY_PARAMETER_VALUE, "Parameter Value");
		
		m_descriptors = new IPropertyDescriptor[] {
			parameterIdDesc,
			parameterValueDesc
		};
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_PARAMETER_ID))
		{
			return m_parameterID;
		} else if (id.equals(PROPERTY_PARAMETER_VALUE))
		{
			return m_parameterValue;
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_PARAMETER_ID.equals(id) ||
			PROPERTY_PARAMETER_VALUE.equals(id))
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
