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
package org.productivity.java.syslog4j.server.impl.event.printstream;

import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;

public class SystemErrSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
    private static final long serialVersionUID = -3496862887351690575L;

    public static SyslogServerSessionEventHandlerIF create() {
        return new SystemErrSyslogServerEventHandler();
    }

    public SystemErrSyslogServerEventHandler() {
        super(System.err);
    }
}
