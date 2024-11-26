/*
 * M3gLabelProvider.java
 * Created on May 29, 2008
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
package com.wizzer.m3g.viewer.ui;

// Import Eclipse classes.
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

class M3gLabelProvider implements ILabelProvider
{
    public String getText(Object element)
    {
        return ((M3gNode) element).getName();
    }

    public Image getImage(Object arg0)
    {
        return null;
    }

    public void addListener(ILabelProviderListener arg0)
    {
    }

    public void dispose()
    {
    }

    public boolean isLabelProperty(Object arg0, String arg1)
    {
        return false;
    }

    public void removeListener(ILabelProviderListener arg0)
    {
    }
}
