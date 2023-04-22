package com.github.seaxlab.core.cache;

import java.util.Optional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
public interface CacheManager {
  // basic ops

  /**
   * get cache key.
   *
   * @param key
   * @return
   */
  Optional<Object> get(String key);

  /**
   * add obj to cache.
   *
   * @param key
   * @param object
   * @return
   */
  boolean add(String key, Object object);

  /**
   * add obj to cache.
   *
   * @param key
   * @param object
   * @param expired
   * @return
   */
  boolean add(String key, Object object, int expired);

  /**
   * del cache key.
   *
   * @param keys
   * @return
   */
  boolean del(String... keys);


  // high level ops.

  /**
   * set json obj
   *
   * @param key
   * @param obj
   */
  boolean setJSON(String key, Object obj);

  /**
   * get obj from json format obj.
   *
   * @param key
   * @param clazz
   * @param <T>
   * @return
   */
  <T> Optional<T> getJSON(String key, Class<T> clazz);

}
