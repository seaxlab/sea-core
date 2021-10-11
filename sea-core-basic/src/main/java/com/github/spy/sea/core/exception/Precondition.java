package com.github.spy.sea.core.exception;

import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

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

    public static <T> void checkNotNull(T reference, String errorMsg) {
        if (reference == null) {
            ExceptionHandler.publishMsg(errorMsg);
        }
    }

    public static void checkNotEmpty(String value, String errorMsg) {
        if (StringUtil.isEmpty(value)) {
            ExceptionHandler.publishMsg(errorMsg);
        }
    }

    public static void checkNotEmpty(String value, String errorCode, String errorMsg) {
        if (StringUtil.isEmpty(value)) {
            ExceptionHandler.publish(errorCode, errorMsg);
        }
    }

    public static void checkNotEmpty(Collection collection, String errorMsg) {
        if (collection == null || collection.isEmpty()) {
            ExceptionHandler.publishMsg(errorMsg);
        }
    }

    public static void checkNotEmpty(Collection collection, String errorCode, String errorMsg) {
        if (collection == null || collection.isEmpty()) {
            ExceptionHandler.publish(errorCode, errorMsg);
        }
    }


    public static void checkNotBlank(String value, String errorMsg) {
        if (StringUtil.isBlank(value)) {
            ExceptionHandler.publishMsg(errorMsg);
        }
    }

    public static void checkNotBlank(String value, String errorCode, String errorMsg) {
        if (StringUtil.isBlank(value)) {
            ExceptionHandler.publish(errorCode, errorMsg);
        }
    }

    public static void checkState(boolean expression, String errorMsg) {
        if (!expression) {
            ExceptionHandler.publishMsg(errorMsg);
        }
    }

    public static void checkState(boolean expression, String errorCode, String errorMsg) {
        if (!expression) {
            ExceptionHandler.publish(errorCode, errorMsg);
        }
    }

}
