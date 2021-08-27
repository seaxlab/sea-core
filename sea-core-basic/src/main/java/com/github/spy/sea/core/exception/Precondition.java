package com.github.spy.sea.core.exception;

import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

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

    public static void checkNotEmpty(String field, String errorMsg) {
        if (StringUtil.isEmpty(field)) {
            ExceptionHandler.publishMsg(errorMsg);
        }
    }

    public static void checkNotEmpty(String field, String errorCode, String errorMsg) {
        if (StringUtil.isEmpty(field)) {
            ExceptionHandler.publish(errorCode, errorMsg);
        }
    }

}
