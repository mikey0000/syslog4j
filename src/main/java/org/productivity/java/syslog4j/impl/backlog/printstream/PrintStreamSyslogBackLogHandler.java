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
package org.productivity.java.syslog4j.impl.backlog.printstream;

import java.io.PrintStream;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.backlog.AbstractSyslogBackLogHandler;

/**
* PrintStreamSyslogBackLogHandler provides a last-chance mechanism to log messages that fail
* (for whatever reason) within the rest of Syslog to a PrintStream.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PrintStreamSyslogBackLogHandler.java,v 1.1 2009/01/24 22:00:18 cvs Exp $
*/
public class PrintStreamSyslogBackLogHandler extends AbstractSyslogBackLogHandler {
    protected PrintStream printStream = null;
    protected boolean appendLinefeed = false;

    public PrintStreamSyslogBackLogHandler(PrintStream printStream) {
        this.printStream = printStream;

        initialize();
    }

    public PrintStreamSyslogBackLogHandler(PrintStream printStream, boolean appendLinefeed) {
        this.printStream = printStream;
        this.appendLinefeed = appendLinefeed;

        initialize();
    }

    public PrintStreamSyslogBackLogHandler(PrintStream printStream, boolean appendLinefeed, boolean appendReason) {
        this.printStream = printStream;
        this.appendLinefeed = appendLinefeed;
        this.appendReason = appendReason;

        initialize();
    }

    public void initialize() throws SyslogRuntimeException {
        if (this.printStream == null) {
            throw new SyslogRuntimeException("PrintStream cannot be null");
        }
    }

    public void down(SyslogIF syslog, String reason) {
        this.printStream.println(syslog.getProtocol() + ": DOWN" + (StringUtils.isBlank(reason) ? "" : " (" + reason + ")"));
    }

    public void up(SyslogIF syslog) {
        this.printStream.println(syslog.getProtocol() + ": UP");
    }

    public void log(SyslogIF syslog, int level, String message, String reason) {
        String combinedMessage = combine(syslog,level,message,reason);

        if (this.appendLinefeed) {
            this.printStream.println(combinedMessage);

        } else {
            this.printStream.print(combinedMessage);
        }
    }
}
