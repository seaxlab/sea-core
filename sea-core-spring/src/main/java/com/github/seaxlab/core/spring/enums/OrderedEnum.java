package com.github.seaxlab.core.spring.enums;

import com.github.seaxlab.core.enums.IBaseEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * ordered enum for aop
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
@Slf4j
@Getter
public enum OrderedEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "unknown"), //
  //add here
  // lower is HIGHER EXECUTE
  LOG_REQUEST(100, "log method request and response"), //
  LOG_COST(200, "log method execute cost"),//
  MOCK_METHOD(300, "mock method"),//
  ;
  private final Integer code;
  private final String desc;

  OrderedEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final OrderedEnum[] VALUES;

  static {
    VALUES = values();
  }

  public static OrderedEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (OrderedEnum item : VALUES) {
      if (code.intValue() == item.getCode().intValue()) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}