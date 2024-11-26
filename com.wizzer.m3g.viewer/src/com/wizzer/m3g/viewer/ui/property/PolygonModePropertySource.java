/*
 * PolygonModePropertySource.java
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
import com.wizzer.m3g.PolygonMode;

/**
 * This class is a Property Source for a M3G PolygonMode.
 * 
 * @author Mark Millard
 */
public class PolygonModePropertySource implements IPropertySource
{
	// The property identifier for the M3G polygonmode culling field.
	private static final String PROPERTY_POLYGONMODE_CULLING = "com.wizzer.m3g.viewer.ui.polygonmode.culling";
	// The property identifier for the M3G polygonmode shading field.
	private static final String PROPERTY_POLYGONMODE_SHADING = "com.wizzer.m3g.viewer.ui.polygonmode.shading";
	// The property identifier for the M3G polygonmode winding field.
	private static final String PROPERTY_POLYGONMODE_WINDING = "com.wizzer.m3g.viewer.ui.polygonmode.winding";
	// The property identifier for the M3G polygonmode twoSidedLightingEnabled field.
	private static final String PROPERTY_POLYGONMODE_TWOSIDEDLIGHTINGENABLED = "com.wizzer.m3g.viewer.ui.polygonmode.twosidedlightingenabled";
	// The property identifier for the M3G polygonmode localCameraLightingEnabled field.
	private static final String PROPERTY_POLYGONMODE_LOCALCAMERALIGHTINGENABLED = "com.wizzer.m3g.viewer.ui.polygonmode.localcameralightingenabled";
	// The property identifier for the M3G polygonmode perspectiveCorrectionEnabled field.
	private static final String PROPERTY_POLYGONMODE_PERSPECTIVECORRECTIONENABLED = "com.wizzer.m3g.viewer.ui.polygonmode.perspectivecorrectionenabled";
	
	// The associated M3G object.
	private PolygonMode m_polygonMode;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private PolygonModePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param polygonMode The associated M3G PolygonMode for this property.
	 */
	public PolygonModePropertySource(PolygonMode polygonMode)
	{
		m_polygonMode = polygonMode;
	}
	
	/**
	 * Get the associated PolygonMode data.
	 * 
	 * @return A <code>PolygonMode</code> is returned.
	 */
	public PolygonMode getPolygonMode()
	{
		return m_polygonMode;
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
			PropertyDescriptor cullingDescr = new PropertyDescriptor(
				PROPERTY_POLYGONMODE_CULLING, "Culling");
			PropertyDescriptor shadingDescr = new PropertyDescriptor(
				PROPERTY_POLYGONMODE_SHADING, "Shading");
			PropertyDescriptor windingDescr = new PropertyDescriptor(
				PROPERTY_POLYGONMODE_WINDING, "Winding");
			PropertyDescriptor twoSidedLightingEnabledDescr = new PropertyDescriptor(
				PROPERTY_POLYGONMODE_TWOSIDEDLIGHTINGENABLED, "Two-Sided Lighting Enabled");
			PropertyDescriptor localCameraLightingDescr = new PropertyDescriptor(
				PROPERTY_POLYGONMODE_LOCALCAMERALIGHTINGENABLED, "Local Camera Lighting Enabled");
			PropertyDescriptor perspectiveCorrectionEnabledDescr = new PropertyDescriptor(
				PROPERTY_POLYGONMODE_PERSPECTIVECORRECTIONENABLED, "Perspective Correction Enabled");
			
			m_descriptors = new IPropertyDescriptor[] {
				cullingDescr,
				shadingDescr,
				windingDescr,
				twoSidedLightingEnabledDescr,
				localCameraLightingDescr,
				perspectiveCorrectionEnabledDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_POLYGONMODE_PERSPECTIVECORRECTIONENABLED))
		{
			return m_polygonMode.isPerspectiveCorrectionEnabled();
		} else if (id.equals(PROPERTY_POLYGONMODE_LOCALCAMERALIGHTINGENABLED))
		{
			return m_polygonMode.isLocalCameraLightingEnabled();
		} else if (id.equals(PROPERTY_POLYGONMODE_CULLING))
		{
			return m_polygonMode.getCulling();
		} else if (id.equals(PROPERTY_POLYGONMODE_SHADING))
		{
			return m_polygonMode.getShading();
		} else if (id.equals(PROPERTY_POLYGONMODE_WINDING))
		{
			return m_polygonMode.getWinding();
		} else if (id.equals(PROPERTY_POLYGONMODE_TWOSIDEDLIGHTINGENABLED))
		{
			return m_polygonMode.isTwoSidedLightingEnabled();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_POLYGONMODE_PERSPECTIVECORRECTIONENABLED.equals(id) ||
			PROPERTY_POLYGONMODE_LOCALCAMERALIGHTINGENABLED.equals(id) ||
			PROPERTY_POLYGONMODE_CULLING.equals(id) ||
			PROPERTY_POLYGONMODE_SHADING.equals(id) ||
			PROPERTY_POLYGONMODE_WINDING.equals(id) ||
			PROPERTY_POLYGONMODE_TWOSIDEDLIGHTINGENABLED.equals(id))
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
