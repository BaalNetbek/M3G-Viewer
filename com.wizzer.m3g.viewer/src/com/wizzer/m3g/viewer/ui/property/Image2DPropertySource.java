/*
 * Image2DPropertySource.java
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
import com.wizzer.m3g.Image2D;

/**
 * This class is a Property Source for a M3G Image2D.
 * 
 * @author Mark Millard
 */
public class Image2DPropertySource implements IPropertySource
{
	// The property identifier for the M3G image2d format field.
	private static final String PROPERTY_IMAGE2D_FORMAT = "com.wizzer.m3g.viewer.ui.image2d.format";
	// The property identifier for the M3G image2d isMutable field.
	private static final String PROPERTY_IMAGE2D_ISMUTABLE = "com.wizzer.m3g.viewer.ui.image2d.ismutable";
	// The property identifier for the M3G image2d width field.
	private static final String PROPERTY_IMAGE2D_WIDTH = "com.wizzer.m3g.viewer.ui.image2d.width";
	// The property identifier for the M3G image2d height field.
	private static final String PROPERTY_IMAGE2D_HEIGHT= "com.wizzer.m3g.viewer.ui.image2d.height";
	// The property identifier for the M3G image2d palette field.
	private static final String PROPERTY_IMAGE2D_PALETTE = "com.wizzer.m3g.viewer.ui.image2d.palette";
	// The property identifier for the M3G image2d pixels field.
	private static final String PROPERTY_IMAGE2D_PIXELS = "com.wizzer.m3g.viewer.ui.image2d.pixels";
	
	// The associated M3G object.
	private Image2D m_image;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private Image2DPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param image The associated M3G Image2D for this property.
	 */
	public Image2DPropertySource(Image2D image)
	{
		m_image = image;
	}
	
	/**
	 * Get the associated Image2D data.
	 * 
	 * @return A <code>Image2D</code> is returned.
	 */
	public Image2D getImage()
	{
		return m_image;
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
			PropertyDescriptor formatDescr = new PropertyDescriptor(
					PROPERTY_IMAGE2D_FORMAT, "Format");
			PropertyDescriptor mutableDescr = new PropertyDescriptor(
					PROPERTY_IMAGE2D_ISMUTABLE, "Mutable");
			PropertyDescriptor widthDescr = new PropertyDescriptor(
					PROPERTY_IMAGE2D_WIDTH, "Width");
			PropertyDescriptor heightDescr = new PropertyDescriptor(
					PROPERTY_IMAGE2D_HEIGHT, "Height");
			
			boolean mutable = m_image.isMutable();
			if (! mutable)
			{
				m_descriptors = new IPropertyDescriptor[] {
					formatDescr,
					mutableDescr,
					widthDescr,
					heightDescr
				};
			} else
			{
				PropertyDescriptor paletteDescr = new PropertyDescriptor(
					PROPERTY_IMAGE2D_PALETTE, "Palette");
				PropertyDescriptor pixelsDescr = new PropertyDescriptor(
					PROPERTY_IMAGE2D_PIXELS, "Pixels");
				
				m_descriptors = new IPropertyDescriptor[] {
					formatDescr,
					mutableDescr,
					widthDescr,
					heightDescr,
					paletteDescr,
					pixelsDescr
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
		if (id.equals(PROPERTY_IMAGE2D_FORMAT))
		{
			return m_image.getFormat();
		} else if (id.equals(PROPERTY_IMAGE2D_ISMUTABLE))
		{
			return m_image.isMutable();
		} else if (id.equals(PROPERTY_IMAGE2D_WIDTH))
		{
			return m_image.getWidth();
		} else if (id.equals(PROPERTY_IMAGE2D_HEIGHT))
		{
			return m_image.getHeight();
		} else if (id.equals(PROPERTY_IMAGE2D_PALETTE))
		{
			m_image.getImage();
			return new String("TBD");
		} else if (id.equals(PROPERTY_IMAGE2D_PIXELS))
		{
			m_image.getImage();
			return new String("TBD");
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_IMAGE2D_FORMAT.equals(id) ||
			PROPERTY_IMAGE2D_ISMUTABLE.equals(id) ||
			PROPERTY_IMAGE2D_WIDTH.equals(id) ||
			PROPERTY_IMAGE2D_HEIGHT.equals(id) ||
			PROPERTY_IMAGE2D_PALETTE.equals(id) ||
			PROPERTY_IMAGE2D_PIXELS.equals(id))
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
