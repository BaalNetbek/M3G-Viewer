/*
 * ApplicationActionBarAdvisor.java
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
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

// Import M3g Viewer classes.
import com.wizzer.m3g.viewer.ui.actions.OpenAction;
import com.wizzer.m3g.viewer.ui.actions.DumpAction;
import com.wizzer.m3g.viewer.ui.actions.AboutAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{
	// Actions - important to allocate these only in makeActions, and then use
	// them in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchAction m_exitAction;
	private IWorkbenchAction m_openAction;
	private IWorkbenchAction m_dumpAction;
	private IWorkbenchAction m_aboutAction;
	private IWorkbenchAction m_contentsHelpAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
	{
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window)
	{
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		m_openAction = new OpenAction();
		register(m_openAction);
		m_dumpAction = new DumpAction();
		register(m_dumpAction);
		m_exitAction = ActionFactory.QUIT.create(window);
		register(m_exitAction);
		
		m_aboutAction = new AboutAction();
		register(m_aboutAction);
		
		m_contentsHelpAction = ActionFactory.HELP_CONTENTS.create(window);
		register(m_contentsHelpAction);
	}

	protected void fillMenuBar(IMenuManager menuBar)
	{
		MenuManager fileMenu = new MenuManager("&File",
			IWorkbenchActionConstants.M_FILE);
		menuBar.add(fileMenu);
		fileMenu.add(m_openAction);
		fileMenu.add(m_dumpAction);
		fileMenu.add(m_exitAction);
		
		MenuManager helpMenu = new MenuManager("&Help",
			IWorkbenchActionConstants.M_HELP);
		menuBar.add(helpMenu);
		helpMenu.add(m_aboutAction);
		helpMenu.add(m_contentsHelpAction);
	}

}
