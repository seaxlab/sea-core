package com.github.seaxlab.core.util;

import static com.github.seaxlab.core.test.util.TestUtil.sleepSecond;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/15
 * @since 1.0
 */
@Slf4j
public class TimeoutUtilTest extends BaseCoreTest {


  @Test
  public void testNormal() throws Exception {
    log.info("try to begin");
    Timer timer = TimeoutUtil.check(10, TimeUnit.SECONDS, () -> {
      log.info("try to abort...");
    });

    sleepSecond(5);
    log.info("end.");
    timer.cancel();
  }


  @Test
  public void testTimeout() throws Exception {
    log.info("try to begin");
    Timer timer = TimeoutUtil.check(10, TimeUnit.SECONDS, () -> {
      log.info("try to abort...");
    });

    sleepSecond(20);
    log.info("end.");
  }

  @Test
  public void test46() throws Exception {
    String str = "";
    try {
      str = CompletableFuture.supplyAsync(() -> {
                               // your biz logic
                               sleepSecond(3);
                               return "success";
                             })
                             .get(30, TimeUnit.SECONDS);
    } catch (TimeoutException e) {
      log.error("timeout exception.", e);
      str = "";
    }

    log.info("str={}", str);
  }
}
