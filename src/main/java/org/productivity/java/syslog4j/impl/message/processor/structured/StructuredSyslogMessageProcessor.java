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
package org.productivity.java.syslog4j.impl.message.processor.structured;

import static org.productivity.java.syslog4j.SyslogConstants.STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE;
import static org.productivity.java.syslog4j.SyslogConstants.STRUCTURED_DATA_PROCESS_ID_DEFAULT_VALUE;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.productivity.java.syslog4j.impl.message.processor.AbstractSyslogMessageProcessor;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;
/**
 * SyslogStructuredMessageProcessor extends SyslogMessageProcessor's ability to
 * split a syslog message into multiple messages when the message is greater
 * than the syslog maximum message length (1024 bytes including the header). It
 * adds support for structured syslog messages as specified by
 * draft-ietf-syslog-protocol-23. More information here:
 *
 * <p>http://tools.ietf.org/html/draft-ietf-syslog-protocol-23</p>
 *
 * <p>Those wishing to replace (or improve upon) this implementation
 * can write a custom SyslogMessageProcessorIF and set it per
 * instance via the SyslogIF.setStructuredMessageProcessor(..) method or set it globally
 * via the StructuredSyslogMessageProcessor.setDefault(..) method.</p>
 *
 * <p>
 * Syslog4j is licensed under the Lesser GNU Public License v2.1. A copy of the
 * LGPL license is available in the META-INF folder in all distributions of
 * Syslog4j and in the base directory of the "doc" ZIP.
 * </p>
 *
 * @author Manish Motwani
 * @version $Id: StructuredSyslogMessageProcessor.java,v 1.4 2011/01/11 05:11:13 cvs Exp $
 */
public class StructuredSyslogMessageProcessor extends AbstractSyslogMessageProcessor {
    public static final String VERSION = "1";

    private static final StructuredSyslogMessageProcessor INSTANCE = new StructuredSyslogMessageProcessor();

    private static StructuredSyslogMessageProcessor defaultInstance = INSTANCE;

    private String applicationName = STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE;
    private String processId = STRUCTURED_DATA_PROCESS_ID_DEFAULT_VALUE;

    private DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public static void setDefault(StructuredSyslogMessageProcessor messageProcessor) {
        if (messageProcessor != null) {
            defaultInstance = messageProcessor;
        }
    }

    public static StructuredSyslogMessageProcessor getDefault() {
        return defaultInstance;
    }

    public StructuredSyslogMessageProcessor() {
        super();
    }

    public StructuredSyslogMessageProcessor(final String applicationName) {
        super();
        this.applicationName = applicationName;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getProcessId() {
        return this.processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Override
    public String createSyslogHeader(final int facility, final int level, String localName, String localProcessId, final boolean sendLocalTimestamp, final boolean sendLocalName) {
        final StringBuffer buffer = new StringBuffer();

        appendPriority(buffer,facility,level);
        buffer.append(VERSION);
        buffer.append(' ');

        getDateTimeFormatter().printTo(buffer,System.currentTimeMillis());
        buffer.append(' ');

        appendLocalName(buffer,localName);

        buffer.append(StructuredSyslogMessage.nilProtect(this.applicationName))
                .append(' ');

        buffer.append(StructuredSyslogMessage.nilProtect(ObjectUtils.firstNonNull(localProcessId, this.processId))).append(' ');

        return buffer.toString();
    }
}
