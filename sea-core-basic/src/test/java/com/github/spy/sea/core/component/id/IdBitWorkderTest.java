package com.github.spy.sea.core.component.id;

import com.github.spy.sea.core.component.id.support.IdBitWorker;
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
 * @version 1.0 2017-07-07 13:23
 * @since 1.0
 */
@Slf4j
public class IdBitWorkderTest {

    @Test
    public void run() {
        final long idepo = System.currentTimeMillis() - 3600 * 1000L;

        IdBitWorker worker = new IdBitWorker(1, 1, 0, idepo);

        for (int i = 0; i < 10; i++) {
            System.out.println(worker.next());
        }

        System.out.println(worker);

        long nextId = worker.next();
        System.out.println(nextId);
        long time = worker.getIdTimestamp(nextId);
        System.out.println(time + "->" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
    }


    //performance
    @Test
    public void run2() throws Exception {

        IdBitWorker worker = new IdBitWorker();
        ExecutorService executor = Executors.newFixedThreadPool(8);

        CountDownLatch countDownLatch = new CountDownLatch(1000000);
        Runnable run = () -> {
            worker.next();
            countDownLatch.countDown();
        };

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            executor.execute(run);
        }
        countDownLatch.await();
        log.debug("const={}ms", System.currentTimeMillis() - startTime);
        executor.shutdown();
    }
}
