package com.github.seaxlab.core.security;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.model.layer.encrypt.EncryptRequestDTO;
import com.github.seaxlab.core.model.layer.encrypt.EncryptResult;
import com.github.seaxlab.core.security.util.AESUtil;
import com.github.seaxlab.core.security.util.SignUtil;
import com.github.seaxlab.core.util.JSONUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Slf4j
public class SignTest extends BaseCoreTest {

  String version;
  String appId;
  String appSecret;

  @Before
  public void before() {
    // 服务提供商分配
    version = "1.0";
    // 服务提供商分配
    appId = "10001";
    // 服务提供商分配,16位
    appSecret = "6898e14c6a184cf0";
  }


  @Test
  public void requestTest() throws Exception {

    // 请求时间戳 ，格式yyyyMMddHHmmss
    String timestamp = "20190722100811";

    // 业务请求参数
    String bizContent = "{'hospCode':'0001'}";

    Map<String, String> map = new HashMap<>();

    map.put("appId", appId);
    map.put("version", version);
    map.put("timestamp", timestamp);
    map.put("bizContent", bizContent);

    // MD5签名，参数名按照升序排列，例如id=100&name=joy
    String sign = SignUtil.getByMd5(map);
    log.info("sign={}", sign);

    // 加密
    //加密方式 AES (AES/ECB/PKCS5Padding)
    String encryptedBizContent = AESUtil.encrypt(bizContent, appSecret);

    EncryptRequestDTO requestDTO = new EncryptRequestDTO();

    requestDTO.setAppId(appId);
    requestDTO.setVersion(version);
    requestDTO.setTimestamp(timestamp);
    requestDTO.setSign(sign);
    requestDTO.setBizContent(encryptedBizContent);

    log.info("encryptRequestDTO={}", JSONUtil.toStr(requestDTO));

    // 解密
    String decryptedBizContent = AESUtil.decrypt(encryptedBizContent, appSecret);

    log.info("decryptebizContent={}", decryptedBizContent);

    Map<String, String> map2 = new HashMap<>();

    map2.put("appId", appId);
    map2.put("version", version);
    map2.put("timestamp", timestamp);
    map2.put("bizContent", decryptedBizContent);

    String sign2 = SignUtil.getByMd5(map);
    log.info("sign2={}", sign);

    Assert.assertEquals(sign, sign2);
  }


  @Test
  public void responseTest() throws Exception {

    // 响应
    Map<String, String> retMap = new HashMap<>();

    retMap.put("recipeId", "123456");
    retMap.put("recipeStatus", "ok");

    String timestamp = "20190722100911";
    String bizContent = JSONUtil.toStr(retMap);
    String msg = "成功";

    Map<String, String> map = new HashMap<>();

    map.put("success", "true");
    map.put("message", msg);
    map.put("appId", appId);
    map.put("version", version);
    map.put("timestamp", timestamp);
    map.put("bizContent", bizContent);

    String sign = SignUtil.getByMd5(map);

    String encryptBizContent = AESUtil.encrypt(bizContent, appSecret);

    EncryptResult result = new EncryptResult();

    result.setSuccess(true);
    result.setAppId(appId);
    result.setBizContent(encryptBizContent);
    result.setMessage(msg);
    result.setSign(sign);
    result.setVersion(version);

    log.info("result={}", JSONUtil.toStr(result));

  }


}
