package com.github.seaxlab.core.lang.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 可以作为同步等待超时时间
 */
@Slf4j
public class WaitNotifyObject {

  private final ConcurrentHashMap<Long/* thread id */, AtomicBoolean/* notified */> waitingThreadTable =
    new ConcurrentHashMap<Long, AtomicBoolean>(16);

  private AtomicBoolean hasNotified = new AtomicBoolean(false);

  public void wakeup() {
    boolean needNotify = hasNotified.compareAndSet(false, true);
    if (needNotify) {
      synchronized (this) {
        this.notify();
      }
    }
  }

  protected void waitForRunning(long interval) {
    if (this.hasNotified.compareAndSet(true, false)) {
      this.onWaitEnd();
      return;
    }
    synchronized (this) {
      try {
        if (this.hasNotified.compareAndSet(true, false)) {
          this.onWaitEnd();
          return;
        }
        this.wait(interval);
      } catch (InterruptedException e) {
        log.error("Interrupted", e);
      } finally {
        this.hasNotified.set(false);
        this.onWaitEnd();
      }
    }
  }

  protected void onWaitEnd() {
  }

  public void wakeupAll() {
    boolean needNotify = false;
    for (Map.Entry<Long, AtomicBoolean> entry : this.waitingThreadTable.entrySet()) {
      if (entry.getValue().compareAndSet(false, true)) {
        needNotify = true;
      }
    }
    if (needNotify) {
      synchronized (this) {
        this.notifyAll();
      }
    }
  }

  public void allWaitForRunning(long interval) {
    long currentThreadId = Thread.currentThread().getId();
    AtomicBoolean notified = this.waitingThreadTable.computeIfAbsent(currentThreadId, k -> new AtomicBoolean(false));
    if (notified.compareAndSet(true, false)) {
      this.onWaitEnd();
      return;
    }
    synchronized (this) {
      try {
        if (notified.compareAndSet(true, false)) {
          this.onWaitEnd();
          return;
        }
        this.wait(interval);
      } catch (InterruptedException e) {
        log.error("Interrupted", e);
      } finally {
        notified.set(false);
        this.onWaitEnd();
      }
    }
  }

  public void removeFromWaitingThreadTable() {
    long currentThreadId = Thread.currentThread().getId();
    synchronized (this) {
      this.waitingThreadTable.remove(currentThreadId);
    }
  }

  public int getWaitingSize() {
    return this.waitingThreadTable.size();
  }
}
