package com.github.spy.sea.core.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Junit4 测试基类
 *
 * @author spy
 * @version 1.0 2019-07-13
 * @since 1.0
 */
public abstract class AbstractCoreTest {

    private Logger _log = LoggerFactory.getLogger(getClass());

    @BeforeClass
    public static void testBefore() {
        System.out.println("-------------------- test begin ------------------");
    }

    @AfterClass
    public static void testEnd() {
        System.out.println("-------------------- test  end  ------------------");
    }


    protected void println(Object obj) {

        _log.info("{}", obj);
    }

    protected void println(String label, Object obj) {
        _log.info("{}={}", label, obj);
    }
}
