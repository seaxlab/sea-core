package com.github.spy.sea.core.cache;

import com.github.spy.sea.core.model.Tuple2;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * sys level cache service
 *
 * @author spy
 * @version 1.0 2021/5/29
 * @since 1.0
 */
public interface CacheService {

    Tuple2<Integer, TimeUnit> CACHE_CONFIG = Tuple2.of(1, TimeUnit.HOURS);


    /**
     * query one obj.
     *
     * @param key
     * @param supplier
     * @param clazz
     * @return
     */
    <T> Optional<T> query(String key, Supplier<T> supplier, Class<T> clazz);

    /**
     * query one obj
     *
     * @param key
     * @param supplier
     * @param clazz
     * @param timeout
     * @param timeUnit
     * @param <T>
     * @return
     */
    <T> Optional<T> query(String key, Supplier<T> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit);

    /**
     * query obj list.
     *
     * @param key
     * @param supplier
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> queryList(String key, Supplier<List<T>> supplier, Class<T> clazz);

    /**
     * query obj list
     *
     * @param key
     * @param supplier
     * @param clazz
     * @param timeout
     * @param timeUnit
     * @param <T>
     * @return
     */
    <T> List<T> queryList(String key, Supplier<List<T>> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit);

    /**
     * set key
     *
     * @param key
     * @param obj
     * @return
     */
    boolean set(String key, Object obj);

    /**
     * set key with expire time.
     *
     * @param key
     * @param obj
     * @param timeout
     * @param timeUnit
     * @return
     */
    boolean set(String key, Object obj, long timeout, TimeUnit timeUnit);

    /**
     * expire key
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * delete key
     *
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * delete keys
     *
     * @param keys
     * @return
     */
    Long delete(Collection keys);

    /**
     * 扫描匹配的key
     *
     * @param pattern
     * @param count
     * @return
     */
    Set<String> scan(String pattern, int count);

}
