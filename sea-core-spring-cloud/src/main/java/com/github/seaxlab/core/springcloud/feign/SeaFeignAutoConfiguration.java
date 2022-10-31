package com.github.seaxlab.core.springcloud.feign;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * sea feign auto configuration
 *
 * @author spy
 * @version 1.0 2022/10/31
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Feign.class)
@ConditionalOnProperty(prefix = "sea.spring.cloud.feign", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({FeignMockProperties.class})
public class SeaFeignAutoConfiguration {


}
