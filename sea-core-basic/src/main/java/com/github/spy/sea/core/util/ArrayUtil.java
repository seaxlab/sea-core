package com.github.spy.sea.core.util;

import com.google.common.collect.ObjectArrays;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Collection;
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

    /**
     * empty Object Array
     * or {}
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * empty string array
     * or {}
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * empty class array
     */
    public static final Class[] EMPTY_CLASS_ARRAY = {};

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
     * check array is not empty.
     *
     * @param arrays 数组
     * @param <T>    数组元素类型
     * @return 是否为空
     */
    public static <T> boolean isNotEmpty(final T... arrays) {
        return !isEmpty(arrays);
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
     * 集合转数组
     *
     * @param data  collection data
     * @param clazz target class
     * @param <T>   generic class type
     * @return array
     */
    public static <T> T[] toArray(Collection<T> data, Class<T> clazz) {
        if (data == null || data.isEmpty()) {
            return (T[]) Array.newInstance(clazz, 0);
        }

        T[] array = (T[]) Array.newInstance(clazz, data.size());
        data.toArray(array);

        return array;
    }

    /**
     * to array
     * pls use toArray(Collection ,clazz)
     *
     * @param list
     * @return
     */
    @Deprecated
    public static String[] toArray(List<String> list) {
        if (ListUtil.isEmpty(list)) {
            return new String[0];
        }

        String[] arrays = new String[list.size()];

        return list.toArray(arrays);
    }

    /**
     * list to array
     * pls use toArray(Collection ,clazz)
     *
     * @param list
     * @return
     */
    @Deprecated
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

    /**
     * concat multi array.
     *
     * @param clazz
     * @param first
     * @param array
     * @param <T>
     * @return
     */
    public static <T> T[] concat(Class<T> clazz, final T[] first, final T[]... array) {
        T[] all = null;
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                all = ObjectArrays.concat(first, array[i], clazz);
            } else {
                all = ObjectArrays.concat(all, array[i], clazz);
            }
        }

        return all;
    }


    /**
     * Convert from variable arguments to array
     *
     * @param values variable arguments
     * @param <T>    The class
     * @return array
     */
    public static <T> T[] of(T... values) {
        return values;
    }

    /**
     * Create a new empty array from the specified component type
     *
     * @param componentType the specified component type
     * @param <T>           the specified component type
     * @return new empty array
     */
    public static <T> T[] emptyArray(Class<T> componentType) {
        return (T[]) Array.newInstance(componentType, 0);
    }
}
