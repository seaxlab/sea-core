package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/10
 * @since 1.0
 */
@Slf4j
public class ChineseNumberUtilTest extends BaseCoreTest {

    @Test
    public void testConvert() throws Exception {
        Assert.assertEquals("壹佰贰拾叁元", ChineseNumberUtil.convert("123"));
        Assert.assertEquals("壹佰贰拾叁元肆角伍分陆厘", ChineseNumberUtil.convert("123.456789"));
        log.info("{}", ChineseNumberUtil.convert("123.456789"));
        //壹亿贰仟叁佰肆拾伍万陆仟柒佰捌拾玖元
        log.info("{}", ChineseNumberUtil.convert("123456789"));
    }
}
