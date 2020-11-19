package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * array util
 *
 * @author spy
 * @version 1.0 2019-08-14
 * @since 1.0
 */
@Slf4j
public final class ArrayUtil {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private ArrayUtil() {
    }

    /**
     * empty object array.
     *
     * @return
     */
    public static Object[] empty() {
        return EMPTY_OBJECT_ARRAY;
    }

    /**
     * empty string array.
     *
     * @return
     */
    public static String[] emptyStr() {
        return EMPTY_STRING_ARRAY;
    }

    /**
     * check array is empty
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(final T... array) {
        return array == null || array.length == 0;
    }

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }

    /**
     * to array
     *
     * @param list
     * @return
     */
    public static String[] toArray(List<String> list) {
        if (ListUtil.isEmpty(list)) {
            return new String[0];
        }

        String[] arrays = new String[list.size()];

        return list.toArray(arrays);
    }

    /**
     * list to array
     *
     * @param list
     * @return
     */
    public static Object[] toObjArray(List<Object> list) {
        if (ListUtil.isEmpty(list)) {
            return new Object[0];
        }
        return list.toArray();
    }

    /**
     * return Object[] ,if null return empty Object[].
     *
     * @param objects
     * @return
     */
    public static Object[] defaultIfNull(Object[] objects) {
        return objects == null ? empty() : objects;
    }

    /**
     * return object[], if null return default value.
     *
     * @param objects
     * @param defaultValue
     * @return
     */
    public static Object[] defaultIfNull(Object[] objects, Object[] defaultValue) {
        return objects == null ? defaultValue : objects;
    }

    /**
     * if objects=null or length=0, then return default value.
     *
     * @param objects
     * @param defaultValue
     * @return
     */
    public static Object[] defaultIfEmpty(Object[] objects, Object[] defaultValue) {
        return (objects == null || objects.length == 0) ? defaultValue : objects;
    }


}
