package com.github.seaxlab.core.springcloud.feign.mock;

import com.github.seaxlab.core.springcloud.feign.FeignMockProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FeignMockProperties.class)
public class MockFeignBeanPostProcessorConfiguration {

    @Bean
    public MockFeignObjectWrapper mockFeignObjectWrapper(BeanFactory beanFactory, FeignMockProperties apiMockProperties) {
        return new MockFeignObjectWrapper(beanFactory, apiMockProperties);
    }

    @Bean
    public MockFeignContextBeanPostProcessor feignContextBeanPostProcessor(BeanFactory beanFactory) {
        return new MockFeignContextBeanPostProcessor(beanFactory);
    }

}
