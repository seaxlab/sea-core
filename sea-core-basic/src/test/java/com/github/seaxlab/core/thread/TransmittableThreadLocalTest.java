package com.github.seaxlab.core.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.github.seaxlab.core.test.AbstractCoreTest;
import com.github.seaxlab.core.thread.util.ThreadPoolUtil;
import com.github.seaxlab.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/21
 * @since 1.0
 */
@Slf4j
public class TransmittableThreadLocalTest extends AbstractCoreTest {

    @Test
    public void testJdkThreadLocal() throws Exception {
        ThreadLocal<String> context = new InheritableThreadLocal<>();

        context.set("parent-abc");

        Thread t = new Thread(() -> {
            while (true) {
                // no changer...
                log.info("get {}", context.get());
                sleepSecond(3);
            }
        });
        t.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                String value = RandomUtil.alphabetic(10);
                context.set(value);
                log.info("set form other thread {}", value);
                sleepSecond(10);
            }
        });
        t2.start();

        sleepMinute(1);
    }

    // 只能做从父线程到子线程的数据传递
    @Test
    public void testTransmittableThreadLocal() throws Exception {

        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        context.set("parent-abc");

        ExecutorService es = ThreadPoolUtil.createTemp("test", 4, 4);
        es.submit(TtlRunnable.get(() -> {
            while (true) {
                // no changer...
                log.info("get {}", context.get());
                sleepSecond(3);
            }
        }));

        String value = RandomUtil.alphabetic(10);
        context.set(value);
        log.info("set form other thread {}", value);
        sleepSecond(10);

        // 之后读取到的是新值
        es.submit(TtlRunnable.get(() -> {
            while (true) {
                // no changer...
                log.info("get {}", context.get());
                sleepSecond(3);
            }
        }));

        // 在其他线程中修改无效
//        Thread t2 = new Thread(TtlRunnable.get(() -> {
//            while (true) {
//                String value = RandomUtil.alphabetic(10);
//                parentCtx.set(value);
//                log.info("set form other thread {}", value);
//                sleepSecond(10);
//            }
//        }));
//        t2.start();

        sleepMinute(1);
    }

    @Test
    public void test96() throws Exception {
//        TransmittableThreadLocal.Transmitter.registerThreadLocal(aThreadLocal, copyLambda);
    }


    //-------test pool
    //定义一个线程池执行ttl，这里必须要用TTL线程池封装
    private static ExecutorService TTLExecutor = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(5));
    //定义另外一个线程池循环执行，模拟业务场景下多Http请求调用的情况
    private static ExecutorService loopExecutor = Executors.newFixedThreadPool(5);
    private static AtomicInteger i = new AtomicInteger(0);
    //TTL的ThreadLocal
    private static ThreadLocal tl = new TransmittableThreadLocal<>(); //这里采用TTL的实现

    @Test
    public void testThreadPool() throws Exception {
        int loopCount = 0;
        while (true) {
            loopCount++;
            if (loopCount > 100) {
                break;
            }
            /*
             这里就是循环执行10次，每次对数值加1并设置到threadlocal中，然后再使用TTL去执行来打印这个值。
             这里外部为什么使用线程池，是为了证明TTL确实可以达到我们想要的效果：即线程池中多任务带着
             父线程各自的ThreadLocal运行互不影响
            */
            loopExecutor.execute(() -> {
                if (i.get() < 10) {
                    tl.set(i.getAndAdd(1));
                    TTLExecutor.execute(() -> {
                        System.out.println(String.format("子线程名称-%s, 变量值=%s", Thread.currentThread().getName(), tl.get()));
                    });
                }
            });
            sleepSecond(3);
        }
        log.info("ttl test end.");
    }

}
