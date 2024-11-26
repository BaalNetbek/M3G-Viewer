/*
 * BackgroundPropertySource.java
 * Created on Jun 19, 2008
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

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Background;

/**
 * This class is a Property Source for a M3G Background.
 * 
 * @author Mark Millard
 */
public class BackgroundPropertySource implements IPropertySource
{
	// The property identifier for the M3G background color field.
	private static final String PROPERTY_BACKGROUND_COLOR = "com.wizzer.m3g.viewer.ui.background.color";
	// The property identifier for the M3G background image field.
	private static final String PROPERTY_BACKGROUND_IMAGE = "com.wizzer.m3g.viewer.ui.background.image";
	// The property identifier for the M3G background image modex field.
	private static final String PROPERTY_BACKGROUND_IMAGE_MODEX = "com.wizzer.m3g.viewer.ui.background.modex";
	// The property identifier for the M3G background image modey field.
	private static final String PROPERTY_BACKGROUND_IMAGE_MODEY = "com.wizzer.m3g.viewer.ui.background.modey";
	// The property identifier for the M3G background cropx field.
	private static final String PROPERTY_BACKGROUND_CROPX = "com.wizzer.m3g.viewer.ui.background.cropx";
	// The property identifier for the M3G background cropy field.
	private static final String PROPERTY_BACKGROUND_CROPY = "com.wizzer.m3g.viewer.ui.background.cropy";
	// The property identifier for the M3G background cropWidth field.
	private static final String PROPERTY_BACKGROUND_CROPWIDTH = "com.wizzer.m3g.viewer.ui.background.cropwidth";
	// The property identifier for the M3G background cropHeight field.
	private static final String PROPERTY_BACKGROUND_CROPHEIGHT = "com.wizzer.m3g.viewer.ui.background.cropheight";
	// The property identifier for the M3G background depthClearEnabled field.
	private static final String PROPERTY_BACKGROUND_DEPTHCLEARENABLED = "com.wizzer.m3g.viewer.ui.background.depthclearenabled";
	// The property identifier for the M3G background colorClearEnabled field.
	private static final String PROPERTY_BACKGROUND_COLORCLEARENABLED = "com.wizzer.m3g.viewer.ui.background.colorclearenabled";
	
	// The associated M3G object.
	private Background m_background;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private BackgroundPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param background The associated M3G Background for this property.
	 */
	public BackgroundPropertySource(Background background)
	{
		m_background = background;
	}
	
	/**
	 * Get the associated Background data.
	 * 
	 * @return A <code>Background</code> is returned.
	 */
	public Background getSection()
	{
		return m_background;
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
			PropertyDescriptor backgroundColorDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_COLOR, "Background Color");
			PropertyDescriptor backgroundImageDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_IMAGE, "Background Image");
			PropertyDescriptor backgroundImageModexDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_IMAGE_MODEX, "Background Image Mode X");
			PropertyDescriptor backgroundImageModeyDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_IMAGE_MODEY, "Background Image Mode Y");
			PropertyDescriptor cropxDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_CROPX, "Crop x Location");
			PropertyDescriptor cropyDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_CROPY, "Crop y Location");
			PropertyDescriptor cropWidthDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_CROPWIDTH, "Crop Width");
			PropertyDescriptor cropHeightDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_CROPHEIGHT, "Crop Height");
			PropertyDescriptor depthClearEnabledDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_DEPTHCLEARENABLED, "Depth Clear Enabled");
			PropertyDescriptor colorClearEnabledDescr = new PropertyDescriptor(
				PROPERTY_BACKGROUND_COLORCLEARENABLED, "Color Clear Enabled");
			
			m_descriptors = new IPropertyDescriptor[] {
				backgroundColorDescr,
				backgroundImageDescr,
				backgroundImageModexDescr,
				backgroundImageModeyDescr,
				cropxDescr,
				cropyDescr,
				cropWidthDescr,
				cropHeightDescr,
				depthClearEnabledDescr,
				colorClearEnabledDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_BACKGROUND_COLOR))
		{
			int color = m_background.getColor();
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_BACKGROUND_IMAGE))
		{
			// TODO: Display thumbnail of image here
			return m_background.getImage();
		} else if (id.equals(PROPERTY_BACKGROUND_IMAGE_MODEX))
		{
			return m_background.getImageModeX();
		} else if (id.equals(PROPERTY_BACKGROUND_IMAGE_MODEY))
		{
			return m_background.getImageModeY();
		} else if (id.equals(PROPERTY_BACKGROUND_CROPX))
		{
			return m_background.getCropX();
		} else if (id.equals(PROPERTY_BACKGROUND_CROPY))
		{
			return m_background.getCropY();
		} else if (id.equals(PROPERTY_BACKGROUND_CROPWIDTH))
		{
			return m_background.getCropWidth();
		} else if (id.equals(PROPERTY_BACKGROUND_CROPHEIGHT))
		{
			return m_background.getCropHeight();
		} else if (id.equals(PROPERTY_BACKGROUND_DEPTHCLEARENABLED))
		{
			return m_background.isDepthClearEnabled();
		} else if (id.equals(PROPERTY_BACKGROUND_COLORCLEARENABLED))
		{
			return m_background.isColorClearEnabled();
		} else
			return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_BACKGROUND_COLOR.equals(id) ||
			PROPERTY_BACKGROUND_IMAGE.equals(id) ||
			PROPERTY_BACKGROUND_IMAGE_MODEX.equals(id) ||
			PROPERTY_BACKGROUND_IMAGE_MODEY.equals(id) ||
			PROPERTY_BACKGROUND_CROPX.equals(id) ||
			PROPERTY_BACKGROUND_CROPY.equals(id) ||
			PROPERTY_BACKGROUND_CROPWIDTH.equals(id) ||
			PROPERTY_BACKGROUND_CROPHEIGHT.equals(id) ||
			PROPERTY_BACKGROUND_DEPTHCLEARENABLED.equals(id) ||
			PROPERTY_BACKGROUND_COLORCLEARENABLED.equals(id))
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
