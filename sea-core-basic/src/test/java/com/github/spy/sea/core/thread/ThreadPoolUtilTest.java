package com.github.spy.sea.core.thread;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.thread.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/18
 * @since 1.0
 */
@Slf4j
public class ThreadPoolUtilTest extends BaseCoreTest {

    @Test
    public void testCreateTemp() throws Exception {

        ThreadPoolExecutor tpe = ThreadPoolUtil.createTemp("test-pool", 2, 2);
        log.info("start");
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("run future");
            sleep(10);
            log.info("---");
            return "f1";
        }, tpe);

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            log.info("run future");
            sleep(20);
            log.info("----");
            return "f2";
        }, tpe);

        Object obj = CompletableFuture.allOf(f1, f2).get();
        log.info("obj={}", obj);
    }
}
