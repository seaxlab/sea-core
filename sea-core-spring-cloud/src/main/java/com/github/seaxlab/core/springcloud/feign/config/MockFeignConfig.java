package com.github.seaxlab.core.springcloud.feign.config;

import com.github.seaxlab.core.springcloud.feign.FeignMockProperties;
import com.github.seaxlab.core.springcloud.feign.mock.MockFeignContextBeanPostProcessor;
import com.github.seaxlab.core.springcloud.feign.mock.MockFeignObjectWrapper;
import com.github.seaxlab.core.springcloud.feign.util.KeyUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * feign mock config
 *
 * @author spy
 * @version 1.0 2022/7/1
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "sea.spring.cloud.feign", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MockFeignConfig {

    @Autowired
    private FeignMockProperties seaMockProperties;

    @Bean
    public FeignMockProperties feignMockProperties() {
        String configPath = seaMockProperties.getConfigPath();
        log.info("config path={}", configPath);

        boolean existFlag = FileUtil.exist(configPath);
        FeignMockProperties props = new FeignMockProperties();

        if (existFlag) {
            String content = FileUtil.readFileToString(configPath + "/sea.mock.url.txt");
            String[] configs = content.split("\r\n");
            Map<String, String> map = new HashMap<>();

            for (String config : configs) {
                if (StringUtil.isBlank(config)) {
                    continue;
                }
                if (config.trim().startsWith("#")) {
                    // ignore comment
                    continue;
                }

                String[] items = config.split(",");
                if (items.length != 3) {
                    log.warn("illegal mock config={}", config);
                    continue;
                }
                map.put(KeyUtil.getKey(items[0], items[1]), items[2].trim());
            }

            props.setEnabled(true);
            props.setConfigPath(configPath);
            props.setApis(map);
        } else {
            props.setEnabled(false);
        }

        return props;
    }

    @Bean
    public MockFeignObjectWrapper mockFeignObjectWrapper(BeanFactory beanFactory, FeignMockProperties apiMockProperties) {
        return new MockFeignObjectWrapper(beanFactory, apiMockProperties);
    }

    @Bean
    public MockFeignContextBeanPostProcessor feignContextBeanPostProcessor(BeanFactory beanFactory) {
        return new MockFeignContextBeanPostProcessor(beanFactory);
    }
}
