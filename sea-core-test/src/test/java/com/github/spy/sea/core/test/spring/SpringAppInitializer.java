package com.github.spy.sea.core.test.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 在初始初始化spring context refresh之前初始化。可以加载指定的配置文件
 *
 * @author spy
 * @version 1.0 2021/2/23
 * @since 1.0
 */
@Slf4j
public class SpringAppInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        log.info("env={}");

        // resource property source
        ResourcePropertySource propertySource = null;
        try {
            propertySource = new ResourcePropertySource("classpath:app.properties");
        } catch (IOException e) {
            log.error("io exception", e);
        }
        env.getPropertySources().addLast(propertySource);


        // map property source
        Map<String, Object> map = new HashMap<>();
        map.put("sea.xxx", "xxx");
        MapPropertySource mapPropertySource = new MapPropertySource("sea", map);
        env.getPropertySources().addLast(mapPropertySource);
    }
}
