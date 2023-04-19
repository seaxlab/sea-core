package com.github.seaxlab.core.component.lock.impl;

import com.github.seaxlab.core.cache.redis.redisson.util.LockUtil;
import com.github.seaxlab.core.component.lock.BaseLockService;
import com.github.seaxlab.core.component.lock.request.BaseLockRequest;
import com.github.seaxlab.core.component.lock.request.LockRequest;
import com.github.seaxlab.core.component.lock.request.LockWithResultRequest;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.CollectionUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * redisson lock service
 *
 * @author spy
 * @version 1.0 2023/04/19
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("java:S2222")
public class RedissonLockService extends BaseLockService {

  private final RedissonClient redissonClient;

  @Override
  public void tryLock(LockRequest request) {
    RLock lock = getLock(request);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", request.getBizName(), request.getLockKeyString(), lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", request.getBizName());
      if (request.isThrowOnFailFlag()) {
        //TODO first from global config....
        ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
      } else {
        return;
      }
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      request.getRunnable().run();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", request.getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Override
  public <R> R tryLock(LockWithResultRequest<R> request) {
    RLock lock = getLock(request);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", request.getBizName(), request.getLockKeyString(), lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", request.getBizName());
      if (request.isThrowOnFailFlag()) {
        //TODO
        ExceptionHandler.publish(ErrorMessageEnum.LOCK_FAIL);
      } else {
        return null;
      }
    }

    R response;
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      response = request.getSupplier().get();
    } finally {
      stopwatch.stop();
      LockUtil.unlock(lock);
    }
    //
    log.info("{} end, cost={}ms", request.getBizName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    return response;
  }

  @Override
  public void tryLock(String lockKey, String bizName, Runnable runnable) {
    RLock lock = redissonClient.getLock(lockKey);
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKey, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
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
  public void tryLock(Collection<String> lockKeyList, String bizName, Runnable runnable) {
    Set<RLock> locks = CollectionUtil.toSet(lockKeyList, item -> redissonClient.getLock(item));

    RLock lock = redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKeyList, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
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
  public <R> R tryLock(Collection<String> lockKeyList, String bizName, Supplier<R> supplier) {
    Set<RLock> locks = CollectionUtil.toSet(lockKeyList, item -> redissonClient.getLock(item));

    RLock lock = redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    boolean lockFlag = lock.tryLock();
    log.info("{}, try to get lock, key={}, flag={}", bizName, lockKeyList, lockFlag);
    if (!lockFlag) {
      log.warn("{} no get lock key, so end.", bizName);
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

  //------------------------private--------------------
  private RLock getLock(BaseLockRequest request) {
    if (StringUtil.isNotBlank(request.getLockKey())) {
      return redissonClient.getLock(request.getLockKey());
    }

    if (CollectionUtil.isEmpty(request.getLockKeys())) {
      Set<RLock> locks = CollectionUtil.toSet(request.getLockKeys(), item -> redissonClient.getLock(item));
      return redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    }
    throw new BaseAppException("lockKey or lockKeys cannot both empty.");
  }
}
