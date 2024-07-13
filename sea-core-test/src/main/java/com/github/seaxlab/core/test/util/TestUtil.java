package com.github.seaxlab.core.test.util;

import com.github.seaxlab.core.test.service.SequenceService;
import com.github.seaxlab.core.test.service.impl.FileSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * test util
 *
 * @author spy
 * @version 1.0 2024/1/18
 * @since 1.0
 */
@Slf4j
public class TestUtil {

  /**
   * get config info
   *
   * @param key
   * @return
   */
  public static String getConfig(String key) {
    return getConfig(key, true);
  }

  public static String getConfig(String key, boolean printFlag) {
    try {
      FileInputStream fileInputStream = new FileInputStream(
        new File(System.getProperty("user.home") + "/sea", "sea.config.properties"));

      Properties props = new Properties();
      props.load(fileInputStream);

      String value = props.getProperty(key, "");
      if (printFlag) {
        log.info("get properties {}={}", key, value);
      }

      _close(fileInputStream);
      return value;
    } catch (Exception e) {
      log.error("fail to get config", e);
    }
    //
    return "";
  }


  public static void println(Object obj) {
    log.info("{}", obj);
  }

  public static void println(String label, Object obj) {
    log.info("{}={}", label, obj);
  }


  public static void _printTable(List<String> headers, List data) {
    _printTable(headers, data, 16);
  }

  /**
   * print table
   *
   * @param headers table header
   * @param data    data
   * @param size    column with size
   */
  public static void _printTable(List<String> headers, List data, int size) {
    String format = "%" + size + "s|";
    for (String header : headers) {
      System.out.printf(format, header);
    }
    System.out.println("");

    if (data == null || data.isEmpty()) {
      return;
    }

    data.stream().forEach(item -> {
      headers.stream().forEach(header -> {
        Object value = null;
        try {
          value = FieldUtils.readField(item, header, true);
        } catch (Exception e) {
          log.error("fail to read field.", e);
          value = "";
        }
        System.out.printf(format, value);
      });
      System.out.println("");
    });

  }

  public static void runInMultiThread(Runnable runnable) {
    runInMultiThread(runnable, 4, 60);
  }

  public static void runInMultiThread(Runnable runnable, int runThreadCount) {
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
  public static void runInMultiThread(Runnable runnable, int runThreadCount, int sleepSecond) {
    ExecutorService executorService = Executors.newFixedThreadPool(8);

    for (int i = 0; i < runThreadCount; i++) {
      executorService.execute(runnable);
    }

    sleepSecond(sleepSecond);
    executorService.shutdown();
  }

  /**
   * 真正的并发执行
   *
   * @param runnable            执行任务
   * @param totalPoolSize       线程池总大小
   * @param runThreadCount      执行任务的线程数
   * @param countDownLatchCount 门栓数量
   */
  public static void runConcurrencyInMultiThread(Runnable runnable, int totalPoolSize,
                                                 int runThreadCount,
                                                 int countDownLatchCount) {
    totalPoolSize = totalPoolSize < 0 ? 8 : totalPoolSize;
    runThreadCount = runThreadCount < 0 ? 4 : runThreadCount;
    countDownLatchCount = countDownLatchCount < 0 ? runThreadCount : countDownLatchCount;

    ExecutorService pool = Executors.newFixedThreadPool(totalPoolSize);

    //主线程根据此CountDownLatch阻塞
    CountDownLatch mainThreadHolder = new CountDownLatch(countDownLatchCount);

    //并发的多个子线程根据此CountDownLatch阻塞
    CountDownLatch multiThreadHolder = new CountDownLatch(1);

    //失败次数计数器
    LongAdder failedCount = new LongAdder();

    //并发执行
    for (int i = 0; i < runThreadCount; i++) {
      pool.execute(() -> {
        try {
          log.info("waiting main thread single");
          //子线程等待，等待主线程通知后统一执行
          multiThreadHolder.await();

          log.info("execute runnable");

          //调用被测试的方法
          runnable.run();

          log.info("execute runnable successfully.");
        } catch (Exception e) {
          log.error("fail to execute runnable", e);
          //异常发生时，对失败计数器+1
          failedCount.increment();
        } finally {
          //主线程的阻塞器奇数-1
          mainThreadHolder.countDown();
        }
      });
    }
    log.info("main thread count down, allow all sub thread execute");
    //通知所有子线程可以执行方法调用了
    multiThreadHolder.countDown();

    try {
      //主线程等子线程都执行完
      log.info("main thread is waiting sub thread execute to finish");
      mainThreadHolder.await();
    } catch (InterruptedException e) {
      log.info("interrupted exception", e);
    }
    log.info("sub thread fail count={}/{}", failedCount.sum(), runThreadCount);
    log.info("all end.");
  }


  /**
   * sleep in millisecond
   *
   * @param time time
   */
  public static void sleepMs(long time) {
    try {
      TimeUnit.MILLISECONDS.sleep(time);
    } catch (InterruptedException e) {
      // eat exception
      log.warn("sleep occur exception");
    }
  }

  /**
   * sleep
   *
   * @param time time
   */
  public static void sleepSecond(long time) {

    try {
      TimeUnit.SECONDS.sleep(time);
    } catch (InterruptedException e) {
      // eat exception
      log.warn("sleep occur exception");
    }
  }

  /**
   * sleep minute
   *
   * @param time time
   */
  public static void sleepMinute(long time) {
    try {
      TimeUnit.MINUTES.sleep(time);
    } catch (InterruptedException e) {
      log.warn("sleep occur exception");
    }
  }

  /**
   * 获取下一个序列
   *
   * @param namespace
   * @return
   */
  public static long nextSeq(String namespace) {
    return getSequenceService().next(namespace);
  }

  private static final AtomicReference<SequenceService> sequenceServiceAtomicRef = new AtomicReference<>(null);

  /**
   * sequence service
   *
   * @return
   */
  private static SequenceService getSequenceService() {
    SequenceService sequenceService = sequenceServiceAtomicRef.get();
    if (sequenceService == null) {
      sequenceServiceAtomicRef.compareAndSet(null, new FileSequenceService());
    }

    return sequenceServiceAtomicRef.get();
  }

  private static void _close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        log.warn("closable occur exception");
        //eat exception
      }
    }
  }

}
