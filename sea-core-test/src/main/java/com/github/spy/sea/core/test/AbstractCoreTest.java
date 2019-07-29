package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Junit4 测试基类
 *
 * @author spy
 * @version 1.0 2019-07-13
 * @since 1.0
 */
@Slf4j
public abstract class AbstractCoreTest {

    @BeforeClass
    public void testBefore() {
        log.info("--------------------before test------------------");
    }

    @AfterClass
    public void testEnd() {
        log.info("--------------------end test------------------");
    }
}
