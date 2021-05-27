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

    /**
     * 非0数字
     *
     * @param value
     * @return
     */
    public static boolean isNotZero(Integer value) {
        if (value == null) {
            return false;
        }
        return value.intValue() != 0L;
    }

    /**
     * 分割成Integer[]
     *
     * @param arrayStr
     * @param splitChar
     * @return
     */
    public static Integer[] split(String arrayStr, char splitChar) {
        String[] array = StringUtils.split(arrayStr, splitChar);
        if (array == null || array.length == 0) {
            return ArrayUtil.emptyArray(Integer.class);
        }
        Integer[] values = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            if (StringUtil.isBlank(item)) {
                values[i] = 0;
            } else {
                values[i] = Integer.valueOf(item.trim());
            }
        }
        return values;
    }

}
