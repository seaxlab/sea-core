package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-13
 * @since 1.0
 */
@Slf4j
public abstract class AbstractCoreTest {

    @Test
    public void testBefore() {
        log.info("--------------------before test------------------");
    }

    @Test
    public void testEnd() {
        log.info("--------------------end test------------------");
    }
}
