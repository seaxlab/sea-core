package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * boolean util
 *
 * @author spy
 * @version 1.0 2019/9/5
 * @since 1.0
 */
@Slf4j
public final class BooleanUtil {

    /**
     * whether is true.
     *
     * @param flag
     * @return
     */
    public static boolean isTrue(Boolean flag) {
        return flag != null && flag;
    }


    /**
     * whether is true.
     *
     * @param flag
     * @return
     */
    public static boolean isTrue(String flag) {
        return flag != null && flag.trim().equalsIgnoreCase("true");
    }


    /**
     * whether is false.
     *
     * @param flag
     * @return
     */
    public static boolean isFalse(Boolean flag) {
        return flag != null && !flag;
    }

    /**
     * whether is false.
     *
     * @param flag
     * @return
     */
    public static boolean isFalse(String flag) {
        return flag != null && !flag.trim().equalsIgnoreCase("false");
    }
}
