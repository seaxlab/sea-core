package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;

/**
 * 版本比较工具
 *
 * @author spy
 * @since 1.0
 */
public final class VersionUtil {

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
