package com.github.seaxlab.core.springcloud.feign.config;

import com.github.seaxlab.core.springcloud.feign.FeignMockProperties;
import com.github.seaxlab.core.springcloud.feign.mock.MockFeignContextBeanPostProcessor;
import com.github.seaxlab.core.springcloud.feign.mock.MockFeignObjectWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign mock config
 *
 * @author spy
 * @version 1.0 2022/7/1
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "sea.spring.cloud.feign", name = {"enabled",
        "mock.enabled"}, havingValue = "true")
public class MockFeignConfig {

  @Bean
  public MockFeignObjectWrapper mockFeignObjectWrapper(BeanFactory beanFactory,
                                                       FeignMockProperties apiMockProperties) {
    return new MockFeignObjectWrapper(beanFactory, apiMockProperties);
  }

  @Bean
  public MockFeignContextBeanPostProcessor feignContextBeanPostProcessor(BeanFactory beanFactory) {
    return new MockFeignContextBeanPostProcessor(beanFactory);
  }
}
