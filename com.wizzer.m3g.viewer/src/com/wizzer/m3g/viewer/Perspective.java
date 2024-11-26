/*
 * Perspective.java
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
package com.wizzer.m3g.viewer;

// Import Eclipse classes.
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.wizzer.m3g.viewer.ui.M3gFileView;
import com.wizzer.m3g.viewer.ui.M3gGraphView;
import com.wizzer.m3g.viewer.ui.M3gOpenGLView;

/**
 * The default perspective for the M3G Viewer.
 * 
 * @author Mark Millard
 */
public class Perspective implements IPerspectiveFactory
{

	public void createInitialLayout(IPageLayout layout)
	{
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		IFolderLayout topLeft =
			layout.createFolder("topLeft", IPageLayout.LEFT, 0.50f, editorArea);
		topLeft.addView(M3gFileView.ID);
		topLeft.addView(M3gGraphView.ID);

		IFolderLayout bottomLeft =
			layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.75f, "topLeft");
		bottomLeft.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		
		IFolderLayout topRight =
			layout.createFolder("topRight", IPageLayout.RIGHT, 0.40f, "topLeft");
		topRight.addView(IPageLayout.ID_PROP_SHEET);
		topRight.addPlaceholder(M3gOpenGLView.ID);
	}

}
