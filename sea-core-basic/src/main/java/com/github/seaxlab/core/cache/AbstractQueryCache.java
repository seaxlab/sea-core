package com.github.seaxlab.core.cache;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.exception.Precondition;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.MapUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * abstract query cache
 *
 * @author spy
 * @version 1.0 2022/9/10
 * @since 1.0
 */
@Slf4j
public abstract class AbstractQueryCache<K, V> implements IQueryCache<K, V> {

  //用于初始化cache的参数及其缺省值
  private final int DEFAULT_MAX_POOL_SIZE = 500;
  // 写入10min后移除
  private final int DEFAULT_EXPIRE_AFTER_WRITE_DURATION = 10;

  // 缓存实例
  private volatile LoadingCache<K, Optional<V>> cache;

  /**
   * 从缓存中获取数据（第一次自动调用fetchData从外部获取数据），并处理异常
   *
   * @param key biz key.
   * @return Value
   */
  @Override
  public Optional<V> get(K key) {
    Precondition.checkNotNull(key, "key cannot be null.");
    Optional<V> result = Optional.empty();
    try {
      result = getCache().get(key);
    } catch (ExecutionException e) {
      log.error("fail to execute loading cache, key={}", key);
      ExceptionHandler.publish(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }

    return result;
  }


  @Override
  public Map<K, Optional<V>> getMap(Collection<K> keys) {
    if (CollectionUtil.isEmpty(keys)) {
      log.warn("keys is empty.");
      return new HashMap<>();
    }

    Map<K, Optional<V>> result;
    try {
      result = getCache().getAll(keys);
    } catch (ExecutionException e) {
      result = new HashMap<>();
      log.error("fail to execute loading cache, keys={}", keys);
      ExceptionHandler.publish(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }

    return result;
  }

  @Override
  public List<V> getList(Collection<K> keys) {
    Map<K, Optional<V>> map = getMap(keys);
    if (MapUtil.isEmpty(map)) {
      return new ArrayList();
    }

    return map.values().stream()//
      .filter(Optional::isPresent) //
      .map(Optional::get) //
      .collect(Collectors.toList());
  }

  /**
   * 业务类型
   *
   * @return biz type info
   */
  protected abstract String getBizType();

  /**
   * 根据key从数据库或其他数据源中获取一个value，并被自动保存到缓存中。
   *
   * @param key key
   * @return value, 连同key一起被加载到缓存中的。
   */
  protected abstract Optional<V> fetchData(K key);

  /**
   * fetch multi data
   *
   * @param keys keys
   * @return map
   */
  protected Map<K, Optional<V>> fetchMultiData(Iterable<? extends K> keys) {
    Map<K, Optional<V>> map = new HashMap<>();
    for (K key : keys) {
      Optional<V> value = fetchData(key);
      map.put(key, Objects.isNull(value) ? Optional.empty() : value);
    }
    return map;
  }

  /**
   * 设置最大缓存条数
   *
   * @return
   */
  public int getMaximumSize() {
    return DEFAULT_MAX_POOL_SIZE;
  }

  /**
   * 设置数据存在时长（分钟）
   *
   * @return
   */
  public int getExpireAfterWriteDuration() {
    return DEFAULT_EXPIRE_AFTER_WRITE_DURATION;
  }

  /**
   * 并发度
   *
   * @return
   */
  public int getConcurrencyLevel() {
    return CoreConst.NCPU;
  }

  /**
   * 是否开启统计
   *
   * @return
   */
  public boolean getRecordStatFlag() {
    return false;
  }

  //-------------------private

  /**
   * cache instance
   *
   * @return loading cache
   */
  private LoadingCache<K, Optional<V>> getCache() {
    //使用双重校验锁保证只有一个cache实例
    if (cache == null) {
      synchronized (this) {
        if (cache == null) {
          buildCache();
        }
      }
    }

    return cache;
  }

  private void buildCache() {
    CacheBuilder builder = CacheBuilder.newBuilder();
    //并发度
    builder.concurrencyLevel(getConcurrencyLevel());
    // 缓存最大数量
    builder.maximumSize(getMaximumSize());
    //数据被创建多久后被移除
    builder.expireAfterWrite(getExpireAfterWriteDuration(), TimeUnit.MINUTES);
    if (getRecordStatFlag()) {
      builder.recordStats();
    }

    cache = builder.build(new CacheLoader<K, Optional<V>>() {
      @Override
      public Optional<V> load(K key) {
        log.info("try to load cache[{}], key={}", getBizType(), key);
        Optional<V> value = fetchData(key);
        return value == null ? Optional.empty() : value;
      }

      @Override
      public Map<K, Optional<V>> loadAll(Iterable<? extends K> keys) throws Exception {
        log.info("try to load cache[{}] by multi keys, key={}", getBizType(), keys);
        Map<K, Optional<V>> map = fetchMultiData(keys);
        return map == null ? new HashMap<>() : map;
      }
    });
    log.info("init local loading cache success [{}]", this.getClass().getSimpleName());
  }
}
