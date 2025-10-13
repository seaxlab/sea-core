package com.github.seaxlab.core.spring.component.json.convert;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.spring.component.json.JsonParamFieldConvertor;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/4
 * @since 1.0
 */
@Slf4j
public class LoginRSADecryptor implements JsonParamFieldConvertor<String, String> {

  //@Resource
  //private RSAService rsaService;

  @Override
  public String convert(JSON json, String[] paths, String value) throws Exception {
    String privateKey = "xx";

    return "xxx";
  }
}
