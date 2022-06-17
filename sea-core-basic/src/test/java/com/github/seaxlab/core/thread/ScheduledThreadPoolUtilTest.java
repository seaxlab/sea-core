package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.thread.util.ScheduledThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/5
 * @since 1.0
 */
@Slf4j
public class ScheduledThreadPoolUtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {

        ScheduledThreadPoolExecutor stpe = ScheduledThreadPoolUtil.get("sea-test-sch");

        // 只执行一次
        stpe.schedule(() -> {
            log.info("----run");
        }, 5, TimeUnit.SECONDS);

        // 周期性执行
        stpe.scheduleAtFixedRate(() -> {
            log.info("zzzz");
        }, 5, 5, TimeUnit.SECONDS);
        sleepSecond(30);
    }

}
