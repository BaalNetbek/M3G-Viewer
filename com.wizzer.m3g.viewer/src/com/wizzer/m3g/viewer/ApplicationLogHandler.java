/*
 * ApplicationLogHandler.java
 * Created on Aug 16, 2008
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * This class is a handler for logging messages to the application's
 * console.
 * 
 * @author Mark Millard
 */
public class ApplicationLogHandler extends Handler
{
	/* (non-Javadoc)
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() throws SecurityException
	{
		Application.getConsole().clearConsole();

	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush()
	{
		// Nothing to do here.
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public void publish(LogRecord record)
	{
		Date date = new Date(record.getMillis());
		DateFormat df = new SimpleDateFormat("yyyy.MMMMM.dd hh:mm:ss aaa");
		
		StringBuffer msgBuffer = new StringBuffer();
		msgBuffer.append(record.getLevel().toString());
		msgBuffer.append(": ");
		msgBuffer.append(df.format(date));
		msgBuffer.append(": ");
		msgBuffer.append(record.getMessage());
		
		Application.printMessage(msgBuffer.toString());
	}

}
