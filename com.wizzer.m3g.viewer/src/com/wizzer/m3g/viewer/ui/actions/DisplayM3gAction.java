/*
 * DisplayM3gAction.java
 * Created on Sep 2, 2008
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
import java.util.logging.*;

// Import Eclipse classes.
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;

// Import M3G Toolkit classes.
import com.wizzer.m3g.viewer.ui.M3gFileView;
import com.wizzer.m3g.viewer.ui.M3gGraphView;
import com.wizzer.m3g.viewer.ui.M3gOpenGLView;

/**
 * Action to display M3G data in 3D.
 * 
 * @author Mark Millard
 */
public class DisplayM3gAction extends Action implements IWorkbenchAction
{
	/** The About Action identifier/ */
	public static final String DISPLAY_M3G_ACTION_ID = "com.wizzer.m3g.viewer.ui.actions.displaym3gaction";
	
	// Flag indicating whether M3gOpenGLView is visible or not.
	private boolean m_isVisible = false;
	
	/**
	 * Get the unique identifier for this action.
	 * 
	 * @return The identifier is returned.
	 */
	public String getId()
	{
		return DISPLAY_M3G_ACTION_ID;
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
		return("Display");
	}
	
	/**
	 * Runs this action.
	 */
	public void run()
	{
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try
		{
			if (! m_isVisible)
			{
				// Show the view.
			    IViewPart part = page.showView(M3gOpenGLView.ID);
			    //page.activate(part);
			    part = page.findView(M3gFileView.ID);
			    ((M3gFileView)part).enableRefreshDisplay(true);
			    part = page.findView(M3gGraphView.ID);
			    ((M3gGraphView)part).enableRefreshDisplay(true);
			    
			    m_isVisible = true;
			} else
			{
				// Hide the view.
			    IViewPart part = page.findView(M3gOpenGLView.ID);
			    page.hideView(part);
			    part = page.findView(M3gFileView.ID);
			    ((M3gFileView)part).enableRefreshDisplay(false);
			    part = page.findView(M3gGraphView.ID);
			    ((M3gGraphView)part).enableRefreshDisplay(false);
			    
			    m_isVisible = false;
			}
		} catch (PartInitException ex)
		{
			Logger.global.logp(Level.WARNING, "com.wizzer.m3g.viewer.ui.actions.DisplayM3gAction",
				"run()", "Unable to display 3D view");
		}
		
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
