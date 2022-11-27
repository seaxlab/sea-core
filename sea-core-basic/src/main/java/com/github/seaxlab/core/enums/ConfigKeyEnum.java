package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * config key enum
 *
 * @author spy
 * @version 1.0 2022/09/01
 * @since 1.0
 */
@Slf4j
@Getter
public enum ConfigKeyEnum implements IBaseEnum<String> {
  UNKNOWN("unknown", "unknown"),

  TRACE_PROVIDER("sea.tracer.provider", ""),
  //
  ;

  private final String code;
  private final String desc;

  ConfigKeyEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final ConfigKeyEnum[] VALUES;

  static {
    VALUES = values();
  }

  public static ConfigKeyEnum of(String code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (ConfigKeyEnum item : VALUES) {
      if (EqualUtil.isEq(code, item.code)) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}