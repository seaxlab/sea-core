package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/23
 * @since 1.0
 */
@Slf4j
public class NamedThreadFactoryTest extends BaseCoreTest {

    @Test
    public void test18() throws Exception {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("DiskCheckScheduledThread"));

        executorService.submit((Runnable) () -> {
            while (true) {
                log.info("----look thread name.");
                sleep(2);
            }
        });
        sleepMinute(5);
    }
}
