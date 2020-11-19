package com.github.spy.sea.core.dubbo.common.dto;

import lombok.Data;

/**
 * dubbo generic invoke dto.
 *
 * @author spy
 * @version 1.0 2020/4/22
 * @since 1.0
 */
@Data
public class DubboGenericInvokeDTO extends AppConfig {

    /**
     * 直连机器服务，格式ip:port
     */
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

    /**
     * 参数类型列表, 不能为空,invoke做了判空处理
     */
    private String[] parameterTypes;
    /**
     * 参数值列表, 不能为空,invoke中做了判空处理
     */
    private Object[] parameterArgs;

    private Boolean async;

}
