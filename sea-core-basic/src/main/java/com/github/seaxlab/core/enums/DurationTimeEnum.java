package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * duration time enum
 *
 * @author spy
 * @version 1.0 2024/04/17
 * @since 1.0
 */
@Slf4j
@Getter
public enum DurationTimeEnum {
  //
  Y("Y", "年"),
  //
  YM("YM", "年月"),
  YMD("YMD", "年月天"),
  //YMDH("YMDH", "年月天小时"),
  //YMDHm("YMDHm", "年月天小时分钟"),
  //
  YD("YD", "年天"),
  YDHm("YDHm", "年天小时分钟"),
  YDHmS("YDHms", "年天小时分钟秒"), //默认，兜底
  //
  //MD("MD", "月天"), //用YMD替换
  //
  //HMS("HMS", "小时分钟秒"), //用YDHmS代替
  //
  ;
  private final String format;
  private final String desc;

  DurationTimeEnum(String format, String desc) {
    this.format = format;
    this.desc = desc;
  }

}