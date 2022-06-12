package com.github.seaxlab.core.thread.util;

import com.github.seaxlab.core.component.matcher.SimpleMatcher;
import com.github.seaxlab.core.component.matcher.impl.DefaultSimpleMatcher;
import com.github.seaxlab.core.exception.Precondition;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/30
 * @since 1.0
 */
@Slf4j
public final class ThreadUtil {

    /**
     * async exec
     *
     * @param threadName
     * @param runnable
     */
    public static void execAsync(String threadName, Runnable runnable) {
        execAsync(threadName, runnable, false);
    }

    /**
     * async exec
     *
     * @param threadName
     * @param runnable
     * @param daemon
     */
    public static void execAsync(String threadName, Runnable runnable, boolean daemon) {
        Preconditions.checkNotNull(threadName);

        Thread t = new Thread(runnable);
        t.setName(threadName);
        t.setDaemon(daemon);
        t.start();
    }

    /**
     * add global uncaught exception handler. it is very useful functio.
     * IMPORTANT: it is chain handler, and it not effect before default uncaught exception handler.
     *
     * @param biFunction
     */
    public static void addGlobalUncaughtExceptionChainHandler(BiConsumer<Thread, Throwable> biFunction) {
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {

            biFunction.accept(thread, exception);
            if (handler != null) {
                handler.uncaughtException(thread, exception);
            }
        });
    }

    public static void sleepMinute(long duration) {
        sleep(duration, TimeUnit.MINUTES);
    }

    public static void sleepSecond(long duration) {
        sleep(duration, TimeUnit.SECONDS);
    }

    public static void sleepMillSecond(long duration) {
        sleep(duration, TimeUnit.MILLISECONDS);
    }

    /**
     * sleep
     *
     * @param duration
     * @param timeUnit
     */
    public static void sleep(long duration, TimeUnit timeUnit) {
        Precondition.checkState(duration > 0, "时长必须大于0");
        Precondition.checkNotNull(timeUnit, "时间单位不能为空");

        try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(duration, timeUnit));
        } catch (Exception e) {
            log.error("fail to sleep", e);
        }

    }

    /**
     * 判断当前JVM是否包含指定的线程
     *
     * @param threadName thread name or wildcard
     * @return
     */
    public static boolean has(String threadName) {
        if (threadName == null || threadName.isEmpty()) {
            log.warn("thread name is empty");
            return false;
        }

        SimpleMatcher matcher = new DefaultSimpleMatcher();

        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        for (ThreadInfo threadInfo : threadMxBean.getThreadInfo(threadMxBean.getAllThreadIds())) {
            if (threadInfo == null) {
                continue;
            }
            if (threadInfo.getThreadName() == null) {
                continue;
            }
            if (matcher.match(threadInfo.getThreadName(), threadName)) {
                return true;
            }
        }

        return false;
    }

}
