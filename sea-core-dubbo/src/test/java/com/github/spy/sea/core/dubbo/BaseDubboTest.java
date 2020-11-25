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

    protected String REG_DING_DAO_UAT = "zookeeper://10.122.2.110:2181";
    protected String REG_QING_DAO_UAT2 = "zookeeper://10.122.2.203:2181";

    @Before
    public void before() {
        dto = new DubboGenericInvokeDTO();
        dto.setAppName("sea-core-test");
        dto.setRegistryAddress("zookeeper://10.10.10.74:2181");
    }


}
