/*
 * KeyframePropertyDescriptor.java
 * Created on Aug 5, 2008
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
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.

/**
 * This class is a property descriptor for a Key Frame referenced
 * by a M3G KeyframeSequence.
 * 
 * @author Mark Millard
 */
public class KeyframePropertyDescriptor extends PropertyDescriptor
{
	/**
	 * Create a property descriptor for a Keyframe.
	 * 
	 * @param id An identifier for the keyframe property.
	 * @param displayName The name to display for the property.
	 */
	public KeyframePropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
		
		// Set the label directive.
		setLabelProvider(new LabelProvider()
		{
			public String getText(Object element)
			{
				KeyframePropertySource node = (KeyframePropertySource) element;
				StringBuffer buffer = new StringBuffer();
				buffer.append("Keyframe Properties");
				
				return buffer.toString();
			}
		});
	}

}
