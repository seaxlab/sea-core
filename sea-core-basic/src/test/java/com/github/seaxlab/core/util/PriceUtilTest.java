package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.enums.PriceUnitEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/11/9
 * @since 1.0
 */
@Slf4j
public class PriceUtilTest extends BaseCoreTest {

  @Test
  public void testConvert() {
    BigDecimal value = new BigDecimal("1234567.1234567");
    //
    //1234567.12
    log.info("{}", PriceUtil.toString(value, 2, PriceUnitEnum.YUAN));
    //1234567.12元
    log.info("{}", PriceUtil.toString(value, 2, PriceUnitEnum.YUAN_AND_UNIT));
    //123.46
    log.info("{}", PriceUtil.toString(value, 2, PriceUnitEnum.WAN));
    // 123.46万元
    log.info("{}", PriceUtil.toString(value, 2, PriceUnitEnum.WAN_AND_UNIT));
    //1234元
    log.info("{}", PriceUtil.toString(new BigDecimal("1234"), 2, PriceUnitEnum.YUAN_OR_WAN_UNIT));
    //123.46万元
    log.info("{}", PriceUtil.toString(new BigDecimal("1234567"), 2, PriceUnitEnum.YUAN_OR_WAN_UNIT));
  }

}
