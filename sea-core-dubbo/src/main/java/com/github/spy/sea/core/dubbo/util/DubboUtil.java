package com.github.spy.sea.core.dubbo.util;

import com.github.spy.sea.core.dubbo.common.commonn.Const;
import com.github.spy.sea.core.dubbo.common.dto.BeanConfig;
import com.github.spy.sea.core.dubbo.common.dto.DubboGenericInvokeDTO;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.util.ArrayUtil;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.ObjectUtil;
import com.github.spy.sea.core.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.service.GenericService;

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

// not support echo feature
//    /**
//     * echo
//     *
//     * @param registryAddress
//     * @return
//     */
//    public static BaseResult echo(String registryAddress) {
//        return invoke(registryAddress, EchoService.class.getName(), "$invoke", null);
//    }

    /**
     * invoke with no args.
     *
     * @param registryAddress
     * @param interfaceName
     * @param method
     * @param version
     * @return
     */
    public static BaseResult invoke(String registryAddress, String interfaceName, String method, String version) {
        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setRegistryAddress(registryAddress);
        dto.setInterfaceName(interfaceName);
        dto.setMethod(method);
        dto.setVersion(version);
        dto.setParameterTypes(new String[0]);
        dto.setParameterArgs(new Object[0]);
        return invoke(dto);
    }

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
    public static BaseResult invoke(String registryAddress, String interfaceName, String method, String version,
                                    String arg1, String arg2) {
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

        args = ArrayUtil.defaultIfNull(args);

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
     * 直连调用
     *
     * @param url
     * @param interfaceName
     * @param method
     * @param version
     * @param args
     * @return
     */
    public static BaseResult invokeHost(String url, String interfaceName, String method, String version, Object... args) {
        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setUrl(url);
        dto.setInterfaceName(interfaceName);
        dto.setMethod(method);
        dto.setVersion(version);

        args = ArrayUtil.defaultIfNull(args);
        List<String> parameterTypeList = new ArrayList<>(args.length);
        List<Object> parameterArgsList = new ArrayList<>(args.length);

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
        Preconditions.checkNotNull(dto.getInterfaceName(), "interface 不能为空");
        Preconditions.checkNotNull(dto.getMethod(), "interface method 不能为空");

        if (StringUtil.isAllEmpty(dto.getRegistryAddress(), dto.getUrl())) {
            throw new IllegalArgumentException("registry address 和url不能同时为空");
        }

        if (dto.getParameterTypes() == null) {
            dto.setParameterTypes(new String[]{});
        }
        if (dto.getParameterArgs() == null) {
            dto.setParameterArgs(new Object[]{});
        }

        if (!EqualUtil.isEq(dto.getParameterTypes().length, dto.getParameterArgs().length)) {
            result.setErrorMessage("参数类型个数和参数类型值个数不相等.");
            return result;
        }

        // application
        ApplicationConfig application = new ApplicationConfig();
        application.setName(StringUtil.defaultIfBlank(dto.getAppName(), Const.DEFAULT_APP_NAME));
        application.setQosEnable(ObjectUtil.defaultIfNull(dto.getQosEnable(), false));

        if (StringUtil.isNotEmpty(dto.getRegistryAddress())) {
            RegistryConfig registry = new RegistryConfig();
            registry.setAddress(dto.getRegistryAddress());
            application.setRegistry(registry);
        }
        ApplicationModel.getConfigManager().setApplication(application);

        // reference
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setUrl(StringUtil.defaultIfEmpty(dto.getUrl(), null));
        reference.setInterface(dto.getInterfaceName());
        reference.setGroup(StringUtil.defaultIfEmpty(dto.getGroup(), null));
        reference.setVersion(StringUtil.defaultIfBlank(dto.getVersion(), Const.DEFAULT_VERSION));
        reference.setTimeout(ObjectUtil.defaultIfNull(dto.getTimeout(), Const.DEFAULT_TIME_OUT));
        reference.setRetries(ObjectUtil.defaultIfNull(dto.getRetry(), Const.DEFAULT_RETRY));
        reference.setTag(dto.getTag());
        reference.setProtocol(dto.getProtocol());
        // 声明为泛化接口
        reference.setGeneric(Boolean.TRUE.toString());

        // get reference from cache.
        ReferenceConfigCache configCache = ReferenceConfigCache.getCache();
        GenericService genericService = configCache.get(reference);

        boolean isAsync = ObjectUtil.defaultIfNull(dto.getAsync(), false);
        try {
            Object ret;
            if (isAsync) {
                // CompletableFuture<Object>
                ret = genericService.$invokeAsync(dto.getMethod(), dto.getParameterTypes(), dto.getParameterArgs());
            } else {
                ret = genericService.$invoke(dto.getMethod(), dto.getParameterTypes(), dto.getParameterArgs());
            }
            if (log.isDebugEnabled()) {
                log.debug("dubbo generic service return ret={}", ret);
            }
            result.value(ret);
        } catch (Exception e) {
            log.error("fail to invoke dubbo generic service", e);
        }
        return result;
    }

    /**
     * get dubbo bean if you have dubbo facade client.
     *
     * @param beanConfig bean info config.
     * @param clazz      real bean class
     * @param <T>        bean class.
     * @return
     */
    public static <T> T getBean(BeanConfig beanConfig, Class<T> clazz) {

        // application
        ApplicationConfig application = new ApplicationConfig();
        application.setName(StringUtil.defaultIfBlank(beanConfig.getAppName(), Const.DEFAULT_APP_NAME));
        application.setQosEnable(ObjectUtil.defaultIfNull(beanConfig.getQosEnable(), false));

        if (StringUtil.isNotEmpty(beanConfig.getRegistryAddress())) {
            RegistryConfig registry = new RegistryConfig();
            registry.setAddress(beanConfig.getRegistryAddress());
            application.setRegistry(registry);
        }
        ApplicationModel.getConfigManager().setApplication(application);

        // reference
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setUrl(StringUtil.defaultIfEmpty(beanConfig.getUrl(), null));
        reference.setInterface(clazz);
        reference.setGroup(StringUtil.defaultIfEmpty(beanConfig.getGroup(), null));
        reference.setVersion(StringUtil.defaultIfBlank(beanConfig.getVersion(), Const.DEFAULT_VERSION));
        reference.setTimeout(ObjectUtil.defaultIfNull(beanConfig.getTimeout(), Const.DEFAULT_TIME_OUT));
        reference.setRetries(ObjectUtil.defaultIfNull(beanConfig.getRetry(), Const.DEFAULT_RETRY));
        reference.setTag(beanConfig.getTag());
        reference.setProtocol(beanConfig.getProtocol());
        // 声明为泛化接口
        reference.setGeneric(Boolean.TRUE.toString());

        // get reference from cache.
        ReferenceConfigCache configCache = ReferenceConfigCache.getCache();
        return configCache.get(reference);
    }


    /**
     * check current node is consumer
     * 必须是default,xxxFilter，否则 rpcContext中url为空
     *
     * @return
     */
    public static boolean isConsumer() {
        RpcContext ctx = RpcContext.getContext();
        return ctx.isConsumerSide();
    }

    /**
     * check current node is provider
     * 必须是default,xxxFilter，否则 rpcContext中url为空
     *
     * @return
     */
    public static boolean isProvider() {
        RpcContext ctx = RpcContext.getContext();
        return ctx.isProviderSide();
    }


    /**
     * check current node is consumer
     * <font color="red">当filter在default之前时使用该方法</font>
     *
     * @param invoker
     * @return
     */
    public static boolean isConsumer(Invoker invoker) {
        return invoker.getUrl().getParameter(CommonConstants.SIDE_KEY, CommonConstants.PROVIDER_SIDE).equals(CommonConstants.CONSUMER_SIDE);
    }

    /**
     * check current node is provider
     * <font color="red">当filter在default之前时使用该方法</font>
     *
     * @param invoker
     * @return
     */
    public static boolean isProvider(Invoker invoker) {
        return !isConsumer(invoker);
    }

}
