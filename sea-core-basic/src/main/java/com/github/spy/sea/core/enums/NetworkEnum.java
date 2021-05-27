package com.github.spy.sea.core.enums;

import com.github.spy.sea.core.util.EqualUtil;

/**
 * network enum.
 *
 * @author spy
 * @version 1.0 2021/1/21
 * @since 1.0
 */
public enum NetworkEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "未知"),

    INTRANET("intranet", "内网"),
    EXTRANET("extranet", "专网"),
    INTERNET("internet", "互联网");

    private String code;
    private String desc;

    NetworkEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * get network enum.
     *
     * @param code
     * @return
     */
    public static NetworkEnum of(String code) {
        if (code == null || code.trim().isEmpty()) {
            return UNKNOWN;
        }
        for (NetworkEnum item : values()) {
            if (EqualUtil.isEq(item.getCode(), code, false)) {
                return item;
            }
        }
        return UNKNOWN;
    }


}
