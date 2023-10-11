package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * date unit enum
 *
 * @author spy
 * @version 1.0 2023/10/11
 * @since 1.0
 */
@Slf4j
@Getter
public enum DateUnitEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "unknown"),

  //add here
  YEAR(10, "年"),
  MONTH(20, "月"),
  WEEK(30, "周"),
  DAY(40, "日"),
  HOUR(50, "时"),
  MINUTE(60, "分"),
  SECOND(70, "秒"),
  ;
  private final Integer code;
  private final String desc;

  DateUnitEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final DateUnitEnum[] VALUES;

  static {
    VALUES = values();
  }

  public static DateUnitEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (DateUnitEnum item : VALUES) {
      if (code.intValue() == item.getCode().intValue()) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}