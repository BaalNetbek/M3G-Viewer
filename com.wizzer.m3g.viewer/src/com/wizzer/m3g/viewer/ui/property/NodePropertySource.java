/*
 * NodePropertySource.java
 * Created on Jul 1, 2008
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
import com.wizzer.m3g.Node;
import com.wizzer.m3g.toolkit.util.Unsigned;

/**
 * This class is a Property Source for a M3G Node.
 * 
 * @author Mark Millard
 */
public class NodePropertySource implements IPropertySource
{
	// The property identifier for the M3G node enableRendering field.
	private static final String PROPERTY_NODE_ENABLERENDERING = "com.wizzer.m3g.viewer.ui.node.enablerendering";
	// The property identifier for the M3G node enablePicking field.
	private static final String PROPERTY_NODE_ENABLEPICKING = "com.wizzer.m3g.viewer.ui.node.enablepicking";
	// The property identifier for the M3G node alphaFactor field.
	private static final String PROPERTY_NODE_ALPHAFACTOR = "com.wizzer.m3g.viewer.ui.node.alphafactor";
	// The property identifier for the M3G node scope field.
	private static final String PROPERTY_NODE_SCOPE = "com.wizzer.m3g.viewer.ui.node.scope";
	// The property identifier for the M3G node hasAlignement field.
	private static final String PROPERTY_NODE_HASALIGNMENT = "com.wizzer.m3g.viewer.ui.node.hasalignment";
	// The property identifier for the M3G node zTarget field.
	private static final String PROPERTY_NODE_ZTARGET = "com.wizzer.m3g.viewer.ui.node.ztarget";
	// The property identifier for the M3G node yTarget field.
	private static final String PROPERTY_NODE_YTARGET = "com.wizzer.m3g.viewer.ui.node.ytarget";
	// The property identifier for the M3G node zReference field.
	private static final String PROPERTY_NODE_ZREFERENCE = "com.wizzer.m3g.viewer.ui.node.zReference";
	// The property identifier for the M3G node yReference field.
	private static final String PROPERTY_NODE_YREFERENCE = "com.wizzer.m3g.viewer.ui.node.yReference";
	
	// The associated M3G object.
	private Node m_node;
	// The zReference Property Source.
	private NodePropertySource m_zReferencePropertySource;
	// The yReference Property Source.
	private NodePropertySource m_yReferencePropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private NodePropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param node The associated M3G Node for this property.
	 */
	public NodePropertySource(Node node)
	{
		m_node = node;
		if (m_node.hasAlignment())
		{
			m_zReferencePropertySource = new NodePropertySource(
				m_node.getZReference());
			m_yReferencePropertySource = new NodePropertySource(
				m_node.getYReference());			
		}
	}
	
	/**
	 * Get the associated Node data.
	 * 
	 * @return A <code>Node</code> is returned.
	 */
	public Node getNode()
	{
		return m_node;
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
			
			PropertyDescriptor enableRenderingDescr = new PropertyDescriptor(
				PROPERTY_NODE_ENABLERENDERING, "Enable Rendering");
			descriptors.add(enableRenderingDescr);
			PropertyDescriptor enablePickingDescr = new PropertyDescriptor(
				PROPERTY_NODE_ENABLEPICKING, "Enable Picking");
			descriptors.add(enablePickingDescr);
			PropertyDescriptor alphaFactorDescr = new PropertyDescriptor(
				PROPERTY_NODE_ALPHAFACTOR, "Alpha Factor");
			descriptors.add(alphaFactorDescr);
			PropertyDescriptor scopeDescr = new PropertyDescriptor(
				PROPERTY_NODE_SCOPE, "Scope");
			descriptors.add(scopeDescr);
			PropertyDescriptor hasAlignmentDescr = new PropertyDescriptor(
				PROPERTY_NODE_HASALIGNMENT, "Has Alignment");
			descriptors.add(hasAlignmentDescr);
			
			if (m_node.hasAlignment())
			{
				PropertyDescriptor zTargetDescr = new PropertyDescriptor(
					PROPERTY_NODE_ZTARGET, "z Target");
				descriptors.add(zTargetDescr);
				PropertyDescriptor yTargetDescr = new PropertyDescriptor(
					PROPERTY_NODE_YTARGET, "y Target");
				descriptors.add(yTargetDescr);
				PropertyDescriptor zReferenceDescr = new NodePropertyDescriptor(
					PROPERTY_NODE_ZREFERENCE, "z Reference");
				descriptors.add(zReferenceDescr);
				PropertyDescriptor yReferenceDescr = new NodePropertyDescriptor(
					PROPERTY_NODE_YREFERENCE, "y Reference");
				descriptors.add(yReferenceDescr);
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
		if (id.equals(PROPERTY_NODE_ZTARGET))
		{
			return m_node.getZTarget();
		} else if (id.equals(PROPERTY_NODE_YTARGET))
		{
			return m_node.getYTarget();
		} else if (id.equals(PROPERTY_NODE_ZREFERENCE))
		{
			return m_zReferencePropertySource;
		} else if (id.equals(PROPERTY_NODE_YREFERENCE))
		{
			return m_yReferencePropertySource;
		} else if (id.equals(PROPERTY_NODE_HASALIGNMENT))
		{
			return m_node.hasAlignment();
		} else if (id.equals(PROPERTY_NODE_ENABLERENDERING))
		{
			return m_node.isRenderingEnabled();
		} else if (id.equals(PROPERTY_NODE_ENABLEPICKING))
		{
			return m_node.isPickingEnabled();
		} else if (id.equals(PROPERTY_NODE_ALPHAFACTOR))
		{
			return m_node.getAlphaFactor();
		} else if (id.equals(PROPERTY_NODE_SCOPE))
		{
			byte[] data = intToByteArray(m_node.getScope());
			return Unsigned.readDWORD(data,0);
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_NODE_ZTARGET.equals(id) ||
			PROPERTY_NODE_YTARGET.equals(id) ||
			PROPERTY_NODE_ZREFERENCE.equals(id) ||
			PROPERTY_NODE_YREFERENCE.equals(id) ||
			PROPERTY_NODE_HASALIGNMENT.equals(id) ||
			PROPERTY_NODE_ENABLERENDERING.equals(id) ||
			PROPERTY_NODE_ENABLEPICKING.equals(id) ||
			PROPERTY_NODE_ALPHAFACTOR.equals(id) ||
			PROPERTY_NODE_SCOPE.equals(id))
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
