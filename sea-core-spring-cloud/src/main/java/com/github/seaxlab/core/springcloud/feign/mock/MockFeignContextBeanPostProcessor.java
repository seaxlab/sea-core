package com.github.seaxlab.core.springcloud.feign.mock;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.openfeign.FeignContext;

/**
 * mock feign context bean post processor.
 */
public class MockFeignContextBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;
    private MockFeignObjectWrapper mockFeignObjectWrapper;

    public MockFeignContextBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FeignContext && !(bean instanceof MockFeignContext)) {
            return new MockFeignContext(getMockFeignObjectWrapper(), (FeignContext) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private MockFeignObjectWrapper getMockFeignObjectWrapper() {
        if (this.mockFeignObjectWrapper == null) {
            this.mockFeignObjectWrapper = this.beanFactory.getBean(MockFeignObjectWrapper.class);
        }
        return this.mockFeignObjectWrapper;
    }
}
