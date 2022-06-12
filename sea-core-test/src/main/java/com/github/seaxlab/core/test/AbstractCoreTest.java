package com.github.seaxlab.core.test;

import com.google.common.base.Stopwatch;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Junit4 测试基类
 *
 * @author spy
 * @version 1.0 2019-07-13
 * @since 1.0
 */
public abstract class AbstractCoreTest extends AbstractCoreSuperTest {

    private static Stopwatch _stopwatch;

    /**
     * you can use it, anywhere.
     */
    protected Stopwatch stopwatch = Stopwatch.createStarted();


    @BeforeClass
    public static void testBefore() {
        _stopwatch = Stopwatch.createStarted();
        System.out.println("-------------------- test begin ------------------");
    }

    @AfterClass
    public static void testEnd() {
        _stopwatch.stop();
        System.out.println("\ncost: " + _stopwatch.toString());
        System.out.println("-------------------- test  end  ------------------");
    }


}
