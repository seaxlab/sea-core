package com.github.spy.sea.core.security;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.security.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class Base64UtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String str = "香港!@#$%^&*()_+abc123";

        String base64Str = Base64Util.encode(str);

        log.info("base64str={}", base64Str);
        Assert.assertEquals("6aaZ5rivIUAjJCVeJiooKV8rYWJjMTIz", base64Str);

        String decodeStr = Base64Util.decode(base64Str);

        Assert.assertEquals(decodeStr, str);


    }
}
