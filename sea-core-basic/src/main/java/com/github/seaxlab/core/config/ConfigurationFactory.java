package com.github.seaxlab.core.config;

import com.github.seaxlab.core.config.enums.ConfigTypeEnum;
import com.github.seaxlab.core.loader.EnhancedServiceLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数配置工厂
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
public class ConfigurationFactory {

  private static class InstanceHolder {
    public static final Configuration INSTANCE = buildConfiguration();

    private static Configuration buildConfiguration() {
      //重要： 此时配置文件还未加载
      return EnhancedServiceLoader.load(Configuration.class, ConfigTypeEnum.TYPESAFE.getCode());
    }
  }

  private ConfigurationFactory() {
  }

  public static Configuration getInstance() {
    return InstanceHolder.INSTANCE;
  }


}
