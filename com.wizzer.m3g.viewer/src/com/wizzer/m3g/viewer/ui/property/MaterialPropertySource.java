/*
 * MaterialPropertySource.java
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
import com.wizzer.m3g.Material;

/**
 * This class is a Property Source for a M3G Material.
 * 
 * @author Mark Millard
 */
public class MaterialPropertySource implements IPropertySource
{
	// The property identifier for the M3G material ambientColor field.
	private static final String PROPERTY_MATERIAL_AMBIENTCOLOR = "com.wizzer.m3g.viewer.ui.material.ambientColor";
	// The property identifier for the M3G material diffuseColor field.
	private static final String PROPERTY_MATERIAL_DIFFUSECOLOR = "com.wizzer.m3g.viewer.ui.material.diffuseColor";
	// The property identifier for the M3G material emmisiveColor field.
	private static final String PROPERTY_MATERIAL_EMISSIVECOLOR = "com.wizzer.m3g.viewer.ui.material.emmisiveColor";
	// The property identifier for the M3G material specularColor field.
	private static final String PROPERTY_MATERIAL_SPECULARCOLOR = "com.wizzer.m3g.viewer.ui.material.specularColor";
	// The property identifier for the M3G material shininess field.
	private static final String PROPERTY_MATERIAL_SHININESS = "com.wizzer.m3g.viewer.ui.material.shininess";
	// The property identifier for the M3G material vertexColorTrackingEnabled field.
	private static final String PROPERTY_MATERIAL_VERTEXCOLORTRACKINGENABLED = "com.wizzer.m3g.viewer.ui.material.vertexcolortrackingenabled";
	
	// The associated M3G object.
	private Material m_material;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private MaterialPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param material The associated M3G Material for this property.
	 */
	public MaterialPropertySource(Material material)
	{
		m_material = material;
	}
	
	/**
	 * Get the associated Material data.
	 * 
	 * @return A <code>Material</code> is returned.
	 */
	public Material getCamera()
	{
		return m_material;
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
			PropertyDescriptor ambientColorDescr = new PropertyDescriptor(
				PROPERTY_MATERIAL_AMBIENTCOLOR, "Ambient Color");
			PropertyDescriptor diffuseColorDescr = new PropertyDescriptor(
				PROPERTY_MATERIAL_DIFFUSECOLOR, "Diffuse Color");
			PropertyDescriptor emissiveColorDescr = new PropertyDescriptor(
				PROPERTY_MATERIAL_EMISSIVECOLOR, "Emissive Color");
			PropertyDescriptor specularColorDescr = new PropertyDescriptor(
				PROPERTY_MATERIAL_SPECULARCOLOR, "Specular Color");
			PropertyDescriptor shininessDescr = new PropertyDescriptor(
				PROPERTY_MATERIAL_SHININESS, "Shininess");
			PropertyDescriptor vertexColorTrackingEnabledDescr = new PropertyDescriptor(
				PROPERTY_MATERIAL_VERTEXCOLORTRACKINGENABLED, "Vertex Tracking Color Enabled");
			
			m_descriptors = new IPropertyDescriptor[] {
				ambientColorDescr,
				diffuseColorDescr,
				emissiveColorDescr,
				specularColorDescr,
				shininessDescr,
				vertexColorTrackingEnabledDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_MATERIAL_VERTEXCOLORTRACKINGENABLED))
		{
			return m_material.isVertexColorTrackingEnabled();
		} else if (id.equals(PROPERTY_MATERIAL_SHININESS))
		{
			return m_material.getShininess();
		} else if (id.equals(PROPERTY_MATERIAL_AMBIENTCOLOR))
		{
			int color = m_material.getColor(Material.AMBIENT);
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_MATERIAL_DIFFUSECOLOR))
		{
			int color = m_material.getColor(Material.DIFFUSE);
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_MATERIAL_EMISSIVECOLOR))
		{
			int color = m_material.getColor(Material.EMISSIVE);
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_MATERIAL_SPECULARCOLOR))
		{
			int color = m_material.getColor(Material.SPECULAR);
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_MATERIAL_VERTEXCOLORTRACKINGENABLED.equals(id) ||
			PROPERTY_MATERIAL_SHININESS.equals(id) ||
			PROPERTY_MATERIAL_AMBIENTCOLOR.equals(id) ||
			PROPERTY_MATERIAL_DIFFUSECOLOR.equals(id) ||
			PROPERTY_MATERIAL_EMISSIVECOLOR.equals(id) ||
			PROPERTY_MATERIAL_SPECULARCOLOR.equals(id))
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
