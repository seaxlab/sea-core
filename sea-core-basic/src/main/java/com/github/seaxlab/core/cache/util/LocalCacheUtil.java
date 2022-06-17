package com.github.seaxlab.core.cache.util;

import com.github.seaxlab.core.exception.Precondition;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/3
 * @since 1.0
 */
@Slf4j
public class LocalCacheUtil {

    private static final ConcurrentHashMap<String, Cache> cache = new ConcurrentHashMap<>();

    /**
     * 获取cache缓存，默认2min
     *
     * @param bizType biz type
     * @return cache
     */
    public static final Cache getInstance(String bizType) {
        return getInstance(bizType, 2000, 2, TimeUnit.MINUTES);
    }

    public static final Cache getInstance(String bizType, int maxSize, long duration, TimeUnit unit) {
        Precondition.checkNotBlank(bizType, "bizType不能为空");
        Preconditions.checkState(maxSize > 0, "maxSize不能小于等于0");
        Preconditions.checkState(duration > 0, "duration不能小于等于0");

        cache.computeIfAbsent(bizType, key -> {
            log.info("create guava cache.");
            Cache loadingCache = CacheBuilder.newBuilder()
                                             .expireAfterWrite(duration, unit)
                                             .maximumSize(maxSize)
                                             .build();
            return loadingCache;
        });

        return cache.get(bizType);
    }

}
