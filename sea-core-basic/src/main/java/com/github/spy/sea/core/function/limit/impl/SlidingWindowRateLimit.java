package com.github.spy.sea.core.function.limit.impl;

import com.github.spy.sea.core.function.limit.RateLimit;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 滑动窗口
 *
 * @author spy
 * @version 1.0 2021/2/3
 * @since 1.0
 */
@Slf4j
public class SlidingWindowRateLimit implements RateLimit, Runnable {

    /**
     * 滑动窗口分段的个数
     */
    private Integer bucketCount;
    private Integer maxAllowCount;
    private Integer maxAllowCountPerBucket;
    private volatile int index;

    private final AtomicLong counter = new AtomicLong(0);
    private AtomicLong[] counterPerBucket;

    private ScheduledExecutorService scheduledExecutorService;

    private SlidingWindowRateLimit() {
    }

    public SlidingWindowRateLimit(int bucketCount, int maxAllowCount, Integer duration, TimeUnit timeUnit) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount cannot be 0 or negative.");
        }
        if (maxAllowCount <= 0) {
            throw new IllegalArgumentException("maxAllowCount cannot be 0 or negative.");
        }

        this.bucketCount = bucketCount;
        this.maxAllowCount = maxAllowCount;

        counterPerBucket = new AtomicLong[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            counterPerBucket[i] = new AtomicLong(0);
        }
        this.maxAllowCountPerBucket = maxAllowCount / bucketCount;
        long ms = timeUnit.toMillis(duration);
        long msPerBucket = ms / bucketCount;

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this, 0, msPerBucket, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryRequire() {
        if (counterPerBucket[index].incrementAndGet() <= maxAllowCountPerBucket) {
            return true;
        }

        return false;
    }

    @Override
    public void reset() {
        index = (index + 1) % bucketCount;
        long val = counterPerBucket[index].getAndSet(0);
        counter.addAndGet(-val);
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
