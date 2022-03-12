package com.github.spy.sea.core.dubbo.biz;

import com.github.spy.sea.core.dubbo.BaseDubboTest;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import com.github.spy.sea.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/27
 * @since 1.0
 */
@Slf4j
public class CorpBasicServiceDubboTest extends BaseDubboTest {

    @Test
    public void getByUrlTest() throws Exception {

        String[] parameterTypes = new String[]{String.class.getName()};
        Object[] parameterArgs = new Object[]{"admin/login"};

        dto.setRegistryAddress("zookeeper://localhost:2181");
        dto.setInterfaceName("com.yuantu.department.client.service.SysResourceDubboApiService");
        dto.setMethod("getByUrl");
        dto.setVersion("1.0.0.uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);


        Result result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }

    @Test
    public void run39() throws Exception {
        String[] parameterTypes = new String[]{String.class.getName()};
        Object[] parameterArgs = new Object[]{"15695882207"};

        dto.setRegistryAddress(REG_DING_DAO_UAT);
        dto.setInterfaceName("com.yuantu.corp.client.service.SysUserDubboApiService");
        dto.setMethod("getUserInfoByPhone");
        dto.setVersion("1.0.0.uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);

        Result result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }

    @Test
    public void getByPhone() throws Exception {
        String[] parameterTypes = new String[]{String.class.getName()};
        Object[] parameterArgs = new Object[]{"15695882207"};

        dto.setRegistryAddress(REG_DING_DAO_UAT);
//        dto.setUrl("dubbo://10.10.11.195:20882");
        dto.setInterfaceName("com.yuantu.corp.client.service.SysUserDubboApiService");
        dto.setMethod("getUserInfoByPhone");
        dto.setVersion("1.0.0.uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);

        Result result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }

    @Test
    public void run74() throws Exception {
        String[] parameterTypes = new String[]{Long.class.getName()};
        Object[] parameterArgs = new Object[]{430L};

        dto.setRegistryAddress(REG_DING_DAO_UAT);
//        dto.setUrl("dubbo://10.10.11.195:20882");
        dto.setInterfaceName("com.yuantu.corp.client.service.SysUserDubboApiService");
        dto.setMethod("getById");
        dto.setVersion("1.0.0.uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);

        Result result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());

    }


    @Test
    public void loginTest() throws Exception {
        String[] parameterTypes = new String[]{String.class.getName(), String.class.getName()};
        Object[] parameterArgs = new Object[]{"admin", "1212"};

//        dto.setRegistryAddress(REG_DING_DAO_UAT);
        dto.setUrl("dubbo://10.10.11.195:20882");
        dto.setInterfaceName("com.yuantu.corp.client.service.SysUserDubboApiService");
        dto.setMethod("loginByNameAndPassword");
        dto.setVersion("1.0.0.uat");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);

        Result result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());

    }
}
