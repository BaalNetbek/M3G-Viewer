/*
 * SceneGraphManager.java
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
package com.wizzer.m3g.viewer.domain;

// Import standard Java classes.
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Vector;

// Import M3G Toolkit classes.
import com.wizzer.m3g.AnimationController;
import com.wizzer.m3g.AnimationTrack;
import com.wizzer.m3g.Appearance;
import com.wizzer.m3g.Background;
import com.wizzer.m3g.CompositingMode;
import com.wizzer.m3g.ExternalReference;
import com.wizzer.m3g.Group;
import com.wizzer.m3g.HeaderObject;
import com.wizzer.m3g.Image2D;
import com.wizzer.m3g.IndexBuffer;
import com.wizzer.m3g.KeyframeSequence;
import com.wizzer.m3g.M3GFile;
import com.wizzer.m3g.HeaderSection;
import com.wizzer.m3g.ExternalReferencesSection;
import com.wizzer.m3g.M3GObject;
import com.wizzer.m3g.Material;
import com.wizzer.m3g.Mesh;
import com.wizzer.m3g.Node;
import com.wizzer.m3g.Object3D;
import com.wizzer.m3g.PolygonMode;
import com.wizzer.m3g.SceneSection;
import com.wizzer.m3g.SkinnedMesh;
import com.wizzer.m3g.Sprite3D;
import com.wizzer.m3g.Texture2D;
import com.wizzer.m3g.VertexArray;
import com.wizzer.m3g.VertexBuffer;
import com.wizzer.m3g.World;
import com.wizzer.m3g.toolkit.util.DumpM3g;
import com.wizzer.m3g.viewer.ui.M3gNode;

/**
 * This class manages a Mobile 3D Graphics scene graph.
 * 
 * @author Mark Millard
 */
public class SceneGraphManager extends Observable
{
	// The singleton instance of the manager.
	private static SceneGraphManager m_manager = null;
	// The Mobile 3D Graphics file.
	private static String m_filename = null;
	
	// A handle to the M3G file.
	protected M3GFile m_file = null;
	// The M3G file header section.
	protected HeaderSection m_header = null;
	// The M3G external references section.
	protected ExternalReferencesSection m_externalReferences = null;
	// The M3G scene sections.
	protected SceneSection[] m_scenes = null;

	/** The file structure root. */
	public Vector<M3gNode> m_fileroot = new Vector<M3gNode>();
	/** The scene graph root. */
	public Vector<M3gNode> m_sceneGraph = new Vector<M3gNode>();
	
	// Hide the default constructor.
	private SceneGraphManager() {}
	
	/**
	 * Get the Singleton instance of the <code>SceneGraphManager</code>.
	 * 
	 * @return The <code>SceneGraphManager</code> is returned.
	 */
	public static SceneGraphManager getInstance()
	{
		if (m_manager == null)
			m_manager = new SceneGraphManager();
		return m_manager;
	}
	
	/**
	 * Set the Mobile 3D Graphics file.
	 * 
	 * @param file The .m3g file to manage.
	 * 
	 * @throws SceneGraphException This exception is thrown if the M3G file
	 * fails to open.
	 */
	public void setFile(String file) throws SceneGraphException
	{
		m_filename = file;
		if (m_file != null)
			// Close the current file.
			close();
		// Attempt to open the file.
		open();
	}
	
	/**
	 * Get the Mobile 3D Graphics file.
	 * 
	 * @return The .m3g file name is returned. This may be <b>null</b>.
	 */
	public String getFile()
	{
		return m_filename;
	}
	
