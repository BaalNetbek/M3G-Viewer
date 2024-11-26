/*
 * PathVar.java
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

// Import standard Java classes.
import java.util.ArrayList;

/**
 * This class is used to decompose a path list into its separable
 * elements. The path list is derived from a system environment variable
 * (e.g. PATH).
 * 
 * @author Mark Millard
 */
public class PathVar
{
	/** The environment variable to manage. */
	protected String m_envVariable;
	/** The path list separator. */
	protected String m_separator;
	/** The array of path elements extracted from the environment variable. */
	protected ArrayList<String> m_paths;
	
	/**
	 * The default constructor.
	 * <p>
	 * The default system environment variable is <b>PATH</b>. The default
	 * separator will be <b>":"</b>.
	 * </p>
	 */
	public PathVar()
	{
		m_envVariable = "PATH";
		m_separator = ":";
		m_paths = new ArrayList<String>();
		String path = System.getenv(m_envVariable);
		buildPaths(path);
	}
	
	/**
	 * Construct a <code>PathVar</code> from the specified environment
	 * variable.
	 * 
	 * @param var The system environment variable to deconstruct.
	 * 
	 * @throws IllegalArgumentException This exception is thrown if <i>var</i>
	 * is <b>null</b>.
	 */
	public PathVar(String var) throws IllegalArgumentException
	{
		if (var == null)
			throw new IllegalArgumentException();
		
		m_envVariable = var;
		m_separator = ":";
		m_paths = new ArrayList<String>();
		String path = System.getenv(m_envVariable);
		buildPaths(path);
	}

	/**
	 * Construct a <code>PathVar</code> from the specified environment
	 * variable. Use the specified separator for parsing the value of
	 * the environment variable. 
	 * 
	 * @param var The system environment variable to deconstruct.
	 * @param separator The separator string to use for deconstruction.
	 * 
	 * @throws IllegalArgumentException This exception is thrown if <i>var</i>
	 * or <i>separator</i> is <b>null</b>.
	 */
	public PathVar(String var, String separator)
	{
		if ((var == null) || (separator == null))
			throw new IllegalArgumentException();

		m_envVariable = var;
		m_separator = separator;
		m_paths = new ArrayList<String>();
		String path = System.getenv(m_envVariable);
		buildPaths(path);
	}
	
	/**
	 * Get the deconstructed paths from the system environment variable.
	 * <p>
	 * Each element in the returned array is a separate path entry.
	 * </p>
	 * 
	 * @return An array of paths is returned.
	 */
	public String[] getPaths()
	{
		Object[] objs = m_paths.toArray();
		String[] paths = new String[objs.length];
		for (int i = 0; i < objs.length; i++)
		{
			paths[i] = new String((String)objs[i]);
		}
		return (paths) ;
	}

	/**
	 * Add a path to the system environment variable.
	 * 
	 * @param path The path to add.
	 */
	protected void addPath(String path)
	{
		if (path == null)
			throw new IllegalArgumentException();

		if (m_paths.contains(path))
			return;
		else
			m_paths.add(path);
	}
	
	/**
	 * Remove a path from the system environment variable.
	 * 
	 * @param path The path to remove.
	 */
	protected void removePath(String path)
	{
		if (path == null)
			throw new IllegalArgumentException();

		if (m_paths.contains(path))
			m_paths.remove(path);
	}
	
	// Deconstruct the system environment variable into its separable
	// path elements.
	private int buildPaths(String pathList)
	{
		if (pathList != null)
		{
			String p = new String(pathList);

			int begin = 0;
			int end = p.indexOf(m_separator);
			while (end != -1)
			{
				// Extract element from modified string.
				String element = p.substring(begin,end);
				addPath(element);
				
				p = p.substring(end + 1);
				end = p.indexOf(m_separator);
			}
			
			// Add last element.
			String element = p.substring(begin);
			addPath(element);
		}

		return m_paths.size();
	}
	
	/**
	 * Print the value of the system environment variable.
	 * 
	 * @return A <code>String</code> is returned containing the value
	 * of the system environment variable.
	 */
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		String[] paths = getPaths();
		for (int i = 0; i < paths.length; i++)
		{
			buffer.append(paths[i]);
			if (i < (paths.length - 1))
			    buffer.append(m_separator);
		}
		return buffer.toString();
	}
}
