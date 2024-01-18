package com.github.seaxlab.core.thread;

import static com.github.seaxlab.core.test.util.TestUtil.runInMultiThread;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * thread context test
 *
 * @author spy
 * @version 1.0 2020/2/28
 * @since 1.0
 */
@Slf4j
public class ThreadContextTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {

    Boolean hit = ThreadContext.get("abc"); //ok
    boolean hit2 = ThreadContext.get("abc"); // throw NPE
  }

  @Test
  public void run25() throws Exception {
    runInMultiThread(() -> {
      try {
        String obj = ThreadContext.get("abc");
        log.info("obj={}", obj);
      } finally {
        ThreadContext.clean();
      }
    });
  }

  @Test
  public void test39() throws Exception {
    ThreadLocal<String> local = new ThreadLocal<>();
    local.remove();
    local.remove();

  }

}
