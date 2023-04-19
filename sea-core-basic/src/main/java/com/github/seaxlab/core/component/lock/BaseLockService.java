package com.github.seaxlab.core.component.lock;

import com.github.seaxlab.core.component.lock.request.LockConfig;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public abstract class BaseLockService implements LockService {
  @Override
  public void tryLock(LockConfig config, Runnable runnable) {

  }

  @Override
  public <R> R tryLock(LockConfig config, Supplier<R> supplier) {
    return null;
  }

  @Override
  public void tryLock(String lockKey, String bizName, Runnable runnable) {

  }

  @Override
  public <R> R tryLock(String lockKey, String bizName, Supplier<R> supplier) {
    return null;
  }

  @Override
  public void tryLock(Collection<String> lockKeys, String bizName, Runnable runnable) {

  }

  @Override
  public <R> R tryLock(Collection<String> lockKeys, String bizName, Supplier<R> supplier) {
    return null;
  }


  @Override
  public <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit) {
    return false;
  }

}
