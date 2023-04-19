package com.github.seaxlab.core.component.lock;

import com.github.seaxlab.core.component.lock.request.LockRequest;
import com.github.seaxlab.core.component.lock.request.LockWithResultRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
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
  public void tryLock(LockRequest request) {

  }

  @Override
  public <R> R tryLock(LockWithResultRequest<R> request) {
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
  public void tryLock(Collection<String> lockKeyList, String bizName, Runnable runnable) {

  }

  @Override
  public <R> R tryLock(Collection<String> lockKeyList, String bizName, Supplier<R> supplier) {
    return null;
  }
}
