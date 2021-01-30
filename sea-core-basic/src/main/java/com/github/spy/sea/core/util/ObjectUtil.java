package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-14
 * @since 1.0
 */
@Slf4j
public final class ObjectUtil {

    private ObjectUtil() {
    }

    private static final Object EMPTY = new Object();

    public static Object empty() {
        return EMPTY;
    }

    /**
     * 取值
     *
     * @param obj
     * @param field
     * @return
     */
    public static Object get(Object obj, String field) {
        if (obj == null) {
            return null;
        }

        return ReflectUtil.read(obj, field);
    }

    /**
     * 全部为空
     *
     * @param objects
     * @return
     */
    public static boolean isAllNull(Object... objects) {
        for (Object obj : objects) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 不全部为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotAllNull(Object... objects) {
        for (Object obj : objects) {
            if (obj != null) {
                // 有值，不全部为空
                return true;
            }
        }
        // 全部为空
        return false;
    }

    public static <T extends Comparable<? super T>> T min(final T... values) {
        return ObjectUtils.min(values);
    }

    public static <T extends Comparable<? super T>> T max(final T... values) {
        return ObjectUtils.min(values);
    }


    /**
     * 如果value为空，则返回defaultValue.
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static <T> T defaultIfNull(T value, T defaultValue) {
//        Preconditions.checkNotNull(defaultValue, "defaultValue can not be null");
        if (value == null) {
            return defaultValue;
        }

        return value;
    }
}
