package com.github.spy.sea.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019/3/15
 * @since 1.0
 */
public enum ErrorTypeEnum {

    SYSTEM("SYS", "系统"),
    APPLICATION("Application", "应用"),
    BIZ("BIZ", "业务");

    @Getter
    @Setter
    String code;
    @Getter
    @Setter
    String desc;

    ErrorTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
