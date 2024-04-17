package com.github.seaxlab.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * duration time enum
 *
 * @author spy
 * @version 1.0 2024/04/17
 * @since 1.0
 */
@Slf4j
@Getter
public enum DurationTimeEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "--", "unknown"),

  //add here
  DD_HH_MM_SS(10, "${day}天${hour}小时${minute}分钟${second}秒", "n天n小时n分钟n秒"),
  DD_HH_MM(20, "${day}天${hour}小时${minute}分钟", "n天n小时n分钟"),
  DD_HH(20, "${day}天${hour}小时", "n天n小时"),
  DD(20, "${day}天", "n天"),
  ;
  private final Integer code;
  private final String format;
  private final String desc;

  DurationTimeEnum(Integer code, String format, String desc) {
    this.code = code;
    this.format = format;
    this.desc = desc;
  }

  private static final DurationTimeEnum[] VALUES;

  static {
    VALUES = values();
  }

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public static DurationTimeEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (DurationTimeEnum item : VALUES) {
      if (code.intValue() == item.getCode().intValue()) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}