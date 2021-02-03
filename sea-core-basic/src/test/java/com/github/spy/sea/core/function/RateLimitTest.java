package com.github.spy.sea.core.function;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.function.limit.RateLimit;
import com.github.spy.sea.core.function.limit.impl.FixedWindowRateLimit;
import com.github.spy.sea.core.function.limit.impl.SlidingWindowRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/3
 * @since 1.0
 */
@Slf4j
public class RateLimitTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        RateLimit rateLimit = new FixedWindowRateLimit(10, 30, TimeUnit.SECONDS);

        for (int i = 0; i < 1000; i++) {
            log.info("index={},try require={}", i, rateLimit.tryRequire());
            sleep(1);
        }
    }

    @Test
    public void test33() throws Exception {
        RateLimit rateLimit = new SlidingWindowRateLimit(10, 100, 100, TimeUnit.SECONDS);
        for (int i = 0; i < 1000; i++) {
            log.info("index={},try require={}", i, rateLimit.tryRequire());
            sleepMs(300);
        }
    }
}
