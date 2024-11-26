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
import com.wizzer.m3g.lcdui.Canvas;
import com.wizzer.m3g.lcdui.Graphics;
import com.wizzer.m3g.Camera;
import com.wizzer.m3g.Graphics3D;
import com.wizzer.m3g.World;

/**
 * @author Mark Millard
 */
public class ViewerCanvas extends Canvas
{
	// Graphics singleton used for rendering.
	private Graphics3D m_graphics3D;
	// The world to render.
	private World m_world;
	
	/* (non-Javadoc)
	 * @see com.wizzer.m3g.lcdui.Canvas#paint(com.wizzer.m3g.lcdui.Graphics)
	 */
	@Override
	protected void paint(Graphics graphics)
	{
		m_graphics3D.bindTarget(graphics);
		//m_graphics3D.clear(null);
		m_graphics3D.render(m_world);
		m_graphics3D.releaseTarget();
	}
	
	/**
	 * Initialize the scene graph to render.
	 * 
	 * @param world The root of the scene graph.
	 */
	protected void init(World world)
	{
		// Get the singleton for 3D rendering;
	    m_graphics3D = Graphics3D.getInstance();
	    
	    // Construct the world.
	    m_world = world;

		// Change the cameras properties to match the current device.
		Camera camera = m_world.getActiveCamera();
		float aspect = (float) getWidth() / (float) getHeight();
		camera.setPerspective(60.0f, aspect, 1.0f, 1000f);
	}
	
	/**
	 * Starts the canvas by firing up a thread.
     */
    public void start()
    {
/*
        Thread workThread = new Thread(this);
        
        // Make sure we know we are running.
        m_running = true;
        m_done = false;
        
        // Start.
        workThread.start();
*/
    }

}
