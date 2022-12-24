package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-13
 * @since 1.0
 */
@Slf4j
public class ByteUnitUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {

    String ret = ByteUnitUtil.format(1024);

    log.info("ret={}", ret);

    long total = DiskUtil.getTotalSpace(USER_HOME);

    log.info("total space={}", ByteUnitUtil.format(total));
    log.info("total space={}", ByteUnitUtil.format(total, 5));
  }

}
