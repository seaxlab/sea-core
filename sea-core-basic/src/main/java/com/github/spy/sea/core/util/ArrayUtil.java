package com.github.spy.sea.core.util;

import com.google.common.collect.ObjectArrays;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;
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
     * empty byte array
     */
    private static final byte[] EMPTY_BYTE_ARRAY = {};

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

    public static byte[] emptyByte() {
        return EMPTY_BYTE_ARRAY;
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

    /**
     * swap element.
     *
     * @param array element
     * @param i     index of element.
     * @param j     index of element.
     * @param <T>
     */
    public static <T> void swap(T[] array, int i, int j) {
        T t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    /**
     * shadow copy
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> T[] copy(T[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * 包装 {@link System#arraycopy(Object, int, Object, int, int)}<br>
     * 数组复制，缘数组和目标数组都是从位置0开始复制
     *
     * @param src    源数组
     * @param dest   目标数组
     * @param length 拷贝数组长度
     * @return 目标数组
     */
    public static Object copy(Object src, Object dest, int length) {
        System.arraycopy(src, 0, dest, 0, length);
        return dest;
    }

    /**
     * 包装 {@link System#arraycopy(Object, int, Object, int, int)}<br>
     * 数组复制
     *
     * @param src     源数组
     * @param srcPos  源数组开始位置
     * @param dest    目标数组
     * @param destPos 目标数组开始位置
     * @param length  拷贝数组长度
     * @return 目标数组
     */
    public static Object copy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }


    /**
     * 将新元素添加到已有数组中<br>
     * 添加新元素会生成一个新的数组，不影响原数组
     *
     * @param <T>         数组元素类型
     * @param buffer      已有数组
     * @param newElements 新元素
     * @return 新数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        if (isEmpty(buffer)) {
            return newElements;
        }
        return insert(buffer, buffer.length, newElements);
    }

    /**
     * 将新元素添加到已有数组中<br>
     * 添加新元素会生成一个新的数组，不影响原数组
     *
     * @param <T>         数组元素类型
     * @param array       已有数组
     * @param newElements 新元素
     * @return 新数组
     */
    @SafeVarargs
    public static <T> Object append(Object array, T... newElements) {
        if (isEmpty(array)) {
            return newElements;
        }
        return insert(array, length(array), newElements);
    }

    /**
     * 将元素值设置为数组的某个位置，当给定的index大于数组长度，则追加
     *
     * @param <T>    数组元素类型
     * @param buffer 已有数组
     * @param index  位置，大于长度追加，否则替换
     * @param value  新值
     * @return 新数组或原有数组
     * @since 4.1.2
     */
    public static <T> T[] setOrAppend(T[] buffer, int index, T value) {
        if (index < buffer.length) {
            Array.set(buffer, index, value);
            return buffer;
        } else {
            return append(buffer, value);
        }
    }

    /**
     * 将元素值设置为数组的某个位置，当给定的index大于数组长度，则追加
     *
     * @param array 已有数组
     * @param index 位置，大于长度追加，否则替换
     * @param value 新值
     * @return 新数组或原有数组
     * @since 4.1.2
     */
    public static Object setOrAppend(Object array, int index, Object value) {
        if (index < length(array)) {
            Array.set(array, index, value);
            return array;
        } else {
            return append(array, value);
        }
    }

    /**
     * 将新元素插入到到已有数组中的某个位置<br>
     * 添加新元素会生成一个新的数组，不影响原数组<br>
     * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用null填充
     *
     * @param <T>         数组元素类型
     * @param buffer      已有数组
     * @param index       插入位置，此位置为对应此位置元素之前的空档
     * @param newElements 新元素
     * @return 新数组
     * @since 4.0.8
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] insert(T[] buffer, int index, T... newElements) {
        return (T[]) insert((Object) buffer, index, newElements);
    }

    /**
     * 将新元素插入到到已有数组中的某个位置<br>
     * 添加新元素会生成一个新的数组，不影响原数组<br>
     * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用null填充
     *
     * @param <T>         数组元素类型
     * @param array       已有数组
     * @param index       插入位置，此位置为对应此位置元素之前的空档
     * @param newElements 新元素
     * @return 新数组
     * @since 4.0.8
     */
    @SuppressWarnings("unchecked")
    public static <T> Object insert(Object array, int index, T... newElements) {
        if (isEmpty(newElements)) {
            return array;
        }
        if (isEmpty(array)) {
            return newElements;
        }

        final int len = length(array);
        if (index < 0) {
            index = (index % len) + len;
        }

        final T[] result = newArray(array.getClass().getComponentType(), Math.max(len, index) + newElements.length);
        System.arraycopy(array, 0, result, 0, Math.min(len, index));
        System.arraycopy(newElements, 0, result, index, newElements.length);
        if (index < len) {
            System.arraycopy(array, index, result, index + newElements.length, len - index);
        }
        return result;
    }


    /**
     * 获取数组长度<br>
     * 如果参数为{@code null}，返回0
     *
     * <pre>
     * ArrayUtil.length(null)            = 0
     * ArrayUtil.length([])              = 0
     * ArrayUtil.length([null])          = 1
     * ArrayUtil.length([true, false])   = 2
     * ArrayUtil.length([1, 2, 3])       = 3
     * ArrayUtil.length(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array 数组对象
     * @return 数组长度
     * @throws IllegalArgumentException 如果参数不为数组，抛出此异常
     * @see Array#getLength(Object)
     * @since 3.0.8
     */
    public static int length(Object array) throws IllegalArgumentException {
        if (null == array) {
            return 0;
        }
        return Array.getLength(array);
    }


    /**
     * 新建一个空数组
     *
     * @param <T>           数组元素类型
     * @param componentType 元素类型
     * @param newSize       大小
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }
}
