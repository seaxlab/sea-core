package com.github.spy.sea.core.spring.web.util;

import com.github.spy.sea.core.spring.web.manager.WebClientManager;
import com.github.spy.sea.core.spring.web.manager.impl.DefaultWebClientManager;
import com.github.spy.sea.core.spring.web.manager.model.WebClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/31
 * @since 1.0
 */
@Slf4j
public final class WebClientUtil {

    private static final Map<String, WebClient> cache = new ConcurrentHashMap<>();

    public static WebClient getInstance(String bizType) {

        cache.computeIfAbsent(bizType, key -> {
            WebClientManager clientManager = new DefaultWebClientManager(bizType);
            WebClientConfig config = new WebClientConfig();
            return clientManager.init(config);
        });

        return cache.get(bizType);
    }
}
