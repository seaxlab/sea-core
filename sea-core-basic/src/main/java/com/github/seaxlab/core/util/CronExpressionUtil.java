package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;

/**
 * cron expression util
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
@Slf4j
public final class CronExpressionUtil {

  /**
   * check recon is valid.
   *
   * @param cronStr
   * @return
   */
  public static boolean isValid(String cronStr) {
    return CronExpression.isValidExpression(cronStr);
  }

}
