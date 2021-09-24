package com.github.spy.sea.core.cache;

import com.github.spy.sea.core.model.EntityKey;
import com.github.spy.sea.core.model.Tuple2;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
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
     * query one obj, if null then cache with default value.
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param <T>      obj class
     * @return obj list
     */
    <T> Optional<T> query(String key, Supplier<T> supplier, Class<T> clazz);

    /**
     * query one key,
     * <p>if null in cache, then supplier.</p>
     * <p>if null in supplier, then return null and no store in cache with default value</p>
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param <T>      obj class
     * @return obj list
     */
    <T> Optional<T> queryIfAbsent(String key, Supplier<T> supplier, Class<T> clazz);

    /**
     * query one obj, if null then cache with default value
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param timeout  cache timeout
     * @param timeUnit cache timeout unit
     * @param <T>      obj class
     * @return obj list
     */
    <T> Optional<T> query(String key, Supplier<T> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit);

    /**
     * query one key,
     * <p>if null in cache, then supplier.</p>
     * <p>if null in supplier, then return null and no store in cache with default value</p>
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param timeout  cache timeout
     * @param timeUnit cache timeout unit
     * @param <T>      obj class
     * @return obj list
     */
    <T> Optional<T> queryIfAbsent(String key, Supplier<T> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit);

    /**
     * query obj list, if null then cache with default value
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param <T>      obj class
     * @return obj list
     */
    <T> List<T> queryList(String key, Supplier<List<T>> supplier, Class<T> clazz);

    /**
     * query obj list,
     * <p>if null in cache, then supplier.</p>
     * <p>if null in supplier, then return null and no store in cache with default value</p>
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param <T>      obj class
     * @return obj list
     */
    <T> List<T> queryListIfAbsent(String key, Supplier<List<T>> supplier, Class<T> clazz);

    /**
     * query obj list, if null then cache with default value
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param timeout  cache timeout
     * @param timeUnit cache timeout unit
     * @param <T>      obj class
     * @return obj list
     */
    <T> List<T> queryList(String key, Supplier<List<T>> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit);

    /**
     * query obj list,
     * <p>if null in cache, then supplier.</p>
     * <p>if null in supplier, then return null and no store in cache with default value</p>
     *
     * @param key      cache key
     * @param supplier supplier function
     * @param clazz    object clazz
     * @param timeout  cache timeout
     * @param timeUnit cache timeout unit
     * @param <T>      obj class
     * @return obj list
     */
    <T> List<T> queryListIfAbsent(String key, Supplier<List<T>> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit);


    /**
     * just query map list, if no exist so return
     * <p>
     * entity should implement EntityKey interface
     * </p>
     *
     * @param key     cache key
     * @param mapKeys hash map keys
     * @param <T>
     * @return
     */
    <T extends EntityKey> List<T> queryMapList(String key, List<String> mapKeys);

    /**
     * query map list
     * <p>
     * entity should implement EntityKey interface
     * </p>
     *
     * @param key      cache key
     * @param mapKeys  hash map keys
     * @param function function to query if absent.
     * @param <T>
     * @return
     */
    <T extends EntityKey> List<T> queryMapList(String key,
                                               List<String> mapKeys,
                                               Function<List<String>/*mapKeys*/, List<T>> function);

    /**
     * query map list
     * <p>
     * entity should implement EntityKey interface
     * </p>
     *
     * @param key      cache key
     * @param mapKeys  hash map keys
     * @param function function to query if absent.
     * @param timeout
     * @param timeUnit
     * @param <T>
     * @return
     */
    <T extends EntityKey> List<T> queryMapList(String key,
                                               List<String> mapKeys,
                                               Function<List<String>/*mapKeys*/, List<T>> function,
                                               long timeout, TimeUnit timeUnit);

    /**
     * set key
     *
     * @param key -
     * @param obj -
     * @return boolean
     */
    boolean set(String key, Object obj);

    /**
     * set key safely
     *
     * @param key -
     * @param obj -
     * @return
     */
    boolean setSafe(String key, Object obj);

    /**
     * 设置成默认过期时间
     *
     * @param key -
     * @param obj -
     * @return boolean
     */
    boolean setExpire(String key, Object obj);

    /**
     * set key with expire time.
     *
     * @param key      -
     * @param obj      -
     * @param timeout  -
     * @param timeUnit -
     * @return boolean
     */
    boolean set(String key, Object obj, long timeout, TimeUnit timeUnit);


    /**
     * set key with expire time.
     *
     * @param key      -
     * @param obj      -
     * @param timeout  -
     * @param timeUnit -
     * @return boolean
     */
    boolean setSafe(String key, Object obj, long timeout, TimeUnit timeUnit);

    /**
     * 设置json
     *
     * @param key -
     * @param obj -
     * @return boolean
     */
    boolean setJson(String key, Object obj);

    /**
     * 设置json
     *
     * @param key -
     * @param obj -
     * @return boolean
     */
    boolean setJsonSafe(String key, Object obj);

    /**
     * 设置json字符串，带默认过期时间
     *
     * @param key -
     * @param obj -
     * @return boolean
     */
    boolean setJsonExpire(String key, Object obj);

    /**
     * 设置json 字符串
     *
     * @param key      -
     * @param obj      -
     * @param timeout  -
     * @param timeUnit -
     * @return boolean
     */
    boolean setJson(String key, Object obj, long timeout, TimeUnit timeUnit);

    /**
     * 设置json 字符串
     *
     * @param key      -
     * @param obj      -
     * @param timeout  -
     * @param timeUnit -
     * @return boolean
     */
    boolean setJsonSafe(String key, Object obj, long timeout, TimeUnit timeUnit);

    /**
     * expire key
     *
     * @param key      -
     * @param timeout  -
     * @param timeUnit -
     * @return boolean
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * delete key
     *
     * @param key -
     * @return boolean
     */
    boolean delete(String key);

    /**
     * delete key safely
     *
     * @param key -
     * @return boolean
     */
    boolean deleteSafe(String key);

    /**
     * delete keys
     *
     * @param keys -
     * @return long
     */
    Long delete(Collection keys);

    /**
     * delete key safely
     *
     * @param keys -
     * @return Long
     */
    Long deleteSafe(Collection keys);

    /**
     * 扫描匹配的key
     *
     * @param pattern  匹配字符, 例如key*
     * @param count    每次遍历的个数
     * @param consumer 消费者
     */
    void scan(String pattern, int count, Consumer<byte[]> consumer);

}
