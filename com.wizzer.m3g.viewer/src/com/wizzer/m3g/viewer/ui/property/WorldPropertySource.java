/*
 * WorldPropertySource.java
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
import com.wizzer.m3g.World;

/**
 * This class is a Property Source for a M3G World.
 * 
 * @author Mark Millard
 */
public class WorldPropertySource implements IPropertySource
{
	// The property identifier for the M3G world activeCamera field.
	private static final String PROPERTY_WORLD_ACTIVECAMERA = "com.wizzer.m3g.viewer.ui.world.activeCamera";
	// The property identifier for the M3G world background field.
	private static final String PROPERTY_WORLD_BACKGROUND = "com.wizzer.m3g.viewer.ui.world.background";
	
	// The associated M3G object.
	private World m_world;
	// The active camera property source.
	private CameraPropertySource m_cameraPropertySource;
	// The background property source.
	private BackgroundPropertySource m_backgroundPropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private WorldPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param world The associated M3G World for this property.
	 */
	public WorldPropertySource(World world)
	{
		m_world = world;
		m_cameraPropertySource = new CameraPropertySource(
			m_world.getActiveCamera());
		m_backgroundPropertySource = new BackgroundPropertySource(
			m_world.getBackground());
	}
	
	/**
	 * Get the associated World data.
	 * 
	 * @return A <code>World</code> is returned.
	 */
	public World getWorld()
	{
		return m_world;
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
			PropertyDescriptor activeCameraDescr = new CameraPropertyDescriptor(
				PROPERTY_WORLD_ACTIVECAMERA, "Active Camera");
			PropertyDescriptor backgroundDescr = new BackgroundPropertyDescriptor(
				PROPERTY_WORLD_BACKGROUND, "Background");
			m_descriptors = new IPropertyDescriptor[] {
				activeCameraDescr,
				backgroundDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_WORLD_BACKGROUND))
		{
			return m_backgroundPropertySource;
		} else if (id.equals(PROPERTY_WORLD_ACTIVECAMERA))
		{
			return m_cameraPropertySource;
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_WORLD_BACKGROUND.equals(id) ||
			PROPERTY_WORLD_ACTIVECAMERA.equals(id))
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
