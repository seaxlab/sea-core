package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.thread.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

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

  @Test
  public void testCreateTemp2() throws Exception {
    // 单线程时，顺序执行
    ThreadPoolExecutor tpe = ThreadPoolUtil.createTemp("test-pool", 1, 1);
    log.info("start");
    AtomicInteger count = new AtomicInteger(0);
    for (int i = 0; i < 100; i++) {
      String idx = "" + i;
      tpe.submit(() -> {
        log.info("count={},index={}", count.incrementAndGet(), idx);
        sleep(3);
      });
    }


    sleepMinute(5);
  }

}
