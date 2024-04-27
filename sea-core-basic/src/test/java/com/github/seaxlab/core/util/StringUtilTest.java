package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.common.SymbolConst;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public class StringUtilTest extends BaseCoreTest {

  @Test
  public void testIsNullStr() throws Exception {
    Assert.assertTrue(StringUtil.isNullStr("nuLL"));
  }

  @Test
  public void testToCamelCase() throws Exception {
    Assert.assertEquals(StringUtil.toCamelCase("hello_world"), "helloWorld");
  }

  @Test
  public void testUniqueKey() throws Exception {
    // 全量
    Assert.assertEquals("1:s", StringUtil.uniqueKey(SymbolConst.COLON, 1, "s"));
    Assert.assertEquals("1:::s", StringUtil.uniqueKey(SymbolConst.COLON, 1, "", "", "s"));
    Assert.assertEquals("1-s", StringUtil.uniqueKey("-", 1, "s"));
  }

  @Test
  public void testJoin() throws Exception {
    // 按需
    Assert.assertEquals("1:s", StringUtil.join(":", "1", "s"));
    Assert.assertEquals("1:s", StringUtil.join(":", "1", "", "s"));
    Assert.assertEquals("1:s", StringUtil.join(":", "1", null, "s", null));
  }

  @Test
  public void testSplit() throws Exception {
    log.info("{}", StringUtil.splitToIterable("abc;;bac;hi", ';'));
    log.info("{}", StringUtil.splitToList("abc;;bac;hi", ';'));
    log.info("{}", StringUtil.splitToList(null, ';'));
  }

  @Test
  public void testDefaultIfBlank() throws Exception {
    Assert.assertEquals("", StringUtil.defaultIfBlank(null, ""));
    Assert.assertEquals("", StringUtil.defaultIfBlank(null));
  }

  @Test
  public void testDefaultIfEmpty() throws Exception {
    Assert.assertEquals("", StringUtil.defaultIfEmpty(null, ""));
    Assert.assertEquals("", StringUtil.defaultIfEmpty(null));
  }

  @Test
  public void testReplaceStart() throws Exception {
    Assert.assertEquals("a0a", StringUtil.replaceStart("00a0a", "0"));
    Assert.assertEquals("abc", StringUtil.replaceStart("abc", "bc"));
  }

  @Test
  public void testContains() throws Exception {
    Assert.assertEquals(true, StringUtil.contains("abcdefg", "abc"));
    Assert.assertEquals(false, StringUtil.contains("abcdefg", "defs"));
    Assert.assertEquals(false, StringUtil.contains(null, "defs"));
    Assert.assertEquals(true, StringUtil.contains("abcdefg", null)); //true
    Assert.assertEquals(true, StringUtil.contains("abcdefg", "")); //true
  }

  @Test
  public void testContains2() throws Exception {
    log.info("{}", "abc".contains(""));
  }

  @Test
  public void testStartsWith() throws Exception {
    log.info("ret={}", StringUtil.startsWith("qingdao_uat", true, "qingdao"));
  }

  @Test
  public void testEndsWith() throws Exception {
    log.info("ret={}", StringUtil.endsWith("qingdao_uat", true, "uat", "sit", "dev"));
  }

  @Test
  public void testInsert() throws Exception {
    Assert.assertEquals("ab-cdef", StringUtil.insert("abcdef", 2, "-"));
    Assert.assertEquals("http://a.www.baidu.com", StringUtil.insert("http://www.baidu.com", 7, "a."));
  }

  @Test
  public void testAddZeroLeft() throws Exception {
    log.info("{}", System.getProperties());
    log.info("--------");
    log.info("{}", System.getenv());

    Assert.assertEquals(StringUtil.addZeroLeft("1", 4), "0001");
  }

  @Test
  public void testToString() throws Exception {
    List<String> data = new ArrayList<>();
    data.add("a");
    data.add("b");
    data.add("c");
    log.info("{}", StringUtil.toString(data));
  }

  @Test
  public void testRemoveStart() throws Exception {
    log.info("{}", StringUtil.removeStart("hello world", "xx"));
    log.info("{}", StringUtil.removeStart("hello world", "he"));
    log.info("{}", StringUtil.removeStart("hello world", "hello"));
  }

  @Test
  public void testRemoveEnd() throws Exception {
    log.info("{}", StringUtil.removeEnd("hello world", "xx"));
    log.info("{}", StringUtil.removeEnd("hello world", "ld"));
    log.info("{}", StringUtil.removeEnd("hello world", "world"));
  }

  @Test
  public void testRemoveEmoji() throws Exception {
    log.info(StringUtil.removeEmoji("abc,ef123！高兴😄高兴"));
  }

  @Test
  public void testRemoveOneSuffix() throws Exception {
    log.info("{}", StringUtil.removeOneSuffix("abcReqBO", "ReqBO", "BO"));
    log.info("{}", StringUtil.removeOneSuffix("abcReqBO", "reqBO", "BO"));
  }

}
