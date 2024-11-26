/*
 * M3gNodePropertySource.java
 * Created on Jun 6, 2008
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

// Import Eclipse classes.
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// Import M3G Toolkit classes.
import com.wizzer.m3g.Section;
import com.wizzer.m3g.HeaderObject;
import com.wizzer.m3g.ExternalReference;
import com.wizzer.m3g.Object3D;
import com.wizzer.m3g.AnimationController;
import com.wizzer.m3g.AnimationTrack;
import com.wizzer.m3g.Appearance;
import com.wizzer.m3g.Background;
import com.wizzer.m3g.Camera;
import com.wizzer.m3g.CompositingMode;
import com.wizzer.m3g.Fog;
import com.wizzer.m3g.Group;
import com.wizzer.m3g.Image2D;
import com.wizzer.m3g.Light;
import com.wizzer.m3g.KeyframeSequence;
import com.wizzer.m3g.Material;
import com.wizzer.m3g.Mesh;
import com.wizzer.m3g.SkinnedMesh;
import com.wizzer.m3g.MorphingMesh;
import com.wizzer.m3g.Node;
import com.wizzer.m3g.PolygonMode;
import com.wizzer.m3g.Sprite3D;
import com.wizzer.m3g.Texture2D;
import com.wizzer.m3g.Transformable;
import com.wizzer.m3g.TriangleStripArray;
import com.wizzer.m3g.VertexArray;
import com.wizzer.m3g.VertexBuffer;
import com.wizzer.m3g.World;
import com.wizzer.m3g.viewer.ui.property.AnimationControllerPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.AnimationControllerPropertySource;
import com.wizzer.m3g.viewer.ui.property.AnimationTrackPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.AnimationTrackPropertySource;
import com.wizzer.m3g.viewer.ui.property.AppearancePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.AppearancePropertySource;
import com.wizzer.m3g.viewer.ui.property.BackgroundPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.BackgroundPropertySource;
import com.wizzer.m3g.viewer.ui.property.CameraPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.CameraPropertySource;
import com.wizzer.m3g.viewer.ui.property.CompositingModePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.CompositingModePropertySource;
import com.wizzer.m3g.viewer.ui.property.FogPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.FogPropertySource;
import com.wizzer.m3g.viewer.ui.property.GroupPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.GroupPropertySource;
import com.wizzer.m3g.viewer.ui.property.HeaderPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.HeaderPropertySource;
import com.wizzer.m3g.viewer.ui.property.Image2DPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.Image2DPropertySource;
import com.wizzer.m3g.viewer.ui.property.KeyframeSequencePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.KeyframeSequencePropertySource;
import com.wizzer.m3g.viewer.ui.property.LightPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.LightPropertySource;
import com.wizzer.m3g.viewer.ui.property.MaterialPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.MaterialPropertySource;
import com.wizzer.m3g.viewer.ui.property.MeshPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.MeshPropertySource;
import com.wizzer.m3g.viewer.ui.property.MorphingMeshPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.MorphingMeshPropertySource;
import com.wizzer.m3g.viewer.ui.property.NodePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.NodePropertySource;
import com.wizzer.m3g.viewer.ui.property.Object3DPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.Object3DPropertySource;
import com.wizzer.m3g.viewer.ui.property.PolygonModePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.PolygonModePropertySource;
import com.wizzer.m3g.viewer.ui.property.SectionPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.SectionPropertySource;
import com.wizzer.m3g.viewer.ui.property.SkinnedMeshPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.SkinnedMeshPropertySource;
import com.wizzer.m3g.viewer.ui.property.Sprite3DPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.Sprite3DPropertySource;
import com.wizzer.m3g.viewer.ui.property.Texture2DPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.Texture2DPropertySource;
import com.wizzer.m3g.viewer.ui.property.TransformablePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.TransformablePropertySource;
import com.wizzer.m3g.viewer.ui.property.TriangleStripArrayPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.TriangleStripArrayPropertySource;
import com.wizzer.m3g.viewer.ui.property.VertexArrayPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.VertexArrayPropertySource;
import com.wizzer.m3g.viewer.ui.property.VertexBufferPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.VertexBufferPropertySource;
import com.wizzer.m3g.viewer.ui.property.WorldPropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.WorldPropertySource;
import com.wizzer.m3g.viewer.ui.property.ExternalReferencePropertyDescriptor;
import com.wizzer.m3g.viewer.ui.property.ExternalReferencePropertySource;

/**
 * This class is a Property Source for a M3G node, <code>M3gNode</code>.
 * 
 * @author Mark Millard
 */
