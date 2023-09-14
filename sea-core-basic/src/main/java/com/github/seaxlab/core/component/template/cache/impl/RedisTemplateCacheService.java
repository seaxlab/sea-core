package com.github.seaxlab.core.component.template.cache.impl;

import com.github.seaxlab.core.component.template.cache.base.BaseCacheService;
import com.github.seaxlab.core.util.BooleanUtil;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * cache service base on redisTemplate.
 *
 * @author spy
 * @version 1.0 2023/4/20
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class RedisTemplateCacheService extends BaseCacheService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public <V> V get(String key) {
    return (V) redisTemplate.boundValueOps(key).get();
  }

  @Override
  public <V> void put(String key, V value) {
    redisTemplate.boundValueOps(key).set(value);
  }

  @Override
  public <V> void put(String key, V value, long timeout, TimeUnit timeUnit) {
    redisTemplate.boundValueOps(key).set(value, timeout, timeUnit);
  }

  @Override
  public <V> void putIfAbsent(String key, V value) {
    redisTemplate.boundValueOps(key).setIfAbsent(value);
  }

  @Override
  public <V> void putIfAbsent(String key, V value, long timeout, TimeUnit timeUnit) {
    redisTemplate.boundValueOps(key).setIfAbsent(value, timeout, timeUnit);
  }

  @Override
  public boolean contains(String key) {
    Boolean flag = redisTemplate.hasKey(key);

    return BooleanUtil.isTrue(flag);
  }

  @Override
  public boolean delete(String key) {
    return BooleanUtil.isTrue(redisTemplate.delete(key));
  }

  @Override
  public long delete(Collection<String> keys) {
    super.delete(keys);
    //
    Long count = redisTemplate.delete(keys);
    //
    return Objects.isNull(count) ? 0L : count;
  }
}
