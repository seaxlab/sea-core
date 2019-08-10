package com.github.spy.sea.core.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Junit5 测试基类
 *
 * @author spy
 * @version 1.0 2019-07-29
 * @since 1.0
 */
@Slf4j
public class AbstractCore5Test {
    @BeforeEach
    public void testBefore() {
        log.info("--------------------before test------------------");
    }

    @AfterEach
    public void testEnd() {
        log.info("--------------------end test------------------");
    }
}
