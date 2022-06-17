package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 统计周期枚举
 *
 * @author spy
 * @version 1.0 2021/7/26
 * @since 1.0
 */
@Getter
@Slf4j
public enum StatPeriodTypeEnum implements IBaseEnum<Integer> {
    UNKNOWN(0, "未知"),

    DAY(1, "天"),
    WEEK(2, "周"),
    WEEK_CURRENT(21, "本周"),
    MONTH(3, "月"),
    MONTH_CURRENT(31, "本月"),
    YEAR(4, "年"),
    YEAR_CURRENT(41, "本年"),
    TIME_SEGMENT(5, "时间段"),
    ;

    private Integer code;
    private String desc;

    StatPeriodTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StatPeriodTypeEnum of(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (StatPeriodTypeEnum item : values()) {
            if (item.getCode().intValue() == code.intValue()) {
                return item;
            }
        }

        log.warn("unknown code={}", code);
        return UNKNOWN;
    }

}
