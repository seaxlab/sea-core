package com.github.seaxlab.core.cache.local;

import com.github.seaxlab.core.cache.CacheManager;
import com.github.seaxlab.core.util.JSONUtil;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * local cache manager impl.
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Slf4j
public class LocalCacheManager implements CacheManager {
  private Cache<String, Object> cache;

  private Map<String, Lock> lockCache;

  @Override
  public Optional<Object> get(String key) {
    return Optional.ofNullable(cache.getIfPresent(key));
  }

  @Override
  public boolean add(String key, Object object) {
    cache.put(key, object);
    return true;
  }

  @Override
  public boolean add(String key, Object object, int expired) {
    // no expired.
    cache.put(key, object);
    return true;
  }

  @Override
  public boolean del(String... keys) {
    cache.invalidateAll(Arrays.asList(keys));
    return true;
  }

  @Override
  public boolean setJSON(String key, Object obj) {
    cache.put(key, JSONUtil.toStr(obj));
    return true;
  }

  @Override
  public <T> Optional<T> getJSON(String key, Class<T> clazz) {
    Object value = cache.getIfPresent(key);
    if (null == value) {
      return Optional.empty();
    }

    return Optional.of(JSONUtil.toObj(value.toString(), clazz));
  }

}
