package com.github.spy.sea.core.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/19
 * @since 1.0
 */
@Slf4j
@Deprecated
public class SeaThread extends Thread {
    private long tid = Thread.currentThread().getId();
    private Runnable wrappedRunnable;
    private Map<String, Object> threadContext;

    public SeaThread(Runnable wrappedRunnable) {
        this.initRunnable(wrappedRunnable, ThreadContext.getContext());
    }

    private void initRunnable(Runnable wrappedRunnable, Map<String, Object> threadContext) {
        this.wrappedRunnable = wrappedRunnable;
        this.threadContext = threadContext;
    }

    @Override
    public void run() {
        try {
            wrappedRunnable.run();
        } finally {

        }
    }
}
