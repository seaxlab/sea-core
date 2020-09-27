package com.github.spy.sea.core.dubbo.biz;

import com.github.spy.sea.core.dubbo.BaseDubboTest;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import com.github.spy.sea.core.model.BaseResult;
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
public class DepartmentDubboTest extends BaseDubboTest {

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


        BaseResult result = DubboUtil.invoke(dto);

        log.info("ret data={}", result.getData());
    }
}
