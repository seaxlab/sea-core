package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.enums.RegExpEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/16
 * @since 1.0
 */
@Slf4j
public class RegExpUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {
    log.info("{}", RegExpUtil.isCarNumber("浙A10232"));
  }

  @Test
  public void run23() throws Exception {
    //不含加号
    log.info("{}", RegExpUtil.is("a_", RegExpEnum.LETTER_AND_NUMBER_AND_UNDER_LINE)); //true
    log.info("{}", RegExpUtil.is("a-", RegExpEnum.LETTER_AND_NUMBER_AND_UNDER_LINE)); //false
    log.info("{}", RegExpUtil.is("a.", RegExpEnum.LETTER_AND_NUMBER_AND_UNDER_LINE)); //false
    log.info("{}", RegExpUtil.is("a+", RegExpEnum.LETTER_AND_NUMBER_AND_UNDER_LINE)); //false
  }

  @Test
  public void run32() throws Exception {
    log.info("{}", RegExpUtil.is("Aa1-_.", RegExpEnum.ID));
  }

  @Test
  public void testChinese() throws Exception {
    log.info("{}", RegExpUtil.find("中文", RegExpEnum.CHINESE));
    log.info("{}", RegExpUtil.find("12abc中文", RegExpEnum.CHINESE));

    log.info("{}", RegExpUtil.find("國", RegExpEnum.CHINESE)); // true
    log.info("{}", RegExpUtil.find("國", RegExpEnum.CHINESE_AND_EXTEND)); // false, why false
  }

  @Test
  public void testGetByBracket1() throws Exception {
    log.info("{}", RegExpUtil.getByBracket("a(abcd)d(aa)"));

  }

  @Test
  public void testGetByBracket2() throws Exception {
    log.info("{}", RegExpUtil.getByBracket("a(abcd)", "a"));
  }

  @Test
  public void testRemoveChinese() throws Exception {
    log.info("{}", RegExpUtil.removeChinese("hello中国"));
  }

}
