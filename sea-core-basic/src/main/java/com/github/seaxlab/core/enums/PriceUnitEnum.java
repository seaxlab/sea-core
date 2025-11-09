package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * price unit enum
 *
 * @author spy
 * @version 1.0 2025/11/06
 * @since 1.0
 */
@Slf4j
@Getter
public enum PriceUnitEnum {
    UNKNOWN("unknown", "unknown"),

    //add here
    YUAN("10", "元(数字)"),
    YUAN_AND_UNIT("20", "N元"),
    WAN("30", "万元(数字)"),
    WAN_AND_UNIT("31", "N万元"),
    YUAN_OR_WAN_UNIT("40", "N元(万)元"),     //不足万元显示元
    ;

    //
    private final String code;
    private final String name;

    PriceUnitEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final PriceUnitEnum[] VALUES;

    static {
        VALUES = values();
    }

    public static PriceUnitEnum of(String code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (PriceUnitEnum item : VALUES) {
            if (item.code.equals(code)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }

    public static PriceUnitEnum ofByName(String name) {
        if (name == null) {
            log.warn("name is null");
            return UNKNOWN;
        }

        for (PriceUnitEnum item : VALUES) {
            if (name.equals(item.getName())) {
                return item;
            }
        }

        log.warn("unknown name={}", name);
        return UNKNOWN;
    }
}