/*
 * SectionPropertySource.java
 * Created on Jun 6, 2008
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
import com.wizzer.m3g.Section;
import com.wizzer.m3g.toolkit.util.Unsigned;

/**
 * This class is a Property Source for a M3G Section.
 * 
 * @author Mark Millard
 */
public class SectionPropertySource implements IPropertySource
{
	// The property identifier for the M3G section compression scheme field.
	private static final String PROPERTY_COMPRESSION_SCHEME = "com.wizzer.m3g.viewer.ui.section.compressionscheme";
	// The property identifier for the M3G section todal section length field.
	private static final String PROPERTY_TOTAL_SECTION_LENGTH = "com.wizzer.m3g.viewer.ui.section.totalsectionlength";
	// The property identifier for the M3G section uncompressed length field.
	private static final String PROPERTY_UNCOMPRESSED_LENGTH = "com.wizzer.m3g.viewer.ui.section.uncompressedlength";
	// The property identifier for the M3G section checksum field.
	private static final String PROPERTY_CHECKSUM = "com.wizzer.m3g.viewer.ui.section.checksum";
	
	// The associated M3G object.
	private Section m_section;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private SectionPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param section The associated M3G Section for this property.
	 */
	public SectionPropertySource(Section section)
	{
		m_section = section;
	}
	
	/**
	 * Get the associated Section data.
	 * 
	 * @return A <code>Section</code> is returned.
	 */
	public Section getSection()
	{
		return m_section;
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
			PropertyDescriptor compressionSchemeDescr = new PropertyDescriptor(
				PROPERTY_COMPRESSION_SCHEME, "Compression Scheme");
			PropertyDescriptor totalSectionLengthDescr = new PropertyDescriptor(
				PROPERTY_TOTAL_SECTION_LENGTH, "Total Section Length");
			PropertyDescriptor uncompressedLengthDescr = new PropertyDescriptor(
				PROPERTY_UNCOMPRESSED_LENGTH, "Uncompressed Length");
			PropertyDescriptor checksumDescr = new PropertyDescriptor(
				PROPERTY_CHECKSUM, "Checksum");
			
			m_descriptors = new IPropertyDescriptor[] {
				compressionSchemeDescr,
				totalSectionLengthDescr,
				uncompressedLengthDescr,
				checksumDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_COMPRESSION_SCHEME))
		{
			return m_section.getCompressionScheme();
		} else if (id.equals(PROPERTY_TOTAL_SECTION_LENGTH))
		{
			byte[] data = intToByteArray(m_section.getTotalSectionLength());
			long value = Unsigned.readDWORD(data,0);
			return value;
		} else if (id.equals(PROPERTY_UNCOMPRESSED_LENGTH))
		{
			byte[] data = intToByteArray(m_section.getUncompressedLength());
			long value = Unsigned.readDWORD(data,0);
			return value;
		} else if (id.equals(PROPERTY_CHECKSUM))
		{
			byte[] data = intToByteArray(m_section.getChecksum());
			long value = Unsigned.readDWORD(data,0);
			return new String("0x" + value);
		} else
			return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_COMPRESSION_SCHEME.equals(id) ||
		    PROPERTY_TOTAL_SECTION_LENGTH.equals(id) ||
		    PROPERTY_UNCOMPRESSED_LENGTH.equals(id) ||
		    PROPERTY_CHECKSUM.equals(id))
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
