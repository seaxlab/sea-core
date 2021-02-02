package com.github.spy.sea.core.enums;

import com.github.spy.sea.core.util.EqualUtil;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/2
 * @since 1.0
 */
public enum ScriptTypeEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "未知脚本类型"),
    GROOVY("groovy", "groovy脚本");

    private String code;
    private String desc;

    ScriptTypeEnum(String code, String desc) {
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
     * get script type enum.
     *
     * @param code
     * @return
     */
    public static ScriptTypeEnum of(String code) {
        for (ScriptTypeEnum item : values()) {
            if (EqualUtil.isEq(item.getCode(), code, false)) {
                return item;
            }
        }
        return UNKNOWN;
    }

}
