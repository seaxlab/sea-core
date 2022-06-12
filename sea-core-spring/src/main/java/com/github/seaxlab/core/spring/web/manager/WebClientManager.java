package com.github.seaxlab.core.spring.web.manager;

import com.github.seaxlab.core.spring.web.manager.model.WebClientConfig;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/31
 * @since 1.0
 */
public interface WebClientManager {

    WebClient init(WebClientConfig config);
}
