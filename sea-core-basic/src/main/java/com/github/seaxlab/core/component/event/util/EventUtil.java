package com.github.seaxlab.core.component.event.util;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 事件分发器
 *
 * @author spy
 * @version 1.0 2019/3/19
 * @since 1.0
 */
@Slf4j
public final class EventUtil {

    private static final ExecutorService executor = new ThreadPoolExecutor(
            10, 10,
            30, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(10000),
            (r, executor) -> log.error("reject runnable={},executor={}", r, executor));

    public static final EventBus BUS = new AsyncEventBus(executor);

    private EventUtil() {
    }
}
