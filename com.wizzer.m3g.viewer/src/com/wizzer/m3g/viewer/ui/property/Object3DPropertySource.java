/*
 * Object3DPropertySource.java
 * Created on Jun 14, 2008
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
import java.util.Hashtable;
import java.util.Enumeration;

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Object3D;
import com.wizzer.m3g.AnimationTrack;

/**
 * This class is a Property Source for a M3G Object3D.
 * 
 * @author Mark Millard
 */
public class Object3DPropertySource implements IPropertySource
{
	// The property identifier for the M3G object3D userID field.
	private static final String PROPERTY_USER_IDENTIFIER = "com.wizzer.m3g.viewer.ui.object3d.useridentifier";
	// The property identifier for the M3G object3d userParameters field.
	private static final String PROPERTY_USER_PARAMETER = "com.wizzer.m3g.viewer.ui.object3d.userparameter";
	// The property identifier for the M3G object3d animationTracks field.
	private static final String PROPERTY_ANIMATION_TRACK = "com.wizzer.m3g.viewer.ui.object3d.animationtrack";

	// The associated M3G object.
	private Object3D m_object;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors = null;

	// The Property Source for the associated User Parameters.
	private UserParameterPropertySource[] m_userParameterPropertySources = null;
	// The Property Source for the associated Animation Tracks.
	private AnimationTrackPropertySource[] m_animationTrackPropertySources = null;
	
	// Hide the default constructor.
	private Object3DPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param object The associated M3G Object3D for this property.
	 */
	public Object3DPropertySource(Object3D object)
	{
		m_object = object;
		
		Hashtable userParams = (Hashtable)m_object.getUserObject();
		if (userParams.size() > 0)
		{
			int i = 0;
			m_userParameterPropertySources = new UserParameterPropertySource[userParams.size()];
			for (Enumeration e = userParams.keys(); e.hasMoreElements(); i++)
			{
				Integer id = (Integer)e.nextElement();
				byte[] value = (byte[])userParams.get(id);
				m_userParameterPropertySources[i] = new UserParameterPropertySource(
					id.longValue(), value);
			}
		}
		
		int numTracks = m_object.getAnimationTrackCount();
		if (numTracks > 0)
		{
			m_animationTrackPropertySources = new AnimationTrackPropertySource[numTracks];
			for (int i = 0; i < numTracks; i++)
			{
				AnimationTrack track = m_object.getAnimationTrack(i);
				m_animationTrackPropertySources[i] = new AnimationTrackPropertySource(
					track);
			}
		}
	}
	
	/**
	 * Get the associated Object3D data.
	 * 
	 * @return A <code>Object3D</code> is returned.
	 */
	public Object3D getHeader()
	{
		return m_object;
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
			
			PropertyDescriptor userIdDesc = new PropertyDescriptor(
				PROPERTY_USER_IDENTIFIER, "User Identifier");
			descriptors.add(userIdDesc);
			
			Hashtable userParameters = (Hashtable)m_object.getUserObject();
			int count = userParameters.size();
			for (int i = 0; i < count; i++)
			{
				String id = new String(PROPERTY_USER_PARAMETER + ":" + i);
				PropertyDescriptor userParamDescr = new UserParameterPropertyDescriptor(
					id, "User Parameter");
				descriptors.add(userParamDescr);
			}
			
			count = m_object.getAnimationTrackCount();
			for (int i = 0; i < count; i++)
			{
				String id = new String(PROPERTY_ANIMATION_TRACK + ":" + i);
				PropertyDescriptor animationTrackDescr = new AnimationTrackPropertyDescriptor(
					id, "AnimationTrack Properties");
				descriptors.add(animationTrackDescr);
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
		if (id.equals(PROPERTY_USER_IDENTIFIER))
		{
			return m_object.getUserID();
		} else
		{
			// Check to see if the id is a PROPERTY_USER_PARAMETER.
			String str = (String)id;
			int index = str.indexOf(":");
			
			if (index != -1)
			{
				// Check for a PROPERTY_USER_PARAMETER or PROPERTY_ANIMATION_TRACK.
				String key = str.substring(index+1);
				Integer keyValue = Integer.valueOf(key);
				String property = str.substring(0, index);
				if (property.equals(PROPERTY_USER_PARAMETER))
				    return m_userParameterPropertySources[keyValue.intValue()];
				else if (property.equals(PROPERTY_ANIMATION_TRACK))
					return m_animationTrackPropertySources[keyValue.intValue()];
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_USER_IDENTIFIER.equals(id))
		{
			return true;
	    } else
		{
			// Check to see if the id is a PROPERTY_USER_PARAMETER or PROPERTY_ANIMATION_TRACK.
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
