package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Date type enum.
 *
 * @author spy
 * @version 1.0 2020/4/2
 * @since 1.0
 */
@Slf4j
public enum DataTypeEnum {
  UNKNOWN("UNKNOWN"),

  BOOLEAN("BOOLEAN"),
  NUMBER("NUMBER"),
  TEXT("TEXT"),
  JSON("JSON"),
  MOBILE("mobile"),
  ;

  @Getter
  private String key;

  private static final DataTypeEnum[] VALUES;

  static {
    VALUES = values();
  }

  DataTypeEnum(String key) {
    this.key = key;
  }

  public static DataTypeEnum of(String str) {
    if (str == null || str.isEmpty()) {
      log.warn("str is empty, plz check.");
      return UNKNOWN;
    }
    for (DataTypeEnum item : VALUES) {
      if (EqualUtil.isEq(item.getKey(), str, false)) {
        return item;
      }
    }
    log.warn("unhandled value={}", str);

    return UNKNOWN;
  }
}
