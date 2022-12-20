package com.github.seaxlab.core.component.limit.enums;

import com.github.seaxlab.core.enums.IBaseEnum;
import com.github.seaxlab.core.util.EqualUtil;

/**
 * rate limit enum
 *
 * @author spy
 * @version 1.0 2021/2/3
 * @since 1.0
 */
public enum RateLimitEnum implements IBaseEnum<String> {
  UNKNOWN("unknown", "未知限流类型"),
  FIXED("fixed", "固定窗口"),
  SLIDING("sliding", "滑动窗口"),
  ;

  private final String code;
  private final String desc;

  RateLimitEnum(String code, String desc) {
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

  public static RateLimitEnum of(String code) {
    for (RateLimitEnum item : values()) {
      if (EqualUtil.isEq(item.getCode(), code, false)) {
        return item;
      }
    }
    return UNKNOWN;
  }


}