public class M3gNodePropertySource implements IPropertySource
{
	// The property identifier for a M3G Section.
	private static final String PROPERTY_SECTION = "com.wizzer.m3g.viewer.ui.section";
	// The property identifier for a M3G HeaderObject.
	private static final String PROPERTY_HEADER = "com.wizzer.m3g.viewer.ui.header";
	// The property identifier for a M3G ExternalReference.
	private static final String PROPERTY_EXTERNALREFERENCE = "com.wizzer.m3g.viewer.ui.externalreference";
	// The property identifier for a M3G Object3D.
	private static final String PROPERTY_OBJECT3D = "com.wizzer.m3g.viewer.ui.object3d";
	// The property identifier for a M3G AnimationController.
	private static final String PROPERTY_ANIMATIONCONTROLLER = "com.wizzer.m3g.viewer.ui.animationcontroller";
	// The property identifier for a M3G AnimationTrack.
	private static final String PROPERTY_ANIMATIONTRACK = "com.wizzer.m3g.viewer.ui.animationtrack";
	// The property identifier for a M3G Appearance.
	private static final String PROPERTY_APPEARANCE = "com.wizzer.m3g.viewer.ui.appearance";
	// The property identifier for a M3G Background.
	private static final String PROPERTY_BACKGROUND = "com.wizzer.m3g.viewer.ui.background";
	// The property identifier for a M3G Camera.
	private static final String PROPERTY_CAMERA = "com.wizzer.m3g.viewer.ui.camera";
	// The property identifier for a M3G CompositingMode.
	private static final String PROPERTY_COMPOSITINGMODE = "com.wizzer.m3g.viewer.ui.compositingmode";
	// The property identifier for a M3G Light.
	private static final String PROPERTY_LIGHT = "com.wizzer.m3g.viewer.ui.light";
	// The property identifier for a M3G Group.
	private static final String PROPERTY_GROUP = "com.wizzer.m3g.viewer.ui.group";
	// The property identifier for a M3G Fog.
	private static final String PROPERTY_FOG = "com.wizzer.m3g.viewer.ui.fog";
	// The property identifier for a M3G Image2D.
	private static final String PROPERTY_IMAGE2D = "com.wizzer.m3g.viewer.ui.image2d";
	// The property identifier for a M3G KeyframeSequence.
	private static final String PROPERTY_KEYFRAMESEQUENCE = "com.wizzer.m3g.viewer.ui.keyframesequence";
	// The property identifier for a M3G Material.
	private static final String PROPERTY_MATERIAL = "com.wizzer.m3g.viewer.ui.material";
	// The property identifier for a M3G Mesh.
	private static final String PROPERTY_MESH = "com.wizzer.m3g.viewer.ui.mesh";
	// The property identifier for a M3G Morphing Mesh.
	private static final String PROPERTY_MORPHINGMESH = "com.wizzer.m3g.viewer.ui.morphingmesh";
	// The property identifier for a M3G Node.
	private static final String PROPERTY_NODE = "com.wizzer.m3g.viewer.ui.node";
	// The property identifier for a M3G PolygonMode.
	private static final String PROPERTY_POLYGONMODE = "com.wizzer.m3g.viewer.ui.polygonmode";
	// The property identifier for a M3G SkinnedMesh.
	private static final String PROPERTY_SKINNEDMESH = "com.wizzer.m3g.viewer.ui.skinnedmesh";
	// The property identifier for a M3G Mesh.
	private static final String PROPERTY_SPRITE3D = "com.wizzer.m3g.viewer.ui.sprite3d";
	// The property identifier for a M3G Texture2D.
	private static final String PROPERTY_TEXTURE2D = "com.wizzer.m3g.viewer.ui.texture2d";
	// The property identifier for a M3G Transformable.
	private static final String PROPERTY_TRANSFORMABLE = "com.wizzer.m3g.viewer.ui.transformable";
	// The property identifier for a M3G TriangleStripArray.
	private static final String PROPERTY_TRIANGLESTRIPARRAY = "com.wizzer.m3g.viewer.ui.trianglestriparray";
	// The property identifier for a M3G VertexArray.
	private static final String PROPERTY_VERTEXARRAY = "com.wizzer.m3g.viewer.ui.vertexarray";
	// The property identifier for a M3G VertexBuffer.
	private static final String PROPERTY_VERTEXBUFFER = "com.wizzer.m3g.viewer.ui.vertexbuffer";
	// The property identifier for a M3G World.
	private static final String PROPERTY_WORLD = "com.wizzer.m3g.viewer.ui.world";
	
