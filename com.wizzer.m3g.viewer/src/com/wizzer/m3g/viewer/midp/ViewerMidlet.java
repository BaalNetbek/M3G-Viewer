/*
 * ViewerMidlet.java
 * Created on Sep 4, 2008
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
package com.wizzer.m3g.viewer.midp;

// Import M3G Toolkit classes.
import java.util.logging.Level;
import java.util.logging.Logger;

// Import M3G Toolkit classes.
import com.wizzer.m3g.World;
import com.wizzer.m3g.midlet.MIDlet;
import com.wizzer.m3g.midlet.MIDletStateChangeException;
import com.wizzer.m3g.lcdui.Display;

// Import M3G Vewer classes.
import com.wizzer.m3g.viewer.domain.SceneGraphManager;
import com.wizzer.m3g.viewer.ui.M3gNode;

/**
 * @author Mark Millard
 */
public class ViewerMidlet extends MIDlet
{
	// The unique display.
	private Display m_display = null;
	// The canvas.
	private ViewerCanvas m_canvas = null;
	
	/* (non-Javadoc)
	 * @see com.wizzer.m3g.midlet.MIDlet#destroyApp(boolean)
	 */
	@Override
	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException
	{
		Logger.global.logp(Level.INFO, "com.wizzer.m3g.viewer.midp.ViewerMidlet",
			"destroyApp()", "Destroying the midlet");
	}

	/* (non-Javadoc)
	 * @see com.wizzer.m3g.midlet.MIDlet#pauseApp()
	 */
	@Override
	protected void pauseApp()
	{
		Logger.global.logp(Level.INFO, "com.wizzer.m3g.viewer.midp.ViewerMidlet",
			"pauseApp()", "Pausing the midlet");
	}

	/* (non-Javadoc)
	 * @see com.wizzer.m3g.midlet.MIDlet#startApp()
	 */
	@Override
	protected void startApp() throws MIDletStateChangeException
	{
		Logger.global.logp(Level.INFO, "com.wizzer.m3g.viewer.midp.ViewerMidlet",
			"startApp()", "Sarting the midlet");
		
		// Get the display for this midlet.
		m_display = Display.getDisplay(this);
		// Allocate the canvas.
		m_canvas = new ViewerCanvas();
		
		// Initialize the canvas.
		SceneGraphManager manager = SceneGraphManager.getInstance();
		M3gNode root = manager.getRoot();
		Object obj = root.getM3gObject();
		if (obj instanceof World)
		{
		    m_canvas.init((World) obj);
		
			// Add commands to the canvas here.
			//m_canvas.addCommand(new Command("Quit", Command.EXIT, 1));
			  
			// Set the listener to be the MIDlet.
			//m_canvas.setCommandListener(this);
			  
			// Start canvas
			m_canvas.start();
			m_display.setCurrent(m_canvas);
			m_canvas.repaint();
		} else
		{
			Logger.global.logp(Level.WARNING, "com.wizzer.m3g.viewer.midp.ViewerMidlet",
				"startApp()", "Unable to start midlet: root is not World");
		}
	}

}
