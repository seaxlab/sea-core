package com.github.seaxlab.core.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/1/26
 * @since 1.0
 */
@Slf4j
public class ResourceUtilTest {
  @Test
  public void testLoad() throws Exception {
    Map<String, InputStream> map = ResourceUtil.load("cool/*/*.*");

    log.info("map={}", map);
  }


}
