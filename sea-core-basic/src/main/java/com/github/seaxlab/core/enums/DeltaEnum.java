package com.github.seaxlab.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * delta 变化类型
 *
 * @author spy
 * @version 1.0 2023/04/23
 * @since 1.0
 */
@Slf4j
@Getter
public enum DeltaEnum implements IBaseEnum<Integer> {
  UNKNOWN(-1, "unknown"),

  //add here
  NONE(0, "无变化"),
  ADD(1, "增加"),
  REDUCE(2, "减少"),
  ;
  @JsonValue
  private final Integer code;
  private final String desc;

  DeltaEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final DeltaEnum[] VALUES;

  static {
    VALUES = values();
  }

  @JsonCreator
  public static DeltaEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (DeltaEnum item : VALUES) {
      if (code.intValue() == item.getCode().intValue()) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}