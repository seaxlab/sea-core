package com.github.seaxlab.core.component.template.cache.base;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.component.template.cache.IObjectQueryCache;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * abstract query cache
 *
 * @author spy
 * @version 1.0 2022/9/10
 * @since 1.0
 */
@Slf4j
public abstract class BaseObjectQueryCache<V> implements IObjectQueryCache<V> {

  //用于初始化cache的参数及其缺省值
  private static final int DEFAULT_MAX_POOL_SIZE = 1;
  // 写入1min后移除
  private static final int DEFAULT_EXPIRE_AFTER_WRITE_DURATION = 1;

  // 缓存实例
  private volatile LoadingCache<String, Optional<V>> cache;
  private static final String BIZ_KEY = "object_cache_biz_key";

  /**
   * 从缓存中获取数据（第一次自动调用fetchData从外部获取数据），并处理异常
   *
   * @return Value
   */
  @Override
  public Optional<V> get() {
    Optional<V> result = Optional.empty();
    try {
      result = getCache().get(BIZ_KEY);
    } catch (ExecutionException e) {
      log.error("fail to execute loading cache");
      ExceptionHandler.publish(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }

    return result;
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
   * @return value, 连同key一起被加载到缓存中的。
   */
  protected abstract Optional<V> fetchData();

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
  private LoadingCache<String, Optional<V>> getCache() {
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
    builder.maximumSize(DEFAULT_MAX_POOL_SIZE);
    //数据被创建多久后被移除
    builder.expireAfterWrite(getExpireAfterWriteDuration(), TimeUnit.MINUTES);
    if (getRecordStatFlag()) {
      builder.recordStats();
    }

    cache = builder.build(new CacheLoader<String, Optional<V>>() {
      @Override
      public Optional<V> load(String key) {
        log.info("try to load cache[{}]", getBizType());
        Optional<V> value = fetchData();
        return Objects.isNull(value) ? Optional.empty() : value;
      }
    });
    log.info("init local loading cache success [{}]", this.getClass().getSimpleName());
  }
}
