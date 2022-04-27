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
package org.productivity.java.syslog4j.impl;

import java.util.List;

import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Lists;

/**
* AbstractSyslogWriter is an implementation of Runnable that supports sending
* syslog messages within a separate Thread or an object pool.
*
* <p>When used in "threaded" mode (see TCPNetSyslogConfig for the option),
* a queuing mechanism is used (via LinkedList).</p>
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogWriter.java,v 1.9 2010/10/25 03:50:25 cvs Exp $
*/
public abstract class AbstractSyslogWriter implements Runnable {
    protected AbstractSyslog syslog = null;

    protected List<byte []> queuedMessages = null;

    protected Thread thread = null;

    protected AbstractSyslogConfigIF syslogConfig = null;

    protected boolean shutdown = false;

    public void initialize(AbstractSyslog abstractSyslog) {
        this.syslog = abstractSyslog;

        try {
            this.syslogConfig = (AbstractSyslogConfigIF) this.syslog.getConfig();

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("config must implement interface AbstractSyslogConfigIF");
        }

        if (this.syslogConfig.isThreaded()) {
            this.queuedMessages = Lists.newLinkedList();
        }
    }

    public void queue(SyslogLevel level, byte[] message) {
        synchronized(this.queuedMessages) {
            if (this.syslogConfig.getMaxQueueSize() == -1 || this.queuedMessages.size() < this.syslogConfig.getMaxQueueSize()) {
                this.queuedMessages.add(message);

            } else {
                this.syslog.backLog(level,SyslogUtility.newString(syslogConfig,message),"MaxQueueSize (" + this.syslogConfig.getMaxQueueSize() + ") reached");
            }
        }
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean hasThread() {
        return this.thread != null && this.thread.isAlive();
    }

    public abstract void write(byte[] message);

    public abstract void flush();

    public abstract void shutdown();

    protected abstract void runCompleted();

    public void run() {
        while(!this.shutdown || !this.queuedMessages.isEmpty()) {
            List<byte []> queuedMessagesCopy = null;

            synchronized(this.queuedMessages) {
                queuedMessagesCopy = Lists.newLinkedList(this.queuedMessages);
                this.queuedMessages.clear();
            }

            if (queuedMessagesCopy != null) {
                while(!queuedMessagesCopy.isEmpty()) {
                    byte[] message = (byte[]) queuedMessagesCopy.remove(0);

                    try {
                        write(message);

                        this.syslog.setBackLogStatus(false);

                    } catch (SyslogRuntimeException sre) {
                        this.syslog.backLog(SyslogLevel.INFO, SyslogUtility.newString(this.syslog.getConfig(), message), sre);
                    }
                }
            }

            SyslogUtility.sleep(this.syslogConfig.getThreadLoopInterval());
        }

        runCompleted();
    }
}
