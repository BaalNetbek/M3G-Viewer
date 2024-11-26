/*
 * PathVarTest.java
 * Created on Jul 24, 2008
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
package com.wizzer.m3g.viewer;

// Import JUnit classes.
import junit.framework.TestCase;

/**
 * This class is a unit test for the com.wizzer.m3g.viewer.PathVar class.
 * 
 * @author Mark Millard
 */
public class PathVarTest extends TestCase
{
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	 * Test method for {@link com.wizzer.m3g.viewer.PathVar#PathVar()}.
	 */
	public void testPathVar()
	{
		PathVar pathVar = new PathVar();
		assertNotNull(pathVar);
		assertEquals(":", pathVar.m_separator);
		assertEquals("PATH", pathVar.m_envVariable);
	}

	/**
	 * Test method for {@link com.wizzer.m3g.viewer.PathVar#PathVar(java.lang.String)}.
	 */
	public void testPathVarString()
	{
		PathVar pathVar = new PathVar("PATH");
		assertNotNull(pathVar);
		assertEquals(":", pathVar.m_separator);
		assertEquals("PATH", pathVar.m_envVariable);
	}

	/**
	 * Test method for {@link com.wizzer.m3g.viewer.PathVar#PathVar(java.lang.String, java.lang.String)}.
	 */
	public void testPathVarStringString()
	{
		PathVar pathVar = new PathVar("PATH",";");
		assertNotNull(pathVar);
		assertEquals(";", pathVar.m_separator);
		assertEquals("PATH", pathVar.m_envVariable);
	}

	/**
	 * Test method for {@link com.wizzer.m3g.viewer.PathVar#getPaths()}.
	 */
	public void testGetPaths()
	{
		PathVar pathVar = new PathVar("PATH",";");
		assertNotNull(pathVar);
		assertEquals(";", pathVar.m_separator);
		assertEquals("PATH", pathVar.m_envVariable);
		
		String[] paths = pathVar.getPaths();
		assertNotNull(paths);
	}

	/**
	 * Test method for {@link com.wizzer.m3g.viewer.PathVar#addPath(java.lang.String)}.
	 */
	public void testAddPath()
	{
		PathVar pathVar = new PathVar("PATH",";");
		assertNotNull(pathVar);
		assertEquals(";", pathVar.m_separator);
		assertEquals("PATH", pathVar.m_envVariable);

		String[] paths = pathVar.getPaths();
		assertNotNull(paths);
		int size1 = paths.length;

		pathVar.addPath("C:\\Users\\msm");
		paths = pathVar.getPaths();
		assertNotNull(paths);
		int size2 = paths.length;
		
		assertEquals(size2, size1+1);
	}

	/**
	 * Test method for {@link com.wizzer.m3g.viewer.PathVar#removePath(java.lang.String)}.
	 */
	public void testRemovePath()
	{
		PathVar pathVar = new PathVar("PATH",";");
		assertNotNull(pathVar);
		assertEquals(";", pathVar.m_separator);
		assertEquals("PATH", pathVar.m_envVariable);

		String[] paths = pathVar.getPaths();
		assertNotNull(paths);
		int size1 = paths.length;

		pathVar.addPath("C:\\Users\\msm");
		paths = pathVar.getPaths();
		assertNotNull(paths);
		pathVar.removePath("C:\\Users\\msm");
		paths = pathVar.getPaths();
		assertNotNull(paths);
		int size2 = paths.length;
		
		assertEquals(size2, size1);
	}

}
