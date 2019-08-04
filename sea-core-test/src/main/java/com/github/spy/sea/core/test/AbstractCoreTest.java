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
    public static void testBefore() {
        System.out.println("-------------------- test begin ------------------");
    }

    @AfterClass
    public static void testEnd() {
        System.out.println("-------------------- test  end  ------------------");
    }


    protected void println(Object obj) {

        log.info("{}", obj);
    }

    protected void println(String label, Object obj) {
        log.info("{}={}", label, obj);
    }
}
