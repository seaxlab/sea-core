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
public final class IntegerUtil {

    private IntegerUtil() {
    }

    /**
     * 如果value为空则返回默认值
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer defaultIfNull(Integer value, Integer defaultValue) {
        Preconditions.checkNotNull(defaultValue, "defaultValue不能为空");

        if (value == null) {
            return defaultValue;
        }
        return value;
    }

}
