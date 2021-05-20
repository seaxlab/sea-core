package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/10
 * @since 1.0
 */
@Slf4j
public final class LongUtil {

    private LongUtil() {
    }


    public static Long defaultIfNull(Long value, Long defaultValue) {
        Preconditions.checkNotNull(defaultValue, "default value can not be null");

        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * 非0数字
     *
     * @param value
     * @return
     */
    public static boolean isNotZero(Long value) {
        if (value == null) {
            return false;
        }
        return value.longValue() != 0L;
    }


}
