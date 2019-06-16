package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-06-14
 * @since 1.0
 */
@Slf4j
public final class ObjectUtil {

    public static Object get(Object obj, String field) {
        if (obj == null) {
            return null;
        }

        return ReflectUtil.read(obj, field);
    }

}
