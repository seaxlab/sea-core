package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/14
 * @since 1.0
 */
@Slf4j
public class VersionUtilTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {
    check("gson", "com/google/gson/Gson.class", "3.0.0");
    check("spring-core", "org/springframework/core/SpringVersion.class", "3.0.0");
  }


  private void check(String name, String path, String minVersion) {
    boolean valid = VersionUtil.validVersion(name, path, minVersion);
    log.info("check {}  min version={}, {}", name, minVersion, valid);
  }
}
