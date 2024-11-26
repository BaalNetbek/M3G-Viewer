/*
 * CompositingModePropertySource.java
 * Created on Jun 29, 2008
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
import com.wizzer.m3g.CompositingMode;


/**
 * This class is a Property Source for a M3G CompositingMode.
 * 
 * @author Mark Millard
 */
public class CompositingModePropertySource implements IPropertySource
{
	// The property identifier for the M3G compositingmode depthTestEnabled field.
	private static final String PROPERTY_COMPOSITINGMODE_DEPTHTESTENABLED = "com.wizzer.m3g.viewer.ui.compositingmode.dpthtestenabled";
	// The property identifier for the M3G compositingmode depthWriteEnabled field.
	private static final String PROPERTY_COMPOSITINGMODE_DEPTHWRITEENABLED = "com.wizzer.m3g.viewer.ui.compositingmode.depthwriteenabled";
	// The property identifier for the M3G compositingmode colorWriteEnabled field.
	private static final String PROPERTY_COMPOSITINGMODE_COLORWRITEENABLED = "com.wizzer.m3g.viewer.ui.compositingmode.colorwriteenabled";
	// The property identifier for the M3G compositingmode alphaWriteEnabled field.
	private static final String PROPERTY_COMPOSITINGMODE_ALPHAWRITEENABLED = "com.wizzer.m3g.viewer.ui.compositingmode.alphawriteenabled";
	// The property identifier for the M3G compositingmode blending field.
	private static final String PROPERTY_COMPOSITINGMODE_BLENDING = "com.wizzer.m3g.viewer.ui.compositingmode.blending";
	// The property identifier for the M3G compositingmode alphaThreshold field.
	private static final String PROPERTY_COMPOSITINGMODE_ALPHATHRESHOLD = "com.wizzer.m3g.viewer.ui.compositingmode.alphaThreshold";
	// The property identifier for the M3G compositingmode depthOffsetFactor field.
	private static final String PROPERTY_COMPOSITINGMODE_DEPTHOFFSETFACTOR = "com.wizzer.m3g.viewer.ui.compositingmode.depthoffsetfactor";
	// The property identifier for the M3G compositingmode depthOffsetUnits field.
	private static final String PROPERTY_COMPOSITINGMODE_DEPTHOFFSETUNITS= "com.wizzer.m3g.viewer.ui.compositingmode.depthoffsetunits";
	
	// The associated M3G object.
	private CompositingMode m_compositingMode;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private CompositingModePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param compositingMode The associated M3G CompositingMode for this property.
	 */
	public CompositingModePropertySource(CompositingMode compositingMode)
	{
		m_compositingMode = compositingMode;
	}
	
	/**
	 * Get the associated CompositingMode data.
	 * 
	 * @return A <code>CompositingMode</code> is returned.
	 */
	public CompositingMode getCamera()
	{
		return m_compositingMode;
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
			PropertyDescriptor depthTestEnabledDescr = new PropertyDescriptor(
				PROPERTY_COMPOSITINGMODE_DEPTHTESTENABLED, "Depth Test Enabled");
			PropertyDescriptor depthWriteEnabledDescr = new PropertyDescriptor(
				PROPERTY_COMPOSITINGMODE_DEPTHWRITEENABLED, "Depth Write Enabled");
			PropertyDescriptor colorWriteEnabledDescr = new PropertyDescriptor(
				PROPERTY_COMPOSITINGMODE_COLORWRITEENABLED, "Color Write Enabled");
			PropertyDescriptor alphaWriteEnabledDescr = new PropertyDescriptor(
				PROPERTY_COMPOSITINGMODE_ALPHAWRITEENABLED, "Alpha Write Enabled");
			PropertyDescriptor blendingDescr = new PropertyDescriptor(
				PROPERTY_COMPOSITINGMODE_BLENDING, "Blending");
			PropertyDescriptor alphaThresholdDescr = new PropertyDescriptor(
				PROPERTY_COMPOSITINGMODE_ALPHATHRESHOLD, "Alpha Threshold");
			PropertyDescriptor depthOffsetFactorDescr = new PropertyDescriptor(
					PROPERTY_COMPOSITINGMODE_DEPTHOFFSETFACTOR, "Depth Offset Factor");
			PropertyDescriptor depthOffsetUnitsDescr = new PropertyDescriptor(
					PROPERTY_COMPOSITINGMODE_DEPTHOFFSETUNITS, "Depth Offset Units");
			
			m_descriptors = new IPropertyDescriptor[] {
				depthTestEnabledDescr,
				depthWriteEnabledDescr,
				colorWriteEnabledDescr,
				alphaWriteEnabledDescr,
				blendingDescr,
				alphaThresholdDescr,
				depthOffsetFactorDescr,
				depthOffsetUnitsDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_COMPOSITINGMODE_DEPTHTESTENABLED))
		{
			return m_compositingMode.isDepthTestEnabled();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_DEPTHWRITEENABLED))
		{
			return m_compositingMode.isDepthWriteEnabled();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_COLORWRITEENABLED))
		{
			return m_compositingMode.isColorWriteEnabled();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_ALPHAWRITEENABLED))
		{
			return m_compositingMode.isAlphaWriteEnabled();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_BLENDING))
		{
			return m_compositingMode.getBlending();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_ALPHATHRESHOLD))
		{
			return m_compositingMode.getAlphaThreshold();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_DEPTHOFFSETFACTOR))
		{
			return m_compositingMode.getDepthOffsetFactor();
		} else if (id.equals(PROPERTY_COMPOSITINGMODE_DEPTHOFFSETUNITS))
		{
			return m_compositingMode.getDepthOffsetUnits();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_COMPOSITINGMODE_DEPTHTESTENABLED.equals(id) ||
			PROPERTY_COMPOSITINGMODE_DEPTHWRITEENABLED.equals(id) ||
			PROPERTY_COMPOSITINGMODE_COLORWRITEENABLED.equals(id) ||
			PROPERTY_COMPOSITINGMODE_ALPHAWRITEENABLED.equals(id) ||
			PROPERTY_COMPOSITINGMODE_BLENDING.equals(id) ||
			PROPERTY_COMPOSITINGMODE_ALPHATHRESHOLD.equals(id) ||
			PROPERTY_COMPOSITINGMODE_DEPTHOFFSETFACTOR.equals(id) ||
			PROPERTY_COMPOSITINGMODE_DEPTHOFFSETUNITS.equals(id))
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
