// COPYRIGHT_BEGIN
//
// Copyright (C) 2000-2008  Wizzer Works (msm@wizzerworks.com)
// 
// This file is part of the M3G Toolkit.
//
// The M3G Toolkit is free software; you can redistribute it and/or modify it
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
package com.wizzer.m3g.nvtristrip;

// This class is a quick summary of parameters used
// to begin a triangle strip.  Some operations may
// want to create lists of such items, so they were
// pulled out into a class
class NvStripStartInfo
{
	public NvFaceInfo m_startFace;
	public NvEdgeInfo m_startEdge;
	public boolean m_toV1;

	public NvStripStartInfo(NvFaceInfo startFace, NvEdgeInfo startEdge, boolean toV1)
	{
		m_startFace = startFace;
		m_startEdge = startEdge;
		m_toV1 = toV1;
	}
}