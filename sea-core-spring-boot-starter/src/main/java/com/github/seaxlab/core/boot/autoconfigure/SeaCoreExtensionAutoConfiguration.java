package com.github.seaxlab.core.boot.autoconfigure;

import com.github.seaxlab.core.spring.extension.ExtensionBootstrap;
import com.github.seaxlab.core.spring.extension.ExtensionExecutor;
import com.github.seaxlab.core.spring.extension.ExtensionRegister;
import com.github.seaxlab.core.spring.extension.ExtensionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Description   :
 * @ Author        :  Frank Zhang
 * @ CreateDate    :  2020/11/09
 * @ Version       :  1.0
 */
@Configuration
public class SeaCoreExtensionAutoConfiguration {

  @Bean("seaCoreExtensionBootstrap")
  @ConditionalOnMissingBean(ExtensionBootstrap.class)
  public ExtensionBootstrap bootstrap() {
    return new ExtensionBootstrap();
  }

  @Bean("seaCoreExtensionRepository")
  @ConditionalOnMissingBean(ExtensionRepository.class)
  public ExtensionRepository repository() {
    return new ExtensionRepository();
  }

  @Bean("seaCoreExtensionExecutor")
  @ConditionalOnMissingBean(ExtensionExecutor.class)
  public ExtensionExecutor executor() {
    return new ExtensionExecutor();
  }

  @Bean("seaCoreExtensionRegister")
  @ConditionalOnMissingBean(ExtensionRegister.class)
  public ExtensionRegister register() {
    return new ExtensionRegister();
  }

}
