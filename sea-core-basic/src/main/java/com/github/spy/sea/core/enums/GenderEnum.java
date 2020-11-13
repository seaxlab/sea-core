package com.github.spy.sea.core.enums;

/**
 * gender enum
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
public enum GenderEnum {
    MAN(0, "男"),
    WOMAN(1, "女"),
    UNKNOWN(2, "未知");

    private int code;
    private String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static GenderEnum get(int code) {
        GenderEnum[] values = values();

        for (int i = 0; i < values.length; i++) {
            GenderEnum genderEnum = values[i];
            if (genderEnum.getCode() == code) {
                return genderEnum;
            }
        }
        return null;
    }

    public static String getLabel(int code) {
        GenderEnum genderEnum = get(code);
        return genderEnum == null ? "" : genderEnum.getDesc();
    }
}
