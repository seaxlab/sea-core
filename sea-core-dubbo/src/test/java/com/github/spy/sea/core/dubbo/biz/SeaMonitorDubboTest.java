package com.github.spy.sea.core.dubbo.biz;

import com.github.spy.sea.core.dubbo.BaseDubboTest;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/27
 * @since 1.0
 */
@Slf4j
public class SeaMonitorDubboTest extends BaseDubboTest {

    ExecutorService executors = Executors.newCachedThreadPool();

    @Test
    public void getByUrlTest() throws Exception {

        String[] parameterTypes = new String[]{};
        Object[] parameterArgs = new Object[]{};

        dto.setRegistryAddress("zookeeper://10.122.2.203:2181");
        dto.setInterfaceName("com.github.spy.sea.monitor.demo.service.UserService");
        dto.setMethod("add");
        dto.setVersion("");
        dto.setParameterTypes(parameterTypes);
        dto.setParameterArgs(parameterArgs);


        for (int i = 0; i < 30; i++) {
            executors.submit(() -> {
                log.info("invoke====");
                BaseResult result = DubboUtil.invoke(dto);
                log.info("ret data={}", result.getData());
            });
        }

        System.in.read();
    }

    @Test
    public void getUserNameTest() throws Exception {

        String[] parameterTypes = new String[]{};
        Object[] parameterArgs = new Object[]{};

        dto.setRegistryAddress("zookeeper://10.122.2.203:2181");
        dto.setInterfaceName("com.github.spy.sea.monitor.demo.service.UserService");
        dto.setMethod("getUserName");
        dto.setVersion("");
//        dto.setParameterTypes(parameterTypes);
//        dto.setParameterArgs(parameterArgs);


        log.info("invoke====");
        BaseResult result = DubboUtil.invoke(dto);
        log.info("ret data={}", result.getData());
    }
}
