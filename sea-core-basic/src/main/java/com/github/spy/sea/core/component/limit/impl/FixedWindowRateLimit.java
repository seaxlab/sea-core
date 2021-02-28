package com.github.spy.sea.core.component.limit.impl;

import com.github.spy.sea.core.component.limit.RateLimit;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 固定时间窗口
 *
 * @author spy
 * @version 1.0 2021/2/3
 * @since 1.0
 */
@Slf4j
public class FixedWindowRateLimit implements RateLimit, Runnable {

    private final AtomicLong counter = new AtomicLong(0);

    private Integer maxAllowCount;

    private long startTime;
    private long durationTime;

    private ScheduledExecutorService scheduledExecutorService;

    private FixedWindowRateLimit() {
    }

    public FixedWindowRateLimit(Integer maxAllowCount, Integer duration, TimeUnit timeUnit) {
        if (maxAllowCount <= 0) {
            throw new IllegalArgumentException("allowCount cannot be 0 or negative.");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("duration cannot be 0 or negative.");
        }
        this.maxAllowCount = maxAllowCount;
        this.startTime = System.currentTimeMillis();
        this.durationTime = timeUnit.toMillis(duration);

        // start reset thread.
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this, 0, duration, timeUnit);
    }

    @Override
    public boolean tryRequire() {
        if (counter.incrementAndGet() < maxAllowCount) {
            return true;
        }

        return false;
    }

    @Override
    public void reset() {
        this.counter.set(0);
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void destroy() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    @Override
    public void run() {
        reset();
    }
}
