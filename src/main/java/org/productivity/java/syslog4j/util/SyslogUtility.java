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
package org.productivity.java.syslog4j.util;

import static org.productivity.java.syslog4j.SyslogConstants.SEND_LOCAL_NAME_DEFAULT_VALUE;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogConstants;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.productivity.java.syslog4j.SyslogCharSetIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
/**
* SyslogUtility provides several common utility methods used within
* Syslog4j.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogUtility.java,v 1.21 2010/11/28 01:38:08 cvs Exp $
*/
public final class SyslogUtility {
    private SyslogUtility() {
        //
    }

    public static InetAddress getInetAddress(String host) throws SyslogRuntimeException
    {
        try {
            return InetAddress.getByName(host);

        } catch (UnknownHostException uhe) {
            throw new SyslogRuntimeException(uhe);
        }
    }

    public static String getFacilityString(int syslogFacility) {
        switch(syslogFacility) {
            case SyslogConstants.FACILITY_KERN:			return "kern";
            case SyslogConstants.FACILITY_USER:			return "user";
            case SyslogConstants.FACILITY_MAIL:			return "mail";
            case SyslogConstants.FACILITY_DAEMON:		return "daemon";
            case SyslogConstants.FACILITY_AUTH:			return "auth";
            case SyslogConstants.FACILITY_SYSLOG:		return "syslog";
            case SyslogConstants.FACILITY_LPR:			return "lpr";
            case SyslogConstants.FACILITY_NEWS:			return "news";
            case SyslogConstants.FACILITY_UUCP:			return "uucp";
            case SyslogConstants.FACILITY_CRON:			return "cron";
            case SyslogConstants.FACILITY_AUTHPRIV:		return "authpriv";
            case SyslogConstants.FACILITY_FTP:			return "ftp";
            case SyslogConstants.FACILITY_LOCAL0:		return "local0";
            case SyslogConstants.FACILITY_LOCAL1:		return "local1";
            case SyslogConstants.FACILITY_LOCAL2:		return "local2";
            case SyslogConstants.FACILITY_LOCAL3:		return "local3";
            case SyslogConstants.FACILITY_LOCAL4:		return "local4";
            case SyslogConstants.FACILITY_LOCAL5:		return "local5";
            case SyslogConstants.FACILITY_LOCAL6:		return "local6";
            case SyslogConstants.FACILITY_LOCAL7:		return "local7";

            default:					return "UNKNOWN";
        }
    }

    public static int getFacility(String facilityName) {
        String _facilityName = StringUtils.trimToNull(facilityName);

        if (facilityName == null) {
            return -1;
        }

        if("KERN".equalsIgnoreCase(_facilityName)) {				return SyslogConstants.FACILITY_KERN;
        } else if("USER".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_USER;
        } else if("MAIL".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_MAIL;
        } else if("DAEMON".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_DAEMON;
        } else if("AUTH".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_AUTH;
        } else if("SYSLOG".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_SYSLOG;
        } else if("LPR".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_LPR;
        } else if("NEWS".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_NEWS;
        } else if("UUCP".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_UUCP;
        } else if("CRON".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_CRON;
        } else if("AUTHPRIV".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_AUTHPRIV;
        } else if("FTP".equalsIgnoreCase(facilityName)) {		return SyslogConstants.FACILITY_FTP;
        } else if("LOCAL0".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL0;
        } else if("LOCAL1".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL1;
        } else if("LOCAL2".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL2;
        } else if("LOCAL3".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL3;
        } else if("LOCAL4".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL4;
        } else if("LOCAL5".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL5;
        } else if("LOCAL6".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL6;
        } else if("LOCAL7".equalsIgnoreCase(facilityName)) {	return SyslogConstants.FACILITY_LOCAL7;
        } else {												return -1;
        }
    }

    public static int getLevel(String levelName) {
        String _levelName = StringUtils.trimToNull(levelName);

        if (levelName == null) {
            return -1;
        }

        if("DEBUG".equalsIgnoreCase(_levelName)) {				return SyslogConstants.LEVEL_DEBUG;
        } else if("INFO".equalsIgnoreCase(_levelName)) {		return SyslogConstants.LEVEL_INFO;
        } else if("NOTICE".equalsIgnoreCase(_levelName)) {		return SyslogConstants.LEVEL_NOTICE;
        } else if("WARN".equalsIgnoreCase(_levelName)) {		return SyslogConstants.LEVEL_WARN;
        } else if("ERROR".equalsIgnoreCase(_levelName)) {		return SyslogConstants.LEVEL_ERROR;
        } else if("CRITICAL".equalsIgnoreCase(_levelName)) {	return SyslogConstants.LEVEL_CRITICAL;
        } else if("ALERT".equalsIgnoreCase(_levelName)) {		return SyslogConstants.LEVEL_ALERT;
        } else if("EMERGENCY".equalsIgnoreCase(_levelName)) {	return SyslogConstants.LEVEL_EMERGENCY;
        } else {												return -1;
        }
    }

    public static boolean isClassExists(String className) {
        try {
            Class.forName(className);
            return true;

        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }

    public static String getLocalName() {

        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();

        } catch (UnknownHostException uhe) {
            return SEND_LOCAL_NAME_DEFAULT_VALUE;
        }
    }

    public static byte[] getBytes(SyslogCharSetIF syslogCharSet, String data)
    {
        return data.getBytes(syslogCharSet.getCharSet());
    }

    public static String newString(SyslogCharSetIF syslogCharSet, byte[] dataBytes) {

        return newString(syslogCharSet,dataBytes,dataBytes.length);
    }

    public static String newString(SyslogCharSetIF syslogCharSet, byte[] dataBytes, int dataLength) {
        return new String(dataBytes, 0, dataLength,syslogCharSet.getCharSet());
    }

    public static String getLevelString(int level) {
        switch(level) {
            case SyslogConstants.LEVEL_DEBUG: return "DEBUG";
            case SyslogConstants.LEVEL_INFO: return "INFO";
            case SyslogConstants.LEVEL_NOTICE: return "NOTICE";
            case SyslogConstants.LEVEL_WARN: return "WARN";
            case SyslogConstants.LEVEL_ERROR: return "ERROR";
            case SyslogConstants.LEVEL_CRITICAL: return "CRITICAL";
            case SyslogConstants.LEVEL_ALERT: return "ALERT";
            case SyslogConstants.LEVEL_EMERGENCY: return "EMERGENCY";

            default:
                return "UNKNOWN";
        }
    }


    public static void sleep(long duration) {
        try {
            Thread.sleep(duration);

        } catch (InterruptedException ie) {
            //
        }
    }
}
