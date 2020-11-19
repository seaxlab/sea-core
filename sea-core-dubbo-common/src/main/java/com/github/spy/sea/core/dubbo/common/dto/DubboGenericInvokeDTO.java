package com.github.spy.sea.core.dubbo.common.dto;

import com.github.spy.sea.core.model.BaseRequestDTO;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/22
 * @since 1.0
 */
@Data
public class DubboGenericInvokeDTO extends BaseRequestDTO {

    private String appName;

    private String registryAddress;

    private Boolean qosEnable;

    private String interfaceName;
    private String method;
    private String version;
    private Integer timeout;

    /**
     * 参数类型列表, 不能为空,invoke做了判空处理
     */
    private String[] parameterTypes;
    /**
     * 参数值列表, 不能为空,invoke中做了判空处理
     */
    private Object[] parameterArgs;

}
