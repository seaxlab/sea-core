package com.github.spy.sea.core.thread.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * module name
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
        return executorMap.remove(uniqueName);
    }

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
    private static ThreadPoolExecutor namedFixMaxThreadPool(final String uniqueName,
                                                            final int core, final int max, final int queueSize,
                                                            RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(core, max, 1L, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(queueSize), new ThreadFactory() {
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
