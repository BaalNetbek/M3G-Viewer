/*
 * M3gOpenGLView.java
 * Created on Aug 27, 2008
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
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

// Include Swing classes.
import java.awt.*;
import javax.swing.*;

// Import Eclipse classes.
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.albireo.core.SwingControl;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.PlatformUI;

// Import M3G Toolkit classes.
import com.wizzer.m3g.midp.MIDPEmulator;

// Import M3G Viewer classes.
import com.wizzer.m3g.viewer.domain.SceneGraphManager;
import com.wizzer.m3g.viewer.midp.ViewerDevice;
import com.wizzer.m3g.viewer.midp.ViewerMidlet;

/**
 * This view displays the content of a M3G file in 3D.
 * <p>
 * It uses JOGL for rendering the content to an embedded Swing
 * control encapsulated by a MIDP emulator.
 * </p>
 * 
 * @author Mark Millard
 */
public class M3gOpenGLView extends ViewPart implements Observer
{
	// The view part's identifier.
	public static final String ID = "com.wizzer.m3g.viewer.ui.m3gopenglview";

	// The top-level Composite widget.
    private Composite m_top;
    private int m_topWidth, m_topHeight;
    // The viewer's device.
    ViewerDevice m_device = null;
    // The viewer's midlet.
    ViewerMidlet m_midlet = null;
    
    // Activation/deactivation listener.
    M3gOpenGLViewPartListener m_partListener;
   
    /**
     * The default constructor.
     * <p>
     * Adds itself as an Observer to the <code>SceneGraphManager</code>.
     * </p>
     */
    public M3gOpenGLView()
    {
    	SceneGraphManager manager = SceneGraphManager.getInstance();
    	manager.addObserver(this);
    	
    	// Add a listener so we know when it is activated/deactivated.
    	IWorkbench wb = PlatformUI.getWorkbench();
    	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
    	IWorkbenchPage page = win.getActivePage();
    	m_partListener = new M3gOpenGLViewPartListener();
    	page.addPartListener(m_partListener);
    }
    
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 * 
	 * @param parent The control's parent widget.
	 */
	@Override
	public void createPartControl(Composite parent)
	{
		// We can't use the default Composite because using the AWT bridge
		// requires that it have the property of SWT.EMBEDDED
		//m_top = new Composite(parent, SWT.EMBEDDED);
		m_top = parent;
		m_top.setLayout(new FillLayout());

		// Create the MIDP device for the Viewer.
		m_device = ViewerDevice.getInstance();
        m_device.createDevice(m_top, m_topWidth, m_topHeight);
        m_device.addObserver(this);

		// Add resize listener.
		parent.addListener(SWT.Resize, new Listener()
		{
			public void handleEvent(Event e)
			{
				Point p = m_top.getSize();
				m_topWidth = p.x;
				m_topHeight = p.y;

				// Make sure the device can be refreshed.
				m_device.resize(m_topWidth, m_topHeight);
				if (! m_device.isDeviceCreated())
					return;
				
				// Get the MIDP Emulator and refresh all midlet displays.
				MIDPEmulator emulator = MIDPEmulator.getInstance();
				emulator.resize(m_topWidth, m_topHeight);
				emulator.refreshDisplays();
			};
		});
		
		// Add paint listener.
		parent.addPaintListener(new PaintListener()
		{
			public void paintControl(PaintEvent e)
			{
				// Make sure the Swing control can be refreshed.
				if (! m_device.isDeviceCreated())
					return;

				// Get the MIDP Emulator and refresh all midlet displays.
				MIDPEmulator emulator = MIDPEmulator.getInstance();
				emulator.refreshDisplays();
			};
		});
	}
	
	/**
	 * Clean up.
	 */
	@Override
	public void dispose()
	{
		super.dispose();
		
		// Remove the midlet from the MIDP Emulator.
		MIDPEmulator.getInstance().removeMidlet(m_midlet);
		
		// Reset the device.
		m_device.reset();
		
		// Remove this view from the SceneGraphManager observable.
		SceneGraphManager manager = SceneGraphManager.getInstance();
    	manager.deleteObserver(this);
    	
    	// No longer need to be registered for activation/deactivation events.
    	IWorkbench wb = PlatformUI.getWorkbench();
    	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
    	IWorkbenchPage page = win.getActivePage();
    	page.removePartListener(m_partListener);
    	
    	System.gc();
	}

	/**
	 * Passing the focus request to the viewer's device control.
	 */
	@Override
	public void setFocus()
	{
		m_device.setFocus();
	}

	/**
	 * This method is called whenever the observed object is changed.
	 * An application calls an Observable object's notifyObservers method to have
	 * all the object's observers notified of the change.
	 * 
	 * @param observable The observable object.
	 * @param obj An argument passed to the notifyObservers method.
	 */
	public void update(Observable observable, Object arg)
	{
		if (((observable instanceof SceneGraphManager) && ((Boolean)arg)) ||
			(observable instanceof ViewerDevice))
		{
			// Remove existing from MIDP Emulator.
			if (m_midlet != null)
				MIDPEmulator.getInstance().removeMidlet(m_midlet);
			
			// Construct a Midlet.
			m_midlet = new ViewerMidlet();
			
			// Add it to the MIDP Emulator. This should start the midlet.
			MIDPEmulator.getInstance().addMidlet(m_midlet);
		}
	}

}
