package com.github.seaxlab.core.component.retry.util;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

/**
 * retry util
 *
 * @author spy
 * @version 1.0 2024/1/8
 * @since 1.0
 */
@Slf4j
public class RetryUtil {

  /**
   * retry call
   *
   * @param callable     biz callable
   * @param attemptCount attempt count
   * @param bizDesc      biz desc
   * @param <V>
   * @return
   */
  public static <V> V call(Callable<V> callable, int attemptCount, String bizDesc) {
    attemptCount = Math.max(attemptCount, 0);
    //
    log.info("retry,{} begin", bizDesc);
    V value = null;

    for (int attemptNumber = 1; ; attemptNumber++) {
      log.info("retry,{},attempt count={}", bizDesc, attemptNumber);
      boolean successFlag;
      //
      try {
        value = callable.call();
        successFlag = true;
      } catch (Exception e) {
        successFlag = false;
      }

      if (successFlag) {
        break;
      }
      if (attemptNumber >= (attemptCount + 1)) {
        log.warn("retry,{},reach max attempt count={},so stop!", bizDesc, attemptCount);
        break;
      }
    }
    log.info("retry,{} end", bizDesc);
    return value;
  }

  /**
   * retry run
   *
   * @param runnable
   * @param attemptCount
   * @param bizDesc
   */
  public static void run(Runnable runnable, int attemptCount, String bizDesc) {
    attemptCount = Math.max(attemptCount, 0);
    //
    log.info("retry,{} begin", bizDesc);

    for (int attemptNumber = 1; ; attemptNumber++) {
      log.info("retry,{},attempt count={}", bizDesc, attemptNumber);
      boolean successFlag;
      //
      try {
        runnable.run();
        successFlag = true;
      } catch (Exception e) {
        successFlag = false;
      }

      if (successFlag) {
        break;
      }
      if (attemptNumber >= (attemptCount + 1)) {
        log.warn("retry,{},reach max attempt count={},so stop!", bizDesc, attemptCount);
        break;
      }
    }
    log.info("retry,{} end", bizDesc);
  }


}
