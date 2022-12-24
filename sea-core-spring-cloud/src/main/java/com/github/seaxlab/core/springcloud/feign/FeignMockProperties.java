package com.github.seaxlab.core.springcloud.feign;

import com.github.seaxlab.core.springcloud.feign.util.KeyUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "sea.spring.cloud.feign.mock")
public class FeignMockProperties implements InitializingBean {

  private Boolean enabled = false;

  private String configPath;

  /**
   * 资源：mock地址
   * <p>格式：GET,http://user-service/api/user/{userId},remote|http://xxx.com/mock/api/1001</p>
   * <p>格式：GET,http://user-service/api/user/{userId}, local|100.json</p>
   * <p>格式：GET,http://user-service/api/user/{userId}, db_json|http://xxx.com/mock/api/10001</p>
   * <p>格式：GET,http://user-service/api/user/{userId},db_remote|http://xxx.com/mock/api/10001</p>
   */
  private Map<String, String> apis = new HashMap<>();


  // --------- public api------------
  public String getMockApi(String resource) {
    if (!enabled) {
      return StringUtil.EMPTY;
    }

    if (CollectionUtils.isEmpty(apis)) {
      return StringUtil.EMPTY;
    }
    return apis.getOrDefault(resource, "");
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    boolean existFlag = FileUtil.exist(configPath);
    log.info("config path={}, exist={}", configPath, existFlag);

    if (!existFlag) {
      return;
    }
    String content = FileUtil.readFileToString(configPath + "/sea.mock.url.txt");
    String[] configs = content.split("\r\n");

    for (String config : configs) {
      if (StringUtil.isBlank(config)) {
        continue;
      }
      if (config.trim().startsWith("#")) {
        // ignore comment
        continue;
      }

      String[] items = config.split(",");
      if (items.length != 3) {
        log.warn("illegal mock config={}", config);
        continue;
      }
      apis.put(KeyUtil.getKey(items[0], items[1]), items[2].trim());
    }
  }
}
