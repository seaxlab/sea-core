package com.github.seaxlab.core.enums;

import lombok.Getter;

/**
 * date format enum
 *
 * @author spy
 * @version 1.0 2022/6/8
 * @since 1.0
 */
public enum DateFormatEnum {

  DEFAULT("yyyy-MM-dd HH:mm:ss"),

  // date
  yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
  yyyyMMddHHmm("yyyyMMddHHmm"),
  yyyyMMddHHmmss("yyyyMMddHHmmss"),
  yyyyMMddHHmmssSSS("yyyyMMddHHmmssSSS"),
  yyyyMMdd_HHmmss("yyyyMMdd_HHmmss"),
  yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
  yyyy_MM_dd_HH("yyyy-MM-dd HH"),
  yyyy_MM_dd("yyyy-MM-dd"),
  yyyyMMdd("yyyyMMdd"),
  yyyyMM("yyyyMM"),


  // year short
  yy_MM_dd_HH_mm_ss("yy-MM-dd HH:mm:ss"),
  yyMMddHHmmss("yyMMddHHmmss"),
  yyMMddHHmmssSSS("yyMMddHHmmssSSS"),
  yyMMdd_HHmmss("yyMMdd_HHmmss"),
  yy_MM_dd_HH_mm("yy-MM-dd HH:mm"),
  yy_MM_dd("yy-MM-dd"),
  yyMMdd("yyMMdd"),
  yyMM("yyMM"),

  //
  MMdd("MMdd"),
  MM_dd("MM-dd"),

  // 中文
  MM_CN("MM月"),
  MM_DD_CN("MM月dd日"),
  yyyy_MM_dd_CN("yyyy年MM月dd日"),
  yyyy_MM_dd_HH_CN("yyyy年MM月dd日HH时"),
  yyyy_MM_dd_HH_mm_CN("yyyy年MM月dd日HH时mm分"),
  yyyy_MM_dd_HH_mm_ss_CN("yyyy年MM月dd日HH时mm分ss秒"),

  // 中文short
  yy_MM_dd_CN("yy年MM月dd日"),
  yy_MM_dd_HH_CN("yy年MM月dd日HH时"),
  yy_MM_dd_HH_mm_CN("yy年MM月dd日HH时mm分"),
  yy_MM_dd_HH_mm_ss_CN("yy年MM月dd日HH时mm分ss秒"),

  //
  // time
  HH_mm_ss("HH:mm:ss"),
  HHmmss("HHmmss"),
  HH_mm("HH:mm"),

  // time 中文
  HH_CN("HH时"),
  HH_mm_CN("HH时mm分"),
  HH_mm_ss_CN("时mm分ss秒"),

  //
  ;

  @Getter
  private final String value;

  DateFormatEnum(String value) {
    this.value = value;
  }
}
