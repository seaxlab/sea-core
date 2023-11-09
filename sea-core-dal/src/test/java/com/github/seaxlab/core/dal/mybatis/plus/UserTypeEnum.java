package com.github.seaxlab.core.dal.mybatis.plus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.seaxlab.core.enums.IBaseEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * user type enum
 *
 * @author spy
 * @version 1.0 2023/11/09
 * @since 1.0
 */
@Slf4j
@Getter
public enum UserTypeEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "unknown"),

  //add here
  V1(1, ""),
  V2(2, "");
  @EnumValue
  @JsonValue
  private final Integer code;
  private final String desc;

  UserTypeEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final UserTypeEnum[] VALUES;

  static {
    VALUES = values();
  }

  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  public static UserTypeEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (UserTypeEnum item : VALUES) {
      if (code.intValue() == item.getCode().intValue()) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}