	/**
	 * Open the M3G file.
	 * <p>
	 * Validate that the file is in the correct format.
	 * </p>
	 * 
	 * @return A handle to the open file is returned.
	 * 
	 * @throws SceneGraphException This exception is thrown if an error
	 * occurs while opening the file.
	 */
	public M3GFile open() throws SceneGraphException
	{
		// Return a handle to the M3G if it has been previously opened.
		if (m_file != null)
			return m_file;
		
		// Create a File construct.
		File file = new File(m_filename);
		if ((! file.exists()) || (! file.canRead()))
			throw new SceneGraphException("File " + m_filename + " can not be opened for reading.");
		
		// Attempt to open the .m3g file.
		try
		{
		    m_file = new M3GFile(file);
		} catch (IOException ex)
		{
			throw new SceneGraphException("File " + m_filename + " can not be opened for reading.");
		}
		if (m_file == null)
			throw new SceneGraphException("File " + m_filename + " can not be opened for reading.");
		
		// Retrieve the Header Section and validate it.
		m_header = m_file.getHeaderSection();
		if (m_header == null)
		{
			throw new SceneGraphException("File " + m_filename + " is invalid.");
		} else
		{
			M3gNode headerSection = addNode(m_header, new String("Header Section"), null);
			addNode(m_header.getHeaderObject(), null, headerSection);
		}
		
		// Retrieve the External References Section.
		if (m_header.getHeaderObject().isHasExternalReferences())
		{
			m_externalReferences = m_file.getExternalReferencesSection();
			
			M3gNode externalReferencesSection = addNode(m_externalReferences, new String("External References Section"), null);
			ExternalReference[] references = m_externalReferences.getExternalReferences();
			for (int i = 0; i < references.length; i++)
			{
				addNode(references[i], null, externalReferencesSection);
			}
		}
		
		// Retrieve the Scene Sections (there should be a least one).
		m_scenes = m_file.getSceneSections();
		for (int i = 0; i < m_scenes.length; i++)
		{
			M3gNode sceneSection = addNode(m_scenes[i], new String("Scene Section " + i), null);
			Object3D[] objects = m_scenes[i].getObjects3D();
			for (int j = 0; j < objects.length; j++)
			{
				addNode(objects[j], getName(objects[j]), sceneSection);
				
				// If the object is the root,(e.g. World),
				// create a scene graph of the instance hierarchy.
				if (objects[j].isRoot())
				{
					M3gNode theRoot = buildSceneGraph(objects[j], null);
					m_sceneGraph.add(theRoot);
				}
			}
		}
		
		// Notify all registered observers of the modified scene graph.
		this.setChanged();
		this.notifyObservers(true);
		this.clearChanged();
		
		return m_file;
	}
	
	/**
	 * Close the M3G file.
	 */
	public void close()
	{
		if (m_file != null)
		    m_file.removeSceneSections();
		m_file = null;
		m_sceneGraph.clear();
		m_fileroot.clear();
		
		// Notify all registered observers of the modified scene graph.
		this.setChanged();
		this.notifyObservers(false);
		this.clearChanged();
	}
	
	/**
	 * Dump the scenegraph to the specified file.
	 * 
	 * @param filename The name of the file to dump to.
	 * 
	 * @throws SceneGraphException This exception is thrown if an error
	 * occurs while dumping the scenegraph.
	 */
	public void dumpFile(String filename) throws SceneGraphException
	{
		// Create a File construct for output.
		File file = new File(filename);
		if (! file.exists())
		{
		    // Create the file since it doesn't exist.
			try {
				file.createNewFile();
			} catch (IOException ex)
			{
				throw new SceneGraphException("File " + m_filename + " can not be created.");
			}
		} else if (! file.canWrite())
			throw new SceneGraphException("File " + m_filename + " can not be opened for writing.");
		
		// Associate the File with an output stream.
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException ex)
		{
			throw new SceneGraphException(ex.getMessage());
		}
        
		// Create a DumpM3G instance to dump the scenegraph to.
		DumpM3g dumpM3g = new DumpM3g(m_file);
		dumpM3g.setVerbose(true);
		dumpM3g.setOutputStream(out);
		
