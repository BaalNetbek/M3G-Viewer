/*
 * MIDPEmulatorTest.java
 * Created on Sep 12, 2008
 */

// COPYRIGHT_BEGIN
//
// Copyright (C) 2000-2008  Wizzer Works (msm@wizzerworks.com)
// 
// This file is part of the M3G Toolkit.
//
// The M3G Toolkit is free software; you can redistribute it and/or modify it
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
package com.wizzer.m3g.midp;

// Import standard Java classes.
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Import Swing classes.
import javax.swing.*;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Loader;
import com.wizzer.m3g.M3GFile;
import com.wizzer.m3g.Object3D;
import com.wizzer.m3g.SceneSection;
import com.wizzer.m3g.World;

/**
 * This class tests the bring-up of the MIDP emulator.
 * 
 * @author Mark Millard
 */
public class MIDPEmulatorTest
{
	// A handle to the emulator.
	private static MIDPEmulator m_emulator;
	
	/**
	 * The main entry point for the test.
	 * 
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args)
	{
		MIDPEmulatorTest emulatorTest = new MIDPEmulatorTest();
		
		// Get the MIDP Emulator and initialize it.
		MIDPEmulator emulator = MIDPEmulator.getInstance();
		emulatorTest.m_emulator = emulator;
		
		// Create a frame component.
		JFrame frame = new JFrame("MIDP Emulator");
		// Handle a resize event.
		frame.addComponentListener(new ComponentListener()
		{
			// This method is called after the component is shown.
			public void componentShown(ComponentEvent ev) { /* Do nothing. */ }

			// This method is called after the component is hidden.
			public void componentHidden(ComponentEvent ev) { /* Do nothing. */ }

			// This method is called after the component is moved.
			public void componentMoved(ComponentEvent ev) { /* Do nothing. */ }

			// This method is called after the component's size changes.
			public void componentResized(ComponentEvent ev)
			{
				Component c = (Component)ev.getSource();
				
				// Get new size;
				Dimension size = c.getSize();
				
				m_emulator.resize(size.width, size.height);
				// Note: can't call method below because JOGL makecurrent() waits
				// for a synchronous lock to be released that never occurs.
				//m_emulator.refreshDisplays();
			}
		});
		frame.pack();
		
		// Determine the screen size.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Initialize the emulator.
		emulator.init(frame, screenSize.width, screenSize.height, MIDPEmulator.DEVICE_USE_KEYPAD);
		
		// Display the M3G file (if one was specified).
		if ((args != null) && (args.length != 0))
		{
			String filename = args[0];
			
			// Read in the .m3g file.
			/*
			File file = new File(filename);
			if (file.exists() && file.canRead())
			{
				try
				{
					// Load the file.
				    M3GFile m3gFile = new M3GFile(file);
				    // Retrieve the scenes.
				    SceneSection[] scenes = m3gFile.getSceneSections();
				    // Just look into the first scene for something to display.
				    Object3D[] objects = scenes[0].getObjects3D();

				    // Find the root object;
				    Object3D root = null;
				    for (int i = 0; i < objects.length; i++)
				    {
				    	if (objects[i].isRoot())
				    	{
				    		root = objects[i];
				    		break;
				    	}
				    }

				    if (root != null)
				    {
					    // Create a new Midlet.
					    TestMidlet midlet = new TestMidlet();
					    midlet.setRenderTarget(root);
					    
					    // Add the midlet to the emulator and start displaying.
					    emulator.addMidlet(midlet);
				    }
				} catch (IOException ex)
				{
					Logger.global.logp(Level.WARNING, "com.wizzer.m3g.midp.MIDPEmulatorTest",
						"main(String[] args)", ex.getMessage());

				}
			}
			*/

			// Load the file. The returned objects are guaranteed
			// to be a root node; however, there is no guarantee
			// what order they will be returned in.
			Object3D[] objects = Loader.load(filename);
			
		    // Find the World object;
		    Object3D root = null;
		    for (int i = 0; i < objects.length; i++)
		    {
		    	if (objects[i] instanceof World)
		    	{
		    		root = objects[i];
		    		break;
		    	}
		    }

		    if (root != null)
		    {
			    // Create a new Midlet.
			    TestMidlet midlet = new TestMidlet();
			    midlet.setRenderTarget(root);
			    
			    // Add the midlet to the emulator and start displaying.
			    emulator.addMidlet(midlet);
		    }
		}
	}

}
