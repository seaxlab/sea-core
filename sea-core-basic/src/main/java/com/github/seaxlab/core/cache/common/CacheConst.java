package com.github.seaxlab.core.cache.common;

import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;

/**
 * cache const
 *
 * @author spy
 * @version 1.0 2021/6/1
 * @since 1.0
 */
@Slf4j
public class CacheConst {

    /**
     * 空对象
     */
    public static final String EMPTY_OBJ = "{}";

    /**
     * 空数组
     */
    public static final String EMPTY_ARRAY = "[]";

    /**
     * guava common remove listener
     */
    public static final RemovalListener COMMON_REMOVE_LISTENER = (RemovalListener<String, Object>) notification -> {
        if (notification != null) {
            log.info("remove cache key={}", notification.getKey());
        }
    };
}
