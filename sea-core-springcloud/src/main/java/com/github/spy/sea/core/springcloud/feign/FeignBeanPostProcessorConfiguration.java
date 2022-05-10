package com.github.spy.sea.core.springcloud.feign;

import com.github.spy.sea.core.springcloud.feign.config.FeignMockProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FeignMockProperties.class)
public class FeignBeanPostProcessorConfiguration {

    @Bean
    public MockFeignObjectWrapper mockFeignObjectWrapper(BeanFactory beanFactory, FeignMockProperties apiMockProperties) {
        return new MockFeignObjectWrapper(beanFactory, apiMockProperties);
    }

    @Bean
    public FeignContextBeanPostProcessor feignContextBeanPostProcessor(BeanFactory beanFactory) {
        return new FeignContextBeanPostProcessor(beanFactory);
    }

}
