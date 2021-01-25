package com.github.spy.sea.core.test;

import com.github.javafaker.Faker;
import com.github.spy.sea.core.test.service.SequenceService;
import com.github.spy.sea.core.test.service.impl.FileSequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/14
 * @since 1.0
 */
class AbstractCoreSuperTest {
    private Logger _log = LoggerFactory.getLogger(getClass());

    protected void println(Object obj) {

        _log.info("{}", obj);
    }

    protected void println(String label, Object obj) {
        _log.info("{}={}", label, obj);
    }

    protected void runInMultiThread(Runnable runnable) {
        runInMultiThread(runnable, 4, 60);
    }

    protected void runInMultiThread(Runnable runnable, int runThreadCount) {
        runInMultiThread(runnable, runThreadCount, 60);
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
    protected long nextSeq(String namespace) {
        return getSequenceService().next(namespace);
    }

    AtomicReference<Faker> fakerAtomicRef = new AtomicReference<>(null);

    protected Faker getFakerInstance() {
        Faker faker = fakerAtomicRef.get();
        if (faker == null) {
            fakerAtomicRef.compareAndSet(null, new Faker(Locale.CHINA));
        }

        return fakerAtomicRef.get();
    }

    AtomicReference<SequenceService> sequenceServiceAtomicRef = new AtomicReference<>(null);

    /**
     * sequence service
     *
     * @return
     */
    private SequenceService getSequenceService() {
        SequenceService sequenceService = sequenceServiceAtomicRef.get();
        if (sequenceService == null) {
            sequenceServiceAtomicRef.compareAndSet(null, new FileSequenceService());
        }

        return sequenceServiceAtomicRef.get();
    }

}
