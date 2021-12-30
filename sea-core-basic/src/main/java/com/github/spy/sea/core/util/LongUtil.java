package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

    public static Long parse(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            log.error("fail to parse long", e);
        }

        return null;
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

    /**
     * 分割成Long[]
     *
     * @param arrayStr
     * @param splitChar
     * @return
     */
    public static Long[] split(String arrayStr, char splitChar) {
        String[] array = StringUtils.split(arrayStr, splitChar);
        if (array == null || array.length == 0) {
            return ArrayUtil.emptyArray(Long.class);
        }
        Long[] values = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            if (StringUtil.isBlank(item)) {
                values[i] = 0L;
            } else {
                values[i] = Long.valueOf(item.trim());
            }
        }
        return values;
    }

}
