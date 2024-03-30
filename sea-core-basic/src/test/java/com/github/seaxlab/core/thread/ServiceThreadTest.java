package com.github.seaxlab.core.thread;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.github.seaxlab.core.test.util.TestUtil.sleepMinute;
import static org.junit.Assert.assertEquals;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/22
 * @since 1.0
 */
@Slf4j
public class ServiceThreadTest extends BaseCoreTest {

  @Test
  public void testShutdown() {
    shutdown(false, false);
    shutdown(false, true);
    shutdown(true, false);
    shutdown(true, true);
  }

  @Test
  public void testStop() {
    stop(true);
    stop(false);
  }

  @Test
  public void testMakeStop() {
    ServiceThread testServiceThread = startTestServiceThread();
    testServiceThread.makeStop();
    assertEquals(true, testServiceThread.isStopped());
  }

  @Test
  public void testWakeup() {
    ServiceThread testServiceThread = startTestServiceThread();
    testServiceThread.wakeup();
    assertEquals(true, testServiceThread.hasNotified.get());
    assertEquals(0, testServiceThread.waitPoint.getCount());
  }

  @Test
  public void testWaitForRunning() {
    ServiceThread testServiceThread = startTestServiceThread();
    // test waitForRunning
    testServiceThread.waitForRunning(1000);
    assertEquals(false, testServiceThread.hasNotified.get());
    assertEquals(1, testServiceThread.waitPoint.getCount());
    // test wake up
    testServiceThread.wakeup();
    assertEquals(true, testServiceThread.hasNotified.get());
    assertEquals(0, testServiceThread.waitPoint.getCount());
    // repeat waitForRunning
    testServiceThread.waitForRunning(1000);
    assertEquals(false, testServiceThread.hasNotified.get());
    assertEquals(0, testServiceThread.waitPoint.getCount());
    // repeat waitForRunning again
    testServiceThread.waitForRunning(1000);
    assertEquals(false, testServiceThread.hasNotified.get());
    assertEquals(1, testServiceThread.waitPoint.getCount());
  }

  private ServiceThread startTestServiceThread() {
    return startTestServiceThread(false);
  }

  private ServiceThread startTestServiceThread(boolean daemon) {
    ServiceThread testServiceThread = new ServiceThread() {

      @Override
      public void run() {
        doNothing();
      }

      private void doNothing() {
        log.info("do biz....");
      }

      @Override
      public String getServiceName() {
        return "TestServiceThread";
      }
    };
    testServiceThread.setDaemon(daemon);
    // test start
    testServiceThread.start();
    assertEquals(false, testServiceThread.isStopped());
    return testServiceThread;
  }

  public void shutdown(boolean daemon, boolean interrupt) {
    ServiceThread testServiceThread = startTestServiceThread(daemon);
    shutdown0(interrupt, testServiceThread);
    // repeat
    shutdown0(interrupt, testServiceThread);
  }

  private void shutdown0(boolean interrupt, ServiceThread testServiceThread) {
    if (interrupt) {
      testServiceThread.shutdown(true);
    } else {
      testServiceThread.shutdown();
    }
    assertEquals(true, testServiceThread.isStopped());
    assertEquals(true, testServiceThread.hasNotified.get());
    assertEquals(0, testServiceThread.waitPoint.getCount());
  }

  public void stop(boolean interrupt) {
    ServiceThread testServiceThread = startTestServiceThread();
    stop0(interrupt, testServiceThread);
    // repeat
    stop0(interrupt, testServiceThread);
  }

  private void stop0(boolean interrupt, ServiceThread testServiceThread) {
    if (interrupt) {
      testServiceThread.stop(true);
    } else {
      testServiceThread.stop();
    }
    assertEquals(true, testServiceThread.isStopped());
    assertEquals(true, testServiceThread.hasNotified.get());
    assertEquals(0, testServiceThread.waitPoint.getCount());
  }

  @Test
  public void testRebalance() throws Exception {
    RebalanceService service = new RebalanceService();
    service.start();

    sleepMinute(1);

    //09:02:29.943 INFO [RebalanceService] [c.g.s.s.c.t.ServiceThreadTest$RebalanceService.run:157] [,] - RebalanceService service started
    //09:02:49.948 INFO [RebalanceService] [c.g.s.s.c.t.ServiceThreadTest$RebalanceService.run:163] [,] - do biz rebalance.
    // 等待指定间隔后执行
  }

  @Test
  public void testRebalance2() throws Exception {
    RebalanceService service = new RebalanceService();
    service.start();
    service.wakeup();

    sleepMinute(1);

    //09:03:21.124 INFO [RebalanceService] [c.g.s.s.c.t.ServiceThreadTest$RebalanceService.run:160] [,] - RebalanceService service started
    //09:03:21.124 INFO [RebalanceService] [c.g.s.s.c.t.ServiceThreadTest$RebalanceService.run:166] [,] - do biz rebalance.
    // 调用wakeup后立即触发
  }


  public static class RebalanceService extends ServiceThread {
    //1 周期性触发Rebalance时间间隔，默认20秒
    private static long waitInterval = 20_000;

    @Override
    public void run() {
      log.info(this.getServiceName() + " service started");
      //2 在消费者没有停止的情况下，通过死循环定时触发Rebalance
      while (!this.isStopped()) {
        //3 等待20秒，如果被唤醒，则无需等待
        this.waitForRunning(waitInterval);
        //4 触发Rebalance
        log.info("do biz rebalance.");
      }

      log.info(this.getServiceName() + " service end");
    }

    @Override
    public String getServiceName() {
      return RebalanceService.class.getSimpleName();
    }
  }

}
