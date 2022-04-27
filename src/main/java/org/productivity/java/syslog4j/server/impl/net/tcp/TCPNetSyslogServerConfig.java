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
package org.productivity.java.syslog4j.server.impl.net.tcp;

import static org.productivity.java.syslog4j.SyslogConstants.TCP_MAX_ACTIVE_SOCKETS_BEHAVIOR_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_MAX_ACTIVE_SOCKETS_DEFAULT;

import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.impl.net.AbstractNetSyslogServerConfig;
/**
* TCPNetSyslogServerConfig provides configuration for TCPNetSyslogServer.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: TCPNetSyslogServerConfig.java,v 1.8 2010/11/28 01:38:08 cvs Exp $
*/
public class TCPNetSyslogServerConfig extends AbstractNetSyslogServerConfig implements TCPNetSyslogServerConfigIF {
    protected int timeout = 0;
    protected int backlog = 0;
    protected int maxActiveSockets = TCP_MAX_ACTIVE_SOCKETS_DEFAULT;
    protected byte maxActiveSocketsBehavior = TCP_MAX_ACTIVE_SOCKETS_BEHAVIOR_DEFAULT;

    public TCPNetSyslogServerConfig() {
        //
    }

    public TCPNetSyslogServerConfig(int port) {
        this.port = port;
    }

    public TCPNetSyslogServerConfig(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
    }

    public TCPNetSyslogServerConfig(String host) {
        this.host = host;
    }

    public TCPNetSyslogServerConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public TCPNetSyslogServerConfig(String host, int port, int backlog) {
        this.host = host;
        this.port = port;
        this.backlog = backlog;
    }

    public Class<? extends SyslogServerIF> getSyslogServerClass() {
        return TCPNetSyslogServer.class;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getBacklog() {
        return this.backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public int getMaxActiveSockets() {
        return maxActiveSockets;
    }

    public void setMaxActiveSockets(int maxActiveSockets) {
        this.maxActiveSockets = maxActiveSockets;
    }

    public byte getMaxActiveSocketsBehavior() {
        return maxActiveSocketsBehavior;
    }

    public void setMaxActiveSocketsBehavior(byte maxActiveSocketsBehavior) {
        this.maxActiveSocketsBehavior = maxActiveSocketsBehavior;
    }
}
