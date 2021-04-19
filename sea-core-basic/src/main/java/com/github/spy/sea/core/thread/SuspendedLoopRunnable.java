package com.github.spy.sea.core.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 可暂停的线程
 *
 * @author spy
 * @version 1.0 2020/7/1
 * @since 1.0
 */
@Slf4j
public abstract class SuspendedLoopRunnable implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        log.info("begin thread [{}]", name());
        init();

        while (running) {
            log.info("{} running", name());
            try {
                loopRun();
            } catch (Exception e) {
                log.error("fail to execute", e);
            }
        }

        destroy();
        log.info("end thread [{}]", name());
    }

    public abstract String name();

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 循环执行
     */
    public abstract void loopRun();

    /**
     * 结束执行
     */
    public abstract void destroy();

    public void stop() {
        running = false;
    }
}
