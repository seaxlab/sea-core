package com.github.spy.sea.core.springcloud.feign;


import com.github.spy.sea.core.springcloud.feign.config.FeignMockProperties;
import feign.Client;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

public class MockFeignObjectWrapper {
    private final BeanFactory beanFactory;

    private CachingSpringLoadBalancerFactory cachingSpringLoadBalancerFactory;
    private SpringClientFactory springClientFactory;

    private FeignMockProperties apiMockProperties;

    MockFeignObjectWrapper(BeanFactory beanFactory, FeignMockProperties apiMockProperties) {
        this.beanFactory = beanFactory;
        this.apiMockProperties = apiMockProperties;
    }

    Object wrap(Object bean) {
        if (bean instanceof Client && !(bean instanceof MockFeignContext)) {
            if (bean instanceof LoadBalancerFeignClient) {
                LoadBalancerFeignClient client = ((LoadBalancerFeignClient) bean);
                return new MockLoadBalancerFeignClient(client.getDelegate(), factory(), clientFactory(), apiMockProperties);
            }
        }
        return bean;
    }

    CachingSpringLoadBalancerFactory factory() {
        if (this.cachingSpringLoadBalancerFactory == null) {
            this.cachingSpringLoadBalancerFactory = this.beanFactory.getBean(CachingSpringLoadBalancerFactory.class);
        }
        return this.cachingSpringLoadBalancerFactory;
    }

    SpringClientFactory clientFactory() {
        if (this.springClientFactory == null) {
            this.springClientFactory = this.beanFactory.getBean(SpringClientFactory.class);
        }
        return this.springClientFactory;
    }

}
