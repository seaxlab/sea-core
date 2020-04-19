package com.github.spy.sea.core.cache.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019-05-30
 * @since 1.0
 */
@Slf4j
public final class CacheKeyUtil {

    private CacheKeyUtil() {
    }

    /**
     * 获取缓存key
     *
     * @param system 系统标识
     * @param module 模块标识
     * @param keys   主键
     * @return
     */
    public static String get(String system, String module, String... keys) {
        Preconditions.checkNotNull(system, "system参数不能为空");
        Preconditions.checkNotNull(module, "module参数不能为空");

        StringBuilder sb = new StringBuilder(system);
        sb.append(":").append(module);

        if (keys != null) {

            for (int i = 0; i < keys.length; i++) {
                sb.append(":").append(keys[i]);
            }
        }
        return sb.toString();
    }

}
