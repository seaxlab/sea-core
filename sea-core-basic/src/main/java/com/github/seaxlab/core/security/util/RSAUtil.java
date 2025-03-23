package com.github.seaxlab.core.security.util;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.model.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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

  public static final String SHA_256_WITH_RSA = "SHA256WithRSA";

  /**
   * 密钥大小
   */
  public static final int KEY_SIZE = 2048;

  /**
   * RSA2最大加密明文大小(2048/8-11=244)
   */
  private static final int MAX_ENCRYPT_BLOCK_SIZE = 244;
  /**
   * RSA2最大解密密文大小(2048/8=256)
   */
  private static final int MAX_DECRYPT_BLOCK_SIZE = 256;


  /**
   * 生成公私秘钥对 2048
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
      String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
      String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
      return Tuple2.of(publicKeyString, privateKeyString);
    } catch (Exception e) {
      log.error("fail to generate RSA key pair, keySize={}", keySize, e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  /**
   * 获取公钥对象
   *
   * @param publicKeyBase64 public key base64
   * @return PublicKey
   * @throws InvalidKeySpecException
   * @throws NoSuchAlgorithmException
   */
  public static PublicKey getPublicKey(String publicKeyBase64)
    throws InvalidKeySpecException, NoSuchAlgorithmException {
    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
    return keyFactory.generatePublic(keySpec);
  }

  /**
   * 获取私钥对象
   *
   * @param privateKeyBase64
   * @return PrivateKey
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  public static PrivateKey getPrivateKey(String privateKeyBase64)
    throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
    return keyFactory.generatePrivate(keySpec);
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
      encodedKey = Base64.decodeBase64(encodedKey);
      PrivateKey priKey = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(encodedKey));

      Signature signature = Signature.getInstance(SHA_256_WITH_RSA);
      signature.initSign(priKey);
      signature.update(content.getBytes(StandardCharsets.UTF_8));
      byte[] signed = signature.sign();
      return new String(Base64.encodeBase64(signed));
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
      encodedKey = Base64.decodeBase64(encodedKey);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
      PublicKey publicKey = keyFactory.generatePublic(keySpec);

      Signature signature = Signature.getInstance(SHA_256_WITH_RSA);
      signature.initVerify(publicKey);
      signature.update(content.getBytes(StandardCharsets.UTF_8));
      return signature.verify(Base64.decodeBase64(sign));
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
      X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
      KeyFactory keyFactory = KeyFactory.getInstance(RSA);
      PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
      Cipher cipher = Cipher.getInstance(RSA);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] result = cipher.doFinal(content.getBytes());
      return Base64.encodeBase64String(result);
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
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
      KeyFactory keyFactory = KeyFactory.getInstance(RSA);
      PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
      Cipher cipher = Cipher.getInstance(RSA);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] result = cipher.doFinal(Base64.decodeBase64(text));
      return new String(result);
    } catch (Exception e) {
      log.error("fail to decrypt", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }
  //--------------------------------RSA加密 end-----------------------------------------

  // 部分段场景下会报错：Data must not be longer than 245 bytes

  //-------------------------------RSA 分段加解密 begin------------------------------------------


  /**
   * 使用公钥加密
   *
   * @param content         待加密内容
   * @param publicKeyBase64 公钥 base64 编码
   * @return 经过 base64 编码后的字符串
   */
  public static String encryptBySegment(String publicKeyBase64, String content) {
    return encryptBySegment(publicKeyBase64, content, MAX_ENCRYPT_BLOCK_SIZE);
  }

  /**
   * 使用公钥加密（分段加密）
   *
   * @param content         待加密内容
   * @param publicKeyBase64 公钥 base64 编码
   * @param segmentSize     分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密）
   * @return 经过 base64 编码后的字符串
   */
  public static String encryptBySegment(String publicKeyBase64, String content, int segmentSize) {
    try {
      PublicKey publicKey = getPublicKey(publicKeyBase64);
      return encryptBySegment(publicKey, content, segmentSize);
    } catch (Exception e) {
      log.error("fail to encrypt by segment", e);
      return null;
    }
  }

  /**
   * 分段加密
   *
   * @param ciphertext  密文
   * @param key         加密秘钥
   * @param segmentSize 分段大小，<=0 不分段
   * @return string
   */
  private static String encryptBySegment(java.security.Key key, String ciphertext, int segmentSize) {
    try {
      // 用公钥加密
      byte[] srcBytes = ciphertext.getBytes();

      // Cipher负责完成加密或解密工作，基于RSA
      Cipher cipher = Cipher.getInstance(RSA);
      // 根据公钥，对Cipher对象进行初始化
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] resultBytes = null;

      if (segmentSize > 0) {
        resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize); //分段加密
      } else {
        resultBytes = cipher.doFinal(srcBytes);
      }
      return Base64.encodeBase64String(resultBytes);
    } catch (Exception e) {
      log.error("fail to encrypt by segment", e);
      return null;
    }
  }

  /**
   * 分段大小
   *
   * @param cipher cipher
   * @param srcBytes src bytes
   * @param segmentSize segment size
   * @return
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   * @throws IOException
   */
  private static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
    throws IllegalBlockSizeException, BadPaddingException, IOException {
    if (segmentSize <= 0) {
      throw new RuntimeException("分段大小必须大于0");
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int inputLen = srcBytes.length;
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > segmentSize) {
        cache = cipher.doFinal(srcBytes, offSet, segmentSize);
      } else {
        cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * segmentSize;
    }
    byte[] data = out.toByteArray();
    out.close();
    return data;
  }

  /**
   * 使用私钥解密
   *
   * @param contentBase64    待加密内容,base64 编码
   * @param privateKeyBase64 私钥 base64 编码
   * @return decrypt by segment
   */
  public static String decryptBySegment(String privateKeyBase64, String contentBase64) {
    return decryptBySegment(privateKeyBase64, contentBase64, MAX_DECRYPT_BLOCK_SIZE);
  }

  /**
   * 使用私钥解密（分段解密）
   *
   * @param contentBase64    待加密内容,base64 编码
   * @param privateKeyBase64 私钥 base64 编码
   * @param segmentSize 分段大小
   * @return decrypt by segment
   */
  public static String decryptBySegment(String privateKeyBase64, String contentBase64, int segmentSize) {
    try {
      PrivateKey privateKey = getPrivateKey(privateKeyBase64);
      return decryptBySegment(privateKey, contentBase64, segmentSize);
    } catch (Exception e) {
      log.error("fail to decrypt by segment", e);
      return null;
    }
  }

  /**
   * 分段解密
   *
   * @param contentBase64 密文
   * @param key           解密秘钥
   * @param segmentSize   分段大小（小于等于0不分段）
   * @return
   */
  private static String decryptBySegment(java.security.Key key, String contentBase64, int segmentSize) {
    try {
      // 用私钥解密
      byte[] srcBytes = Base64.decodeBase64(contentBase64);
      // Cipher负责完成加密或解密工作，基于RSA
      Cipher deCipher = Cipher.getInstance(RSA);
      // 根据公钥，对Cipher对象进行初始化
      deCipher.init(Cipher.DECRYPT_MODE, key);
      byte[] decBytes = null;//deCipher.doFinal(srcBytes);
      if (segmentSize > 0) {
        decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize); //分段加密
      } else {
        decBytes = deCipher.doFinal(srcBytes);
      }

      return new String(decBytes);
    } catch (Exception e) {
      log.error("fail to decrypt by segment", e);
      return null;
    }
  }
  //-------------------------------RSA 分段加解密 end------------------------------------------


}
