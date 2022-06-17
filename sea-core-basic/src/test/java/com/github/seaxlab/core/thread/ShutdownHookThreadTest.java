package com.github.seaxlab.core.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/24
 * @since 1.0
 */
@Slf4j
public class ShutdownHookThreadTest {

    @Test
    public void test16() throws Exception {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(log, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                log.info("shutdown...");
                return null;
            }
        }));
    }
}
