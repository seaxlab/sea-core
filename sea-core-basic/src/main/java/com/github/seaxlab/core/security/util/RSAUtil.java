package com.github.seaxlab.core.security.util;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * RSA util
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public class RSAUtil {

    /**
     * 参数名称：模
     */
    public static final String MODULUS_NAME = "modulus";

    /**
     * 参数名称：指数
     */
    public static final String EXPONENT_NAME = "exponent";

    /**
     * 算法名称
     */
    public static final String ALGORITHM = "RSA";

    /**
     * 密钥大小
     */
    public static final int KEY_SIZE = 1024;

    private static KeyPairGenerator keyPairGen;
    private static KeyFactory keyFactory;
    /**
     * 缓存的密钥对。
     */
    private static KeyPair oneKeyPair = null;


    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private RSAUtil() {
    }

    /**
     * 生成并返回RSA密钥对。
     */
    private static synchronized KeyPair generateKeyPair() {
        if (oneKeyPair != null) {
            return oneKeyPair;
        }

        try {
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes()));
            oneKeyPair = keyPairGen.generateKeyPair();
            return oneKeyPair;
        } catch (InvalidParameterException ex) {
            throw new IllegalStateException("KeyPairGenerator does not support a key length of " + KEY_SIZE + ".", ex);
        } catch (NullPointerException ex) {
            throw new IllegalStateException("RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.",
                    ex);
        }
    }

    /**
     * 返回已经生成的RSA密钥对。
     */
    private static KeyPair getKeyPair() {
        if (oneKeyPair == null) {
            return generateKeyPair();
        }
        return oneKeyPair;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     *
     * @param modulus        系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("RSAPublicKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            throw new IllegalStateException("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     *
     * @param modulus         系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("RSAPrivateKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            throw new IllegalStateException("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
    }

    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
     *
     * @param hexModulus         系数。
     * @param hexPrivateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if (StringUtils.isEmpty(hexModulus) || StringUtils.isEmpty(hexPrivateExponent)) {
            throw new IllegalArgumentException("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
        }
        try {
            byte[] modulus = Hex.decodeHex(hexModulus.toCharArray());
            byte[] privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
            return generateRSAPrivateKey(modulus, privateExponent);
        } catch (DecoderException ex) {
            throw new IllegalStateException("hexModulus or hexPrivateExponent value is invalid.", ex);
        }
    }

    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     *
     * @param hexModulus        系数。
     * @param hexPublicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey getRSAPublicKey(String hexModulus, String hexPublicExponent) {
        if (StringUtils.isEmpty(hexModulus) || StringUtils.isEmpty(hexPublicExponent)) {
            throw new IllegalArgumentException("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
        }
        try {
            byte[] modulus = Hex.decodeHex(hexModulus.toCharArray());
            byte[] publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
            return generateRSAPublicKey(modulus, publicExponent);
        } catch (DecoderException ex) {
            throw new IllegalStateException("hexModulus or hexPublicExponent value is invalid.", ex);
        }
    }

    /**
     * 使用指定的公钥加密数据。
     *
     * @param publicKey 给定的公钥。
     * @param data      要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        try {
            Cipher ci = Cipher.getInstance(ALGORITHM);
            ci.init(Cipher.ENCRYPT_MODE, publicKey);
            return ci.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException e) {
            throw new IllegalStateException(e);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalArgumentException("can not encrypt data.", e);
        }
    }

    /**
     * 使用指定的私钥解密数据。
     *
     * @param privateKey 给定的私钥。
     * @param data       要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        try {
            Cipher ci = Cipher.getInstance(ALGORITHM);
            ci.init(Cipher.DECRYPT_MODE, privateKey);
            return ci.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException e) {
            throw new IllegalStateException(e);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalArgumentException("input data is not a validate RSA encrypted data", e);
        }
    }

    /**
     * 使用给定的公钥加密给定的字符串。
     *
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (Objects.isNull(publicKey) || Objects.isNull(plaintext)) {
            throw new IllegalArgumentException("public key or plain text should not be null");
        }
        byte[] data = plaintext.getBytes();
        byte[] encryptData = encrypt(publicKey, data);
        return new String(Hex.encodeHex(encryptData));
    }

    /**
     * 使用默认的公钥加密给定的字符串。
     * <p/>
     * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
     *
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(String plaintext) {
        return encryptString(getDefaultPublicKey(), plaintext);
    }

    /**
     * 使用给定的私钥解密给定的字符串。
     * <p/>
     * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     *
     * @param privateKey    给定的私钥。
     * @param encryptedText 密文。
     * @return 原文字符串。
     */
    public static String decryptString(PrivateKey privateKey, String encryptedText) {
        if (privateKey == null || encryptedText == null) {
            throw new IllegalArgumentException("private key or encrypted text should not be null!");
        }
        try {
            byte[] encryptedData = Hex.decodeHex(encryptedText.toCharArray());
            byte[] data = decrypt(privateKey, encryptedData);
            return new String(data, StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            throw new IllegalArgumentException("encrypted text is not a legal hex string", e);
        }
    }

    /**
     * 使用默认的私钥解密给定的字符串。
     *
     * @param encryptedText 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encryptedText) {
        return decryptString(getDefaultPrivateKey(), encryptedText);
    }

    /**
     * 返回已初始化的默认的公钥。
     */
    public static RSAPublicKey getDefaultPublicKey() {
        KeyPair keyPair = getKeyPair();
        return (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 返回已初始化的默认的私钥。
     */
    public static RSAPrivateKey getDefaultPrivateKey() {
        KeyPair keyPair = getKeyPair();
        return (RSAPrivateKey) keyPair.getPrivate();
    }

    /**
     * 返回当前默认公钥的模数和指数信息
     *
     * @return 包含系数和模的不可变map，{@link RSAUtil#MODULUS_NAME}, {@link RSAUtil#EXPONENT_NAME}
     */
    public static Map<String, String> publicKeyInfo() {
        return publicKeyInfo(getDefaultPublicKey());
    }

    /**
     * 将rsa公钥使用的模和系数经过16进制转换后放入map并返回。
     *
     * @return 包含系数和模的不可变map，{@link RSAUtil#MODULUS_NAME}, {@link RSAUtil#EXPONENT_NAME}
     */
    public static Map<String, String> publicKeyInfo(RSAPublicKey publicKey) {
        return ImmutableMap.<String, String>builder()
                           .put(MODULUS_NAME, new String(Hex.encodeHex(publicKey.getModulus().toByteArray())))
                           .put(EXPONENT_NAME, new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())))
                           .build();
    }
}
