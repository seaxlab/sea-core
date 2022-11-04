package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * sync type enum
 *
 * @author spy
 * @version 1.0 2022/11/04
 * @since 1.0
 */
@Slf4j
@Getter
public enum SyncTypeEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "unknown"),

  ALL(1, "全量"),//
  INCREMENT(2, "增量"),//
  ;
  private final Integer code;
  private final String desc;

  SyncTypeEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private static final SyncTypeEnum[] VALUES;

  static {
    VALUES = values();
  }

  public static SyncTypeEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (SyncTypeEnum item : VALUES) {
      if (code.intValue() == item.getCode().intValue()) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }
}