		// Dump the contents.
		try
		{
			dumpM3g.dumpHeaderSection();
			dumpM3g.dumpExternalReferenceSection();
			dumpM3g.dumpSceneSections();
		} catch (IOException ex)
		{
			throw new SceneGraphException("Unable to dump " + m_filename + ".");
		}
	}

	/**
	 * Add a Mobile 3D Graphics object to the domain model.
	 * 
	 * @param obj The M3G object to add.
	 * @param parent The parent node in the scene graph domain.
	 * If the value is <b>null</b>, then the M3G object will be
	 * added to the root of the scene graph.
	 * 
	 * @return The M3G node is returned.
	 * 
	 * @throws SceneGraphException This exception is thrown if the object
	 * being added to the domain is not a valid M3G object.
	 */
    public M3gNode addNode(Object obj, String name, M3gNode parent)
        throws SceneGraphException
    {
    	M3gNode node = null;
    	
    	if ((obj instanceof HeaderSection) || (obj instanceof HeaderObject) ||
    		(obj instanceof ExternalReferencesSection) || (obj instanceof ExternalReference) ||
    		(obj instanceof SceneSection) || (obj instanceof Object3D))
    	{
        	// Create a new node.
        	node = new M3gNode(obj, name, parent);
            // If the parent is null, then add it to the root of the scene graph.
    		if (parent == null)
    	        m_fileroot.add(node);
    	} else
    		throw new SceneGraphException("Invalid M3G Scene Graph object.");
    	
    	return node;
    }
    
    public M3gNode getRoot()
    {
    	if (m_sceneGraph.isEmpty())
    		return null;
    	else
    	    return m_sceneGraph.firstElement();
    }
    
    // Build the scene graph of the instance hierarchy.
    private M3gNode buildSceneGraph(Object3D obj, M3gNode parent)
    {
    	M3gNode root = new M3gNode(obj, getName(obj), parent);
    	
    	// Process groups.
    	if (obj instanceof Group)
    	{
    		// Process the scene graph root.
	    	if (obj instanceof World)
	    	{
	    		/*
	    		Camera camera = ((World)obj).getActiveCamera();
	    		if (camera != null)
	    		{
	    			buildSceneGraph(camera, root);
	    		}
	    		*/
	    		Background background = ((World)obj).getBackground();
	    		if (background != null)
	    		{
	    			buildSceneGraph(background, root);
	    		}
	    	}
	    	
	    	// Process the group's children.
    		int numChildren = ((Group)obj).getChildCount();
    		for (int i = 0; i < numChildren; i++)
    		{
    			Node child = ((Group)obj).getChild(i);
    			buildSceneGraph(child, root);
    		}
    	}
    	
    	// Process mesh.
    	if (obj instanceof Mesh)
    	{
    		VertexBuffer vertexBuffer = ((Mesh)obj).getVertexBuffer();
    		buildSceneGraph(vertexBuffer, root);
    		
    		int numSubmesh = ((Mesh)obj).getSubmeshCount();
    		for (int i = 0; i < numSubmesh; i++)
    		{
    			Appearance appearance = ((Mesh)obj).getAppearance(i);
    			if (appearance != null)
    			    buildSceneGraph(appearance, root);
    			
    			IndexBuffer indexBuffer = ((Mesh)obj).getIndexBuffer(i);
    			if (indexBuffer != null)
    			    buildSceneGraph(indexBuffer, root);
    		}
    		
    		if (obj instanceof SkinnedMesh)
    		{
    			Group skeleton = ((SkinnedMesh)obj).getSkeleton();
    			if (skeleton != null)
    				buildSceneGraph(skeleton, root);
    		}
    	}
    	
    	// Process appearance.
    	if (obj instanceof Appearance)
    	{
    		CompositingMode compositingMode = ((Appearance)obj).getCompositingMode();
    		if (compositingMode != null)
    		    buildSceneGraph(compositingMode, root);
    		
    		Material material = ((Appearance)obj).getMaterial();
    		if (material != null)
    		    buildSceneGraph(material, root);
    		
    		PolygonMode polygonMode = ((Appearance)obj).getPolygonMode();
    		if (polygonMode != null)
    		     buildSceneGraph(polygonMode, root);
    		
    		// Read in textures until there are no more to retrieve.
    		int index = 0;
    		Texture2D texture = ((Appearance)obj).getTexture(index++);
    		while (texture != null)
    		{
    		    buildSceneGraph(texture, root);
    		    texture = ((Appearance)obj).getTexture(index++);
    		}
    	}
    	
    	// Process vertex buffer.
    	if (obj instanceof VertexBuffer)
    	{
    		VertexArray colors = ((VertexBuffer)obj).getColors();
    		if (colors != null)
    			buildSceneGraph(colors, root);
    		
    		VertexArray normals = ((VertexBuffer)obj).getNormals();
    		if (normals != null)
    			buildSceneGraph(normals, root);
    		
    		float[] scaleBias = new float[4];
    		VertexArray positions  = ((VertexBuffer)obj).getPositions(scaleBias);
    		if (positions != null)
    			buildSceneGraph(positions, root);
    		
    		int texCoordCount = ((VertexBuffer)obj).getTexcoordArrayCount();
    		for (int i = 0; i < texCoordCount; i++)
    		{
    			VertexArray texCoords = ((VertexBuffer)obj).getTexCoords(i, scaleBias);
    			if (texCoords != null)
    				buildSceneGraph(texCoords, root);
    		}
    	}
    	
    	// Process texture.
    	if (obj instanceof Texture2D)
    	{
    		Image2D image = ((Texture2D)obj).getImage();
    		if (image != null)
    			buildSceneGraph(image, root);
    	}
    	
    	// Process sprite.
    	if (obj instanceof Sprite3D)
    	{
			Appearance appearance = ((Sprite3D)obj).getAppearance();
			if (appearance != null)
			    buildSceneGraph(appearance, root);

			Image2D image = ((Sprite3D)obj).getImage();
    		if (image != null)
    			buildSceneGraph(image, root);
    	}
    	
    	// Process animation track.
    	if (obj instanceof AnimationTrack)
    	{
    		AnimationController controller = ((AnimationTrack)obj).getController();
    		if (controller != null)
    			buildSceneGraph(controller, root);
    		KeyframeSequence sequence = ((AnimationTrack)obj).getKeyframeSequence();
    		if (sequence != null)
    			buildSceneGraph(sequence, root);
    	}
    	
    	// Process animation tracks referenced by this object.
    	int numAnimationTracks = obj.getAnimationTrackCount();
    	for (int i = 0; i < numAnimationTracks; i++)
    	{
    		buildSceneGraph(obj.getAnimationTrack(i), root);
    	}
    	
    	return root;
    }
    
    // Get a user-friendly name for the specified object.
    private String getName(Object3D object)
    {
    	String name = null;
    	
		int type = object.getObjectType();
		switch (type)
		{
			case M3GObject.ANIMATION_CONTROLLER:
				name = new String("AnimationController");
				break;
			case M3GObject.ANIMATION_TRACK:
				name = new String("AnimationTrack");
				break;
			case M3GObject.APPEARANCE:
				name = new String("Appearance");
				break;
			case M3GObject.BACKGROUND:
				name = new String("Background");
				break;
			case M3GObject.CAMERA:
				name = new String("Camera");
				break;
			case M3GObject.COMPOSITING_MODE:
				name = new String("CompositingMode");
				break;
			case M3GObject.FOG:
				name = new String("Fog");
				break;
			case M3GObject.GROUP:
				name = new String("Group");
				break;
			case M3GObject.IMAGE2D:
				name = new String("Image2D");
				break;
			case M3GObject.KEYFRAME_SEQUENCE:
				name = new String("KeyFrameSequence");
				break;
			case M3GObject.LIGHT:
				name = new String("Light");
				break;
			case M3GObject.MATERIAL:
				name = new String("Material");
				break;
			case M3GObject.MESH:
				name = new String("Mesh");
				break;
			case M3GObject.MORPHING_MESH:
				name = new String("MorphingMesh");
				break;
			case M3GObject.SKINNED_MESH:
				name = new String("SkinnedMesh");
				break;
			case M3GObject.POLYGON_MODE:
				name = new String("PolygonMode");
				break;
			case M3GObject.SPRITE3D:
				name = new String("Sprite3D");
				break;
			case M3GObject.TEXTURE2D:
				name = new String("Texture2D");
				break;
			case M3GObject.TRIANGLE_STRIP_ARRAY:
				name = new String("TriangleStripArray");
				break;
			case M3GObject.VERTEX_ARRAY:
				name = new String("VertexArray");
				break;
			case M3GObject.VERTEX_BUFFER:
				name = new String("VertexBuffer");
				break;
			case M3GObject.WORLD:
				name = new String("World");
				break;
			default:
				name = new String("UKNOWN = " +  type);
		}
		
		return name;
    }

}
