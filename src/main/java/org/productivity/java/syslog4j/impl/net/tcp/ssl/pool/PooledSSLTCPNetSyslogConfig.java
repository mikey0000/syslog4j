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
package org.productivity.java.syslog4j.impl.net.tcp.ssl.pool;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.impl.net.tcp.pool.PooledTCPNetSyslogConfig;
import org.productivity.java.syslog4j.impl.net.tcp.ssl.SSLTCPNetSyslog;
import org.productivity.java.syslog4j.impl.net.tcp.ssl.SSLTCPNetSyslogConfigIF;
import org.productivity.java.syslog4j.impl.net.tcp.ssl.SSLTCPNetSyslogWriter;

/**
* PooledSSLTCPNetSyslogConfig is an extension of PooledTCPNetSyslogConfig
* which provides configuration support for the Apache Commons Pool.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PooledSSLTCPNetSyslogConfig.java,v 1.2 2009/03/29 17:38:58 cvs Exp $
*/
public class PooledSSLTCPNetSyslogConfig extends PooledTCPNetSyslogConfig implements SSLTCPNetSyslogConfigIF {
    protected String keyStore = null;
    protected String keyStorePassword = null;

    protected String trustStore = null;
    protected String trustStorePassword = null;

    public PooledSSLTCPNetSyslogConfig() {
        super();
    }

    public PooledSSLTCPNetSyslogConfig(int facility, String host, int port) {
        super(facility, host, port);
    }

    public PooledSSLTCPNetSyslogConfig(int facility, String host) {
        super(facility, host);
    }

    public PooledSSLTCPNetSyslogConfig(int facility) {
        super(facility);
    }

    public PooledSSLTCPNetSyslogConfig(String host, int port) {
        super(host, port);
    }

    public PooledSSLTCPNetSyslogConfig(String host) {
        super(host);
    }

    public String getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getTrustStore() {
        return this.trustStore;
    }

    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public Class<? extends SyslogIF> getSyslogClass() {
        return SSLTCPNetSyslog.class;
    }

    public Class<? extends AbstractSyslogWriter> getSyslogWriterClass() {
        return SSLTCPNetSyslogWriter.class;
    }
}
