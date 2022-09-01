package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * trigger type enum
 *
 * @author spy
 * @version 1.0 2022/09/01
 * @since 1.0
 */
@Slf4j
@Getter
public enum TriggerTypeEnum implements IBaseEnum<Integer> {
    UNKNOWN(0, "unknown"), //
    MANUAL(1, "手动"), //
    AUTO(2, "自动"),//
    ;

    private Integer code;
    private String desc;

    TriggerTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final TriggerTypeEnum[] VALUES;

    static {
        VALUES = values();
    }

    public static TriggerTypeEnum of(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (TriggerTypeEnum item : VALUES) {
            if (code.intValue() == item.getCode().intValue()) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }
}