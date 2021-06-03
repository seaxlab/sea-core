package com.github.spy.sea.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * value type enum
 *
 * @author spy
 * @version 1.0 2021/6/3
 * @since 1.0
 */
@Slf4j
@Getter
public enum ValueType implements IBaseEnum<Integer> {
    UNKNOWN(0, "未知", "unknown", ""),

    TEXT(1, "text", "文本", ""),
    NUMBER(2, "number", "数字", ""),
    BOOLEAN(3, "boolean", "布尔", ""),
    JSON(4, "json", "json", ""),
    MOBILE(5, "mobile", "手机号", ""),
    EMAIL(6, "email", "email", ""),
    MONEY(7, "money", "钱", ""),

    DATETIME(40, "datetime", "日期时间", "yyyy-MM-dd HH:mm:ss"),
    DATETIME_RANGE(41, "datetime_range", "日期时间范围", ""),

    DATE(42, "date", "日期", "yyyy-MM-dd"),
    DATE_RANGE(43, "date_range", "日期范围", ""),

    DATE_MMDD(44, "date_month_and_day", "月份和天", "MM-dd"),
    DATE_MMDD_RANGE(45, "date_month_and_day_range", "月份和天范围", ""),

    TIME(46, "time", "时间", "HH:mm:ss"),
    TIME_RANGE(47, "time_range", "时间范围", ""),

    TIME_HHMM(48, "time", "时分", "HH:mm"),
    TIME_HHMM_RANGE(49, "time_range", "时分范围", ""),
    ;

    private Integer code;
    private String abbr;
    private String name;
    private String desc;

    ValueType(Integer code, String abbr, String name, String desc) {
        this.code = code;
        this.abbr = abbr;
        this.name = name;
        this.desc = desc;
    }

    public static ValueType of(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }

        for (ValueType item : values()) {
            if (item.code == code) {
                return item;
            }
        }
        log.warn("unknown code={}", code);

        return UNKNOWN;
    }

}
