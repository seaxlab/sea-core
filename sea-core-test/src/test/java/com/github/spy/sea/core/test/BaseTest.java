package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/14
 * @since 1.0
 */
@Slf4j
public class BaseTest extends AbstractCoreTest {

    @Test
    public void test16() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("seq={}", nextSeq("abc"));
        }
    }

    @Test
    public void test24() throws Exception {
        Runnable runnable = () -> {
            log.info("seq={}", nextSeq("abc"));
        };

        runInMultiThread(runnable);
        sleepMinute(5);
    }
}
