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
public class DiskUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {


    log.info("free space={}", DiskUtil.getFreeSpace(PathUtil.getUserHome()));
    log.info("usable space={}", DiskUtil.getUsableSpace(PathUtil.getUserHome()));
    log.info("total space={}", DiskUtil.getTotalSpace(PathUtil.getUserHome()));
    log.info("used percent={}", DiskUtil.getSpaceUsedPercent(PathUtil.getUserHome()));
  }
}
