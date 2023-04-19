package com.github.seaxlab.core.component.lock;

import com.github.seaxlab.core.component.lock.request.LockConfig;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/19
 * @since 1.0
 */
public interface LockService {

  void tryLock(LockConfig config, Runnable runnable);

  <R> R tryLock(LockConfig config, Supplier<R> supplier);

  void tryLock(String lockKey, String bizName, Runnable runnable);

  <R> R tryLock(String lockKey, String bizName, Supplier<R> supplier);

  void tryLock(Collection<String> lockKeys, String bizName, Runnable runnable);

  <R> R tryLock(Collection<String> lockKeys, String bizName, Supplier<R> supplier);

  <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit);

}
