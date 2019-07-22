package com.github.spy.sea.core.security;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.security.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class AESUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {

        String s = "hello,您好";

        System.out.println("s:" + s);

        String key = "1234567890123456";

        String s1 = AESUtil.encrypt(s, key);
        System.out.println("s1:" + s1);

        System.out.println("s2:" + AESUtil.decrypt(s1, key));
    }
}
