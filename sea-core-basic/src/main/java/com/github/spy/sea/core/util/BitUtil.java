package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * bit util.
 *
 * @author spy
 * @version 1.0 2020/8/29
 * @since 1.0
 */
@Slf4j
public final class BitUtil {
    /**
     * 添加标记位,在原来的基础上添加
     *
     * @param base
     * @param flag
     * @return
     */
    public static int addFlag(int base, int flag) {
        base |= flag;
        return base;
    }

    /**
     * 清除指定的标记
     *
     * @param base
     * @param flag
     */
    public static int clearFlag(int base, int flag) {
        base &= ~flag;
        return base;
    }

    /**
     * 清除所有标记,设为默认
     */
//    public static void clearAll() {
//        mflag = DEFAULT;
//    }

    /**
     * 判断是否存在指定的标记
     *
     * @param base
     * @param flag
     * @return
     */
    public static boolean hasFlag(int base, int flag) {
        return (base & flag) == flag;
    }

    /**
     * 判断是否 只有指定的标记
     *
     * @param base
     * @param flag
     * @return
     */
    public static boolean onlyFlag(int base, int flag) {
        return base == flag;
    }

}
