package com.github.seaxlab.core.config.enums;

import com.github.seaxlab.core.enums.IBaseEnum;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * config type enum
 *
 * @author spy
 * @version 1.0 2022/09/01
 * @since 1.0
 */
@Slf4j
@Getter
public enum ConfigTypeEnum implements IBaseEnum<String> {
  UNKNOWN("unknown", "unknown"),
  SIMPLE("simple", "base on HashMap"),
  TYPESAFE("typesafe", "based on TypeSafe config"),
  ;

  private final String code;
  private final String desc;

  ConfigTypeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final ConfigTypeEnum[] VALUES;

  static {
    VALUES = values();
  }

  public static ConfigTypeEnum of(String code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (ConfigTypeEnum item : VALUES) {
      if (EqualUtil.isEq(code, item.code)) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}