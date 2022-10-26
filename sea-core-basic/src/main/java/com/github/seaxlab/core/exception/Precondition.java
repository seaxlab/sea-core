package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * custom precondition check.
 *
 * @author spy
 * @version 1.0 2021/8/3
 * @since 1.0
 */
@Slf4j
public final class Precondition {

    private Precondition() {
    }


    public static <T> void checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
    }

    public static <T> void checkNotNull(T reference, String msg) {
        if (reference == null) {
            ExceptionHandler.publishMsg(msg);
        }
    }

    public static void checkNotEmpty(String value, String msg) {
        if (StringUtil.isEmpty(value)) {
            ExceptionHandler.publishMsg(msg);
        }
    }

    public static void checkNotEmpty(String value, String code, String msg) {
        if (StringUtil.isEmpty(value)) {
            ExceptionHandler.publish(code, msg);
        }
    }

    public static <T> void checkNotEmpty(Collection<T> collection, String msg) {
        if (collection == null || collection.isEmpty()) {
            ExceptionHandler.publishMsg(msg);
        }
    }

    public static <T> void checkNotEmpty(Collection<T> collection, String code, String msg) {
        if (collection == null || collection.isEmpty()) {
            ExceptionHandler.publish(code, msg);
        }
    }

    public static <K, V> void checkNotEmpty(Map<K, V> data, String msg) {
        if (data == null || data.isEmpty()) {
            ExceptionHandler.publish("", msg);
        }
    }

    public static <K, V> void checkNotEmpty(Map<K, V> data, String code, String msg) {
        if (data == null || data.isEmpty()) {
            ExceptionHandler.publish(code, msg);
        }
    }


    public static void checkNotBlank(String value, String msg) {
        if (StringUtil.isBlank(value)) {
            ExceptionHandler.publishMsg(msg);
        }
    }

    public static void checkNotBlank(String value, String code, String msg) {
        if (StringUtil.isBlank(value)) {
            ExceptionHandler.publish(code, msg);
        }
    }

    public static void checkState(boolean expression, String msg) {
        if (!expression) {
            ExceptionHandler.publishMsg(msg);
        }
    }

    public static void checkState(boolean expression, String code, String msg) {
        if (!expression) {
            ExceptionHandler.publish(code, msg);
        }
    }

    /**
     * check stats is true.
     *
     * @param flag flag
     * @param msg  msg
     */
    public static void checkTrue(boolean flag, String msg) {
        if (!flag) {
            ExceptionHandler.publishMsg(msg);
        }
    }

    /**
     * check stats is false
     *
     * @param flag flag
     * @param msg  msg
     */
    public static void checkFalse(boolean flag, String msg) {
        if (!flag) {
            ExceptionHandler.publishMsg(msg);
        }
    }


    /**
     * 检查每个元素是否不为空
     *
     * @param arrays
     * @param message
     * @param <T>
     */
    public static <T> void checkElementNotNull(T[] arrays, String message) {
        checkNotNull(arrays);

        for (T element : arrays) {
            checkNotNull(element, message);
        }
    }

    /**
     * 检查每个‘字符串’元素不为空
     *
     * @param arrays
     * @param message
     */
    public static void checkStrElementNotBlank(String[] arrays, String message) {
        checkNotNull(arrays);

        for (String element : arrays) {
            checkNotBlank(element, message);
        }
    }

}
