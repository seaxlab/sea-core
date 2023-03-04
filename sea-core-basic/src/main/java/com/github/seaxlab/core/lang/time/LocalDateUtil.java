package com.github.seaxlab.core.lang.time;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.exception.Precondition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * local Date util
 *
 * @author spy
 * @version 1.0 2023/3/4
 * @since 1.0
 */
@Slf4j
public class LocalDateUtil {

  public static LocalDate parse(String text) {
    return parse(text, DateFormatEnum.yyyy_MM_dd);
  }

  public static LocalDate parse(String text, DateFormatEnum dateFormatEnum) {
    Precondition.checkNotEmpty(text, "text cannot be empty.");
    Precondition.checkNotNull(dateFormatEnum, "date format cannot be empty.");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatEnum.getValue());
    return LocalDate.parse(text, formatter);
  }

  public static LocalDate addDay(LocalDate date, int day) {
    return date.plusDays(day);
  }

  public static LocalDate add(LocalDate date, long delta, ChronoUnit unit) {
    return date.plus(delta, unit);
  }

}
