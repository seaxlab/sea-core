package com.github.seaxlab.core.enums;

import lombok.Getter;

/**
 * error type enum
 *
 * @author spy
 * @version 1.0 2019/3/15
 * @since 1.0
 */
@Getter
public enum ErrorTypeEnum implements IBaseEnum<String> {

    SYSTEM("SYS", "系统"),
    APPLICATION("Application", "应用"),
    BIZ("BIZ", "业务"),
    VALIDATOR("VALIDATOR", "参数校验");

    private String code;
    private String desc;

    ErrorTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
