package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * safe util
 *
 * @author spy
 * @version 1.0 2023/03/17
 * @since 1.0
 */
@Slf4j
public final class SafeUtil {

  private SafeUtil() {
  }

  /**
   * run in try and catch
   *
   * @param runnable
   */
  public static void run(Runnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      log.warn("safe runnable exception", e);
    }
  }

  /**
   * main run and finally run
   *
   * @param runnable
   * @param finalRunnable
   */
  public static void run(Runnable runnable, Runnable finalRunnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      log.warn("safe runnable exception", e);
    } finally {
      finalRunnable.run();
    }
  }

}
