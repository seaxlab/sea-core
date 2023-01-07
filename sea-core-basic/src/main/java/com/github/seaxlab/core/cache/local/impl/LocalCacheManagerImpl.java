package com.github.seaxlab.core.cache.local.impl;

import com.github.seaxlab.core.cache.CacheConfig;
import com.github.seaxlab.core.cache.CacheManager;
import com.github.seaxlab.core.cache.local.domain.LocalReentrantLock;
import com.github.seaxlab.core.util.JSONUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
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
public class LocalCacheManagerImpl implements CacheManager {
  private CacheConfig cacheConfig;
  private Cache<String, Object> cache;

  private Map<String, Lock> lockCache;

  @Override
  public void start(CacheConfig cacheConfig) {
    this.cacheConfig = cacheConfig;
    cache = CacheBuilder.newBuilder().build();
    lockCache = new HashMap<>();
  }

  @Override
  public void stop() {
    if (cache != null) {
      cache.invalidateAll();
      cache = null;
    }
  }

  @Override
  public String getType() {
    return cacheConfig.getType();
  }

  @Override
  public CacheConfig getCacheConfig() {
    return cacheConfig;
  }

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

  @Override
  public Lock getLock(String lockKey) {
    return lockCache.computeIfAbsent(lockKey, key -> new LocalReentrantLock(lockCache, lockKey));
  }
}
