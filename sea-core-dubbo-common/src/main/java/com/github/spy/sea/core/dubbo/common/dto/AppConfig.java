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

    /**
     * 一个应用只能有个一个application,dubbo限制
     * Duplicate Config found for ApplicationConfig, you should use only one unique ApplicationConfig for one application.
     */
    private Boolean hasApplication = false;
    private String appName;

    /**
     * 注册中心地址
     */
    private String registryAddress;
    /**
     * registry 缓存文件
     */
    private String registryFile;
    /**
     * 机器服务，格式ip:port
     */
    private String defaultUrl;

    private Boolean qosEnable;
}
