package com.github.seaxlab.core.component.template.cache;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * cache service
 *
 * @author spy
 * @version 1.0 2023/4/20
 * @since 1.0
 */
public interface CacheService {

  /**
   * get cache key.
   *
   * @param key biz key
   * @return value
   */
  <V> V get(String key);

  /**
   * add obj to cache.
   *
   * @param key   biz key
   * @param value value
   */
  <V> void put(String key, V value);

  /**
   * add obj to cache.
   *
   * @param key      biz key
   * @param value    value
   * @param timeout  time out time
   * @param timeUnit time unit
   */
  <V> void put(String key, V value, long timeout, TimeUnit timeUnit);

  /**
   * put obj to cache if absent
   *
   * @param key   biz key
   * @param value value
   */
  <V> void putIfAbsent(String key, V value);

  /**
   * put obj to cache if absent
   *
   * @param key      biz key
   * @param value    value
   * @param timeout  time out time
   * @param timeUnit time unit
   */
  <V> void putIfAbsent(String key, V value, long timeout, TimeUnit timeUnit);

  /**
   * check key exist
   *
   * @param key biz key
   * @return boolean
   */
  boolean contains(String key);

  /**
   * delete cache key.
   *
   * @param key biz key
   * @return boolean
   */
  boolean delete(String key);

  /**
   * delete multi
   *
   * @param keys biz key list
   * @return delete count
   */
  long delete(Collection<String> keys);
}
