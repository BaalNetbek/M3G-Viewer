/*
 * AnimationTrackPropertySource.java
 * Created on Jul 11, 2008
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
import com.wizzer.m3g.AnimationTrack;

/**
 * This class is a Property Source for a M3G AnimationTrack.
 * 
 * @author Mark Millard
 */
public class AnimationTrackPropertySource implements IPropertySource
{
	// The property identifier for the M3G animationtrack keyframeSequence field.
	private static final String PROPERTY_ANIMATIONTRACK_KEYFRAMESEQUENCE = "com.wizzer.m3g.viewer.ui.animationtrack.keyframesequence";
	// The property identifier for the M3G animationtrack animationController field.
	private static final String PROPERTY_ANIMATIONTRACK_ANIMATIONCONTROLLER = "com.wizzer.m3g.viewer.ui.animationtrack.animationcontroller";
	// The property identifier for the M3G animatintrack propertyID field.
	private static final String PROPERTY_ANIMATIONTRACK_PROPERTYID = "com.wizzer.m3g.viewer.ui.animationtrack.propertyid";
	
	// The associated M3G object.
	private AnimationTrack m_animationTrack;
	// The Keyframe Property Source.
	private KeyframeSequencePropertySource m_keyframeSequencePropertySource;
	// The AnimationController Property Source.
	private AnimationControllerPropertySource m_animationControllerPropertySource;

	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors;

	// Hide the default constructor.
	private AnimationTrackPropertySource() {}

	/**
	 * Create a new property source.
	 * 
	 * @param animationtrack The associated M3G AnimationTrack for this property.
	 */
	public AnimationTrackPropertySource(AnimationTrack animationtrack)
	{
		m_animationTrack = animationtrack;
		m_keyframeSequencePropertySource = new KeyframeSequencePropertySource(
			m_animationTrack.getKeyframeSequence());
		m_animationControllerPropertySource = new AnimationControllerPropertySource(
			m_animationTrack.getController());
	}
	
	/**
	 * Get the associated AnimationTrack data.
	 * 
	 * @return A <code>AnimationTrack</code> is returned.
	 */
	public AnimationTrack getAnimationTrack()
	{
		return m_animationTrack;
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
			PropertyDescriptor keyframeSequenceDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONTRACK_KEYFRAMESEQUENCE, "Keyframe Sequence");
			PropertyDescriptor animationControllerDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONTRACK_ANIMATIONCONTROLLER, "Animation Controller");
			PropertyDescriptor propertyIdDescr = new PropertyDescriptor(
				PROPERTY_ANIMATIONTRACK_PROPERTYID, "Property ID");
			
			m_descriptors = new IPropertyDescriptor[] {
				keyframeSequenceDescr,
				animationControllerDescr,
				propertyIdDescr
			};
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		if (id.equals(PROPERTY_ANIMATIONTRACK_ANIMATIONCONTROLLER))
		{
			return m_animationControllerPropertySource;
		} else if (id.equals(PROPERTY_ANIMATIONTRACK_KEYFRAMESEQUENCE))
		{
			return m_keyframeSequencePropertySource;
		} else if (id.equals(PROPERTY_ANIMATIONTRACK_PROPERTYID))
		{
			return m_animationTrack.getTargetProperty();
		} else
		    return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_ANIMATIONTRACK_ANIMATIONCONTROLLER.equals(id) ||
			PROPERTY_ANIMATIONTRACK_KEYFRAMESEQUENCE.equals(id) ||
			PROPERTY_ANIMATIONTRACK_PROPERTYID.equals(id))
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
