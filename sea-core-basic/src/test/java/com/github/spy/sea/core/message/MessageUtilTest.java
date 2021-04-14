package com.github.spy.sea.core.message;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.message.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/14
 * @since 1.0
 */
@Slf4j
public class MessageUtilTest extends BaseCoreTest {
    @Test
    public void testFormat() throws Exception {
        Assert.assertEquals("1,2", MessageUtil.format("{},{}", 1, 2));
    }

    @Test
    public void testFormatByIndex() throws Exception {
        Assert.assertEquals("2,1,1,1", MessageUtil.formatByIndex("{1},{0},{0},{0}", "1", "2"));
    }
}
