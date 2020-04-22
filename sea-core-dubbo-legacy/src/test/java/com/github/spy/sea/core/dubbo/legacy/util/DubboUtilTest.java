package com.github.spy.sea.core.dubbo.legacy.util;

import com.github.spy.sea.core.dubbo.common.dto.DubboGenericInvokeDTO;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/21
 * @since 1.0
 */
@Slf4j
public class DubboUtilTest {
    private String registryAddress = "zookeeper://10.10.10.74:2181";
    private DubboGenericInvokeDTO dto;

    @Before
    public void before() {
        dto = new DubboGenericInvokeDTO();
        dto.setAppName("sea-core-test");
        dto.setRegistryAddress("zookeeper://10.10.10.74:2181");
    }

    @Test
    public void simple2Test() throws Exception {

        String[] parameterTypes = new String[]{String.class.getName(), String.class.getName()};
        Object[] parameterArgs = new Object[]{"1231", "123123"};

        dto.setInterfaceName("com.yuantu.cardcore.dubbo.service.VirtualizationCardDubboService");
        dto.setMethod("queryQRCode");
        dto.setVersion("1.0.0.yibin_uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);


        BaseResult result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }

    @Test
    public void simple3Test() throws Exception {
        String interfaceName = "com.yuantu.cardcore.dubbo.service.VirtualizationCardDubboService";
        String method = "queryQRCode";
        String version = "1.0.0.yibin_uat";

        BaseResult result = DubboUtil.invoke(registryAddress, interfaceName, method, version, "1231", "123123");

        log.info("ret data={}", result.getData());
    }

    @Test
    public void simpleTest() throws Exception {

//        new String[]{int.class.getName()}, new Object[]{1}

        String[] parameterTypes = new String[]{String.class.getName(), String.class.getName()};
        Object[] parameterArgs = new Object[]{"1231", "123123"};

        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setAppName("sea-core-test");
        dto.setRegistryAddress("zookeeper://10.10.10.74:2181");
        dto.setInterfaceName("com.yuantu.cardcore.dubbo.service.VirtualizationCardDubboService");
        dto.setMethod("queryQRCode");
        dto.setVersion("1.0.0.yibin_uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);


        BaseResult result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }

    @Test
    public void pojoTest() throws Exception {
        String[] parameterTypes = new String[]{"com.yuantu.cardcore.param.GetQRCodeByIdNoParam"};

        Map<String, Object> params = new HashMap<>();
        params.put("class", "com.yuantu.cardcore.param.GetQRCodeByIdNoParam");
        params.put("idNo", "411425199002061091");
        params.put("idType", "01");
        params.put("name", "丁乾坤");
        params.put("userUnionId", "3414");

        Object[] parameterArgs = new Object[]{params};


        DubboGenericInvokeDTO dto = new DubboGenericInvokeDTO();
        dto.setAppName("sea-core-test");
        dto.setRegistryAddress("zookeeper://10.10.10.74:2181");
        dto.setInterfaceName("com.yuantu.cardcore.dubbo.service.VirtualizationCardDubboService");
        dto.setMethod("getQRCodeByIdNo");
        dto.setVersion("1.0.0.yibin_uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);

        BaseResult result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }
}
