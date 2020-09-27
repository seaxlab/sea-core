package com.github.spy.sea.core.dubbo;

import com.github.spy.sea.core.dubbo.common.dto.DubboGenericInvokeDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/4/21
 * @since 1.0
 */
@Slf4j
public abstract class BaseDubboTest {

    protected String registryAddress = "zookeeper://10.10.10.74:2181";
    protected DubboGenericInvokeDTO dto;

    @Before
    public void before() {
        dto = new DubboGenericInvokeDTO();
        dto.setAppName("sea-core-test");
        dto.setRegistryAddress("zookeeper://10.10.10.74:2181");
    }


}
