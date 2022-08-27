package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * network enum.
 *
 * @author spy
 * @version 1.0 2021/1/21
 * @since 1.0
 */
@Getter
@Slf4j
public enum NetworkEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "未知"),

    INTRANET("intranet", "内网"), EXTRANET("extranet", "专网"), INTERNET("internet", "互联网");

    private String code;
    private String desc;

    NetworkEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final NetworkEnum[] VALUES;

    static {
        VALUES = values();
    }

    /**
     * get network enum.
     *
     * @param code
     * @return
     */
    public static NetworkEnum of(String code) {
        if (code == null || code.trim().isEmpty()) {
            log.warn("code is empty, plz check.");
            return UNKNOWN;
        }
        for (NetworkEnum item : VALUES) {
            if (EqualUtil.isEq(item.getCode(), code, false)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }


}
