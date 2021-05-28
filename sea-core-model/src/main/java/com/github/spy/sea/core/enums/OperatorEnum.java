package com.github.spy.sea.core.enums;

import lombok.extern.slf4j.Slf4j;

/**
 * operator enum
 *
 * @author spy
 * @version 1.0 2021/5/26
 * @since 1.0
 */
@Slf4j
public enum OperatorEnum implements IBaseEnum<Integer> {
    UNKNOWN(0, "未知", "??"),

    EQUAL(1, "等于", "="),
    NOT_EQUAL(2, "不等于", "!="),
    GREATER_THAN(3, "大于", ">"),
    GREATER_THAN_OR_EQUAL(4, "大于等于", ">="),
    LESS_THAN(5, "小于", "<"),
    LESS_THAN_OR_EQUAL(6, "小于等于", "<="),
    RANGE(7, "在此范围（含边界）", "[,]"),

    ;
    private Integer code;
    private String name;
    private String symbol;

    OperatorEnum(Integer code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public static OperatorEnum of(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }

        for (OperatorEnum item : values()) {
            if (item.code == code) {
                return item;
            }
        }
        log.warn("unknown code={}", code);

        return UNKNOWN;
    }
}
