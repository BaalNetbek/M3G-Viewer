/*
 * HeaderPropertySource.java
 * Created on Jun 13, 2008
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
import com.wizzer.m3g.HeaderObject;
import com.wizzer.m3g.toolkit.util.Unsigned;

/**
 * This class is a Property Source for a M3G HeaderObject.
 * 
 * @author Mark Millard
 */
public class HeaderPropertySource implements IPropertySource
{
	// The property identifier for the M3G header VersionNumber[0] (major) field.
	private static final String PROPERTY_VERSION_MAJOR = "com.wizzer.m3g.viewer.ui.header.versionmajor";
	// The property identifier for the M3G header VersionNumber[1] (minor) field.
	private static final String PROPERTY_VERSION_MINOR = "com.wizzer.m3g.viewer.ui.header.versionminor";
	// The property identifier for the M3G header hasExternalReferences field.
	private static final String PROPERTY_HASEXTERNALREFERENCES = "com.wizzer.m3g.viewer.ui.header.hasexternalreferences";
	// The property identifier for the M3G header TotalFileSize field.
	private static final String PROPERTY_TOTALFILESIZE = "com.wizzer.m3g.viewer.ui.header.totalfilesize";
	// The property identifier for the M3G header ApproximateContentSize field.
	private static final String PROPERTY_APPROXIMATECONTENTSIZE = "com.wizzer.m3g.viewer.ui.header.approximatecontentsize";
	// The property identifier for the M3G header AuthoringField field.
	private static final String PROPERTY_AUTHORINGFIELD = "com.wizzer.m3g.viewer.ui.header.authoringfield";
	
	// The associated M3G object.
	private HeaderObject m_header;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private HeaderPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param header The associated M3G HeaderObject for this property.
	 */
	public HeaderPropertySource(HeaderObject header)
	{
		m_header = header;
	}
	
	/**
	 * Get the associated Header data.
	 * 
	 * @return A <code>HeaderObject</code> is returned.
	 */
	public HeaderObject getHeader()
	{
		return m_header;
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
			PropertyDescriptor versionMajorDescr = new PropertyDescriptor(
				PROPERTY_VERSION_MAJOR, "Version Major Number");
			PropertyDescriptor versionMinorDescr = new PropertyDescriptor(
				PROPERTY_VERSION_MINOR, "Version Minor Number");
			PropertyDescriptor hasExternalReferencesDescr = new PropertyDescriptor(
				PROPERTY_HASEXTERNALREFERENCES, "Has External References");
			PropertyDescriptor totalFileSizeDescr = new PropertyDescriptor(
				PROPERTY_TOTALFILESIZE, "Total File Size");
			PropertyDescriptor approximateContentSizeDescr = new PropertyDescriptor(
					PROPERTY_APPROXIMATECONTENTSIZE, "Approximate Content Size");
			PropertyDescriptor auhtoringFieldDescr = new PropertyDescriptor(
					PROPERTY_AUTHORINGFIELD, "Authoring Field");
			
			m_descriptors = new IPropertyDescriptor[] {
				versionMajorDescr,
				versionMinorDescr,
				hasExternalReferencesDescr,
				totalFileSizeDescr,
				approximateContentSizeDescr,
				auhtoringFieldDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_VERSION_MAJOR))
		{
			return m_header.getVersionMajor();
		} else if (id.equals(PROPERTY_VERSION_MINOR))
		{
			return m_header.getVersionMinor();
		} else if (id.equals(PROPERTY_HASEXTERNALREFERENCES))
		{
			return m_header.isHasExternalReferences();
		} else if (id.equals(PROPERTY_TOTALFILESIZE))
		{
			byte[] data = intToByteArray((int)m_header.getTotalFileSize());
			long value = Unsigned.readDWORD(data,0);
			return value;
		} else if (id.equals(PROPERTY_APPROXIMATECONTENTSIZE))
		{
			byte[] data = intToByteArray((int)m_header.getApproximateContentSize());
			long value = Unsigned.readDWORD(data,0);
			return value;
		} else if (id.equals(PROPERTY_AUTHORINGFIELD))
		{
			return m_header.getAuthoringField();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_VERSION_MAJOR.equals(id) ||
			PROPERTY_VERSION_MINOR.equals(id) ||
			PROPERTY_HASEXTERNALREFERENCES.equals(id) ||
			PROPERTY_TOTALFILESIZE.equals(id) ||
			PROPERTY_APPROXIMATECONTENTSIZE.equals(id) ||
			PROPERTY_AUTHORINGFIELD.equals(id))
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

	/*
	 * Returns a byte array containing the two's-complement representation of the integer.<br>
	 * The byte array will be in big-endian byte-order with a fixes length of 4
	 * (the least significant byte is in the 4th element).<br>
	 * <br>
	 * <b>Example:</b><br>
	 * <code>intToByteArray(258)</code> will return { 0, 0, 1, 2 },<br>
	 * <code>BigInteger.valueOf(258).toByteArray()</code> returns { 1, 2 }. 
	 * @param integer The integer to be converted.
	 * @return The byte array of length 4.
	 */
	private byte[] intToByteArray (final int integer)
	{
		int byteNum = (40 - Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer)) / 8;
		byte[] byteArray = new byte[4];
		
		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));
		
		return (byteArray);
	}
}
