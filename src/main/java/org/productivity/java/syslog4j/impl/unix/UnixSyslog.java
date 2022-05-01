/**
 *
 * (C) Copyright 2008-2011 syslog4j.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package org.productivity.java.syslog4j.impl.unix;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import org.productivity.java.syslog4j.SyslogMessageProcessorIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslog;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.util.OSDetectUtility;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;

/**
* UnixSyslog is an extension of AbstractSyslog that provides support for
* Unix-based syslog clients.
*
* <p>This class requires the JNA (Java Native Access) library to directly
* access the native C libraries utilized on Unix platforms.</p>
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UnixSyslog.java,v 1.27 2010/10/25 04:21:19 cvs Exp $
*/
public class UnixSyslog extends AbstractSyslog {
    protected UnixSyslogConfig unixSyslogConfig = null;

    protected interface CLibrary extends Library {
        void openlog(final Memory ident, int option, int facility);
        void syslog(int priority, final String format, final String message);
        void closelog();
    }

    private static int currentFacility = -1;
    private static boolean openlogCalled = false;

    private static CLibrary libraryInstance = null;
    private static final Map<String, Memory> identMap = Maps.newHashMap();

    protected static synchronized void loadLibrary(UnixSyslogConfig config) throws SyslogRuntimeException {
        if (!OSDetectUtility.isUnix()) {
            throw new SyslogRuntimeException("UnixSyslog not supported on non-Unix platforms");
        }

        if (libraryInstance == null) {
            libraryInstance = (CLibrary) Native.loadLibrary(config.getLibrary(),CLibrary.class);
        }
    }

    private static void setOpenlogCalled()
    {
        openlogCalled = true;
    }

    public void initialize() throws SyslogRuntimeException {
        try {
            this.unixSyslogConfig = (UnixSyslogConfig) this.syslogConfig;

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("config must be of type UnixSyslogConfig");
        }

        loadLibrary(this.unixSyslogConfig);
    }

    protected static void write(int level, String message, UnixSyslogConfig config) throws SyslogRuntimeException {
        synchronized(libraryInstance) {
            if (currentFacility != config.getFacility()) {
                if (openlogCalled) {
                    libraryInstance.closelog();
                    openlogCalled = false;
                }

                currentFacility = config.getFacility();
            }

            if (!openlogCalled) {
                String ident = config.getIdent();

                if (!StringUtils.isBlank(ident)) {
                    ident = null;
                }

				Memory identBuffer = ident == null ? null : (Memory) identMap.get(ident);

				if (ident != null && identBuffer == null) {
                    identBuffer = new Memory(128);
                    identBuffer.setString(0, ident);
                    identMap.put(ident, identBuffer);
                }

                libraryInstance.openlog(identBuffer,config.getOption(),currentFacility);
                openlogCalled = true;
            }

            int priority = currentFacility | level;

            libraryInstance.syslog(priority,"%s",message);
        }
    }

    protected void write(int level, byte[] message) throws SyslogRuntimeException {
        // NO-OP
    }

    public void log(SyslogMessageProcessorIF messageProcessor, int level, String message) {
        write(level,message,this.unixSyslogConfig);
    }

    public void flush() throws SyslogRuntimeException {
        synchronized(libraryInstance) {
            libraryInstance.closelog();
            UnixSyslog.setOpenlogCalled();
        }
    }

    public void shutdown() throws SyslogRuntimeException {
        flush();
    }

    public AbstractSyslogWriter getWriter() {
        return null;
    }

    public void returnWriter(AbstractSyslogWriter syslogWriter) {
        //
    }
}
