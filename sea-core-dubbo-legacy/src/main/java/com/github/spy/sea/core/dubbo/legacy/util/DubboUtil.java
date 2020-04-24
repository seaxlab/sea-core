package com.github.spy.sea.core.dubbo.legacy.util;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.github.spy.sea.core.dubbo.common.dto.DubboGenericInvokeDTO;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.util.ArrayUtil;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.ObjectUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/21
 * @since 1.0
 */
@Slf4j
public final class DubboUtil {

    private static final String DEFAULT_APP_NAME = "sea-core-dubbo";

    private static final String DEFAULT_VERSION = "1.0.0";
    // 默认超时时间
    private static final Integer DEFAULT_TIME_OUT = 30 * 1000;

    /**
     * invoke
     *
     * @param registryAddress
     * @param interfaceName
     * @param method
     * @param version
     * @param arg             第一个字符串参数
     * @return
     */
    public static BaseResult invoke(String registryAddress, String interfaceName, String method, String version, String arg) {
        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setRegistryAddress(registryAddress);
        dto.setInterfaceName(interfaceName);
        dto.setMethod(method);
        dto.setVersion(version);
        dto.setParameterTypes(new String[]{String.class.getName()});
        dto.setParameterArgs(new Object[]{arg});
        return invoke(dto);
    }

    /**
     * invoke
     *
     * @param registryAddress
     * @param interfaceName
     * @param method
     * @param version
     * @param arg1            第一个字符串参数
     * @param arg2            第二个字符串参数
     * @return
     */
    public static BaseResult invoke(String registryAddress, String interfaceName, String method, String version, String arg1, String arg2) {
        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setRegistryAddress(registryAddress);
        dto.setInterfaceName(interfaceName);
        dto.setMethod(method);
        dto.setVersion(version);
        dto.setParameterTypes(new String[]{String.class.getName(), String.class.getName()});
        dto.setParameterArgs(new Object[]{arg1, arg2});
        return invoke(dto);
    }

    /**
     * 只支持String,Integer,Long,Double,Map,List等等基础类型混合
     * 这个方法很牛的，实在不行就用dto那个方法 over
     *
     * @param registryAddress
     * @param interfaceName
     * @param method
     * @param version
     * @param args
     * @return
     */
    public static BaseResult invoke(String registryAddress, String interfaceName, String method, String version, Object... args) {
        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setRegistryAddress(registryAddress);
        dto.setInterfaceName(interfaceName);
        dto.setMethod(method);
        dto.setVersion(version);

        ArrayUtil.isArray(args);

        List<String> parameterTypeList = new ArrayList<>();
        List<Object> parameterArgsList = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            parameterTypeList.add(arg.getClass().getName());
            parameterArgsList.add(arg);
        }
        dto.setParameterTypes(ArrayUtil.toArray(parameterTypeList));
        dto.setParameterArgs(ArrayUtil.toObjArray(parameterArgsList));
        return invoke(dto);
    }


    /**
     * direct invoke
     *
     * @param dto
     * @return
     */
    public static BaseResult invoke(DubboGenericInvokeDTO dto) {
        log.info("invoke dubbo method, dto={}", dto);
        BaseResult result = BaseResult.fail();

        Preconditions.checkNotNull(dto, "参数对象不能为空");
        Preconditions.checkNotNull(dto.getRegistryAddress(), "registry address 不能为空");
        Preconditions.checkNotNull(dto.getInterfaceName(), "interface 不能为空");
        Preconditions.checkNotNull(dto.getMethod(), "interface method 不能为空");

        if (dto.getParameterTypes() != null && dto.getParameterArgs() != null) {
            if (!EqualUtil.isEq(dto.getParameterTypes().length, dto.getParameterArgs().length)) {
                result.setErrorMessage("参数类型个数和参数类型值个数不相等.");
                return result;
            }
        }

        ApplicationConfig application = new ApplicationConfig();
        application.setName(StringUtil.defaultIfBlank(dto.getAppName(), DEFAULT_APP_NAME));

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(dto.getRegistryAddress());

        application.setRegistry(registry);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface(dto.getInterfaceName());
        reference.setVersion(StringUtil.defaultIfBlank(dto.getVersion(), DEFAULT_VERSION));
        reference.setTimeout(ObjectUtil.defaultIfNull(dto.getTimeout(), DEFAULT_TIME_OUT));
        // 声明为泛化接口
        reference.setGeneric(true);
        reference.setApplication(application);

        GenericService genericService = reference.get();

        try {
            Object ret = genericService.$invoke(dto.getMethod(), dto.getParameterTypes(), dto.getParameterArgs());
            if (log.isDebugEnabled()) {
                log.debug("dubbo generic service return ret={}", ret);
            }
            result.setData(ret);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("fail to invoke dubbo generic service", e);
        }
        return result;
    }
}
