package com.github.seaxlab.core.component.lock.impl;

import com.github.seaxlab.core.cache.redis.redisson.util.LockUtil;
import com.github.seaxlab.core.common.SeaGlobalConfig;
import com.github.seaxlab.core.component.lock.BaseLockService;
import com.github.seaxlab.core.component.lock.request.LockConfig;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Stopwatch;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * redisson lock service
 *
 * @author spy
 * @version 1.0 2023/04/19
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"java:S2222", "java:S1192"})
public class RedissonLockService extends BaseLockService {

  private final RedissonClient redissonClient;

  @Override
  public void tryLock(LockConfig config, Runnable runnable) {
    RLock lock = getLock(config);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", config.getBizName(), config.getLockKeyString(), lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", config.getBizName());
      if (config.isThrowOnFailFlag()) {
        if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
          throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
        }
        ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
      } else {
        return;
      }
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      runnable.run();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", config.getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Override
  public <R> R tryLock(LockConfig config, Supplier<R> supplier) {
    RLock lock = getLock(config);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", config.getBizName(), config.getLockKeyString(), lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", config.getBizName());
      if (config.isThrowOnFailFlag()) {
        if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
          throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
        }
        ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
      } else {
        return null;
      }
    }

    R response;
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      response = supplier.get();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", config.getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    return response;
  }

  @Override
  public void tryLock(String lockKey, String bizName, Runnable runnable) {
    RLock lock = redissonClient.getLock(lockKey);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKey, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
      if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
        throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
      }
      ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      runnable.run();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", bizName, stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Override
  @SuppressWarnings("java:S2222")
  public <R> R tryLock(String lockKey, String bizName, Supplier<R> supplier) {
    RLock lock = redissonClient.getLock(lockKey);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKey, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
      if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
        throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
      }
      ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
    }

    R response;
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      response = supplier.get();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", bizName, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    return response;
  }

  @Override
  public void tryLock(Collection<String> lockKeys, String bizName, Runnable runnable) {
    Set<RLock> locks = CollectionUtil.toSet(lockKeys, redissonClient::getLock);

    RLock lock = redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKeys, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
      if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
        throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
      }
      ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      runnable.run();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", bizName, stopwatch.elapsed(TimeUnit.MILLISECONDS));

  }

  @Override
  public <R> R tryLock(Collection<String> lockKeys, String bizName, Supplier<R> supplier) {
    Set<RLock> locks = CollectionUtil.toSet(lockKeys, redissonClient::getLock);

    RLock lock = redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKeys, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
      if (SeaGlobalConfig.EXCEPTION_LOCK_FAIL != null) {
        throw SeaGlobalConfig.EXCEPTION_LOCK_FAIL;
      }
      ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
    }

    R response;
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      response = supplier.get();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", bizName, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    return response;
  }


  @Override
  public <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit) {
    RBucket<V> bucket = redissonClient.getBucket(key);
    boolean flag = bucket.trySet(value, timeToLive, timeUnit);
    log.info("try set key={},flag={}", key, flag);
    return flag;
  }


  //------------------------private--------------------
  private RLock getLock(LockConfig config) {
    if (StringUtil.isNotBlank(config.getLockKey())) {
      return redissonClient.getLock(config.getLockKey());
    }

    if (CollectionUtil.isEmpty(config.getLockKeys())) {
      Set<RLock> locks = CollectionUtil.toSet(config.getLockKeys(), redissonClient::getLock);
      return redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    }
    throw new BaseAppException("lockKey or lockKeys cannot both empty.");
  }
}
