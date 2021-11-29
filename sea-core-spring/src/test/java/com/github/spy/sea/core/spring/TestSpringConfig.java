package com.github.spy.sea.core.spring;

import com.github.spy.sea.core.spring.context.SpringContextHolder;
import org.springframework.context.annotation.*;

/**
 * TestSpringConfig
 *
 * @author Frank Zhang
 * @date 2020-06-18 8:03 PM
 */
@Configuration
@ComponentScan(value = {"com.github.spy.sea.core.spring"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.github.spy.sea.core.spring.context.*")})
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

//    @Bean(initMethod = "init")
//    public ExtensionBootstrap bootstrap() {
//        ExtensionBootstrap bootstrap = new ExtensionBootstrap();
//        return bootstrap;
//    }


}
