package com.github.seaxlab.core.cache.local.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Slf4j
public class LocalReentrantLock implements Lock {

  private Map<String, Lock> pool;
  private String lockKey;

  private Lock lock = new ReentrantLock();

  public LocalReentrantLock(Map<String, Lock> pool, String lockKey) {
    this.pool = pool;
    this.lockKey = lockKey;
  }

  @Override
  public void lock() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean tryLock() {
    return lock.tryLock();
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    long expired = time;
    if (expired == 0) {
      expired = 3000;
    }
    return lock.tryLock(expired, unit);
  }

  @Override
  public void unlock() {
    lock.unlock();
    pool.remove(lockKey);
  }

  @Override
  public Condition newCondition() {
    throw new UnsupportedOperationException();
  }
}
