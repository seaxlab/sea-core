package com.github.seaxlab.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * country enum
 *
 * @author spy
 * @version 1.0 2022/12/21
 * @since 1.0
 */
@Slf4j
@Getter
public enum CountryEnum implements IBaseEnum<String> {
  UNKNOWN("unknown", "unknown"),

  CN("cn", "中国"), //
  US("us", "美国"),
  ;
  @JsonValue
  private final String code;
  private final String desc;

  CountryEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final CountryEnum[] VALUES;

  static {
    VALUES = values();
  }

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public static CountryEnum of(String code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (CountryEnum item : VALUES) {
      if (code.equalsIgnoreCase(item.code)) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}