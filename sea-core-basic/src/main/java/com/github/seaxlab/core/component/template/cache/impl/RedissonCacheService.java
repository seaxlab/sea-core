package com.github.seaxlab.core.component.template.cache.impl;

import com.github.seaxlab.core.component.template.cache.BaseCacheService;
import com.github.seaxlab.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * redisson cache service
 *
 * @author spy
 * @version 1.0 2023/04/28
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class RedissonCacheService extends BaseCacheService {

  private final RedissonClient redissonClient;

  @Override
  public <V> V get(String key) {
    return (V) redissonClient.getBucket(key).get();
  }

  @Override
  public <V> void put(String key, V value) {
    redissonClient.getBucket(key).set(value);
  }

  @Override
  public <V> void put(String key, V value, long timeout, TimeUnit timeUnit) {
    redissonClient.getBucket(key).set(value, timeout, timeUnit);
  }

  @Override
  public <V> void putIfAbsent(String key, V value) {
    redissonClient.getBucket(key).trySet(value);
  }

  @Override
  public <V> void putIfAbsent(String key, V value, long timeout, TimeUnit timeUnit) {
    redissonClient.getBucket(key).trySet(value, timeout, timeUnit);
  }

  @Override
  public boolean contains(String key) {
    return redissonClient.getKeys().countExists(key) > 0;
  }

  @Override
  public boolean delete(String key) {
    return redissonClient.getKeys().delete(key) > 0;
  }

  @Override
  public long delete(Collection<String> keys) {
    super.delete(keys);
    //
    return redissonClient.getKeys().delete(ArrayUtil.toArray(keys, String.class));
  }
}
