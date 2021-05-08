package com.github.spy.sea.core.enums;

import lombok.Getter;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/8
 * @since 1.0
 */
public enum SimpleStatus implements IBaseEnum<Integer> {
    UNKNOWN(-1, "未知"),
    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    @Getter
    private int code;

    @Getter
    private String desc;

    SimpleStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * of
     *
     * @param code
     * @return
     */
    public static SimpleStatus of(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }

        for (SimpleStatus status : values()) {
            if (code.intValue() == status.getCode().intValue()) {
                return status;
            }
        }
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
            return false;
        }

        return ENABLED.code == code.intValue();
    }


    /**
     * 是否禁用
     *
     * @param code
     * @return
     */
    public static boolean isDisabled(Integer code) {
        if (code == null) {
            return false;
        }
        return DISABLED.code == code.intValue();
    }

}
