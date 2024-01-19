package com.github.seaxlab.core.thread.util;

import static com.github.seaxlab.core.test.util.TestUtil.sleepMinute;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.lang.jvm.manager.StackManager;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/21
 * @since 1.0
 */
@Slf4j
public class ThreadUtilTest extends BaseCoreTest {

  @Test
  public void testExceptionHandler() throws Exception {

    ThreadUtil.addGlobalUncaughtExceptionChainHandler((t, e) -> {
      log.info("handler1 t", t.getName());
    });

    ThreadUtil.addGlobalUncaughtExceptionChainHandler((t, e) -> {
      log.info("handler2 t", t.getName());
    });

    Thread t = new Thread(() -> {
      int a = 1 / 0;
    });

    t.start();

    sleep(10);
  }


  @Test
  public void testSleep() throws Exception {
    ThreadUtil.sleep(30, TimeUnit.SECONDS);
  }

  @Test
  public void testDump() throws Exception {
    log.info("{}", StackManager.dump());
    sleepMinute(1);
  }


  @Test
  public void testHas() throws Exception {
    log.info("check main thread, {}", ThreadUtil.has("main"));
    log.info("check main thread, {}", ThreadUtil.has("main*"));
    log.info("check main thread, {}", ThreadUtil.has("ma*"));
    log.info("check main thread, {}", ThreadUtil.has("ma1*"));
  }
}
