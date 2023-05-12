package com.github.seaxlab.core.util;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * time unit util
 *
 * @author spy
 * @version 1.0 11/18/20
 * @since 1.0
 */
@Slf4j
public final class TimeUnitUtil {

  /**
   * convert duration to ms
   *
   * @param duration duration
   * @param timeUnit time unit
   * @return long
   */
  public static long toMills(long duration, TimeUnit timeUnit) {
    return TimeUnit.MILLISECONDS.convert(duration, timeUnit);
  }
}
