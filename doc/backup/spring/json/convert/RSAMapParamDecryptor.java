package com.github.seaxlab.core.spring.component.json.convert;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.spring.component.json.JsonParamFieldConvertor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/4
 * @since 1.0
 */
@Slf4j
public class RSAMapParamDecryptor implements JsonParamFieldConvertor<Map, Map> {
  @Override
  public Map convert(JSON json, String[] paths, Map value) throws Exception {
    // 遍历所有需要转换字段
    for (String path : paths) {
      //String paramValue = value.get(path);
      String paramValue = "";
      if (StringUtils.isNotEmpty(paramValue)) {
        String pwd = "xxx";//TODO
        if (StringUtils.isNotEmpty(pwd)) {
          value.put(path, pwd);
        }
      }
    }

    return value;
  }
}
