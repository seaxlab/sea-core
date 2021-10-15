package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/15
 * @since 1.0
 */
@Slf4j
public class TimeoutUtilTest extends BaseCoreTest {


    @Test
    public void testNormal() throws Exception {
        log.info("try to begin");
        Timer timer = TimeoutUtil.check(10, TimeUnit.SECONDS, () -> {
            log.info("try to abort...");
        });

        sleepSecond(5);
        log.info("end.");
        timer.cancel();
    }


    @Test
    public void testTimeout() throws Exception {
        log.info("try to begin");
        Timer timer = TimeoutUtil.check(10, TimeUnit.SECONDS, () -> {
            log.info("try to abort...");
        });

        sleepSecond(20);
        log.info("end.");
    }
}
