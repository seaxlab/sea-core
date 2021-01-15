package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/5
 * @since 1.0
 */
@Slf4j
public final class ExceptionUtil {

    private ExceptionUtil() {

    }

    /**
     * get message
     *
     * @param throwable
     * @return
     */
    public static String getMessage(final Throwable throwable) {
        return ExceptionUtils.getMessage(throwable);
    }

    /**
     * 获取堆栈信息
     * InvocationTargetException中Message为null，需要使用此方法来获取
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(final Throwable throwable) {
        return ExceptionUtils.getStackTrace(throwable);
    }

    /**
     * 获取最底层的异常,非常重要的接口
     * 排除 InvocationTargetException/NestedServletException等嵌套异常
     *
     * @param throwable
     * @return
     */
    public static Throwable getRootCause(final Throwable throwable) {
        return ExceptionUtils.getRootCause(throwable);
    }

    /**
     * 获取跟路径堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getRootCauseMessage(final Throwable throwable) {
        return ExceptionUtils.getRootCauseMessage(throwable);
    }

}
