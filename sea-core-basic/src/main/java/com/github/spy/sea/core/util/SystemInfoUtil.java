package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 系统信息
 *
 * @author spy
 * @version 1.0 2019-07-04
 * @since 1.0
 */
@Slf4j
public final class SystemInfoUtil {

    private SystemInfoUtil() {
    }

    /**
     * 获取主机名称
     *
     * @return
     */
    public static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }

    /**
     * 获取主机名称
     *
     * @return
     */
    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            // host = "hostname: hostname"
            String host = uhe.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }
}
