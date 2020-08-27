package com.github.spy.sea.core.util;

import com.github.spy.sea.core.enums.OsTypeEnum;
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


    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static OsTypeEnum getOsName() {
        OsTypeEnum osTypeEnum;
        if (isAix()) {
            osTypeEnum = OsTypeEnum.AIX;
        } else if (isDigitalUnix()) {
            osTypeEnum = OsTypeEnum.Digital_Unix;
        } else if (isFreeBSD()) {
            osTypeEnum = OsTypeEnum.FreeBSD;
        } else if (isHPUX()) {
            osTypeEnum = OsTypeEnum.HP_UX;
        } else if (isIrix()) {
            osTypeEnum = OsTypeEnum.Irix;
        } else if (isLinux()) {
            osTypeEnum = OsTypeEnum.Linux;
        } else if (isMacOS()) {
            osTypeEnum = OsTypeEnum.Mac_OS;
        } else if (isMacOSX()) {
            osTypeEnum = OsTypeEnum.Mac_OS_X;
        } else if (isMPEiX()) {
            osTypeEnum = OsTypeEnum.MPEiX;
        } else if (isNetWare()) {
            osTypeEnum = OsTypeEnum.NetWare_411;
        } else if (isOpenVMS()) {
            osTypeEnum = OsTypeEnum.OpenVMS;
        } else if (isOS2()) {
            osTypeEnum = OsTypeEnum.OS2;
        } else if (isOS390()) {
            osTypeEnum = OsTypeEnum.OS390;
        } else if (isOSF1()) {
            osTypeEnum = OsTypeEnum.OSF1;
        } else if (isSolaris()) {
            osTypeEnum = OsTypeEnum.Solaris;
        } else if (isSunOS()) {
            osTypeEnum = OsTypeEnum.SunOS;
        } else if (isWindows()) {
            osTypeEnum = OsTypeEnum.Windows;
        } else {
            osTypeEnum = OsTypeEnum.Others;
        }
        return osTypeEnum;
    }

}
