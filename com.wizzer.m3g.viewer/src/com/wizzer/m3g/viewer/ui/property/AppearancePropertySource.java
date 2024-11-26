/*
 * AppearancePropertySource.java
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

// Import standard Java classes.
import java.util.Vector;

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Appearance;
import com.wizzer.m3g.Texture2D;

/**
 * This class is a Property Source for a M3G Appearance.
 * 
 * @author Mark Millard
 */
public class AppearancePropertySource implements IPropertySource
{
	// The property identifier for the M3G appearance layer field.
	private static final String PROPERTY_APPEARANCE_LAYER = "com.wizzer.m3g.viewer.ui.appearance.layer";
	// The property identifier for the M3G appearance compositingMode field.
	private static final String PROPERTY_APPEARANCE_COMPOSITINGMODE = "com.wizzer.m3g.viewer.ui.appearance.compositingMode";
	// The property identifier for the M3G appearance fog field.
	private static final String PROPERTY_APPEARANCE_FOG = "com.wizzer.m3g.viewer.ui.appearance.fog";
	// The property identifier for the M3G appearance polygonMode field.
	private static final String PROPERTY_APPEARANCE_POLYGONMODE = "com.wizzer.m3g.viewer.ui.appearance.polygonMode";
	// The property identifier for the M3G appearance material field.
	private static final String PROPERTY_APPEARANCE_MATERIAL = "com.wizzer.m3g.viewer.ui.appearance.material";
	// The property identifier for the M3G appearance textures field.
	private static final String PROPERTY_APPEARANCE_TEXTURE = "com.wizzer.m3g.viewer.ui.appearance.textures";
	
	// The associated M3G object.
	private Appearance m_appearance;
	// The CompositingMode property source.
	private CompositingModePropertySource m_compositingModePropertySource;
	// The Fog property source.
	private FogPropertySource m_fogPropertySource;
	// The PolygonMode property source.
	private PolygonModePropertySource m_polygonModePropertySource;
	// The Material property source.
	private MaterialPropertySource m_materialPropertySource;
	// The Texture2D property sources.
	private Texture2DPropertySource[] m_texture2DPropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private AppearancePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param appearance The associated M3G Appearance for this property.
	 */
	public AppearancePropertySource(Appearance appearance)
	{
		m_appearance = appearance;
		m_compositingModePropertySource = new CompositingModePropertySource(
			m_appearance.getCompositingMode());
		m_fogPropertySource = new FogPropertySource(m_appearance.getFog());
		m_polygonModePropertySource = new PolygonModePropertySource(m_appearance.getPolygonMode());
		m_materialPropertySource = new MaterialPropertySource(m_appearance.getMaterial());
		int count = 0;
		while (m_appearance.getTexture(count) != null)
			count++;
		m_texture2DPropertySource = new Texture2DPropertySource[count];
		for (int i = 0; i < count; i++)
		{
			Texture2D texture = m_appearance.getTexture(i);
			m_texture2DPropertySource[i] = new Texture2DPropertySource(texture);
		}
	}
	
	/**
	 * Get the associated Appearance data.
	 * 
	 * @return A <code>Appearance</code> is returned.
	 */
	public Appearance getAppearance()
	{
		return m_appearance;
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
			PropertyDescriptor layerDescr = new PropertyDescriptor(
				PROPERTY_APPEARANCE_LAYER, "Layer");
			descriptors.add(layerDescr);
			if (m_appearance.getCompositingMode() != null)
			{
				PropertyDescriptor compositingModeDescr = new CompositingModePropertyDescriptor(
					PROPERTY_APPEARANCE_COMPOSITINGMODE, "Compositing Mode");
				descriptors.add(compositingModeDescr);
			}
			if (m_appearance.getFog() != null)
			{
				PropertyDescriptor fogDescr = new FogPropertyDescriptor(
					PROPERTY_APPEARANCE_FOG, "Fog");
				descriptors.add(fogDescr);
			}
			if (m_appearance.getPolygonMode() != null)
			{
				PropertyDescriptor polygonModeDescr = new PolygonModePropertyDescriptor(
					PROPERTY_APPEARANCE_POLYGONMODE, "Polygon Mode");
				descriptors.add(polygonModeDescr);
			}
			if (m_appearance.getMaterial() != null)
			{
				PropertyDescriptor materialDescr = new MaterialPropertyDescriptor(
					PROPERTY_APPEARANCE_MATERIAL, "Material");
				descriptors.add(materialDescr);
			}
			
			int index = 0;
			Texture2D texture = m_appearance.getTexture(index);
			while (texture != null)
			{
				String id = new String(PROPERTY_APPEARANCE_TEXTURE + ":" + index);
				
				PropertyDescriptor texture2DDescr = new Texture2DPropertyDescriptor(
					id, "Texture");
				descriptors.add(texture2DDescr);
				
				// Retrieve the next texture if there is one.
				index++;
				texture = m_appearance.getTexture(index);
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
		if (id.equals(PROPERTY_APPEARANCE_MATERIAL))
		{
			return m_materialPropertySource;
		} else if (id.equals(PROPERTY_APPEARANCE_LAYER))
		{
			return m_appearance.getLayer();
		} else if (id.equals(PROPERTY_APPEARANCE_COMPOSITINGMODE))
		{
			return m_compositingModePropertySource;
		} else if (id.equals(PROPERTY_APPEARANCE_FOG))
		{
			return m_fogPropertySource;
		} else if (id.equals(PROPERTY_APPEARANCE_POLYGONMODE))
		{
			return m_polygonModePropertySource;
		} else
		{
			// Check to see if the id is a PROPERTY_APPEARANCE_TEXTURE.
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				// Only a PROPERTY_APPEARANCE_TEXTURE identifier should contain a ":" character.
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				if (property.equals(PROPERTY_APPEARANCE_TEXTURE))
				{
					return m_texture2DPropertySource[keyValue.intValue()];
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
		if (PROPERTY_APPEARANCE_MATERIAL.equals(id) ||
			PROPERTY_APPEARANCE_LAYER.equals(id) ||
			PROPERTY_APPEARANCE_COMPOSITINGMODE.equals(id) ||
			PROPERTY_APPEARANCE_FOG.equals(id) ||
			PROPERTY_APPEARANCE_POLYGONMODE.equals(id))
			return true;
		else
		{
			// Check to see if the id is a PROPERTY_APPEARANCE_TEXTURE.
			String str = (String)id;
			int index = str.indexOf(":");
			
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
