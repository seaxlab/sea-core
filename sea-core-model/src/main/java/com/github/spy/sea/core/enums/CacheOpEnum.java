package com.github.spy.sea.core.enums;

import lombok.Getter;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/8
 * @since 1.0
 */
@Getter
public enum CacheOpEnum implements IBaseEnum<String> {
    SET("set", "set op"),
    GET("get", "get op"),
    DELETE("delete", "delete op"),
    SCAN("scan", "scan op")

    // add here
    ;

    private String code;
    private String desc;

    CacheOpEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
