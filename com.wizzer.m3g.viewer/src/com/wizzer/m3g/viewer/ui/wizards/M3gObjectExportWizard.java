/*
 * M3gObjectExportWizard.java
 * Created on Aug 14, 2008
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
package com.wizzer.m3g.viewer.ui.wizards;

// Import standard Java classes.
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

// Import Eclipse classes.
import org.eclipse.ui.IWorkbench;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.viewers.IStructuredSelection;

// Import M3G Toolkit classes.
import com.wizzer.m3g.M3GObject;
import com.wizzer.m3g.Object3D;

// Import M3G Viewer classes.
import com.wizzer.m3g.viewer.domain.SceneGraphMarshaller;
import com.wizzer.m3g.viewer.ui.M3gNode;


/**
 * The <code>M3gNodeExportWizard</code> implements a Wizard for
 * constructing a valid Mobile 3D Grpahics file containing just
 * one <code>M3GObject</code> and its corresponding dependencies.
 * <p>
 * For example, a <code>Mesh</code> object will be exported along
 * with it's <code>Appearance</code>, <code>TriangleStripArray</code>
 * and <code>VertexBuffer</code> objects.
 * </p>
 * 
 * @author Mark Millard
 */
public class M3gObjectExportWizard extends Wizard
{
	/** The associated Workbench item. */
	protected IWorkbench m_workbench;
	/** The associated selection. */
	protected IStructuredSelection m_selection;
	/** The selected M3G Object. */
	protected Object3D m_m3gObj = null;
	
	// The wizard's export page.
	private M3gObjectExportWizardPage m_exportPage;
	
	/**
	 * Initialize the wizard.
	 * 
	 * @param workbench
	 * @param selection
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
	   m_workbench = workbench;
	   m_selection = selection;
	   if (selection != null && !selection.isEmpty())
	   {
	       Object obj = selection.getFirstElement();
		   if (obj instanceof M3gNode)
		   {
			   M3gNode node = (M3gNode) obj;
			   M3GObject m3gObj = (M3GObject) node.getM3gObject();
			   m_m3gObj = (Object3D) m3gObj;
			   
			   // XXX - Initialize the Wizard pages with data from the M3G Object.
		   }
	    }
    }
	
	/**
	 * Add pages to the wizard.
	 */
	@Override
	public void addPages()
	{
		m_exportPage = new M3gObjectExportWizardPage("Export M3G Object");
	    addPage(m_exportPage);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish()
	{
		String filename = m_exportPage.getFilename();
		if ((filename == null) || (filename.equals("")))
			return false;
		
		// Create a new .m3g file.
		try
		{
			Logger.global.logp(Level.INFO,
				"com.wizzer.m3g.viewer.ui.wizards", "M3gObjectExportWizard",
				"Exporting .m3g file " + filename);

			File file = new File(filename);
			FileOutputStream os = new FileOutputStream(file);
			SceneGraphMarshaller writer = new SceneGraphMarshaller(os);
			writer.addScene(m_m3gObj);
			writer.marshall();
		} catch (FileNotFoundException ex)
		{
			Logger.global.logp(Level.WARNING,
				"com.wizzer.m3g.viewer.ui.wizards", "M3gObjectExportWizard",
				ex.getMessage());
		} catch (IOException ex)
		{
			Logger.global.logp(Level.WARNING,
				"com.wizzer.m3g.viewer.ui.wizards", "M3gObjectExportWizard",
				ex.getMessage());			
		}
		
		return true;
	}
}
