package com.github.seaxlab.core.util;

import com.github.seaxlab.core.common.CoreConst;
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

    private BooleanUtil() {
    }

    /**
     * check is true or false
     *
     * @param flag
     * @return
     */
    public static boolean isTrueOrFalseStr(String flag) {
        return flag != null && (flag.trim().equalsIgnoreCase(CoreConst.TRUE_STR) || flag.trim().equalsIgnoreCase(CoreConst.FALSE_STR));
    }

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
        return flag != null && flag.trim().equalsIgnoreCase(CoreConst.TRUE_STR);
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
        return flag != null && !flag.trim().equalsIgnoreCase(CoreConst.FALSE_STR);
    }

    /**
     * whether is true
     *
     * @param flag int
     * @return boolean
     */
    public static boolean isTrue(Integer flag) {
        return flag != null && flag > 0;
    }

    /**
     * whether is false
     *
     * @param flag int
     * @return boolean
     */
    public static boolean isFalse(Integer flag) {
        return flag != null && flag <= 0;
    }

    /**
     * whether is true
     *
     * @param flag byte
     * @return boolean
     */
    public static boolean isTrue(Byte flag) {
        return flag != null && flag > 0;
    }

    /**
     * whether is true
     *
     * @param flag byte
     * @return boolean
     */
    public static boolean isFalse(Byte flag) {
        return flag != null && flag <= 0;
    }

    /**
     * one true is true
     *
     * @param flags flags
     * @return boolean
     */
    public static boolean hasTrue(Boolean... flags) {
        if (flags.length == 0) {
            return false;
        }
        for (Boolean flag : flags) {
            if (isTrue(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * both true are true
     *
     * @param flags flags
     * @return boolean
     */
    public static boolean bothTrue(Boolean... flags) {
        if (flags.length == 0) {
            return false;
        }
        for (Boolean flag : flags) {
            if (isTrue(flag)) {

            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * one false is true
     *
     * @param flags flags
     * @return boolean
     */
    public static boolean hasFalse(Boolean... flags) {
        if (flags.length == 0) {
            return false;
        }
        for (Boolean flag : flags) {
            if (isFalse(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * both false are true
     *
     * @param flags flags
     * @return boolean
     */
    public static boolean bothFalse(Boolean... flags) {
        if (flags.length == 0) {
            return false;
        }
        for (Boolean flag : flags) {
            if (isFalse(flag)) {

            } else {
                return false;
            }
        }
        return true;
    }

}
