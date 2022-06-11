package com.github.spy.sea.core.enums;

import lombok.Getter;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/8
 * @since 1.0
 */
public enum DateFormatEnum {

    DEFAULT("yyyy-MM-dd HH:mm:ss"),

    // date
    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    yyyyMMddHHmmssSSS("yyyyMMddHHmmssSSS"),
    yyyyMMdd_HHmmss("yyyyMMdd_HHmmss"),
    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyyyMMdd("yyyyMMdd"),
    yyyyMM("yyyyMM"),

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

    //
    // time

    HH_mm_ss("HH:mm:ss"),
    HHmmss("HHmmss"),
    HH_mm("HH:mm");

    @Getter
    private String value;

    DateFormatEnum(String value) {
        this.value = value;
    }
}
