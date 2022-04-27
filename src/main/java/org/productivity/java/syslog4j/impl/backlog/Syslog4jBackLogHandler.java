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
package org.productivity.java.syslog4j.impl.backlog;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* Syslog4jBackLogHandler is used to send Syslog backLog messages to
* another Syslog4j protocol whenever the main Syslog protocol fails.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: Syslog4jBackLogHandler.java,v 1.1 2009/07/25 18:42:47 cvs Exp $
*/
public class Syslog4jBackLogHandler extends AbstractSyslogBackLogHandler {
    protected SyslogIF syslog = null;
    protected SyslogLevel downLevel = SyslogLevel.WARN;
    protected SyslogLevel upLevel = SyslogLevel.WARN;

    public Syslog4jBackLogHandler(String protocol) {
        this.syslog = Syslog.getInstance(protocol);
    }

    public Syslog4jBackLogHandler(String protocol, boolean appendReason) {
        this.syslog = Syslog.getInstance(protocol);
        this.appendReason = appendReason;
    }

    public Syslog4jBackLogHandler(SyslogIF syslog) {
        this.syslog = syslog;
    }

    public Syslog4jBackLogHandler(SyslogIF syslog, boolean appendReason) {
        this.syslog = syslog;
        this.appendReason = appendReason;
    }

    public void initialize() throws SyslogRuntimeException {
        // NO-OP
    }

    public void log(SyslogIF syslog, SyslogLevel level, String message, String reason) throws SyslogRuntimeException {
        if (this.syslog.getProtocol().equals(syslog.getProtocol())) {
            throw new SyslogRuntimeException("Ignoring this log entry since the backLog protocol \"%s\" is the same as the main protocol", this.syslog.getProtocol());
        }

        String combinedMessage = combine(syslog,level,message,reason);

        this.syslog.log(level,combinedMessage);
    }

    public void down(SyslogIF syslog, String reason) {
        if (!this.syslog.getProtocol().equals(syslog.getProtocol())) {
            this.syslog.log(this.downLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is down: " + reason);
        }
    }

    public void up(SyslogIF syslog) {
        if (!this.syslog.getProtocol().equals(syslog.getProtocol())) {
            this.syslog.log(this.upLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is up");
        }
    }
}
