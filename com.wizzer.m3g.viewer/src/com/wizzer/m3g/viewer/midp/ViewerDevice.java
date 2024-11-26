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

// Import standard Java classes.
import java.awt.Color;
import java.util.Observable;

// Import Swing classes
import javax.swing.JComponent;
import javax.swing.JScrollPane;

// Import Eclipse classes.
import org.eclipse.albireo.core.SwingControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

//Import M3G Toolkit classes.
import com.wizzer.m3g.midp.MIDPEmulator;

/**
 * @author Mark Millard
 */
public class ViewerDevice extends Observable
{
	// The Singleton instance.
	static private ViewerDevice m_device = null;

	// The top-level Composite widget.
    private Composite m_top;
    private int m_topWidth, m_topHeight;
    // The embedded Swing control.
    private SwingControl m_swingControl;
    private boolean m_swingControlCreated = false;
    
	// Hide default constructor.
    private ViewerDevice() {};
    
    static public ViewerDevice getInstance()
    {
    	if (m_device == null)
    		m_device = new ViewerDevice();
    	return m_device;
    }
    
    public void createDevice(Composite parent, int width, int height)
    {
    	//if (m_swingControl != null)
    	//	return;

    	m_top = parent;
    	m_topWidth = width;
    	m_topHeight = height;
   	
		// Create the special frame bridge to AWT.
		m_swingControl = new SwingControl(m_top, SWT.NONE)
		{
			/**
			 * Create the Swing component.
			 * <p>
			 * Note that it is executed in the AWT context thread (so we can't make
			 * calls into SWT).
			 * </p>
			 * 
			 * @return The Swing component is returned.
			 */
			protected JComponent createSwingComponent()
			{
				// Get the MIDP Emulator.
				MIDPEmulator emulator = MIDPEmulator.getInstance();
				// And initialize it.
				JScrollPane pane = new JScrollPane();
				pane.setBackground(new Color(0, 0, 0));
				emulator.init(pane, m_topWidth, m_topHeight, MIDPEmulator.DEVICE_USE_SCREEN);

				return pane;
			}
			
			/**
			 * Get the parent of this layout.
			 * 
			 * @return A <code>Composite</code> widget is returned.
			 */
			public Composite getLayoutAncestor()
			{
				return m_top;
			}
			
			/**
		     * This callback is invoked after the embedded Swing component has been
		     * added to this control.
		     * <p>
		     * This method is executed on the SWT thread.
		     * </p>
		     */
		    protected void afterComponentCreatedSWTThread()
		    {
				// Notify all registered observers that the component is created.
				setChanged();
				notifyObservers();
				clearChanged();

				m_swingControlCreated = true;
		    }
		    
		    /**
		     * Dispose of system resources.
		     */
		    public void dispose()
		    {
		    	super.dispose();
		    	
		    	// Get the MIDP Emulator.
				MIDPEmulator emulator = MIDPEmulator.getInstance();
				// and reset it.
				emulator.reset();
		    }
		};
    }

	public boolean isDeviceCreated()
	{
		return m_swingControlCreated;
	}

	public boolean setFocus()
	{
		return m_swingControl.setFocus();
	}
	
	public void resize(int width, int height)
	{
		m_topWidth = width;
		m_topHeight = height;
	}
	
	public void reset()
	{
		m_swingControl.dispose();
		m_swingControl = null;
	    m_swingControlCreated = false;
	    m_top = null;
	    this.deleteObservers();
	}
}
