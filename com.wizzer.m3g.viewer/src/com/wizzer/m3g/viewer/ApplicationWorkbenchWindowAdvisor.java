/*
 * ApplicationWorkbenchWindowAdvisor.java
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

// Import standard Java classes.
import java.util.logging.Logger;
import java.util.logging.Level;

// Import Eclipse classes.
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;

// Import M3G Viewer classes.
import com.wizzer.m3g.viewer.ui.M3gGraphView;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{
	private static int INITIAL_WIDTH = 840;
	private static int INITIAL_HEIGHT = 680;
	

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
	{
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
	    IActionBarConfigurer configurer)
	{
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen()
	{
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(INITIAL_WIDTH, INITIAL_HEIGHT));
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);
	}
	
	public void postWindowOpen()
	{
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		IWorkbenchWindow window = configurer.getWindow();
		IWorkbenchPage page = window.getActivePage();
		try
		{
			// Make the Graph view the default.
			page.showView(M3gGraphView.ID);
			
			// Display the Console for output messages.
			String consoleId = IConsoleConstants.ID_CONSOLE_VIEW;
			IConsoleView view = (IConsoleView) page.showView(consoleId);
			view.display(Application.getConsole());
			
			// Associate a logger with the new console.
			Logger.global.addHandler(new ApplicationLogHandler());
			//Logger.global.log(Level.INFO, "Hear I am baby!");
		} catch (PartInitException ex) {}
	}
}
