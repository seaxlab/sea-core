package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/19
 * @since 1.0
 */
@Slf4j
public class SeaRunnableTest extends BaseCoreTest {


    @Test
    public void run18() throws Exception {
        ThreadContext.put("name", "smith");


        new Thread() {
            @Override
            public void run() {
                log.info("---thread,{}", (String) ThreadContext.get("name"));
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("---thread runnable,{}", (String) ThreadContext.get("name"));

            }
        }).start();
        //
        SeaRunnable runnable = new SeaRunnable(new Runnable() {
            @Override
            public void run() {
                log.info("---custom,{}", (String) ThreadContext.get("name"));
            }
        });

        runnable.run();


        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                log.info("-----run {}", (String) ThreadContext.get("name"));
            }
        };

        runnable1.run();


        TimeUnit.SECONDS.sleep(10);
    }
}
