package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * gender enum
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
@Slf4j
@Getter
public enum GenderEnum implements IBaseEnum<Integer> {
    UNKNOWN(0, "未知"),

    MAN(1, "男"),
    WOMAN(2, "女");

    private Integer code;
    private String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GenderEnum of(Integer code) {
        return get(code);
    }

    /**
     * plz use [of] method.
     *
     * @param code code
     * @return gender enum
     */
    @Deprecated
    public static GenderEnum get(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        GenderEnum[] values = values();
        for (GenderEnum genderEnum : values) {
            if (genderEnum.getCode().intValue() == code.intValue()) {
                return genderEnum;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }

    public static String getLabel(Integer code) {
        GenderEnum genderEnum = get(code);
        return genderEnum == null ? "" : genderEnum.getDesc();
    }
}
