/*
 * M3gObjectExportWizardPage.java
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
import java.io.IOException;

// Import Eclipse classes.
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

// Import M3G Viewer classes.
import com.wizzer.m3g.viewer.domain.SceneGraphException;
import com.wizzer.m3g.viewer.domain.SceneGraphManager;


/**
 * @author Mark Millard
 */
public class M3gObjectExportWizardPage extends WizardPage implements Listener
{
	/** The filename Text widget. */
	protected Text m_filenameText;
	/** The browse Button widget. */
	protected Button m_browseButton;

	/**
	 * @param pageName
	 */
	public M3gObjectExportWizardPage(String pageName)
	{
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public M3gObjectExportWizardPage(String pageName, String title,
	    ImageDescriptor titleImage)
	{
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent)
	{
	    // Create the composite to hold the widgets.
		Composite composite = new Composite(parent, SWT.NONE);
		
		// Create the desired layout for this wizard page.
		GridLayout gl = new GridLayout();
		int ncol = 3;
		gl.numColumns = ncol;
		composite.setLayout(gl);		
 
		Label label = new Label(composite, SWT.NONE);
		label.setText( "File:");
		
		m_filenameText = new Text(composite, SWT.BORDER);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    m_filenameText.setLayoutData(gd);
	    m_filenameText.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent event)
	    	{
	    		handleModifyEvent(event);
	    	}
	    });
	    
	    m_browseButton = new Button(composite, SWT.PUSH);
	    m_browseButton.setText("Browse...");
	    m_browseButton.addListener(SWT.Selection, this);

		// Set the composite as the control for this page.
		setControl(composite);
	}
	
	// Return the specified filename for the new .m3g file.
	public String getFilename()
	{
		return m_filenameText.getText();
	}

	/**
	 * Handle the widget events.
	 */
	public void handleEvent(Event event)
	{
	     // Initialize a variable with the no error status.
	     Status status = new Status(IStatus.OK, "not_used", 0, "", null);
	     if (event.widget == m_browseButton)
	     {
	    	 handleBrowseButtonEvent(event);
	     }
	}
	
	// Handle the Browse button event.
	private void handleBrowseButtonEvent(Event event)
	{
		String[] extensions = {"*.m3g", "*.*"};
		Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		
		// Create a new file dialog and initialize it.
		FileDialog dialog = new FileDialog(shell,SWT.SAVE);
		dialog.setFilterExtensions(extensions);
		String filename = dialog.open();
		if (filename != null)
		{
			SceneGraphManager scenegraph = SceneGraphManager.getInstance();
			try {
			    m_filenameText.setText(filename);
			} catch (Exception ex)
			{
				MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR);
				msg.setText("M3G Viewer Error");
				msg.setMessage(ex.getMessage() + "\nUnable to create a new .m3g file.");
				msg.open();
			}
		}
	}
	
	// Handle the Modify event from the filename Text widget.
	private void handleModifyEvent(ModifyEvent event)
	{
		File file = new File(m_filenameText.getText());
		File dir = file.getParentFile();
		if (dir != null)
		{
			if (dir.isDirectory())
			{
				this.setErrorMessage(null);
				this.setMessage("Create a new .m3g file from the selected object.");
			} else
			{
				this.setErrorMessage("Invalid directory path " + dir.getPath());
			}
		}
	}
}
