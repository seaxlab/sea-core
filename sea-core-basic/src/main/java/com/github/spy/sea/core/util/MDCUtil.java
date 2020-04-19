package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-25
 * @since 1.0
 */
@Slf4j
public class MDCUtil {

    private MDCUtil() {
    }

    /**
     * 获取MDC上下文值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String get(String key, String defaultValue) {
        String value = MDC.get(key);
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }

        return value;
    }


    /**
     * 获取MDC上下文值,如果不存在则存入上下文
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getAndSet(String key, String defaultValue) {
        String value = MDC.get(key);

        if (StringUtil.isEmpty(value)) {

            MDC.put(key, defaultValue);

            return defaultValue;
        }

        return value;
    }
}
