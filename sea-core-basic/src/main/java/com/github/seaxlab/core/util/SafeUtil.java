package com.github.seaxlab.core.util;

import java.util.function.Supplier;
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
   * run safe with try catch
   *
   * @param supplier
   * @param <T>
   * @return
   */
  public static <T> T run(Supplier<T> supplier) {
    T data = null;
    try {
      data = supplier.get();
    } catch (Exception e) {
      log.warn("safe run supplier", e);
    }
    return data;
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
