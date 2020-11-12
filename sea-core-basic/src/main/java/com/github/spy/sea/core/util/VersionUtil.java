package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.CodeSource;

/**
 * 版本比较工具
 *
 * @author spy
 * @since 1.0
 */
@Slf4j
public final class VersionUtil {

    private VersionUtil() {
    }

    /**
     * 获取jar包版本号
     *
     * @param cls
     * @param defaultVersion
     * @return
     * @see com.github.spy.sea.core.common.Version
     */
    public static String getVersion(Class<?> cls, String defaultVersion) {
        try {
            // find version info from MANIFEST.MF first
            Package pkg = cls.getPackage();
            String version = null;
            if (pkg != null) {
                version = pkg.getImplementationVersion();
                if (StringUtils.isNotEmpty(version)) {
                    return version;
                }

                version = pkg.getSpecificationVersion();
                if (StringUtils.isNotEmpty(version)) {
                    return version;
                }
            }

            // guess version from jar file name if nothing's found from MANIFEST.MF
            CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
            if (codeSource == null) {
                log.info("No codeSource for class " + cls.getName() + " when getVersion, use default version " + defaultVersion);
                return defaultVersion;
            }

            String file = codeSource.getLocation().getFile();
            if (!StringUtils.isEmpty(file) && file.endsWith(".jar")) {
                version = getFromFile(file);
            }

            // return default version if no version info is found
            return StringUtils.isEmpty(version) ? defaultVersion : version;
        } catch (Throwable e) {
            // return default version when any exception is thrown
            log.error("return default version, ignore exception " + e.getMessage(), e);
            return defaultVersion;
        }
    }

    /**
     * get version from file: path/to/group-module-x.y.z.jar, returns x.y.z
     */
    private static String getFromFile(String file) {
        // remove suffix ".jar": "path/to/group-module-x.y.z"
        file = file.substring(0, file.length() - 4);

        // remove path: "group-module-x.y.z"
        int i = file.lastIndexOf('/');
        if (i >= 0) {
            file = file.substring(i + 1);
        }

        // remove group: "module-x.y.z"
        i = file.indexOf("-");
        if (i >= 0) {
            file = file.substring(i + 1);
        }

        // remove module: "x.y.z"
        while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
            i = file.indexOf("-");
            if (i >= 0) {
                file = file.substring(i + 1);
            } else {
                break;
            }
        }
        return file;
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1 版本1，如1.0.0
     * @param version2 版本2，如2.0.0
     * @return
     */
    public static int compare(String version1, String version2) {
        Preconditions.checkNotNull(version1, "version1不能为null");
        Preconditions.checkNotNull(version2, "version2不能为null");


        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }


}
