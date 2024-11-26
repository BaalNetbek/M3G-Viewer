/*
 * M3gGraphView.java
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
package com.wizzer.m3g.viewer.ui;

// Import standard Java classes.
import java.util.Vector;
import java.util.Observer;
import java.util.Observable;

// Import Eclipse classes.
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

// Import M3G Toolkit classes.
import com.wizzer.m3g.viewer.Activator;
import com.wizzer.m3g.viewer.domain.SceneGraphManager;
import com.wizzer.m3g.viewer.ui.actions.DisplayM3gAction;
import com.wizzer.m3g.viewer.ui.wizards.M3gObjectExportWizard;
import com.wizzer.m3g.viewer.ui.actions.RefreshDisplayAction;


public class M3gGraphView extends ViewPart implements Observer
{
	// The view part's identifier.
	public static final String ID = "com.wizzer.m3g.viewer.ui.m3ggraphview";

	// The Tree Viewer for the graph view.
	private TreeViewer m_graphViewer;
	// The node export Action.
	private Action m_exportAction;
	// The display 3D Action.
	private Action m_displayAction;
	// The refresh display Action;
	private Action m_refreshDisplayAction;

	// The root of the scene graph.
	static Vector<M3gNode> g_sceneGraph = new Vector<M3gNode>();

    /**
     * The default constructor.
     * <p>
     * Adds itself as an Observer to the <code>SceneGraphManager</code>.
     * </p>
     */
    public M3gGraphView()
    {
    	SceneGraphManager manager = SceneGraphManager.getInstance();
    	manager.addObserver(this);
    }
    
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent)
	{
        // Create and initialize a Tree View of the scene graph.
		m_graphViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		m_graphViewer.setContentProvider(new M3gContentProvider());
		m_graphViewer.setLabelProvider(new M3gLabelProvider());
		m_graphViewer.setInput(g_sceneGraph);
        
        // Set up default selection provider for Properties View.
        getSite().setSelectionProvider(m_graphViewer);

        // Create menu and toolbars.
        createActions();
        //createMenu();
        createToolbar();
        createContextMenu();
	}

	// Create the actions for the menus and toolbars.
	private void createActions()
	{
        m_exportAction = new Action("Export...")
        {
            public void run()
            { 
                System.out.println("Exporting M3gNode!");
                IStructuredSelection selection = 
                    (IStructuredSelection)m_graphViewer.getSelection();
               
                // Instantiates and initializes the wizard.
                M3gObjectExportWizard wizard = new M3gObjectExportWizard();
                wizard.init(getSite().getWorkbenchWindow().getWorkbench(),
                            (IStructuredSelection)selection);
                // Instantiates the wizard container with the wizard and opens it.
                WizardDialog dialog = new WizardDialog(getSite().getShell(), wizard);
                dialog.create();
                dialog.setTitle("M3G Object Export Wizard");
                dialog.setMessage("Create a new .m3g file from the selected object.");
                dialog.open();
            }
        };
        m_exportAction.setImageDescriptor(Activator.getImageDescriptor("icons/cube_16.png"));

        m_displayAction = new DisplayM3gAction();
        m_refreshDisplayAction = new RefreshDisplayAction();
        
        // Add selection listener.
        m_graphViewer.addSelectionChangedListener(new ISelectionChangedListener()
        {
            public void selectionChanged(SelectionChangedEvent event)
            {
                updateActionEnablement();
            }
        });
    }
	
	// Create the view's toolbar.
	private void createToolbar()
	{
		IActionBars actionBars = getViewSite().getActionBars();
		IMenuManager dropDownMenu = actionBars.getMenuManager();
		m_displayAction.setEnabled(false);
		m_refreshDisplayAction.setEnabled(false);
		dropDownMenu.add(m_displayAction);
		dropDownMenu.add(m_refreshDisplayAction);
	}
	
	private void updateActionEnablement()
	{
        IStructuredSelection sel = 
                (IStructuredSelection)m_graphViewer.getSelection();
        //deleteItemAction.setEnabled(sel.size() > 0);
    }

	// Create the context menu.
	private void createContextMenu()
	{
        // Create menu manager.
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener()
        {
            public void menuAboutToShow(IMenuManager mgr)
            {
                fillContextMenu(mgr);
            }
        });
        
        // Create menu.
        Menu menu = menuMgr.createContextMenu(m_graphViewer.getControl());
        m_graphViewer.getControl().setMenu(menu);
        
        // Register menu for extension.
        getSite().registerContextMenu(menuMgr, m_graphViewer);
    }
	
	private void fillContextMenu(IMenuManager mgr)
	{
        mgr.add(m_exportAction);
        mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        //mgr.add(deleteItemAction);
        //mgr.add(new Separator());
        //mgr.add(selectAllAction);
    }

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
		m_graphViewer.getControl().setFocus();
	}
	
	/**
	 * Enable the display button.
	 * 
	 * @param enabled <b>true</b> if the display button should be
	 * displayed. Otherwise set to <b>false</b>.
	 */
	public void enableDisplay(boolean enabled)
	{
		m_displayAction.setEnabled(enabled);
	}
	
	/**
	 * Enable the refresh display button.
	 * 
	 * @param enabled <b>true</b> if the refresh display button should be
	 * displayed. Otherwise set to <b>false</b>.
	 */
	public void enableRefreshDisplay(boolean enabled)
	{
		m_refreshDisplayAction.setEnabled(enabled);
	}

	/**
	 * This method is called whenever the observed object is changed.
	 * An application calls an Observable object's notifyObservers method to have
	 * all the object's observers notified of the change.
	 * 
	 * @param observable The observable object.
	 * @param obj An argument passed to the notifyObservers method.
	 */
	public void update(Observable observable, Object obj)
	{
		if (observable instanceof SceneGraphManager)
		{		
			// Update the graph view.
			Vector<M3gNode> sceneGraph = ((SceneGraphManager)observable).m_sceneGraph;
			g_sceneGraph.removeAllElements();
			for (int i = 0; i < sceneGraph.size(); i++)
				g_sceneGraph.add(sceneGraph.elementAt(i));
			m_graphViewer.refresh();
			m_graphViewer.expandAll();
		}
	}

}