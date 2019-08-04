package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public final class ClassUtil {

    private ClassUtil() {
    }


    /**
     * 包含包路径的名称，如“java.lang.String”
     *
     * @param clazz
     * @return
     */
    public static String getFullClassName(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        return clazz.getName();
    }

    /**
     * 类名，只有java.lang.String 返回"String"
     *
     * @param clazz
     * @return
     */
    public static String getClassName(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        return clazz.getSimpleName();
    }
}
