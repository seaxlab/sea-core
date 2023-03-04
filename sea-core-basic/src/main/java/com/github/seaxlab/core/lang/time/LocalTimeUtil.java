package com.github.seaxlab.core.lang.time;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.exception.Precondition;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * local time util
 *
 * @author spy
 * @version 1.0 2023/03/04
 * @since 1.0
 */
@Slf4j
public final class LocalTimeUtil {

  private LocalTimeUtil() {
  }

  public static LocalTime parse(String text) {
    return parse(text, DateFormatEnum.HH_mm_ss);
  }

  public static LocalTime parse(String text, DateFormatEnum dateFormatEnum) {
    Precondition.checkNotEmpty(text, "text cannot be empty.");
    Precondition.checkNotNull(dateFormatEnum, "date format cannot be empty.");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatEnum.getValue());
    return LocalTime.parse(text, formatter);
  }

  public static LocalTime add(LocalTime time, long delta, ChronoUnit unit) {
    return time.plus(delta, unit);
  }
}
