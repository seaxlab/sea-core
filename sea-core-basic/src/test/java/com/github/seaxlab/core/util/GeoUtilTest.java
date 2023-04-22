package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/23
 * @since 1.0
 */
@Slf4j
public class GeoUtilTest {
  @Test
  public void testDistanceDesc() throws Exception {
    log.info("{}", GeoUtil.getDistanceDesc(100));
    log.info("{}", GeoUtil.getDistanceDesc(1000));
    log.info("{}", GeoUtil.getDistanceDesc(1001));
  }

}
