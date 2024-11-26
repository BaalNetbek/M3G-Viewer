/*
 * VertexBufferPropertySource.java
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
import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.VertexBuffer;

/**
 * This class is a Property Source for a M3G VertexBuffer.
 * 
 * @author Mark Millard
 */
public class VertexBufferPropertySource implements IPropertySource
{
	// The property identifier for the M3G vertex buffer defaultColor field.
	private static final String PROPERTY_VERTEXBUFFER_DEFAULTCOLOR = "com.wizzer.m3g.viewer.ui.vertexbuffer.defaultcolor";
	// The property identifier for the M3G vertex positions field.
	private static final String PROPERTY_VERTEXBUFFER_POSITIONS = "com.wizzer.m3g.viewer.ui.vertexbuffer.positions";
	// The property identifier for the M3G vertex buffer positionBias field.
	private static final String PROPERTY_VERTEXBUFFER_POSITIONBIAS = "com.wizzer.m3g.viewer.ui.vertexbuffer.positionbias";
	// The property identifier for the M3G vertex buffer positionScale field.
	private static final String PROPERTY_VERTEXBUFFER_POSITIONSCALE = "com.wizzer.m3g.viewer.ui.vertexbuffer.positionscale";
	// The property identifier for the M3G vertex buffer normals field.
	private static final String PROPERTY_VERTEXBUFFER_NORMALS = "com.wizzer.m3g.viewer.ui.vertexbuffer.normals";
	// The property identifier for the M3G vertex buffer colors field.
	private static final String PROPERTY_VERTEXBUFFER_COLORS = "com.wizzer.m3g.viewer.ui.vertexbuffer.colors";
	// The property identifier for the M3G vertex buffer texCoordArrayCount field.
	private static final String PROPERTY_VERTEXBUFFER_TEXCOORDARRAYCOUNT = "com.wizzer.m3g.viewer.ui.vertexbuffer.texcoordarraycount";
	// The property identifier for the M3G vertex buffer texCoords field.
	private static final String PROPERTY_VERTEXBUFFER_TEXCOORDS = "com.wizzer.m3g.viewer.ui.vertexbuffer.texcoords";
	// The property identifier for the M3G vertex buffer texCoordBias field.
	private static final String PROPERTY_VERTEXBUFFER_TEXCOORDBIAS = "com.wizzer.m3g.viewer.ui.vertexbuffer.texcoordBias";
	// The property identifier for the M3G vertex buffer texCoordScale field.
	private static final String PROPERTY_VERTEXBUFFER_TEXCOORDSCALE = "com.wizzer.m3g.viewer.ui.vertexbuffer.texcoordScale";
	
	// The associated M3G object.
	private VertexBuffer m_vertexBuffer;
	// The Property Source for the M3G positions VertexArray.
	private VertexArrayPropertySource m_positionsPropertySource;
	// The Property Source for the M3G colors VertexArray.
	private VertexArrayPropertySource m_colorsPropertySource;
	// The Property Source for the M3G normals VertexArray.
	private VertexArrayPropertySource m_normalsPropertySource;
	// The Property Source for the M3G textures VertexArray.
	private VertexArrayPropertySource[] m_texturesPropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private VertexBufferPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param buffer The associated M3G VertexBuffer for this property.
	 */
	public VertexBufferPropertySource(VertexBuffer buffer)
	{
		m_vertexBuffer = buffer;
		
		float[] scaleBias = new float[4];
		m_positionsPropertySource = new VertexArrayPropertySource(
			m_vertexBuffer.getPositions(scaleBias));
		m_normalsPropertySource = new VertexArrayPropertySource(
			m_vertexBuffer.getNormals());
		m_colorsPropertySource = new VertexArrayPropertySource(
			m_vertexBuffer.getColors());
		m_texturesPropertySource = new VertexArrayPropertySource[m_vertexBuffer.getTexcoordArrayCount()];
		for (int i = 0; i < m_vertexBuffer.getTexcoordArrayCount(); i++)
		{
			m_texturesPropertySource[i] = new VertexArrayPropertySource(
				m_vertexBuffer.getTexCoords(i, scaleBias));
		}
	}
	
