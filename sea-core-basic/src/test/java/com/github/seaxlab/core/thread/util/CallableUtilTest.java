package com.github.seaxlab.core.thread.util;

import static com.github.seaxlab.core.test.util.TestUtil.sleepMinute;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/15
 * @since 1.0
 */
@Slf4j
public class CallableUtilTest extends BaseCoreTest {

  @Test
  public void testCreate() throws Exception {
    ThreadPoolExecutor tpe = ThreadPoolUtil.createTemp("sea-test", 4, 4);

    log.info("-----");
    Callable<String> callable = CallableUtil.create(true, () -> {
      log.info("get data");
      return "";
    });

    tpe.submit(callable);
    sleepMinute(2);

  }
}
