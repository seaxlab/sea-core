package com.github.seaxlab.core.component.queue.persist;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/8
 * @since 1.0
 */
@Slf4j
public class MFQueueTest extends BaseCoreTest {

    public static MFQueue consumeRecordQueue;

    @Before
    public void before() {
        MFQueuePool.init(getUserHome() + "/logs/queue_persist");
        consumeRecordQueue = MFQueuePool.getFQueue("my-test-queue");
    }

    @Test
    public void addTest() throws Exception {
        new Thread(() -> consumeTest()).start();

        for (int i = 0; i < 10; i++) {
            consumeRecordQueue.add((i + "sss").getBytes());
            log.info("add msg");
        }
        sleep(10000);
    }

    private void consumeTest() {
        while (true) {
            log.info("poll msg");
            byte[] item = consumeRecordQueue.poll();
            if (item != null) {
                log.info("msg={}", new String(item));
            }
            sleep(1);
        }
    }

    /**
     * 不支持
     */
    private void consume2Test() {
        while (true) {
            log.info("poll msg");
            byte[] item = consumeRecordQueue.peek();
            if (item != null) {
                log.info("msg={}", new String(item));
            }
            sleep(1);
        }
    }

    @After
    public void destory() {
        MFQueuePool.destory();
    }
}