	/**
	 * Get the associated VertexBuffer data.
	 * 
	 * @return A <code>VertexBuffer</code> is returned.
	 */
	public VertexBuffer getVertexBuffer()
	{
		return m_vertexBuffer;
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
			Vector<PropertyDescriptor> descriptors = new Vector<PropertyDescriptor>();
			
			PropertyDescriptor defaultColorDescr = new PropertyDescriptor(
				PROPERTY_VERTEXBUFFER_DEFAULTCOLOR, "Default Color");
			descriptors.add(defaultColorDescr);
			PropertyDescriptor positionsDescr = new VertexArrayPropertyDescriptor(
				PROPERTY_VERTEXBUFFER_POSITIONS, "Positions");
			descriptors.add(positionsDescr);
			PropertyDescriptor positionBiasDescr = new PropertyDescriptor(
				PROPERTY_VERTEXBUFFER_POSITIONBIAS, "Position Bias");
			descriptors.add(positionBiasDescr);
			PropertyDescriptor postionScaleDescr = new PropertyDescriptor(
				PROPERTY_VERTEXBUFFER_POSITIONSCALE, "Position Scale");
			descriptors.add(postionScaleDescr);
			PropertyDescriptor normalsDescr = new VertexArrayPropertyDescriptor(
				PROPERTY_VERTEXBUFFER_NORMALS, "Normals");
			descriptors.add(normalsDescr);
			PropertyDescriptor colorsDescr = new VertexArrayPropertyDescriptor(
				PROPERTY_VERTEXBUFFER_COLORS, "Colors");
			descriptors.add(colorsDescr);
			PropertyDescriptor texCoordArrayCountDescr = new PropertyDescriptor(
				PROPERTY_VERTEXBUFFER_TEXCOORDARRAYCOUNT, "Number of Texture Coordinates");
			descriptors.add(texCoordArrayCountDescr);
			for (int i = 0; i < m_vertexBuffer.getTexcoordArrayCount(); i++)
			{
				String id = new String(PROPERTY_VERTEXBUFFER_TEXCOORDS + ":" + i);
				PropertyDescriptor texCoordsDescr = new VertexArrayPropertyDescriptor(
					id, "Texture Coordinates");
				descriptors.add(texCoordsDescr);
				id = new String(PROPERTY_VERTEXBUFFER_TEXCOORDBIAS + ":" + i);
				PropertyDescriptor texCoordBiasDescr = new PropertyDescriptor(
					id, "Texture Coordinate Bias");
				descriptors.add(texCoordBiasDescr);
				id = new String(PROPERTY_VERTEXBUFFER_TEXCOORDSCALE + ":" + i);
				PropertyDescriptor texCoordScaleDescr = new PropertyDescriptor(
					id, "Texture Coordinate Scale");
				descriptors.add(texCoordScaleDescr);
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
		if (id.equals(PROPERTY_VERTEXBUFFER_DEFAULTCOLOR))
		{
			int color = m_vertexBuffer.getDefaultColor();
			String value = new String("0x" + Integer.toHexString(color));
			return value;
		} else if (id.equals(PROPERTY_VERTEXBUFFER_POSITIONS))
		{
			return m_positionsPropertySource;
		} else if (id.equals(PROPERTY_VERTEXBUFFER_POSITIONBIAS))
		{
			float[] scaleBias = new float[4];
			m_vertexBuffer.getPositions(scaleBias);
			StringBuffer buffer = new StringBuffer("x = ");
			buffer.append(scaleBias[1]);
			buffer.append(", y = ");
			buffer.append(scaleBias[2]);
			buffer.append(", z = ");
			buffer.append(scaleBias[3]);
			return buffer.toString();
		} else if (id.equals(PROPERTY_VERTEXBUFFER_POSITIONSCALE))
		{
			float[] scaleBias = new float[4];
			m_vertexBuffer.getPositions(scaleBias);
			StringBuffer buffer = new StringBuffer("scale = ");
			buffer.append(scaleBias[0]);
			return buffer.toString();
		} else if (id.equals(PROPERTY_VERTEXBUFFER_NORMALS))
		{
			return m_normalsPropertySource;
		} else if (id.equals(PROPERTY_VERTEXBUFFER_COLORS))
		{
			return m_colorsPropertySource;
		} else if (id.equals(PROPERTY_VERTEXBUFFER_TEXCOORDARRAYCOUNT))
		{
			return m_vertexBuffer.getTexcoordArrayCount();
		} else
		{
			// Check to see if the id is a PROPERTY_VERTEXBUFFER_TEXCOORD.
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				// Only a PROPERTY_VERTEXBUFFER_TEXCOORD identifier should contain a ":" character.
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				if (property.equals(PROPERTY_VERTEXBUFFER_TEXCOORDS))
				{
					return m_texturesPropertySource[keyValue.intValue()];
				} else if (property.equals(PROPERTY_VERTEXBUFFER_TEXCOORDBIAS))
				{
					float[] scaleBias = new float[4];
					m_vertexBuffer.getTexCoords(keyValue.intValue(), scaleBias);
					
					StringBuffer buffer = new StringBuffer("x = ");
					buffer.append(scaleBias[1]);
					buffer.append(", y = ");
					buffer.append(scaleBias[2]);
					buffer.append(", z = ");
					buffer.append(scaleBias[3]);
					return buffer.toString();
				} else if (property.equals(PROPERTY_VERTEXBUFFER_TEXCOORDSCALE))
				{
					float[] scaleBias = new float[4];
					m_vertexBuffer.getTexCoords(keyValue.intValue(), scaleBias);
					
					StringBuffer buffer = new StringBuffer("scale = ");
					buffer.append(scaleBias[0]);
					return buffer.toString();
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
		if (PROPERTY_VERTEXBUFFER_DEFAULTCOLOR.equals(id) ||
			PROPERTY_VERTEXBUFFER_POSITIONS.equals(id) ||
			PROPERTY_VERTEXBUFFER_POSITIONBIAS.equals(id) ||
			PROPERTY_VERTEXBUFFER_POSITIONSCALE.equals(id) ||
			PROPERTY_VERTEXBUFFER_NORMALS.equals(id) ||
			PROPERTY_VERTEXBUFFER_COLORS.equals(id) ||
			PROPERTY_VERTEXBUFFER_TEXCOORDARRAYCOUNT.equals(id))
			return true;
		else
		{
			// Check to see if the id is a PROPERTY_VERTEXBUFFER_TEXCOORD.
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
