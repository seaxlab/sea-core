package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/25
 * @since 1.0
 */
public enum EnvEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "unknown env"),
    LOCAL("local", "local env"),
    DEV("dev", "develop env"),
    TEST("test", "test env"),
    SIT("sit", "sit"),
    UAT("uat", "uat"),
    PRE("pre", "pre env"),
    PRO("pro", "production env"),
    ;
    private String code;
    private String desc;

    EnvEnum(String code, String desc) {
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
     * get env enum.
     *
     * @param code
     * @return
     */
    public static EnvEnum of(String code) {
        for (EnvEnum item : values()) {
            if (EqualUtil.isEq(item.getCode(), code, false)) {
                return item;
            }
        }
        return UNKNOWN;
    }

}
