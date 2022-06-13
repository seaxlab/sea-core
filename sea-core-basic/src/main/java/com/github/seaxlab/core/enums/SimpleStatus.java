package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * simple status
 *
 * @author spy
 * @version 1.0 2021/5/8
 * @since 1.0
 */
@Slf4j
@Getter
public enum SimpleStatus implements IBaseEnum<Integer> {
    UNKNOWN(-1, "未知"),
    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    private Integer code;

    private String desc;

    private static final SimpleStatus[] VALUES;

    static {
        VALUES = values();
    }

    SimpleStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * of
     *
     * @param code
     * @return
     */
    public static SimpleStatus of(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (SimpleStatus status : VALUES) {
            if (code.intValue() == status.getCode().intValue()) {
                return status;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }

    /**
     * 是否启用
     *
     * @param code
     * @return
     */
    public static boolean isEnabled(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return false;
        }

        return ENABLED.code.intValue() == code.intValue();
    }


    /**
     * 是否禁用
     *
     * @param code
     * @return
     */
    public static boolean isDisabled(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return false;
        }
        return DISABLED.code.intValue() == code.intValue();
    }

}