	// The associated element.
	private M3gNode m_node;
	// The property name.
	private String m_text;
	// The property descriptors.
	private IPropertyDescriptor[] m_descriptors = null;
	
	// The Property Source for the M3G Section.
	private SectionPropertySource m_sectionPropertySource;
	// The Property Source for the M3G HeaderObject.
	private HeaderPropertySource m_headerPropertySource;
	// The Property Source for the M3G ExternalReference.
	private ExternalReferencePropertySource m_externalReferencePropertySource;
	// The Property Source for the M3G Object3D.
	private Object3DPropertySource m_object3DPropertySource;
	// The Property Source for the M3G AnimationController.
	private AnimationControllerPropertySource m_animationControllerPropertySource;
	// The Property Source for the M3G AnimationTrack.
	private AnimationTrackPropertySource m_animationTrackPropertySource;
	// The Property Source for the M3G Appearance.
	private AppearancePropertySource m_appearancePropertySource;
	// The Property Source for the M3G Camera.
	private CameraPropertySource m_cameraPropertySource;
	// The Property Source for the M3G CompositingMode.
	private CompositingModePropertySource m_compositingModePropertySource;
	// The Property Source for the M3G Background.
	private BackgroundPropertySource m_backgroundPropertySource;
	// The Property Source for the M3G Fog.
	private FogPropertySource m_fogPropertySource;
	// The Property Source for the M3G Group.
	private GroupPropertySource m_groupPropertySource;
	// The Property Source for the M3G Image2D.
	private Image2DPropertySource m_image2DPropertySource;
	// The Property Source for the M3G KeyframeSequence.
	private KeyframeSequencePropertySource m_keyframeSequencePropertySource;
	// The Property Source for the M3G Light.
	private LightPropertySource m_lightPropertySource;
	// The Property Source for the M3G Material.
	private MaterialPropertySource m_materialPropertySource;
	// The Property Source for the M3G Mesh.
	private MeshPropertySource m_meshPropertySource;
	// The Property Source for the M3G MorphingMesh.
	private MorphingMeshPropertySource m_morphingMeshPropertySource;
	// The Property Source for the M3G Node.
	private NodePropertySource m_nodePropertySource;
	// The Property Source for the M3G PolygonMode.
	private PolygonModePropertySource m_polygonModePropertySource;
	// The Property Source for the M3G SkinnedMesh.
	private SkinnedMeshPropertySource m_skinnedMeshPropertySource;
	// The Property Source for the M3G Sprite3D.
	private Sprite3DPropertySource m_sprite3dPropertySource;
// The Property Source for the M3G Texture2D.
	private Texture2DPropertySource m_texture2DPropertySource;
	// The Property Source for the M3G Transformable.
	private TransformablePropertySource m_transformablePropertySource;
	// The Property Source for the M3G TriangleStripArray.
	private TriangleStripArrayPropertySource m_triangleStripArrayPropertySource;
	// The Property Source for the M3G VertexArray.
	private VertexArrayPropertySource m_vertexArrayPropertySource;
	// The Property Source for the M3G VertexBuffer.
	private VertexBufferPropertySource m_vertexBufferPropertySource;
	// The Property Source for the M3G World.
	private WorldPropertySource m_worldPropertySource;
	
	// Hide the default constructor.
	public M3gNodePropertySource() {}
	
