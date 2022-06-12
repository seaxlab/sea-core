package com.github.seaxlab.core.security.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES加密
 * 注意 加密key长度必须为16、24、32位
 * 注意：如要支持32位长度秘钥，需要JDK8u162以上版本
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    /**
     * 默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


    private static final String DEFAULT_KEY = "d4f653337327e9e4";

    private AESUtil() {
    }

    /**
     * 使用默认key加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        return encrypt(content, DEFAULT_KEY);
    }

    /**
     * 使用默认key解密
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_KEY);
    }

    /**
     * AES 加密操作
     *
     * @param content   待加密内容
     * @param secretKey 秘钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64.encodeBase64String(result);//通过Base64转码返回
        } catch (Exception ex) {
            log.error("encrypt error", ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param secretKey 秘钥(重点：JAVA只支持16位/24位/32位)
     * @return
     */
    public static String decrypt(String content, String secretKey) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey));

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            log.error("decrypt error", ex);
        }

        return null;
    }


    // -------------private

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String secretKey) {

        byte[] raw = new byte[0];
        try {
            raw = secretKey.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("fail to get secret key.", e);
        }

        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        return skeySpec;
    }
}
