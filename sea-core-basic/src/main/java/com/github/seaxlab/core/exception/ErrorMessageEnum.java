package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.enums.IErrorEnum;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * error message enum
 *
 * @author spy
 * @version 1.0 2022/09/01
 * @since 1.0
 */
@Slf4j
@Getter
public enum ErrorMessageEnum implements IErrorEnum {
    UNKNOWN("unknown", "unknown"),

    CODE_IS_NULL("sys_code_is_null", "code is null"), //
    LOCK_FAIL("sys_lock_fail", "操作进行中，请稍后尝试。"),

    DB_ERR("", ""), //
    RPC_INVOKE_ERR("", ""),
    HTTP_ERROR("", ""),//
    SERIAL_ERR("", ""),//
    UNSERIAL_ERR("", ""),//
    UNSUPPORT_OPERATION("", ""),//
    SYS_EXCEPTION("", ""),//
    SYS_INVALID_REQUEST("", ""),//
    SYS_RES_NOT_EXIST("", ""),//
    SYS_OPERATION_FREQUENTLY("", ""),//
    SYS_REFLECT_OPERATION_ERR("", ""),//
    SYS_RATE_LIMITER_ERR("", ""),//
    SYS_FORBIDDEN_ACCESS("", "禁止访问"),//
    SYS_FORBIDDEN_ACCESS_F("", "禁止访问[{}]"),//
    SYS_FORBIDDEN_ACCESS_AGENT("", "该Agent禁止访问"),//

    //TODO move core error const here!
    ;

    private String code;
    private String message;

    ErrorMessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final ErrorMessageEnum[] VALUES;

    static {
        VALUES = values();
    }

    public static ErrorMessageEnum of(String code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (ErrorMessageEnum item : VALUES) {
            if (EqualUtil.isEq(code, item.code)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }
}