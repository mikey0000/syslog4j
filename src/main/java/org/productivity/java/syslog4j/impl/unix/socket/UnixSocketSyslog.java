package org.productivity.java.syslog4j.impl.unix.socket;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Charsets;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslog;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.util.OSDetectUtility;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

/**
* UnixSocketSyslog is an extension of AbstractSyslog that provides support for
* Unix socket-based syslog clients.
*
* <p>This class requires the JNA (Java Native Access) library to directly
* access the native C libraries utilized on Unix platforms.</p>
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UnixSocketSyslog.java,v 1.13 2010/11/16 00:52:01 cvs Exp $
*/
public class UnixSocketSyslog extends AbstractSyslog {
    protected static class SockAddr extends Structure {
        public final static int SUN_PATH_SIZE = 108;
        private final static byte[] ZERO_BYTE = new byte[] { 0 };

        @SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
        public short sun_family = 1;
        public byte[] sun_path = new byte[SUN_PATH_SIZE];

        public void setSunPath(String sunPath) {
            System.arraycopy(sunPath.getBytes(Charsets.UTF_8), 0,this.sun_path, 0, sunPath.length());
            System.arraycopy(ZERO_BYTE,0,this.sun_path,sunPath.length(),1);
        }

        protected List getFieldOrder() {
            return Arrays.asList("sun_family", "sun_path");
        }
    }

    protected interface CLibrary extends Library {
        int socket(int domain, int type, int protocol);
        int connect(int sockfd, SockAddr sockaddr, int addrlen);
        int write(int fd, ByteBuffer buffer, int count);
        int close(int fd);
        String strerror(int errno);
    }

    protected boolean libraryLoaded = false;
    private CLibrary libraryInstance = null;

    protected UnixSocketSyslogConfig unixSocketSyslogConfig = null;
    protected int fd = -1;

    protected synchronized void loadLibrary() {
        if (!OSDetectUtility.isUnix()) {
            throw new SyslogRuntimeException("UnixSyslog not supported on non-Unix platforms");
        }

        if (!this.libraryLoaded) {
            this.libraryInstance = (CLibrary) Native.loadLibrary(this.unixSocketSyslogConfig.getLibrary(),CLibrary.class);
            this.libraryLoaded = true;
        }
    }

    public void initialize() throws SyslogRuntimeException {
        try {
            this.unixSocketSyslogConfig = (UnixSocketSyslogConfig) this.syslogConfig;

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("config must be of type UnixSocketSyslogConfig");
        }

        loadLibrary();

    }

    protected synchronized void connect() {
        if (this.fd != -1) {
            return;
        }

        this.fd = this.libraryInstance.socket(this.unixSocketSyslogConfig.getFamily(),this.unixSocketSyslogConfig.getType(),this.unixSocketSyslogConfig.getProtocol());

        if (this.fd == -1) {
            this.fd = -1;
            return;
        }

        SockAddr sockAddr = new SockAddr();

        sockAddr.sun_family = this.unixSocketSyslogConfig.getFamily();
        sockAddr.setSunPath(this.unixSocketSyslogConfig.getPath());

        int c = this.libraryInstance.connect(this.fd, sockAddr, sockAddr.size());

        if (c == -1) {
            this.fd = -1;
            return;
        }
    }

    protected void write(int level, byte[] message) throws SyslogRuntimeException {
        if (this.fd == -1) {
            connect();
        }

        if (this.fd == -1) {
            return;
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap(message);

        this.libraryInstance.write(this.fd,byteBuffer,message.length);
    }

    public synchronized void flush() throws SyslogRuntimeException {
        shutdown();

        this.fd = this.libraryInstance.socket(this.unixSocketSyslogConfig.getFamily(),this.unixSocketSyslogConfig.getType(),this.unixSocketSyslogConfig.getProtocol());
    }

    public synchronized void shutdown() throws SyslogRuntimeException {
        if (this.fd == -1) {
            return;
        }

        this.libraryInstance.close(this.fd);

        this.fd = -1;
    }

    public AbstractSyslogWriter getWriter() {
        return null;
    }

    public void returnWriter(AbstractSyslogWriter syslogWriter) {
        //
    }
}
