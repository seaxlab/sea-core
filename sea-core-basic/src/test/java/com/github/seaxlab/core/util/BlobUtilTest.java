package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Blob;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class BlobUtilTest {

  @Test
  public void run16() throws Exception {

    Blob blob = BlobUtil.string2blob("abc123");

    log.info("{}", blob);
  }
}
