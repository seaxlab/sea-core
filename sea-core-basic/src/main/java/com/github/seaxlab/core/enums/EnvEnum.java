package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * env enum
 *
 * @author spy
 * @version 1.0 2021/1/25
 * @since 1.0
 */
@Slf4j
public enum EnvEnum implements IBaseEnum<String> {
  UNKNOWN("unknown", "unknown env"),
  LOCAL("local", "local env"),
  DEV("dev", "develop env"),
  TEST("test", "test env"),
  SIT("sit", "sit"),
  UAT("uat", "uat"),
  PRE("pre", "pre env"),
  PRO("pro", "production env"),
  ;
  private final String code;
  private final String desc;

  private static final EnvEnum[] VALUES;

  static {
    VALUES = values();
  }

  EnvEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

  /**
   * get env enum.
   *
   * @param code
   * @return
   */
  public static EnvEnum of(String code) {
    if (Objects.isNull(code)) {
      log.warn("code is null.");
      return UNKNOWN;
    }

    for (EnvEnum item : VALUES) {
      if (EqualUtil.isEq(item.getCode(), code, false)) {
        return item;
      }
    }
    log.warn("unknown code={}", code);
    return UNKNOWN;
  }

}
