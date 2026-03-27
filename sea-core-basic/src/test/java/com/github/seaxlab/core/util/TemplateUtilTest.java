package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串模板工具测试
 *
 * @author spy
 * @version 1.0 2020/5/13
 * @since 1.0
 */
@Slf4j
public class TemplateUtilTest extends BaseCoreTest {

  @Test
  public void testFormat() {
    log.info("{}", TemplateUtil.format("hi {}!", "Mark"));
  }

  @Test
  public void testFormatByMap() {
    Map<String, String> data = new HashMap<>();
    data.put("name", "Mark");
    Assert.assertEquals("hi Mark!", TemplateUtil.format("hi ${name}!", data));
  }

  @Test
  public void testFormatV2() throws Exception {
    Map<String, String> data = new HashMap<>();
    data.put("name", "Mark");
    Assert.assertEquals("hi Mark!", TemplateUtil.formatV2("hi {name}!", data));
  }

  @Test
  public void testFormatByIndex() throws Exception {
    Assert.assertEquals("2,1,1,1", TemplateUtil.formatByIndex("{1},{0},{0},{0}", "1", "2"));
  }
}
