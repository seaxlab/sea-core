package com.github.seaxlab.core.thread.util;

import static com.github.seaxlab.core.thread.util.ThreadUtil.sleepSecond;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * future util test
 *
 * @author spy
 * @version 1.0 2023/03/01
 * @since 1.0
 */
@Slf4j
public class FutureUtilTest extends BaseCoreTest {

  ThreadPoolExecutor executor = ThreadPoolUtil.get("custom-task-pool");

  @Test
  public void testSimple() throws Exception {
    CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
      log.info("get user info begin.");
      sleepSecond(10);
      log.info("get user info end.");
      return "123";
    }, executor);
    log.info("user info={}", userFuture.get());
  }

}
