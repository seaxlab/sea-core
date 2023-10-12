package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.RemarkFormatEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/10/12
 * @since 1.0
 */
@Slf4j
public class RemarkUtilTest {

  @Test
  public void test16() throws Exception {
    log.info("{}", RemarkUtil.build("支付失败", "余额不足"));
    log.info("{}", RemarkUtil.build("支付失败", "余额不足", RemarkFormatEnum.V4));
  }

}
