package com.github.seaxlab.core.component.id;

import com.github.seaxlab.core.component.id.support.IdHexWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模块名
 *
 * @author shi.pengyan
 * @version 1.0 2017-07-07 13:27
 * @since 1.0
 */
@Slf4j
public class IdHexWorkerTest {

    @Test
    public void run() {
        final long idepo = System.currentTimeMillis() / 1000 - 3600;

        IdHexWorker worker = new IdHexWorker(1, 1, 0, idepo);

        for (int i = 0; i < 100000; i++) {
            System.out.println(worker.next());
        }

        System.out.println(worker);

        long nextId = worker.next();
        System.out.println(nextId);
        long time = worker.getIdTime(nextId);
        System.out.println(time + "->" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000)));
    }

    @Test
    public void test2() throws Exception {

        IdHexWorker worker = new IdHexWorker(1, 1, 0);
        ExecutorService executor = Executors.newFixedThreadPool(8);

        CountDownLatch countDownLatch = new CountDownLatch(1000000);
        Runnable run = () -> {
            System.out.println(worker.next());
            countDownLatch.countDown();
        };

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            executor.execute(run);
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - startTime);
        executor.shutdown();
    }
}
