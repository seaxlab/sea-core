package com.github.spy.sea.core.spring;

import com.github.spy.sea.core.spring.context.SpringContextHolder;
import com.github.spy.sea.core.spring.extension.SpringExtensionBootstrap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TestSpringConfig
 *
 * @author Frank Zhang
 * @date 2020-06-18 8:03 PM
 */
@Configuration
@ComponentScan(value = {"com.github.spy.sea.core.spring"})
@PropertySource(value = {"classpath:sample.properties"})
public class TestSpringConfig {

    /**
     * 必须在bootstrap初始化之前
     *
     * @return
     */
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean(initMethod = "init")
    public SpringExtensionBootstrap bootstrap() {
        SpringExtensionBootstrap bootstrap = new SpringExtensionBootstrap();
        return bootstrap;
    }


}
