package com.github.spy.sea.core.thread.util;

import com.alipay.common.tracer.core.async.SofaTracerCallable;
import com.alipay.common.tracer.core.async.SofaTracerRunnable;
import com.github.spy.sea.core.common.CoreConst;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/2/27
 * @since 1.0
 */
@Slf4j
public final class ExecutorServiceUtil {

    private ExecutorServiceUtil() {
    }

    private static class Holder {
        private static ExecutorService executor = new ThreadPoolExecutor(
                10, 10,
                30, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(5000),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * global executor service.
     *
     * @return
     */
    public static ExecutorService get() {
        return Holder.executor;
    }

    /**
     * submit callable
     *
     * @param callable
     * @param <T>
     */
    public static <T> void submit(Callable<T> callable) {
        get().submit(CoreConst.HAS_SOFA_TRACER ? new SofaTracerCallable(callable) : callable);
    }

    /**
     * submit runnable.
     *
     * @param runnable
     */
    public static void submit(Runnable runnable) {
        get().submit(CoreConst.HAS_SOFA_TRACER ? new SofaTracerRunnable(runnable) : runnable);
    }
}