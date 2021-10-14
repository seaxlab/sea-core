package com.github.spy.sea.core.thread.util;

import com.github.spy.sea.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/14
 * @since 1.0
 */
@Slf4j
public final class ScheduledThreadPoolUtil {

    private ScheduledThreadPoolUtil() {
    }

    private static final ConcurrentHashMap<String, ScheduledThreadPoolExecutor> executorMap = new ConcurrentHashMap<>();


    /**
     * create executor
     *
     * @param uniqueName thread name/ biz name
     * @return
     */
    public static ScheduledThreadPoolExecutor get(String uniqueName) {
        return get(uniqueName, 4);
    }

    /**
     * create executor
     *
     * @param uniqueName   thread name/ biz name
     * @param corePoolSize core pool size
     * @return ThreadPoolExecutor
     */
    public static ScheduledThreadPoolExecutor get(final String uniqueName, final int corePoolSize) {
        Precondition.checkState(corePoolSize > 0, "corePoolSize必须大于0");
        executorMap.computeIfAbsent(uniqueName, (key) -> {
            return namedFixMaxThreadPool(uniqueName, corePoolSize,
                    (runnable, executor) -> log.error("{} reject exception.", uniqueName));
        });

        return executorMap.get(uniqueName);
    }


    /**
     * 创建固定大小的线程数
     *
     * @param uniqueName thread name
     * @param core       core pool size
     * @param handler    reject handler.
     * @return ThreadPoolExecutor
     */
    private static ScheduledThreadPoolExecutor namedFixMaxThreadPool(final String uniqueName,
                                                                     final int core,
                                                                     RejectedExecutionHandler handler) {
        return new ScheduledThreadPoolExecutor(core,
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
