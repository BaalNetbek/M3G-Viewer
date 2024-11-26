/*
 * CameraPropertySource.java
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
import com.wizzer.m3g.Camera;
import com.wizzer.m3g.Transform;

/**
 * This class is a Property Source for a M3G Camera.
 * 
 * @author Mark Millard
 */
public class CameraPropertySource implements IPropertySource
{
	// The property identifier for the M3G camera fovy field.
	private static final String PROPERTY_CAMERA_FOVY = "com.wizzer.m3g.viewer.ui.camera.fovy";
	// The property identifier for the M3G camera aspect ratio field.
	private static final String PROPERTY_CAMERA_ASPECT_RATIO = "com.wizzer.m3g.viewer.ui.camera.aspectratio";
	// The property identifier for the M3G camera near field.
	private static final String PROPERTY_CAMERA_NEAR = "com.wizzer.m3g.viewer.ui.camera.near";
	// The property identifier for the M3G camera far field.
	private static final String PROPERTY_CAMERA_FAR = "com.wizzer.m3g.viewer.ui.camera.far";
	// The property identifier for the M3G camera projection matrix field.
	private static final String PROPERTY_CAMERA_PROJECTION_MATRIX = "com.wizzer.m3g.viewer.ui.camera.projectionmatrix";
	// The property identifier for the M3G camera projection type field.
	private static final String PROPERTY_CAMERA_PROJECTION_TYPE = "com.wizzer.m3g.viewer.ui.camera.projectiontype";
	
	// The associated M3G object.
	private Camera m_camera;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private CameraPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param camera The associated M3G Camera for this property.
	 */
	public CameraPropertySource(Camera camera)
	{
		m_camera = camera;
	}
	
	/**
	 * Get the associated Camera data.
	 * 
	 * @return A <code>Camera</code> is returned.
	 */
	public Camera getCamera()
	{
		return m_camera;
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
			float params[] = new float[4];
			int type = m_camera.getProjection(params);
			
			PropertyDescriptor projectionTypeDescr = new PropertyDescriptor(
					PROPERTY_CAMERA_PROJECTION_TYPE, "Projection Type");
			
			if (type == Camera.GENERIC)
			{
				PropertyDescriptor projectionMatrixDescr = new PropertyDescriptor(
					PROPERTY_CAMERA_PROJECTION_MATRIX, "Projection Matrix");
				
				m_descriptors = new IPropertyDescriptor[] {
					projectionTypeDescr,
					projectionMatrixDescr
				};
			} else
			{
				PropertyDescriptor fovyDescr = new PropertyDescriptor(
					PROPERTY_CAMERA_FOVY, "Fovy");
				PropertyDescriptor aspectRatioDescr = new PropertyDescriptor(
					PROPERTY_CAMERA_ASPECT_RATIO, "Aspect Ratio");
				PropertyDescriptor nearDescr = new PropertyDescriptor(
					PROPERTY_CAMERA_NEAR, "Near");
				PropertyDescriptor farDescr = new PropertyDescriptor(
					PROPERTY_CAMERA_FAR, "Far");
				
				m_descriptors = new IPropertyDescriptor[] {
					projectionTypeDescr,
					fovyDescr,
					aspectRatioDescr,
					nearDescr,
					farDescr
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
		if (id.equals(PROPERTY_CAMERA_PROJECTION_TYPE))
		{
			float params[] = new float[4];
			int type = m_camera.getProjection(params);
			
			return type;
		} else if (id.equals(PROPERTY_CAMERA_PROJECTION_MATRIX))
		{
			Transform t = new Transform();
			m_camera.getProjection(t);
			return t;
		} else if (id.equals(PROPERTY_CAMERA_FOVY))
		{
			float params[] = new float[4];
			m_camera.getProjection(params);
			
			return params[0];
		} else if (id.equals(PROPERTY_CAMERA_ASPECT_RATIO))
		{
			float params[] = new float[4];
			m_camera.getProjection(params);
			
			return params[1];
		} else if (id.equals(PROPERTY_CAMERA_NEAR))
		{
			float params[] = new float[4];
			m_camera.getProjection(params);
			
			return params[2];
		} else if (id.equals(PROPERTY_CAMERA_FAR))
		{
			float params[] = new float[4];
			m_camera.getProjection(params);
			
			return params[3];
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_CAMERA_PROJECTION_TYPE.equals(id) ||
			PROPERTY_CAMERA_PROJECTION_MATRIX.equals(id) ||
			PROPERTY_CAMERA_FOVY.equals(id) ||
			PROPERTY_CAMERA_ASPECT_RATIO.equals(id) ||
			PROPERTY_CAMERA_NEAR.equals(id) ||
			PROPERTY_CAMERA_FAR.equals(id))
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
