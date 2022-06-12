package com.github.seaxlab.core.security;

import cn.hutool.crypto.symmetric.RC4;
import com.github.seaxlab.core.test.AbstractCoreTest;
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
public class RC4Test extends AbstractCoreTest {

    @Test
    public void testCryptMessage() {
        String key = "This is pretty long key";
        RC4 rc4 = new RC4(key);
        String message = "Hello, World!";
        byte[] crypt = rc4.encrypt(message);
        String msg = rc4.decrypt(crypt);
        Assert.assertEquals(message, msg);

        String message2 = "Hello, World， this is megssage 2";
        byte[] crypt2 = rc4.encrypt(message2);
        String msg2 = rc4.decrypt(crypt2);
        Assert.assertEquals(message2, msg2);
    }

    @Test
    public void testCryptWithChineseCharacters() {
        String message = "这是一个中文消息！";
        String key = "我是一个文件密钥";
        RC4 rc4 = new RC4(key);
        byte[] crypt = rc4.encrypt(message);
        String msg = rc4.decrypt(crypt);
        Assert.assertEquals(message, msg);

        String message2 = "这是第二个中文消息！";
        byte[] crypt2 = rc4.encrypt(message2);
        String msg2 = rc4.decrypt(crypt2);
        Assert.assertEquals(message2, msg2);
    }
}
