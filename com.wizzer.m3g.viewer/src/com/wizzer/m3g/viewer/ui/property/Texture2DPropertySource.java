/*
 * Texture2DPropertySource.java
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
import com.wizzer.m3g.Texture2D;

/**
 * This class is a Property Source for a M3G Texture2D.
 * 
 * @author Mark Millard
 */
public class Texture2DPropertySource implements IPropertySource
{
	// The property identifier for the M3G compositingmode image field.
	private static final String PROPERTY_TEXTURE2D_IMAGE = "com.wizzer.m3g.viewer.ui.compositingmode.image";
	// The property identifier for the M3G compositingmode blendColor field.
	private static final String PROPERTY_TEXTURE2D_BLENDCOLOR = "com.wizzer.m3g.viewer.ui.compositingmode.blendcolor";
	// The property identifier for the M3G compositingmode blending field.
	private static final String PROPERTY_TEXTURE2D_BLENDING = "com.wizzer.m3g.viewer.ui.compositingmode.blending";
	// The property identifier for the M3G compositingmode wrappings field.
	private static final String PROPERTY_TEXTURE2D_WRAPPINGS = "com.wizzer.m3g.viewer.ui.compositingmode.wrappings";
	// The property identifier for the M3G compositingmode wrappingT field.
	private static final String PROPERTY_TEXTURE2D_WRAPPINGT = "com.wizzer.m3g.viewer.ui.compositingmode.wrappingt";
	// The property identifier for the M3G compositingmode levelFilter field.
	private static final String PROPERTY_TEXTURE2D_LEVELFILTER = "com.wizzer.m3g.viewer.ui.compositingmode.levelfilter";
	// The property identifier for the M3G compositingmode imageFilter field.
	private static final String PROPERTY_TEXTURE2D_IMAGEFILTER= "com.wizzer.m3g.viewer.ui.compositingmode.imagefilter";
	
	// The associated M3G object.
	private Texture2D m_texture2D;
	// The Image2D property source.
	private Image2DPropertySource m_image2DPropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private Texture2DPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param texture2D The associated M3G Texture2D for this property.
	 */
	public Texture2DPropertySource(Texture2D texture2D)
	{
		m_texture2D = texture2D;
		m_image2DPropertySource = new Image2DPropertySource(m_texture2D.getImage());
	}
	
	/**
	 * Get the associated Texture2D data.
	 * 
	 * @return A <code>Texture2D</code> is returned.
	 */
	public Texture2D getCamera()
	{
		return m_texture2D;
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
				PROPERTY_TEXTURE2D_IMAGE, "Image");
			PropertyDescriptor blendColorDescr = new PropertyDescriptor(
				PROPERTY_TEXTURE2D_BLENDCOLOR, "Blend Color");
			PropertyDescriptor blendingDescr = new PropertyDescriptor(
				PROPERTY_TEXTURE2D_BLENDING, "Blending");
			PropertyDescriptor wrappingSDescr = new PropertyDescriptor(
				PROPERTY_TEXTURE2D_WRAPPINGS, "Wrapping S");
			PropertyDescriptor wrappingTDescr = new PropertyDescriptor(
				PROPERTY_TEXTURE2D_WRAPPINGT, "Wrapping T");
			PropertyDescriptor levelFilterDescr = new PropertyDescriptor(
					PROPERTY_TEXTURE2D_LEVELFILTER, "Level Filter");
			PropertyDescriptor imageFilterDescr = new PropertyDescriptor(
					PROPERTY_TEXTURE2D_IMAGEFILTER, "Image Filter");
			
			m_descriptors = new IPropertyDescriptor[] {
				imageDescr,
				blendColorDescr,
				blendingDescr,
				wrappingSDescr,
				wrappingTDescr,
				levelFilterDescr,
				imageFilterDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_TEXTURE2D_IMAGE))
		{
			return m_image2DPropertySource;
		} else if (id.equals(PROPERTY_TEXTURE2D_BLENDCOLOR))
		{
			int color = m_texture2D.getBlendColor();
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_TEXTURE2D_BLENDING))
		{
			return m_texture2D.getBlending();
		} else if (id.equals(PROPERTY_TEXTURE2D_WRAPPINGS))
		{
			return m_texture2D.getWrappingS();
		} else if (id.equals(PROPERTY_TEXTURE2D_WRAPPINGT))
		{
			return m_texture2D.getWrappingT();
		} else if (id.equals(PROPERTY_TEXTURE2D_LEVELFILTER))
		{
			return m_texture2D.getLevelFilter();
		} else if (id.equals(PROPERTY_TEXTURE2D_IMAGEFILTER))
		{
			return m_texture2D.getImageFilter();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_TEXTURE2D_IMAGE.equals(id) ||
			PROPERTY_TEXTURE2D_BLENDCOLOR.equals(id) ||
			PROPERTY_TEXTURE2D_BLENDING.equals(id) ||
			PROPERTY_TEXTURE2D_WRAPPINGS.equals(id) ||
			PROPERTY_TEXTURE2D_WRAPPINGT.equals(id) ||
			PROPERTY_TEXTURE2D_LEVELFILTER.equals(id) ||
			PROPERTY_TEXTURE2D_IMAGEFILTER.equals(id))
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
