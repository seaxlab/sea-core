package com.github.spy.sea.core.dubbo.common.dto;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/24
 * @since 1.0
 */
@Data
public class BeanConfig extends AppConfig {

    private String url;

    private String interfaceName;
    private String method;
    private String group;
    private String version;

    private String tag;
    private String protocol;
    /**
     * 重试次数
     */
    private Integer retry;
    private Integer timeout;
}
