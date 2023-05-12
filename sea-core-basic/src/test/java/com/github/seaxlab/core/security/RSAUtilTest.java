package com.github.seaxlab.core.security;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.model.Tuple2;
import com.github.seaxlab.core.security.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
  public void test1024() throws Exception {
    Tuple2<String, String> tuple = RSAUtil.generateKeyPair();
    log.info("公钥：{}", tuple.getFirst());
    log.info("私钥：{}", tuple.getSecond());

    String content = "hi,马克！----------------------------adfadfa-asdfasdf";
    test1(tuple.getFirst(), tuple.getSecond(), content);
    System.out.println("\n");
    test2(tuple.getFirst(), tuple.getSecond(), content);
    System.out.println("\n");
  }

  @Test
  public void test2048() throws Exception {
    Tuple2<String, String> tuple = RSAUtil.generateKeyPair(2048);
    log.info("公钥：{}", tuple.getFirst());
    log.info("私钥：{}", tuple.getSecond());

    String content = "hi,马克！----------------------------adfadfa-asdfasdf";
    test1(tuple.getFirst(), tuple.getSecond(), content);
    System.out.println("\n");
    test2(tuple.getFirst(), tuple.getSecond(), content);
    System.out.println("\n");
  }

  private static void test1(String publicKey, String privateKey, String source) throws Exception {
    System.out.println("***************** 公钥加密私钥解密开始 *****************");
    String text1 = RSAUtil.encrypt(publicKey, source);
    String text2 = RSAUtil.decrypt(privateKey, text1);
    System.out.println("加密前：" + source);
    System.out.println("加密后：" + text1);
    System.out.println("解密后：" + text2);
    if (source.equals(text2)) {
      System.out.println("解密字符串和原始字符串一致，解密成功");
    } else {
      System.out.println("解密字符串和原始字符串不一致，解密失败");
    }
    System.out.println("***************** 公钥加密私钥解密结束 *****************");
  }

  private static void test2(String publicKey, String privateKey, String source) throws Exception {
    System.out.println("***************** 私钥加签，公钥验证 *****************");
    String sign = RSAUtil.generateSign(privateKey, source);
    boolean verifyFlag = RSAUtil.verifySign(publicKey, source, sign);
    log.info("sign={}", sign);
    log.info("verifyFlag={}", verifyFlag);
  }


}
