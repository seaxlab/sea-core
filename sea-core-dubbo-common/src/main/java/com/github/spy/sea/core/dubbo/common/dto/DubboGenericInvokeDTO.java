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

    private String interfaceName;
    private String method;
    private String version;
    private Integer timeout;

    // 参数类型
    private String[] parameterTypes;
    // 参数值
    private Object[] parameterArgs;

}
