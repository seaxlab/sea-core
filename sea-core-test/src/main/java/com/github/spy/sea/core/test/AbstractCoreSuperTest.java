package com.github.spy.sea.core.test;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/14
 * @since 1.0
 */
public class AbstractCoreSuperTest {
    private Logger _log = LoggerFactory.getLogger(getClass());

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

    /**
     * 获取下一个序列
     *
     * @param namespace
     * @return
     */
    protected int nextSeq(String namespace) {
        //TODO

        return 1;
    }

    /**
     * create java faker of china.
     *
     * @return
     */
    protected Faker createFaker() {
        return new Faker(Locale.CHINA);
    }
}
