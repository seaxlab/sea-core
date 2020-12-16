package com.github.spy.sea.core.test;

import com.google.common.base.Stopwatch;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Junit4 测试基类
 *
 * @author spy
 * @version 1.0 2019-07-13
 * @since 1.0
 */
public abstract class AbstractCoreTest {

    private Logger _log = LoggerFactory.getLogger(getClass());

    private static Stopwatch _stopwatch;

    @BeforeClass
    public static void testBefore() {
        _stopwatch = Stopwatch.createStarted();
        System.out.println("-------------------- test begin ------------------");
    }

    @AfterClass
    public static void testEnd() {
        _stopwatch.stop();
        System.out.println("cost: " + _stopwatch.toString());
        System.out.println("-------------------- test  end  ------------------");
    }


    protected void println(Object obj) {

        _log.info("{}", obj);
    }

    protected void println(String label, Object obj) {
        _log.info("{}={}", label, obj);
    }

    protected void runInMultiThread(Runnable runnable) {
        runInMultiThread(runnable, 4, 8);
    }

    protected void runInMultiThread(Runnable runnable, int runThreadCount) {
        runInMultiThread(runnable, runThreadCount, 8);
    }

    /**
     * 多线程环境中执行runnable
     * <pre>{@code
     *  AtomicBoolean check = new AtomicBoolean(false);
     *  Runnable runnable = () -> {
     *      if (check.compareAndSet(false, true)) {
     *          log.info("cas suc, value={}",check.get());
     *      } else {
     *          log.info("cas fail, value={}",check.get());
     *      }
     *  };
     *  runInMultiThread(runnable);
     *  }
     * </pre>
     *
     * @param runnable       待执行runnable
     * @param runThreadCount 创建多少个执行线程
     * @param sleepSecond    提交后等待多长时间
     */
    protected void runInMultiThread(Runnable runnable, int runThreadCount, int sleepSecond) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < runThreadCount; i++) {
            executorService.execute(runnable);
        }

        sleepSecond(sleepSecond);
    }


    /**
     * sleep in millisecond
     *
     * @param time time
     */
    protected void sleepMs(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            // eat exception
        }
    }

    /**
     * sleep
     *
     * @param time time
     */
    protected void sleepSecond(long time) {

        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            // eat exception
        }
    }

    /**
     * sleep minute
     *
     * @param time time
     */
    protected void sleepMinute(long time) {
        try {
            TimeUnit.MINUTES.sleep(time);
        } catch (InterruptedException e) {

        }
    }
}
