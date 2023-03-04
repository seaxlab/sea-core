package com.github.seaxlab.core.lang.time;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.exception.Precondition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * local date time util
 *
 * @author spy
 * @version 1.0 2023/03/04
 * @since 1.0
 */
@Slf4j
public final class LocalDateTimeUtil {

  private LocalDateTimeUtil() {
  }

  public static LocalDateTime parse(String text) {
    return parse(text, DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
  }

  public static LocalDateTime parse(String text, DateFormatEnum dateFormatEnum) {
    Precondition.checkNotEmpty(text, "text cannot be empty.");
    Precondition.checkNotNull(dateFormatEnum, "date format cannot be empty.");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatEnum.getValue());
    return LocalDateTime.parse(text, formatter);
  }

  public static LocalDateTime addDay(LocalDateTime date, int day) {
    return date.plusDays(day);
  }


  public static LocalDateTime add(LocalDateTime date, long delta, ChronoUnit unit) {
    return date.plus(delta, unit);
  }
}
