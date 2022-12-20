package com.github.seaxlab.core.thread.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * 创建全局线程池，或创建临时线程池
 *
 * @author spy
 * @version 1.0 2020/12/4
 * @since 1.0
 */
@Slf4j
public final class ThreadPoolUtil {

  private ThreadPoolUtil() {
  }

  /**
   * 线程池
   */
  private static final ConcurrentHashMap<String, ThreadPoolExecutor> executorMap = new ConcurrentHashMap<>();


  /**
   * create executor
   *
   * @param uniqueName thread name/ biz name
   * @return
   */
  public static ThreadPoolExecutor get(String uniqueName) {
    return get(uniqueName, 8, 10, 1000);
  }

  /**
   * create executor
   *
   * @param uniqueName   thread name/ biz name
   * @param corePoolSize core pool size
   * @param queueSize    queue size
   * @return ThreadPoolExecutor
   */
  public static ThreadPoolExecutor get(final String uniqueName, final int corePoolSize, final int maxPoolSize,
    final int queueSize) {
    executorMap.computeIfAbsent(uniqueName, (key) -> {
      int finalCorePoolSize = 8;
      if (corePoolSize <= 0) {
        // auto detect.
        int cpuCount = Runtime.getRuntime().availableProcessors();
        cpuCount = cpuCount > 32 ? 32 : cpuCount;
        finalCorePoolSize = cpuCount < 8 ? 8 : cpuCount;
      } else {
        finalCorePoolSize = corePoolSize;
      }
      int finalQueueSize = queueSize < 0 ? 0 : queueSize;

      return namedFixMaxThreadPool(uniqueName, finalCorePoolSize, maxPoolSize, finalQueueSize,
        (runnable, executor) -> log.error("{} reject exception.", uniqueName));
    });

    return executorMap.get(uniqueName);
  }

  /**
   * remove thread pool executor.
   *
   * @param uniqueName thread name/ biz name
   * @return ThreadPoolExecutor old thread pool executor.
   */
  public static ThreadPoolExecutor remove(String uniqueName) {
    if (executorMap.contains(uniqueName)) {
      destroyTpe(uniqueName, executorMap.get(uniqueName));
    }
    return executorMap.remove(uniqueName);
  }

  /**
   * try to destroy global thread pool executors.
   */
  public static void destroy() {
    if (!executorMap.isEmpty()) {
      executorMap.forEach((uniqueName, tpe) -> {
        destroyTpe(uniqueName, tpe);
      });
    }
  }

  /**
   * destroy thread pool executor
   *
   * @param executor
   */
  public static void destroy(ThreadPoolExecutor executor) {
    log.info("try shutdown thread pool executor");
    if (executor != null && !executor.isShutdown()) {
      try {
        executor.shutdownNow();
      } catch (Exception e) {
        log.warn("fail to shutdown thread pool executor.", e);
      }
    }
  }

  /**
   * destroy single tpe.
   *
   * @param uniqueName unique name
   * @param tpe        thread pool executor
   */
  public static void destroyTpe(String uniqueName, ThreadPoolExecutor tpe) {
    log.info("try shutdown thread pool executor [{}]", uniqueName);
    if (tpe != null && !tpe.isShutdown()) {
      try {
        tpe.shutdownNow();
      } catch (Exception e) {
        log.warn("fail to shutdown thread pool executor.", e);
      }
    }
  }

  /**
   * create temp thread pool executor, and it is away from global executor map.
   * <p>
   * You Should destroy it by yourself.
   * </p>
   *
   * @param uniqueName   thread pool name
   * @param corePoolSize core pool size
   * @param maxPoolSize  max pool size
   * @return thread pool executor
   */
  public static ThreadPoolExecutor createTemp(final String uniqueName, final int corePoolSize, final int maxPoolSize) {
    log.info("try to create temp thread pool executor[{}]", uniqueName);
    return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
      new ThreadFactory() {
        private AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
          Thread thread = new Thread(r);
          thread.setName(uniqueName + "-" + counter.incrementAndGet());
          return thread;
        }
      }, (runnable, executor) -> log.error("{} reject exception.", uniqueName));
  }

  //------------------------private method.


  /**
   * 创建固定大小的线程数
   *
   * @param uniqueName thread name
   * @param core       core pool size
   * @param max        max pool size
   * @param queueSize  block queue size.
   * @param handler    reject handler.
   * @return ThreadPoolExecutor
   */
  private static ThreadPoolExecutor namedFixMaxThreadPool(final String uniqueName, final int core, final int max,
    final int queueSize, RejectedExecutionHandler handler) {
    return new ThreadPoolExecutor(core, max, 1L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(queueSize),
      new ThreadFactory() {
        private AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
          Thread thread = new Thread(r);
          thread.setName(uniqueName + "-" + counter.incrementAndGet());
          return thread;
        }
      }, handler);
  }

}
