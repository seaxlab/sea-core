package com.github.seaxlab.core.component.retry;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.retry.util.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * retry util test
 *
 * @author spy
 * @version 1.0 2024/01/08
 * @since 1.0
 */
@Slf4j
public class RetryUtilTest extends BaseCoreTest {

  @Test
  public void callTest() throws Exception {
    Boolean flag = RetryUtil.call(() -> {
      log.info("do call");
      int a = 1 / 0;
      return null;
    }, 3, "query user info");

    log.info("flag={}", flag);

  }

  @Test
  public void runTest() throws Exception {
    RetryUtil.run(() -> {
      log.info("do call");
      int a = 1 / 0;
    }, 3, "query user info");

    log.info("biz end.");

  }
}
