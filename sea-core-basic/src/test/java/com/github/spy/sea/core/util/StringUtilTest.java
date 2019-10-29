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


}
