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
     * 获取跟路径堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getRootCauseMessage(final Throwable throwable) {
        return ExceptionUtils.getRootCauseMessage(throwable);
    }

}
