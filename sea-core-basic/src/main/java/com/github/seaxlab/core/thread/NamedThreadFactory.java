package com.github.seaxlab.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * thread factory create new thread
 *
 * @author spy
 * @version 1.0 2020-04-23
 * @since 1.0
 */
public class NamedThreadFactory implements ThreadFactory {
  private final AtomicLong threadIndex = new AtomicLong(0);
  private final String threadNamePrefix;
  private final boolean daemon;

  public NamedThreadFactory(final String threadNamePrefix) {
    this(threadNamePrefix, false);
  }

  public NamedThreadFactory(final String threadNamePrefix, boolean daemon) {
    this.threadNamePrefix = threadNamePrefix;
    this.daemon = daemon;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r, threadNamePrefix + "-" + this.threadIndex.incrementAndGet());
    thread.setDaemon(daemon);
    return thread;
  }
}
