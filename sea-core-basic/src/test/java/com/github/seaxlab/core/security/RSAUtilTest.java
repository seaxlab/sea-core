package com.github.seaxlab.core.security;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.security.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public class RSAUtilTest extends BaseCoreTest {

    @Test
    public void testNormalRsa() {
        final String givenString = "this is a test string";

        String encryptedString = RSAUtil.encryptString(givenString);
        String decryptedString = RSAUtil.decryptString(encryptedString);

        assertEquals(givenString, decryptedString);
    }

    private static KeyPair generateRandomRsaKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSAUtil.ALGORITHM);
            generator.initialize(RSAUtil.KEY_SIZE);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void testRsaWithGivenByteModulusAndByteExponent() {
        final KeyPair keyPair = generateRandomRsaKeyPair();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 生成一组密钥对的模数和指数
        final byte[] publicKeyModulus = publicKey.getModulus().toByteArray();
        final byte[] publicKeyExponent = publicKey.getPublicExponent().toByteArray();
        final byte[] privateKeyModulus = privateKey.getModulus().toByteArray();
        final byte[] privateKeyExponent = privateKey.getPrivateExponent().toByteArray();
        final String givenString = "the string to test";

        // 重新生成一组密钥对
        RSAPublicKey genPublicKey = RSAUtil.generateRSAPublicKey(publicKeyModulus, publicKeyExponent);
        RSAPrivateKey genPrivateKey = RSAUtil.generateRSAPrivateKey(privateKeyModulus, privateKeyExponent);

        // 根据指定密钥对生成的内容进行加解密验证
        String encryptString = RSAUtil.encryptString(genPublicKey, givenString);
        assertEquals(givenString, RSAUtil.decryptString(genPrivateKey, encryptString));

        // 验证生成的公钥加密的内容是否可以使用原有私钥解密
        assertEquals(givenString, RSAUtil.decryptString(privateKey, encryptString));
    }

    @Test
    public void testRsaWithGivenHexModulusAndHexExponent() {
        final KeyPair keyPair = generateRandomRsaKeyPair();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 生成一组密钥对的模数和指数
        final String publicKeyModulus = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
        final String publicKeyExponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
        final String privateKeyModulus = new String(Hex.encodeHex(privateKey.getModulus().toByteArray()));
        final String privateKeyExponent = new String(Hex.encodeHex(privateKey.getPrivateExponent().toByteArray()));
        final String givenString = "the string to test";

        // 重新生成一组密钥对
        RSAPublicKey genPublicKey = RSAUtil.getRSAPublicKey(publicKeyModulus, publicKeyExponent);
        RSAPrivateKey genPrivateKey = RSAUtil.getRSAPrivateKey(privateKeyModulus, privateKeyExponent);

        // 根据指定密钥对生成的内容进行加解密验证
        String encryptString = RSAUtil.encryptString(genPublicKey, givenString);
        assertEquals(givenString, RSAUtil.decryptString(genPrivateKey, encryptString));

        // 验证生成的公钥加密的内容是否可以使用原有私钥解密
        assertEquals(givenString, RSAUtil.decryptString(privateKey, encryptString));
    }

    @Test
    public void testPublicKeyExportInfo() {
        Map<String, String> publicKeyInfo = RSAUtil.publicKeyInfo();
        Map<String, String> defaultPublicKeyInfo = RSAUtil.publicKeyInfo(RSAUtil.getDefaultPublicKey());
        assertEquals(publicKeyInfo, defaultPublicKeyInfo);

        try {
            publicKeyInfo.put("any key", "any value");
            fail("publicKey must be a immutable map");
        } catch (UnsupportedOperationException ignore) {
        }

        assertNotNull(publicKeyInfo.get(RSAUtil.MODULUS_NAME));
        assertNotNull(publicKeyInfo.get(RSAUtil.EXPONENT_NAME));
    }
}
