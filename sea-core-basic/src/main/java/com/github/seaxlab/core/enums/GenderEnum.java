package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * gender enum
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
@Slf4j
@Getter
public enum GenderEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "未知"),

  MAN(1, "男"),
  WOMAN(2, "女");

  private final Integer code;
  private final String desc;

  private static final GenderEnum[] VALUES;

  static {
    VALUES = values();
  }

  GenderEnum(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static GenderEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (GenderEnum genderEnum : VALUES) {
      if (genderEnum.getCode().intValue() == code.intValue()) {
        return genderEnum;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }

  public static String getDesc(Integer code) {
    GenderEnum genderEnum = of(code);
    return genderEnum == UNKNOWN ? "" : genderEnum.getDesc();
  }
}
