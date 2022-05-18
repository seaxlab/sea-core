package com.github.spy.sea.core.springcloud.feign.mock;


import com.github.spy.sea.core.springcloud.feign.config.FeignMockProperties;
import feign.Client;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;

public class MockFeignObjectWrapper {
    private final BeanFactory beanFactory;

    private LoadBalancerClient loadBalancerClient;
    private LoadBalancerProperties loadBalancerProperties;
    private LoadBalancerClientFactory springClientFactory;

    private FeignMockProperties apiMockProperties;

    public MockFeignObjectWrapper(BeanFactory beanFactory, FeignMockProperties apiMockProperties) {
        this.beanFactory = beanFactory;
        this.apiMockProperties = apiMockProperties;
    }

    Object wrap(Object bean) {
        if (bean instanceof Client && !(bean instanceof MockFeignContext)) {
            if (bean instanceof FeignBlockingLoadBalancerClient) {
                FeignBlockingLoadBalancerClient client = ((FeignBlockingLoadBalancerClient) bean);

                return new MockLoadBalancerFeignClient(client.getDelegate(), loadBalancerClient(), loadBalancerProperties(), clientFactory(), apiMockProperties);
            }
        }
        return bean;
    }

    LoadBalancerClient loadBalancerClient() {
        if (this.loadBalancerClient == null) {
            this.loadBalancerClient = this.beanFactory.getBean(LoadBalancerClient.class);
        }
        return this.loadBalancerClient;
    }

    LoadBalancerProperties loadBalancerProperties() {
        if (this.loadBalancerProperties == null) {
            this.loadBalancerProperties = this.beanFactory.getBean(LoadBalancerProperties.class);
        }
        return this.loadBalancerProperties;
    }

    LoadBalancerClientFactory clientFactory() {
        if (this.springClientFactory == null) {
            this.springClientFactory = this.beanFactory.getBean(LoadBalancerClientFactory.class);
        }
        return this.springClientFactory;
    }

}
