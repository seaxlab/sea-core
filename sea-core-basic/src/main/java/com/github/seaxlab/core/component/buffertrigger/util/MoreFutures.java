package com.github.seaxlab.core.component.buffertrigger.util;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.google.common.util.concurrent.AbstractFuture;
import java.time.Duration;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MoreFutures增强工具集合
 * <p>用于简化{@link Future}相关的操作</p>
 *
 * @author w.vela Created on 2018-06-25.
 */
public class MoreFutures {

  private static final Logger logger = LoggerFactory.getLogger(MoreFutures.class);


  /**
   * 执行一个计划任务，按照上一次计划任务返回的等待时间，来运行下一次任务
   *
   * @param executor  任务执行器，当任务执行器停止时，所有任务将停止运行
   * @param initDelay 首次执行的延迟时间
   * @param task      执行的任务，在任务中抛出的异常会终止任务的运行，需谨慎处理
   * @return 执行任务的Future，可以用来取消任务
   */
  public static Future<?> scheduleWithDynamicDelay(@Nonnull ScheduledExecutorService executor,
    @Nullable Duration initDelay, @Nonnull Scheduled task) {
    checkNotNull(executor);
    checkNotNull(task);
    AtomicBoolean canceled = new AtomicBoolean(false);
    AbstractFuture<?> future = new AbstractFuture<Object>() {

      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
        canceled.set(true);
        return super.cancel(mayInterruptIfRunning);
      }
    };
    executor.schedule(new ScheduledTaskImpl(executor, task, canceled),
      initDelay == null ? 0 : initDelay.toMillis(), MILLISECONDS);
    return future;
  }

  /**
   * 执行一个计划任务，按照上一次计划任务返回的等待时间，来运行下一次任务
   *
   * @param executor     任务执行器，当任务执行器停止时，所有任务将停止运行
   * @param initialDelay 首次执行的延迟时间
   * @param delay        任务延迟提供器，用来设置任务执行之后下一次的执行间隔时间
   * @param task         执行的任务，在任务中抛出的异常会终止任务的运行，需谨慎处理
   * @return 执行任务的Future，可以用来取消任务
   */
  public static Future<?> scheduleWithDynamicDelay(@Nonnull ScheduledExecutorService executor,
    @Nonnull Duration initialDelay, @Nonnull Supplier<Duration> delay,
    @Nonnull ThrowableRunnable<Throwable> task) {
    checkNotNull(initialDelay);
    checkNotNull(delay);
    checkNotNull(task);
    return scheduleWithDynamicDelay(executor, initialDelay, () -> {
      try {
        task.run();
      } catch (Throwable e) {
        logger.error("", e);
      }
      return delay.get();
    });
  }

  /**
   * 执行一个计划任务，按照上一次计划任务返回的等待时间，来运行下一次任务
   *
   * @param executor 任务执行器，当任务执行器停止时，所有任务将停止运行
   * @param delay    任务延迟提供器，用来设置任务执行之后下一次的执行间隔时间
   * @param task     执行的任务，在任务中抛出的异常会终止任务的运行，需谨慎处理
   * @return 执行任务的Future，可以用来取消任务
   */
  public static Future<?> scheduleWithDynamicDelay(@Nonnull ScheduledExecutorService executor,
    @Nonnull Supplier<Duration> delay, @Nonnull ThrowableRunnable<Throwable> task) {
    checkNotNull(delay);
    return scheduleWithDynamicDelay(executor, delay.get(), () -> {
      try {
        task.run();
      } catch (Throwable e) {
        logger.error("", e);
      }
      return delay.get();
    });
  }

  public interface Scheduled {

    /**
     * @return a delay for next run. {@code null} means stop.
     */
    @Nullable
    Duration run();
  }

  private static class ScheduledTaskImpl implements Runnable {

    private final ScheduledExecutorService executorService;
    private final Scheduled scheduled;
    private final AtomicBoolean canceled;

    private ScheduledTaskImpl(ScheduledExecutorService executorService, Scheduled scheduled,
      AtomicBoolean canceled) {
      this.executorService = executorService;
      this.scheduled = scheduled;
      this.canceled = canceled;
    }

    @Override
    public void run() {
      if (canceled.get()) {
        return;
      }
      try {
        Duration delay = scheduled.run();
        if (!canceled.get() && delay != null) {
          executorService.schedule(this, delay.toMillis(), MILLISECONDS);
        }
      } catch (Throwable e) {
        logger.error("", e);
      }
    }
  }
}
