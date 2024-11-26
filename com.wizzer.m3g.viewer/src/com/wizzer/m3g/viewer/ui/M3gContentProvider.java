/*
 * M3gContentProvider.java
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

// Import standard Java classes.
import java.util.Vector;

// Import Eclipse classes.
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

class M3gContentProvider implements ITreeContentProvider
{
    public Object[] getChildren(Object parentElement)
    {
        Vector children = ((M3gNode) parentElement).getChildren();
        return children == null ? new Object[0] : children.toArray();
    }

    public Object getParent(Object element)
    {
        return ((M3gNode) element).getParent();
    }

    public boolean hasChildren(Object element)
    {
        return ((M3gNode) element).getChildren() != null;
    }

    public Object[] getElements(Object inputElement)
    {
        if (inputElement != null && inputElement instanceof Vector)
        {
            return ((Vector) inputElement).toArray();
        }
        return new Object[0];
    }

    public void dispose()
    {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }
}
