package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/13
 * @since 1.0
 */
@Slf4j
public final class MBeanServerUtil {
    private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    private static final Integer ERROR_INT = -1;
    private static final Long ERROR_LONG = -1L;
    private static final String ERROR_ATTRIBUTE = "unknown";

    private MBeanServerUtil() {
    }

    /**
     * get int
     *
     * @param objectName
     * @param attribute
     * @return
     */
    public static Integer getIntegerSafe(ObjectName objectName, String attribute) {
        try {
            return (Integer) mBeanServer.getAttribute(objectName, attribute);
        } catch (Exception e) {
            return ERROR_INT;
        }
    }

    /**
     * get long
     *
     * @param objectName
     * @param attribute
     * @return
     */
    public static Long getLongSafe(ObjectName objectName, String attribute) {
        try {
            return (Long) mBeanServer.getAttribute(objectName, attribute);
        } catch (Exception e) {
            return ERROR_LONG;
        }
    }

    /**
     * get string
     *
     * @param objectName
     * @param attribute
     * @return
     */
    public static String getStringSafe(ObjectName objectName, String attribute) {
        try {
            return (String) mBeanServer.getAttribute(objectName, attribute);
        } catch (Exception e) {
            return ERROR_ATTRIBUTE;
        }
    }


}
