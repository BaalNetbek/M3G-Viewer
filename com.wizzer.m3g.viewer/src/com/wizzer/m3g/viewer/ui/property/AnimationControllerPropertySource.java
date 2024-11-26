/*
 * AnimationControllerPropertySource.java
 * Created on Jul 9, 2008
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
import com.wizzer.m3g.AnimationController;

/**
 * This class is a Property Source for a M3G AnimationController.
 * 
 * @author Mark Millard
 */
public class AnimationControllerPropertySource implements IPropertySource
{
	// The property identifier for the M3G animationcontroller speed field.
	private static final String PROPERTY_ANIMATIONCONTROLLER_SPEED = "com.wizzer.m3g.viewer.ui.animationcontroller.speed";
	// The property identifier for the M3G animationcontroller weight field.
	private static final String PROPERTY_ANIMATIONCONTROLLER_WEIGHT = "com.wizzer.m3g.viewer.ui.animationcontroller.weight";
	// The property identifier for the M3G animationcontroller activeIntervalStart field.
	private static final String PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALSTART = "com.wizzer.m3g.viewer.ui.animationcontroller.activeintervalstart";
	// The property identifier for the M3G animationcontroller activeIntervalEnd field.
	private static final String PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALEND = "com.wizzer.m3g.viewer.ui.animationcontroller.activeintervalend";
	// The property identifier for the M3G animationcontroller referencesequencetime field.
	private static final String PROPERTY_ANIMATIONCONTROLLER_REFERENCESEQUENCETIME = "com.wizzer.m3g.viewer.ui.animationcontroller.referencesequencetime";
	// The property identifier for the M3G animationcontroller referenceworldtime field.
	private static final String PROPERTY_ANIMATIONCONTROLLER_REFERENCEWORLDTIME = "com.wizzer.m3g.viewer.ui.animationcontroller.referenceworldtime";
	
	// The associated M3G object.
	private AnimationController m_animationController;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private AnimationControllerPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param animationController The associated M3G AnimationController for this property.
	 */
	public AnimationControllerPropertySource(AnimationController animationController)
	{
		m_animationController = animationController;
	}
	
	/**
	 * Get the associated AnimationController data.
	 * 
	 * @return A <code>AnimationController</code> is returned.
	 */
	public AnimationController getAnimationController()
	{
		return m_animationController;
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
			PropertyDescriptor speedDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONCONTROLLER_SPEED, "Speed");
			PropertyDescriptor weightDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONCONTROLLER_WEIGHT, "Weight");
			PropertyDescriptor activeIntervalStartDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALSTART, "Active Interval Start");
			PropertyDescriptor activeIntervalEndDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALEND, "Active Interval End");
			PropertyDescriptor referenceSequenceTimeDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONCONTROLLER_REFERENCESEQUENCETIME, "Reference Sequence Time");
			PropertyDescriptor referenceWorldTimeDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONCONTROLLER_REFERENCEWORLDTIME, "Reference World Time");
				
			m_descriptors = new IPropertyDescriptor[] {
				speedDescr,
				weightDescr,
				activeIntervalStartDescr,
				activeIntervalEndDescr,
				referenceSequenceTimeDescr,
				referenceWorldTimeDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_ANIMATIONCONTROLLER_WEIGHT))
		{
			return m_animationController.getWeight();
		} else if (id.equals(PROPERTY_ANIMATIONCONTROLLER_SPEED))
		{
			return m_animationController.getSpeed();
		} else if (id.equals(PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALSTART))
		{
			return m_animationController.getActiveIntervalStart();
		} else if (id.equals(PROPERTY_ANIMATIONCONTROLLER_REFERENCESEQUENCETIME))
		{
			return m_animationController.getRefSequenceTime();
		} else if (id.equals(PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALEND))
		{
			return m_animationController.getActiveIntervalEnd();
		} else if (id.equals(PROPERTY_ANIMATIONCONTROLLER_REFERENCEWORLDTIME))
		{
			return m_animationController.getRefWorldTime();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_ANIMATIONCONTROLLER_WEIGHT.equals(id) ||
			PROPERTY_ANIMATIONCONTROLLER_SPEED.equals(id) ||
			PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALSTART.equals(id) ||
			PROPERTY_ANIMATIONCONTROLLER_REFERENCESEQUENCETIME.equals(id) ||
			PROPERTY_ANIMATIONCONTROLLER_REFERENCEWORLDTIME.equals(id) ||
			PROPERTY_ANIMATIONCONTROLLER_ACTIVEINTERVALEND.equals(id))
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
