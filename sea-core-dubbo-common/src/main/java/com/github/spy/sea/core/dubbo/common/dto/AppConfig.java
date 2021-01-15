package com.github.spy.sea.core.dubbo.common.dto;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Data
public class AppConfig {

    private Boolean hasApplication = false;
    private String appName;

    /**
     * 注册中心地址
     */
    private String registryAddress;
    /**
     * 机器服务，格式ip:port
     */
    private String defaultUrl;

    private Boolean qosEnable;
}
