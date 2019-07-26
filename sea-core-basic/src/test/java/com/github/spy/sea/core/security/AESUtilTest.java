package com.github.spy.sea.core.security;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.security.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class AESUtilTest extends BaseCoreTest {

    String msg;

    @Before
    public void before() {
        msg = "中文abc123456";
    }

    @Test
    public void run17() throws Exception {
        System.out.println("s:" + msg);

        String key = "1234567890123456";

        String s1 = AESUtil.encrypt(msg, key);
        System.out.println("s1:" + s1);

        System.out.println("s2:" + AESUtil.decrypt(s1, key));
    }

    @Test
    public void pwdAny() throws Exception {
        System.out.println("s:" + msg);

        //17位无法解密
        String key = "12345678901234561";

        String s1 = AESUtil.encrypt(msg, key);
        System.out.println("加密:" + s1);

        System.out.println("解密:" + AESUtil.decrypt(s1, key));
    }

    @Test
    public void pwd162432() throws Exception {
        System.out.println("s:" + msg);

        String key = "123456789012345612345678";

        String s1 = AESUtil.encrypt(msg, key);
        System.out.println("加密:" + s1);

        System.out.println("解密:" + AESUtil.decrypt(s1, key));
    }


    @Test
    public void run65() throws Exception {

        KeyGenerator generator = KeyGenerator.getInstance("AES/CBC/PKCS5PADDING");
        generator.init(256);
        SecretKey key = generator.generateKey();
    }

}
