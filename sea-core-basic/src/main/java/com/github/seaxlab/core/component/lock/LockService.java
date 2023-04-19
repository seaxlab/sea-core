package com.github.seaxlab.core.component.lock;

import com.github.seaxlab.core.component.lock.request.LockRequest;
import com.github.seaxlab.core.component.lock.request.LockWithResultRequest;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/19
 * @since 1.0
 */
public interface LockService {

  void tryLock(LockRequest request);

  <R> R tryLock(LockWithResultRequest<R> request);

  void tryLock(String lockKey, String bizName, Runnable runnable);

  <R> R tryLock(String lockKey, String bizName, Supplier<R> supplier);

  void tryLock(Collection<String> lockKeyList, String bizName, Runnable runnable);

  <R> R tryLock(Collection<String> lockKeyList, String bizName, Supplier<R> supplier);


}
