package com.github.seaxlab.core.example.config;

import com.github.seaxlab.core.spring.component.encrypt.EncryptPropertiesBeanFactoryPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
@Configuration
public class BeanConfig {

  //@Bean
  public EncryptPropertiesBeanFactoryPostProcessor encryptPropertiesBeanFactoryPostProcessor(ConfigurableEnvironment environment) {
    return new EncryptPropertiesBeanFactoryPostProcessor(environment);
  }
}
