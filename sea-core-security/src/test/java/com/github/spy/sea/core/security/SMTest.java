package com.github.spy.sea.core.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.github.spy.sea.core.test.AbstractCoreTest;
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
public class SMTest extends AbstractCoreTest {

    @Test
    public void sm3Test() {
        String digestHex = SmUtil.sm3("aaaaa");
        Assert.assertEquals("136ce3c86e4ed909b76082055a61586af20b4dab674732ebd4b599eef080c9be", digestHex);
    }

    @Test
    public void sm4Test() {
        String content = "test中文";
        SymmetricCrypto sm4 = SmUtil.sm4();

        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        Assert.assertEquals(content, decryptStr);
    }

    @Test
    public void sm4Test2() {
        String content = "test中文";
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding");

        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        Assert.assertEquals(content, decryptStr);
    }
}
