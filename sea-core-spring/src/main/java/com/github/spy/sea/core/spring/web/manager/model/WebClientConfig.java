package com.github.spy.sea.core.spring.web.manager.model;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/31
 * @since 1.0
 */
@Data
public class WebClientConfig {

    private Integer maxConnection;

    private Long maxIdleTime;
    private Long pendingAcquireTimeout;

    private Long connReadTimeout;
    private Long connWriteTimeout;

}
