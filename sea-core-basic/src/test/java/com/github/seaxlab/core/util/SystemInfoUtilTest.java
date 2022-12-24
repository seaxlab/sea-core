package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-04
 * @since 1.0
 */
@Slf4j
public class SystemInfoUtilTest {

  @Test
  public void run16() throws Exception {
    log.info("hostname={}", SystemInfoUtil.getHostName());
  }
}
