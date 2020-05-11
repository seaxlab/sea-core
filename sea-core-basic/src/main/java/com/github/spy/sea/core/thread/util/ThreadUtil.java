package com.github.spy.sea.core.thread.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

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

}
