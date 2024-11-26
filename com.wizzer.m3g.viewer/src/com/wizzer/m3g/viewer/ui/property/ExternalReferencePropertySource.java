/*
 * ExternalReferencePropertySource.java
 * Created on Jul 28, 2008
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
import com.wizzer.m3g.ExternalReference;

/**
 * This class is a Property Source for a M3G HeaderObject.
 * 
 * @author Mark Millard
 */
public class ExternalReferencePropertySource implements IPropertySource
{
	// The property identifier for the M3G external reference URI field.
	private static final String PROPERTY_EXTERNALREFERENCE_URI = "com.wizzer.m3g.viewer.ui.externalreference.uri";
	// The property identifier for the current working directory.
	private static final String PROPERTY_EXTERNALREFERENCE_CWD = "com.wizzer.m3g.viewer.ui.externalreference.cwd";
	
	// The associated M3G object.
	private ExternalReference m_reference;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private ExternalReferencePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param reference The associated M3G ExternalReference for this property.
	 */
	public ExternalReferencePropertySource(ExternalReference reference)
	{
		m_reference = reference;
	}
	
	/**
	 * Get the associated ExternalReference data.
	 * 
	 * @return A <code>ExternalReference</code> is returned.
	 */
	public ExternalReference getReference()
	{
		return m_reference;
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
			PropertyDescriptor uriDescr = new PropertyDescriptor(
				PROPERTY_EXTERNALREFERENCE_URI, "URI");
			PropertyDescriptor cwdDescr = new PropertyDescriptor(
				PROPERTY_EXTERNALREFERENCE_CWD, "Current Working Directory");
			
			m_descriptors = new IPropertyDescriptor[] {
				uriDescr,
				cwdDescr,
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_EXTERNALREFERENCE_URI))
		{
			return m_reference.getURI();
		} else if (id.equals(PROPERTY_EXTERNALREFERENCE_CWD))
		{
			return ExternalReference.getCwd();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_EXTERNALREFERENCE_URI.equals(id) ||
			PROPERTY_EXTERNALREFERENCE_CWD.equals(id))
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
