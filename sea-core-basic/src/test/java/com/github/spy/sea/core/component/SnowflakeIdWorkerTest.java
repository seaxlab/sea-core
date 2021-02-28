package com.github.spy.sea.core.component;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.component.snowflake.DefaultSnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.InetAddress;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/20
 * @since 1.0
 */
@Slf4j
public class SnowflakeIdWorkerTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {

        log.info("hostname={}", InetAddress.getLocalHost().getHostName());
        log.info("{}", System.currentTimeMillis());
        long startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            long id = DefaultSnowflakeIdWorker.generateId();
            log.info("id={}", id);
//2020 139530304863948801
//2015 511669915243073543
        }
        log.info("{}ms", (System.nanoTime() - startTime) / 1000000);
    }
}