	/**
	 * Create a new property source.
	 * 
	 * @param node The associated M3G node for this property.
	 * @param name The name of the property.
	 */
	public M3gNodePropertySource(M3gNode node, String name)
	{
		m_node = node;
		m_text = name;
		
		Object ref = m_node.getM3gObject();
		if (ref instanceof Section)
		{
			m_sectionPropertySource = new SectionPropertySource((Section)ref);
	    } else if (ref instanceof HeaderObject)
		{
			m_headerPropertySource = new HeaderPropertySource((HeaderObject)ref);
		} else if (ref instanceof ExternalReference)
		{
			m_externalReferencePropertySource = new ExternalReferencePropertySource((ExternalReference)ref);
		} else if (ref instanceof Object3D)
		{
			m_object3DPropertySource = new Object3DPropertySource((Object3D) ref);
			if (ref instanceof Transformable)
			{
				m_transformablePropertySource = new TransformablePropertySource(
					(Transformable) ref);
			}
			if (ref instanceof Node)
			{
				m_nodePropertySource = new NodePropertySource(
					(Node) ref);
			}
			if (ref instanceof Group)
			{
				m_groupPropertySource = new GroupPropertySource(
					(Group) ref);
			}
			if (ref instanceof Appearance)
				m_appearancePropertySource = new AppearancePropertySource((Appearance) ref);
			else if (ref instanceof AnimationController)
				m_animationControllerPropertySource = new AnimationControllerPropertySource((AnimationController) ref);
			else if (ref instanceof AnimationTrack)
				m_animationTrackPropertySource = new AnimationTrackPropertySource((AnimationTrack) ref);
			else if (ref instanceof Camera)
				m_cameraPropertySource = new CameraPropertySource((Camera) ref);
			else if (ref instanceof CompositingMode)
				m_compositingModePropertySource = new CompositingModePropertySource((CompositingMode) ref);
			else if (ref instanceof Background)
				m_backgroundPropertySource = new BackgroundPropertySource((Background) ref);
			else if (ref instanceof Fog)
				m_fogPropertySource = new FogPropertySource((Fog) ref);
			else if (ref instanceof Image2D)
				m_image2DPropertySource = new Image2DPropertySource((Image2D) ref);
			else if (ref instanceof Light)
				m_lightPropertySource = new LightPropertySource((Light) ref);
			else if (ref instanceof KeyframeSequence)
				m_keyframeSequencePropertySource = new KeyframeSequencePropertySource((KeyframeSequence) ref);
			else if (ref instanceof Material)
				m_materialPropertySource = new MaterialPropertySource((Material) ref);
			else if (ref instanceof Mesh)
			{
				m_meshPropertySource = new MeshPropertySource((Mesh) ref);
				
				if (ref instanceof MorphingMesh)
					m_morphingMeshPropertySource = new MorphingMeshPropertySource((MorphingMesh) ref);
				else if (ref instanceof SkinnedMesh)
					m_skinnedMeshPropertySource = new SkinnedMeshPropertySource((SkinnedMesh) ref);
			}
			else if (ref instanceof PolygonMode)
				m_polygonModePropertySource = new PolygonModePropertySource((PolygonMode) ref);
			else if (ref instanceof Sprite3D)
				m_sprite3dPropertySource = new Sprite3DPropertySource((Sprite3D) ref);
			else if (ref instanceof Texture2D)
				m_texture2DPropertySource = new Texture2DPropertySource((Texture2D) ref);
			else if (ref instanceof TriangleStripArray)
				m_triangleStripArrayPropertySource = new TriangleStripArrayPropertySource((TriangleStripArray) ref);
			else if (ref instanceof VertexArray)
				m_vertexArrayPropertySource = new VertexArrayPropertySource((VertexArray) ref);
			else if (ref instanceof VertexBuffer)
				m_vertexBufferPropertySource = new VertexBufferPropertySource((VertexBuffer) ref);
			else if (ref instanceof World)
				m_worldPropertySource = new WorldPropertySource((World) ref);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue()
	{
		return this;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors()
	{
		if (m_descriptors == null)
		{
			Vector<PropertyDescriptor> descriptors = new Vector<PropertyDescriptor>();
			
			Object nodeObj = m_node.getM3gObject();
			if (nodeObj instanceof Section)
			{
				PropertyDescriptor sectionDesc = new SectionPropertyDescriptor(
					PROPERTY_SECTION, m_node.getName());
				//sectionDesc.setCategory(CATEGORY_SECTION);
				descriptors.add(sectionDesc);
			} else if (nodeObj instanceof HeaderObject)
			{
				PropertyDescriptor headerDesc = new HeaderPropertyDescriptor(
					PROPERTY_HEADER, m_node.getName());
				//headerDesc.setCategory(CATEGORY_HEADER);
				descriptors.add(headerDesc);
			} else if (nodeObj instanceof ExternalReference)
			{
				PropertyDescriptor externalReferenceDesc = new ExternalReferencePropertyDescriptor(
					PROPERTY_EXTERNALREFERENCE, m_node.getName());
				descriptors.add(externalReferenceDesc);
			} else if (nodeObj instanceof Object3D)
			{
				PropertyDescriptor object3DDesc = new Object3DPropertyDescriptor(
					PROPERTY_OBJECT3D, "Object3D");
				descriptors.add(object3DDesc);
				
				if (nodeObj instanceof Transformable)
				{
					PropertyDescriptor transformableDesc = new TransformablePropertyDescriptor(
						PROPERTY_TRANSFORMABLE, "Transformable");
					descriptors.add(transformableDesc);
				}

				if (nodeObj instanceof Node)
				{
					PropertyDescriptor nodeDesc = new NodePropertyDescriptor(
						PROPERTY_NODE, "Node");
					descriptors.add(nodeDesc);
				}

				if (nodeObj instanceof Group)
				{
					PropertyDescriptor groupDesc = new GroupPropertyDescriptor(
						PROPERTY_GROUP, "Group");
					descriptors.add(groupDesc);
				}

				if (nodeObj instanceof Appearance)
				{
					PropertyDescriptor appearanceDesc = new AppearancePropertyDescriptor(
						PROPERTY_APPEARANCE, m_node.getName());
					descriptors.add(appearanceDesc);
				} else if (nodeObj instanceof AnimationController)
				{
					PropertyDescriptor animationControllerDesc = new AnimationControllerPropertyDescriptor(
						PROPERTY_ANIMATIONCONTROLLER, m_node.getName());
					descriptors.add(animationControllerDesc);
				} else if (nodeObj instanceof AnimationTrack)
				{
					PropertyDescriptor animationTrackDesc = new AnimationTrackPropertyDescriptor(
						PROPERTY_ANIMATIONTRACK, m_node.getName());
					descriptors.add(animationTrackDesc);
				} else if (nodeObj instanceof Camera)
				{
					PropertyDescriptor cameraDesc = new CameraPropertyDescriptor(
						PROPERTY_CAMERA, m_node.getName());
					descriptors.add(cameraDesc);
				} else if (nodeObj instanceof CompositingMode)
				{
					PropertyDescriptor compositingModeDesc = new CompositingModePropertyDescriptor(
						PROPERTY_COMPOSITINGMODE, m_node.getName());
					descriptors.add(compositingModeDesc);
				} else if (nodeObj instanceof Background)
				{
					PropertyDescriptor backgroundDesc = new BackgroundPropertyDescriptor(
						PROPERTY_BACKGROUND, m_node.getName());
					descriptors.add(backgroundDesc);
				} else if (nodeObj instanceof Fog)
				{
					PropertyDescriptor fogDesc = new FogPropertyDescriptor(
						PROPERTY_FOG, m_node.getName());
					descriptors.add(fogDesc);
				} else if (nodeObj instanceof Image2D)
				{
					PropertyDescriptor image2DDesc = new Image2DPropertyDescriptor(
						PROPERTY_IMAGE2D, m_node.getName());
					descriptors.add(image2DDesc);
				} else if (nodeObj instanceof Light)
				{
					PropertyDescriptor lightDesc = new LightPropertyDescriptor(
						PROPERTY_LIGHT, m_node.getName());
					descriptors.add(lightDesc);
				} else if (nodeObj instanceof KeyframeSequence)
				{
					PropertyDescriptor keyframeSequenceDesc = new KeyframeSequencePropertyDescriptor(
						PROPERTY_KEYFRAMESEQUENCE, m_node.getName());
					descriptors.add(keyframeSequenceDesc);
				} else if (nodeObj instanceof Material)
				{
					PropertyDescriptor materialDesc = new MaterialPropertyDescriptor(
						PROPERTY_MATERIAL, m_node.getName());
					descriptors.add(materialDesc);
				} else if (nodeObj instanceof Mesh)
				{
					PropertyDescriptor meshDesc = new MeshPropertyDescriptor(
						PROPERTY_MESH, m_node.getName());
					descriptors.add(meshDesc);
					
					if (nodeObj instanceof MorphingMesh)
					{
						PropertyDescriptor morphingMeshDesc = new MorphingMeshPropertyDescriptor(
							PROPERTY_MORPHINGMESH, m_node.getName());
						descriptors.add(morphingMeshDesc);
					} else if (nodeObj instanceof SkinnedMesh)
					{
						PropertyDescriptor skinnedMeshDesc = new SkinnedMeshPropertyDescriptor(
							PROPERTY_SKINNEDMESH, m_node.getName());
						descriptors.add(skinnedMeshDesc);
					}
				} else if (nodeObj instanceof PolygonMode)
				{
					PropertyDescriptor polygonModeDesc = new PolygonModePropertyDescriptor(
						PROPERTY_POLYGONMODE, m_node.getName());
					descriptors.add(polygonModeDesc);
				} else if (nodeObj instanceof Sprite3D)
				{
					PropertyDescriptor sprite3dDesc = new Sprite3DPropertyDescriptor(
						PROPERTY_SPRITE3D, m_node.getName());
					descriptors.add(sprite3dDesc);
				} else if (nodeObj instanceof Texture2D)
				{
					PropertyDescriptor texture2DDesc = new Texture2DPropertyDescriptor(
						PROPERTY_TEXTURE2D, m_node.getName());
					descriptors.add(texture2DDesc);
				} else if (nodeObj instanceof TriangleStripArray)
				{
					PropertyDescriptor triangleStripArrayDesc = new TriangleStripArrayPropertyDescriptor(
						PROPERTY_TRIANGLESTRIPARRAY, m_node.getName());
					descriptors.add(triangleStripArrayDesc);
				} else if (nodeObj instanceof VertexArray)
				{
					PropertyDescriptor vertexArrayDesc = new VertexArrayPropertyDescriptor(
						PROPERTY_VERTEXARRAY, m_node.getName());
					descriptors.add(vertexArrayDesc);
				} else if (nodeObj instanceof VertexBuffer)
				{
					PropertyDescriptor vertexBufferDesc = new VertexBufferPropertyDescriptor(
						PROPERTY_VERTEXBUFFER, m_node.getName());
					descriptors.add(vertexBufferDesc);
				} else if (nodeObj instanceof World)
				{
					PropertyDescriptor worldDesc = new WorldPropertyDescriptor(
						PROPERTY_WORLD, m_node.getName());
					descriptors.add(worldDesc);
				}
			}
			
			Object[] objs = descriptors.toArray();
			m_descriptors = new IPropertyDescriptor[objs.length];
			for (int i = 0; i < m_descriptors.length; i++)
			{
				m_descriptors[i] = (IPropertyDescriptor)objs[i];
			}
		}
		
		return m_descriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id)
	{
		Object value = null;
		
		if (id.equals(PROPERTY_SECTION))
			value = m_sectionPropertySource;
		else if (id.equals(PROPERTY_HEADER))
			value = m_headerPropertySource;
		else if (id.equals(PROPERTY_EXTERNALREFERENCE))
			value = m_externalReferencePropertySource;
		else if (id.equals(PROPERTY_OBJECT3D))
			value = m_object3DPropertySource;
		else if (id.equals(PROPERTY_TRANSFORMABLE))
			value = m_transformablePropertySource;
		else if (id.equals(PROPERTY_APPEARANCE))
			value = m_appearancePropertySource;
		else if (id.equals(PROPERTY_ANIMATIONCONTROLLER))
			value = m_animationControllerPropertySource;
		else if (id.equals(PROPERTY_ANIMATIONTRACK))
			value = m_animationTrackPropertySource;
		else if (id.equals(PROPERTY_CAMERA))
			value = m_cameraPropertySource;
		else if (id.equals(PROPERTY_COMPOSITINGMODE))
			value = m_compositingModePropertySource;
		else if (id.equals(PROPERTY_BACKGROUND))
			value = m_backgroundPropertySource;
		else if (id.equals(PROPERTY_FOG))
			value = m_fogPropertySource;
		else if (id.equals(PROPERTY_GROUP))
			value = m_groupPropertySource;
		else if (id.equals(PROPERTY_IMAGE2D))
			value = m_image2DPropertySource;
		else if (id.equals(PROPERTY_LIGHT))
			value = m_lightPropertySource;
		else if (id.equals(PROPERTY_KEYFRAMESEQUENCE))
			value = m_keyframeSequencePropertySource;
		else if (id.equals(PROPERTY_MATERIAL))
			value = m_materialPropertySource;
		else if (id.equals(PROPERTY_MESH))
			value = m_meshPropertySource;
		else if (id.equals(PROPERTY_MORPHINGMESH))
			value = m_morphingMeshPropertySource;
		else if (id.equals(PROPERTY_SKINNEDMESH))
			value = m_skinnedMeshPropertySource;
		else if (id.equals(PROPERTY_NODE))
			value = m_nodePropertySource;
		else if (id.equals(PROPERTY_POLYGONMODE))
			value = m_polygonModePropertySource;
		else if (id.equals(PROPERTY_SPRITE3D))
			value = m_sprite3dPropertySource;
		else if (id.equals(PROPERTY_TEXTURE2D))
			value = m_texture2DPropertySource;
		else if (id.equals(PROPERTY_TRIANGLESTRIPARRAY))
			value = m_triangleStripArrayPropertySource;
		else if (id.equals(PROPERTY_VERTEXARRAY))
			value = m_vertexArrayPropertySource;
		else if (id.equals(PROPERTY_VERTEXBUFFER))
			value = m_vertexBufferPropertySource;
		else if (id.equals(PROPERTY_WORLD))
			value = m_worldPropertySource;
			
		return value;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id)
	{
		if (PROPERTY_SECTION.equals(id) ||
			PROPERTY_HEADER.equals(id) ||
			PROPERTY_EXTERNALREFERENCE.equals(id) ||
			PROPERTY_OBJECT3D.equals(id) ||
			PROPERTY_TRANSFORMABLE.equals(id) ||
			PROPERTY_ANIMATIONCONTROLLER.equals(id) ||
			PROPERTY_ANIMATIONTRACK.equals(id) ||
			PROPERTY_APPEARANCE.equals(id) ||
			PROPERTY_CAMERA.equals(id) ||
			PROPERTY_COMPOSITINGMODE.equals(id) ||
			PROPERTY_BACKGROUND.equals(id) ||
			PROPERTY_FOG.equals(id) ||
			PROPERTY_GROUP.equals(id) ||
			PROPERTY_IMAGE2D.equals(id) ||
			PROPERTY_KEYFRAMESEQUENCE.equals(id) ||
			PROPERTY_LIGHT.equals(id) ||
			PROPERTY_MATERIAL.equals(id) ||
			PROPERTY_MESH.equals(id) ||
			PROPERTY_MORPHINGMESH.equals(id) ||
			PROPERTY_NODE.equals(id) ||
			PROPERTY_POLYGONMODE.equals(id) ||
			PROPERTY_SPRITE3D.equals(id) ||
			PROPERTY_SKINNEDMESH.equals(id) ||
			PROPERTY_TRIANGLESTRIPARRAY.equals(id) ||
			PROPERTY_TEXTURE2D.equals(id) ||
			PROPERTY_VERTEXARRAY.equals(id) ||
			PROPERTY_VERTEXBUFFER.equals(id) ||
			PROPERTY_WORLD.equals(id))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
	 */
	public void resetPropertyValue(Object id)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value)
	{
		// TODO Auto-generated method stub
	}

}
