package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * velocity util test
 *
 * @author spy
 * @version 1.0 2022/11/27
 * @since 1.0
 */
@Slf4j
public class VelocityUtilTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {
    Map<String, Object> params = new HashMap<>();
    String content = VelocityUtil.render("/velocity/test.vm", params);
    log.info("{}", content);
  }
}
