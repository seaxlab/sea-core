package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * remark format enum
 *
 * @author spy
 * @version 1.0 2023/10/12
 * @since 1.0
 */
@Slf4j
@Getter
public enum RemarkFormatEnum implements IBaseEnum<String> {
  UNKNOWN("unknown", "unknown"),

  //add here
  V1("{}({})", "v1"), //
  V2("{}[{}]", "v2"), //
  V3("{}【{}】", "v3"), //
  V4("{},{}", "v4"), //

  ;
  private final String code;
  private final String desc;

  RemarkFormatEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final RemarkFormatEnum[] VALUES;

  static {
    VALUES = values();
  }

  public static RemarkFormatEnum of(String code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (RemarkFormatEnum item : VALUES) {
      if (EqualUtil.isEq(code, item.code)) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}