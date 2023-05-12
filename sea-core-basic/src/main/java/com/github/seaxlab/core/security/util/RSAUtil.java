package com.github.seaxlab.core.security.util;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.model.Tuple2;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;

/**
 * RSA util
 * <ul>
 * <li>RSA加密: 公钥负责加密，私钥负责解密</li>
 * <li>RSA签名: 私钥负责签名，公钥负责验证。</li>
 * </ul>
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public class RSAUtil {

  /**
   * 加密算法
   */
  public static final String RSA = "RSA";

  /**
   * 密钥大小
   */
  public static final int KEY_SIZE = 1024;

  public static final String SHA_256_WITH_RSA = "SHA256WithRSA";


  /**
   * 生成公私秘钥对 1024
   *
   * @return tuple
   */
  public static Tuple2<String, String> generateKeyPair() {
    return generateKeyPair(KEY_SIZE);
  }


  /**
   * 生成公私秘钥对，生成指定keySize大小的秘钥
   *
   * @return tuple
   */
  public static Tuple2<String, String> generateKeyPair(int keySize) {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
      keyPairGenerator.initialize(keySize);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
      RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
      //
      String publicKeyString = Base64.toBase64String(rsaPublicKey.getEncoded());
      String privateKeyString = Base64.toBase64String(rsaPrivateKey.getEncoded());
      return Tuple2.of(publicKeyString, privateKeyString);
    } catch (Exception e) {
      log.error("fail to generate RSA key pair, keySize={}", keySize, e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  //--------------------------------RSA签名 begin-----------------------------------------

  /**
   * RSA签名：私钥签名
   *
   * @param privateKey 私钥
   * @param content    私钥
   * @return 签名
   */
  public static String generateSign(String privateKey, String content) {
    try {
      byte[] encodedKey = privateKey.getBytes();
      encodedKey = Base64.decode(encodedKey);
      PrivateKey priKey = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(encodedKey));

      Signature signature = Signature.getInstance(SHA_256_WITH_RSA);
      signature.initSign(priKey);
      signature.update(content.getBytes(StandardCharsets.UTF_8));
      byte[] signed = signature.sign();
      return new String(Base64.encode(signed));
    } catch (Exception e) {
      log.error("fail to generate sign", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  /**
   * RSA签名：公钥验签
   *
   * @param publicKeyStr 公钥
   * @param content      内容
   * @param sign         签名
   * @return boolean
   */
  public static boolean verifySign(String publicKeyStr, String content, String sign) {

    try {
      KeyFactory keyFactory = KeyFactory.getInstance(RSA);
      //
      byte[] encodedKey = publicKeyStr.trim().getBytes();
      encodedKey = Base64.decode(encodedKey);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
      PublicKey publicKey = keyFactory.generatePublic(keySpec);

      Signature signature = Signature.getInstance(SHA_256_WITH_RSA);
      signature.initVerify(publicKey);
      signature.update(content.getBytes(StandardCharsets.UTF_8));
      return signature.verify(Base64.decode(sign));
    } catch (Exception e) {
      log.error("fail to verify sign", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  //--------------------------------RSA签名 end-----------------------------------------

  //--------------------------------RSA加密 begin---------------------------------------

  /**
   * 加密
   *
   * @param publicKeyText 公钥
   * @param content       明文
   * @return 密文
   */
  public static String encrypt(String publicKeyText, String content) {
    try {
      X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decode(publicKeyText));
      KeyFactory keyFactory = KeyFactory.getInstance(RSA);
      PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
      Cipher cipher = Cipher.getInstance(RSA);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] result = cipher.doFinal(content.getBytes());
      return Base64.toBase64String(result);
    } catch (Exception e) {
      log.error("fail to encrypt", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  /**
   * 私钥解密
   *
   * @param privateKeyText 私钥
   * @param text           密文
   * @return 明文
   */
  public static String decrypt(String privateKeyText, String text) {
    try {
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decode(privateKeyText));
      KeyFactory keyFactory = KeyFactory.getInstance(RSA);
      PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
      Cipher cipher = Cipher.getInstance(RSA);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] result = cipher.doFinal(Base64.decode(text));
      return new String(result);
    } catch (Exception e) {
      log.error("fail to decrypt", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }
  //--------------------------------RSA加密 end-----------------------------------------


}
