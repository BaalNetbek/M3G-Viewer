/*
 * SceneGraphMarshaller.java
 * Created on Aug 15, 2008
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
package com.wizzer.m3g.viewer.domain;

// Import standard Java classes.
import java.io.OutputStream;
import java.io.IOException;

// Import M3G Toolkit classes.
import com.wizzer.m3g.M3GFile;
import com.wizzer.m3g.Object3D;
import com.wizzer.m3g.SceneSection;

/**
 * This class is used to marshall M3G Scene Graphs to output streams.
 * 
 * @author Mark Millard
 */
public class SceneGraphMarshaller
{
	/** The output file. */
	protected M3GFile m_outputFile;
	/** The output stream. */
	protected OutputStream m_output;
	
	// Hide the default constructor.
    private SceneGraphMarshaller() {}
    
    /**
     * Constructs a <code>SceneGraphMarshaller</code> with the specified
     * output stream.
     * 
     * @param out The output stream to marshall to.
     */
    public SceneGraphMarshaller(OutputStream out)
    {
    	if (out == null)
    		throw new NullPointerException("SceneGraphMarshaller: out is null");
    	
    	m_output = out;
    	m_outputFile = new M3GFile();
    }
    
    /**
     * Add a new Scene wrapping the specified M3G object.
     * 
     * @param obj The M3G object.
     * 
     * @return A <code>SceneSection</code> is returned.
     */
    public SceneSection addScene(Object3D obj)
    {
    	if (obj == null)
    		throw new NullPointerException("SceneGraphMarshaller: obj is null");

    	SceneSection scene = new SceneSection();
    	scene.addObject3D(obj);
    	m_outputFile.addSceneSection(scene);
    	
    	return scene;
    }
    
    /**
     * Remove a Scene from the marshaller.
     * 
     * @param scene The <code>SecneSection</code> to remove.
     */
    public void removeScene(SceneSection scene)
    {
    	if (scene == null)
    		throw new NullPointerException("SceneGraphMarshaller: scene is null");

    	m_outputFile.removeSceneSection(scene);
    }
    
    /**
     * Write the scene graph.
     * 
     * @throws IOException
     */
    public void marshall() throws IOException
    {
    	m_outputFile.marshall(m_output);
    }
}
