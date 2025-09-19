package com.github.seaxlab.core.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 星期枚举
 *
 * <p>
 * 0:周日
 * 1：周一
 * .
 * 6：周六
 * </p>
 *
 * @author spy
 * @version 1.0 2019-08-20
 * @since 1.0
 */
@Getter
public enum WeekEnum {

  UNKNOWN(-1, "未知"),

  /**
   * 周一
   */
  Monday(1, "周一"),
  /**
   * 周一
   */
  Tuesday(2, "周二"),
  /**
   * 周三
   */
  Wednesday(3, "周三"),
  /**
   * 周四
   */
  Thursday(4, "周四"),
  /**
   * 周五
   */
  Friday(5, "周五"),
  /**
   * 周六
   */
  Saturday(6, "周六"),
  /**
   * 周日
   */
  Sunday(0, "周日");

  private final int type;
  private final String desc;

  private static final WeekEnum[] VALUES;

  static {
    VALUES = values();
  }

  WeekEnum(int type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public static WeekEnum of(int week) {
    for (WeekEnum item : VALUES) {
      if (item.getType() == week) {
        return item;
      }
    }
    return UNKNOWN;
  }


  public static String getDescByType(int type) {
    for (WeekEnum item : VALUES) {
      if (item.getType() == type) {
        return item.desc;
      }
    }
    return StringUtils.EMPTY;
  }
}
