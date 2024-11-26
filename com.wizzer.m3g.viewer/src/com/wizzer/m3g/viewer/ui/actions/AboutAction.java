/*
 * AboutAction.java
 * Created on Jul 8, 2008
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
package com.wizzer.m3g.viewer.ui.actions;

// Import standard Java classes.

// Import Eclipse classes.
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

// Import M3G Toolkit classes.

/**
 * Action to display information about this program.
 * 
 * @author Mark Millard
 */
public class AboutAction extends Action implements IWorkbenchAction
{
	/** The About Action identifier. */
	public static final String ABOUT_ACTION_ID = "com.wizzer.m3g.viewer.ui.actions.aboutaction";
	
	/**
	 * Get the unique identifier for this action.
	 * 
	 * @return The identifier is returned.
	 */
	public String getId()
	{
		return ABOUT_ACTION_ID;
	}
	
	/**
	 * Get the text for this action.
	 * <p>
	 * This method is associated with the TEXT property;
	 * property change events are reported when its value changes.
     * </p>
	 */
	public String getText()
	{
		return("About");
	}
	
	/**
	 * Runs this action.
	 */
	public void run()
	{
		Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		
		StringBuffer msg = new StringBuffer("M3G Viewer v0.3\n\n");
		msg.append("This utility is used to examine Mobile 3D Graphics files.\n");
		msg.append("It is available under the GNU Lesser General Public License\n");
		msg.append("as published by the Free Software Foundation.\n\n");
		msg.append("Copyright (C) 2008, Wizzer Works\n\n");
		msg.append("For more information, visit http://www.wizzerworks.com\n");
		
		// Create a new message dialog and initialize it.
        MessageBox msgBox = new MessageBox(shell, SWT.ICON_INFORMATION);
        msgBox.setText("About M3G Viewer");
        msgBox.setMessage(msg.toString());
        msgBox.open();
	}
	
	/**
	 * Disposes of this action.
	 * <p>
	 * Once disposed, this action cannot be used. This operation has no
	 * effect if the action has already been disposed.
	 * </p>
	 */
	public void dispose()
	{
		// Do nothing for now.
	}
}
