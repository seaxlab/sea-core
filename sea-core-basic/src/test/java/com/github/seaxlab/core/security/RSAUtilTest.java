package com.github.seaxlab.core.security;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.model.Tuple2;
import com.github.seaxlab.core.security.util.RSAUtil;
import com.github.seaxlab.core.util.RandomUtil;
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
  public void testKeyPair() throws Exception {
    Tuple2<String, String> tuple = RSAUtil.generateKeyPair();

    System.out.println("公钥：" + tuple.getFirst());
    System.out.println("私钥：" + tuple.getSecond());

    System.out.println("---------------------");
    tuple = RSAUtil.generateKeyPair(2048);

    System.out.println("公钥：" + tuple.getFirst());
    System.out.println("私钥：" + tuple.getSecond());
  }

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


  @Test
  public void testGenerateSignature() throws Exception {
    String publicKey = getConfig("sea.rsa_public_key");
    String privateKey = getConfig("sea.rsa_private_key");
    //
    String content = "";
    content += RandomUtil.uuid();

    String sign = RSAUtil.generateSign(privateKey, content);

    boolean verifyFlag = RSAUtil.verifySign(publicKey, content, sign);
    log.info("sign={}", sign);
    log.info("verifyFlag={}", verifyFlag);
  }


  @Test
  public void testEncrypt() {
    String publicKey = getConfig("sea.rsa_public_key");
    String content = "";
    content += "\n" + RandomUtil.uuid();
    System.out.println(RSAUtil.encryptBySegment(publicKey, content));

    //
  }

  @Test
  public void testDecrypt() throws Exception {
    String privateKey = getConfig("sea.rsa_private_key");
    String content = "IaW8tzMLobhVeb2cZCPzOiS7owXT12sLPLMhW8kBXXa+UiFlqXEXMt1alMcaAu9tq7PVSF1WSB8fyRoVjev7wM0FgAXxAKMx314usmt1GKGzfZAT24qsu0bvWFH7iobugEAWlMfvviRtbp2ECAxzFPL5UH8xJjMs0rfd1YbLEAG6huW/2QuR2/87Dl0zQU4tfeh4a4UjxfKC0SKl450gtWkn4GUz2J1CQAdxdV1Hb+h5W6FatePf/1b/iC4qjCYFratUCTsAfMO1BKd9ML9hpJr3FBTwk/YbeH9m1QKfleCpaych4BaEtSrMuR/WLJrAwFpfqWs5+hEL4woF1iC33T8h/hlekPhnn6RUyh3LccJP955GIVdeE6PSfWuBs6Sz3r//at8P+K0fOQfnrgyiGMn3pXfU07uHCExOuXrQCWaXFbFoqhEeHRliT42YPfMapK6o71cwS2aZN9hJtws7CvlBqCvYLYt7HssnwIlfHau6DmSAVN1ZQ7cha23jWqJ0IoI4dK0cM80rB0hNhXSm4/9620pIzlVDvO2AR+eJ9/6UTBnH+XQKri9FlBKCKR88t4leo2nV0rN+0sK/QpoU5NpzDkV4CNJURmwEd00qR8gh0NluHociDv0IKiK06PRHHrawwpjLEm1vphQugq/6Mzb2dXmSOKoPPU14xgXiVtZKuitJuaeu90QbvX1oQv39nSkhSR45WO/x/h3HN2YX2DLkdzuFDd92+jO/TvvKEhMgs9wWXJrZOB7ZSX5T4K/2qW3J5P5ruDoT3XuoPsn8Vhc7CbXQJbQHxaIz/8tA4+NEvFtHf/WVbfNvWENHg5JJwOnTH68A37sVU1bVXNlgwGQ4eHfU7VAaDnv4pZR6OrAZ1xuTF3TZwu6InVIxMXZZv8TS6y222gFrL31yLBIyp1KBYcKZv2XnST7W2Dt6TqL8PKYom84Ahqwfa85gWngn5bTNxYDZIHYG95BcB+AEgzuzVH100hk6mrleoE69oolcsIBy6qUekYJo0DrgzbLY";

    System.out.println(RSAUtil.decryptBySegment(privateKey, content));
  }

  //-------------------------------------

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
