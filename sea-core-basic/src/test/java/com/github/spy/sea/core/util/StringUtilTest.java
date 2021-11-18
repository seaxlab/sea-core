package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
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
    public void run16() throws Exception {

        Assert.assertEquals(StringUtil.toCamelCase("hello_world"), "helloWorld");


        Assert.assertEquals("1:s", StringUtil.uniqueKey(1, "s"));
        Assert.assertEquals("1-s", StringUtil.uniqueKey("-", 1, "s"));
    }

    @Test
    public void joinTest() throws Exception {
        Assert.assertEquals("1:s", StringUtil.join(":", "1", "s"));
        Assert.assertEquals("1:s", StringUtil.join(":", "1", "", "s"));
        Assert.assertEquals("1:s", StringUtil.join(":", "1", null, "s", null));

    }

    @Test
    public void splitTest() throws Exception {

        log.info("{}", StringUtil.splitToIterable("abc;;bac;hi", ';'));
        log.info("{}", StringUtil.splitToList("abc;;bac;hi", ';'));
        log.info("{}", StringUtil.splitToList(null, ';'));
    }

    @Test
    public void run36() throws Exception {
        Assert.assertEquals("", StringUtil.defaultIfBlank(null, ""));
    }

    @Test
    public void run41() throws Exception {
        Assert.assertEquals("a0a", StringUtil.replaceStart("00a0a", "0"));
    }

    @Test
    public void removeEmojiTest() throws Exception {
        log.info(StringUtil.removeEmoji("abc,ef123！高兴😄高兴"));
    }

    @Test
    public void run59() throws Exception {
        Assert.assertEquals(true, StringUtil.contains("abcdefg", "abc"));
        Assert.assertEquals(false, StringUtil.contains("abcdefg", "defs"));
        Assert.assertEquals(false, StringUtil.contains(null, "defs"));
        Assert.assertEquals(true, StringUtil.contains("abcdefg", null)); //true
        Assert.assertEquals(true, StringUtil.contains("abcdefg", "")); //true
    }

    @Test
    public void run68() throws Exception {
        log.info("{}", "abc".contains(""));
    }

    @Test
    public void startsWithTest() throws Exception {
        log.info("ret={}", StringUtil.startsWith("qingdao_uat", true, "qingdao"));
    }

    @Test
    public void endsWithTest() throws Exception {
        log.info("ret={}", StringUtil.endsWith("qingdao_uat", true, "uat", "sit", "dev"));
    }

    @Test
    public void testInsert() throws Exception {
        Assert.assertEquals("ab-cdef", StringUtil.insert("abcdef", 2, "-"));
    }

    @Test
    public void test88() throws Exception {
        log.info("{}", System.getProperties());
        log.info("--------");
        log.info("{}", System.getenv());

        Assert.assertEquals(StringUtil.addZeroLeft("1", 4), "0001");
    }

}
