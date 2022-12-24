package com.github.seaxlab.core.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.github.seaxlab.core.test.AbstractCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class RSATest extends AbstractCoreTest {

  @Test
  public void run17() throws Exception {

    KeyPair pair = KeyUtil.generateKeyPair("RSA");
    Assert.assertNotNull(pair.getPrivate());
    Assert.assertNotNull(pair.getPublic());

    log.info("public key={}", pair.getPublic());
    log.info("private key={}", pair.getPrivate());
  }

  @Test
  public void rsaCustomKeyTest() {
    KeyPair pair = KeyUtil.generateKeyPair("RSA");
    byte[] privateKey = pair.getPrivate().getEncoded();
    byte[] publicKey = pair.getPublic().getEncoded();

    RSA rsa = SecureUtil.rsa(privateKey, publicKey);

    //重点 获取公私钥
    log.info("public key={}", rsa.getPublicKeyBase64());
    log.info("private key={}", rsa.getPrivateKeyBase64());

    // 公钥加密，私钥解密
    byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
    byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
    Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

    // 私钥加密，公钥解密
    byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
    byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
    Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
  }
}
