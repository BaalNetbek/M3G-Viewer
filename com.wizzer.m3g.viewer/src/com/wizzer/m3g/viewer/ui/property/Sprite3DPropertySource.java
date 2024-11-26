/*
 * Sprite3DPropertySource.java
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

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Sprite3D;

/**
 * This class is a Property Source for a M3G Sprite3D.
 * 
 * @author Mark Millard
 */
public class Sprite3DPropertySource implements IPropertySource
{
	// The property identifier for the M3G sprite3d image field.
	private static final String PROPERTY_SPRITE3D_IMAGE = "com.wizzer.m3g.viewer.ui.sprite3d.image";
	// The property identifier for the M3G sprite3d appearance field.
	private static final String PROPERTY_SPRITE3D_APPEARANCE = "com.wizzer.m3g.viewer.ui.sprite3d.appearance";
	// The property identifier for the M3G sprite3d isScaled field.
	private static final String PROPERTY_SPRITE3D_ISSCALED = "com.wizzer.m3g.viewer.ui.sprite3d.isscaled";
	// The property identifier for the M3G sprite3d cropx field.
	private static final String PROPERTY_SPRITE3D_CROPX = "com.wizzer.m3g.viewer.ui.sprite3d.cropx";
	// The property identifier for the M3G sprite3d cropy field.
	private static final String PROPERTY_SPRITE3D_CROPY = "com.wizzer.m3g.viewer.ui.sprite3d.cropy";
	// The property identifier for the M3G sprite3d cropWidth field.
	private static final String PROPERTY_SPRITE3D_CROPWIDTH = "com.wizzer.m3g.viewer.ui.sprite3d.cropwidth";
	// The property identifier for the M3G sprite3d cropHeight field.
	private static final String PROPERTY_SPRITE3D_CROPHEIGHT = "com.wizzer.m3g.viewer.ui.sprite3d.cropheight";
	
	// The associated M3G object.
	private Sprite3D m_sprite;
	// The image field property source.
	private Image2DPropertySource m_imagePropertySource;
	// The appearance property source.
	private AppearancePropertySource m_appearancePropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private Sprite3DPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param sprite3d The associated M3G Sprite3D for this property.
	 */
	public Sprite3DPropertySource(Sprite3D sprite3d)
	{
		m_sprite = sprite3d;
		m_imagePropertySource = new Image2DPropertySource(m_sprite.getImage());
		m_appearancePropertySource = new AppearancePropertySource(m_sprite.getAppearance());
	}
	
	/**
	 * Get the associated Sprite3D data.
	 * 
	 * @return A <code>Sprite3D</code> is returned.
	 */
	public Sprite3D getSprite3D()
	{
		return m_sprite;
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
			PropertyDescriptor imageDescr = new Image2DPropertyDescriptor(
				PROPERTY_SPRITE3D_IMAGE, "Image");
			PropertyDescriptor appearanceDescr = new AppearancePropertyDescriptor(
				PROPERTY_SPRITE3D_APPEARANCE, "Appearance");
			PropertyDescriptor isScaledDescr = new PropertyDescriptor(
				PROPERTY_SPRITE3D_ISSCALED, "Is Scaled");
			PropertyDescriptor cropXDescr = new PropertyDescriptor(
				PROPERTY_SPRITE3D_CROPX, "Crop X");
			PropertyDescriptor cropYDescr = new PropertyDescriptor(
				PROPERTY_SPRITE3D_CROPY, "Crop Y");
			PropertyDescriptor cropWidthDescr = new PropertyDescriptor(
				PROPERTY_SPRITE3D_CROPWIDTH, "Crop Width");
			PropertyDescriptor cropHeightDescr = new PropertyDescriptor(
				PROPERTY_SPRITE3D_CROPHEIGHT, "Crop Height");
				
			m_descriptors = new IPropertyDescriptor[] {
				imageDescr,
				appearanceDescr,
				isScaledDescr,
				cropXDescr,
				cropYDescr,
				cropWidthDescr,
				cropHeightDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_SPRITE3D_APPEARANCE))
		{
			return m_appearancePropertySource;
		} else if (id.equals(PROPERTY_SPRITE3D_IMAGE))
		{
			return m_imagePropertySource;
		} else if (id.equals(PROPERTY_SPRITE3D_ISSCALED))
		{
			return m_sprite.isScaled();
		} else if (id.equals(PROPERTY_SPRITE3D_CROPY))
		{
			return m_sprite.getCropY();
		} else if (id.equals(PROPERTY_SPRITE3D_CROPX))
		{
			return m_sprite.getCropX();
		} else if (id.equals(PROPERTY_SPRITE3D_CROPHEIGHT))
		{
			return m_sprite.getCropHeight();
		} else if (id.equals(PROPERTY_SPRITE3D_CROPWIDTH))
		{
			return m_sprite.getCropWidth();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_SPRITE3D_APPEARANCE.equals(id) ||
			PROPERTY_SPRITE3D_IMAGE.equals(id) ||
			PROPERTY_SPRITE3D_ISSCALED.equals(id) ||
			PROPERTY_SPRITE3D_CROPY.equals(id) ||
			PROPERTY_SPRITE3D_CROPX.equals(id) ||
			PROPERTY_SPRITE3D_CROPWIDTH.equals(id) ||
			PROPERTY_SPRITE3D_CROPHEIGHT.equals(id))
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
