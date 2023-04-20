package com.github.seaxlab.core.component.template.cache;

import com.github.seaxlab.core.common.CoreConst;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/20
 * @since 1.0
 */
@Slf4j
public abstract class BaseCacheService implements CacheService {

  protected static final int DEFAULT_DELETE_MAX_SIZE = CoreConst.NUMBER_50;

  @Override
  public <V> V get(String key) {
    return null;
  }

  @Override
  public <V> void put(String key, V value) {
  }

  @Override
  public <V> void put(String key, V value, long timeout, TimeUnit timeUnit) {

  }

  @Override
  public <V> void putIfAbsent(String key, V value) {

  }

  @Override
  public <V> void putIfAbsent(String key, V value, long timeout, TimeUnit timeUnit) {

  }

  @Override
  public boolean contains(String key) {
    return false;
  }

  @Override
  public boolean delete(String key) {
    return false;
  }

  @Override
  public long delete(Collection<String> keys) {
    return 0;
  }
}
