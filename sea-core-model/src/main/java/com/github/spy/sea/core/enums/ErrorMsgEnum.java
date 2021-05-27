package com.github.spy.sea.core.enums;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/27
 * @since 1.0
 */
public enum ErrorMsgEnum {
    SUCCESS("sea-sys-0000", "成功"),
    FAIL_TO_GET_DISTRIBUTED_LOCK("sea-sys-0001", "当前正在操作中，请勿重复提交"),

    //

    ;

    private String code;
    private String desc;

    ErrorMsgEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
