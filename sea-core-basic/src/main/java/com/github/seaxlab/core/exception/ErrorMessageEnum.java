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

    // basic
    CODE_IS_NULL("sea_basic_code_is_null", "code is null"), //
    LOCK_FAIL("sea_basic_lock_fail", "操作进行中，请稍后尝试。"),

    DB_ERROR("sea_db_", "数据库异常"), //
    RPC_INVOKE_ERROR("sea_rpc_invoke_error", "远程调用异常"), //
    HTTP_ERROR("sea_http_error", "HTTP调用异常"),//
    SERIAL_ERROR("sea_serial_error", "序列化异常"),//
    DESERIAL_ERROR("sea_deserial_error", "反序列化异常"),//
    UNSUPPORTED_OPERATION("sea_unsupported_operation", "不支持的操作"),//
    //
    SYS_EXCEPTION("sea_sys_exception", "系统异常"),//
    SYS_REQUEST_INVALID("sea_request_invalid", "请求异常"),//
    SYS_PARAM_INVALID("sea_param_invalid", "参数异常"),//
    SYS_RESOURCE_NOT_EXIST("sea_resource_not_exist", "请求的资源不存在"),//
    SYS_OPERATION_FREQUENTLY("sea_operation_frequently", "操作过于频繁"),//
    SYS_REFLECT_OPERATION_ERROR("sea_reflect_operation_error", "反射异常"),//
    SYS_RATE_LIMITER_ERROR("sea_rate_limiter_error", "触发限流"),//
    SYS_FORBIDDEN_ACCESS("sea_forbidden_access", "禁止访问"),//
    SYS_FORBIDDEN_ACCESS_F("sea_forbidden_access", "禁止访问[{}]"),//
    SYS_FORBIDDEN_ACCESS_AGENT("sea_forbidden_access_agent", "该Agent禁止访问"),//
    //
    EXTENSION_NOT_EXIST("sea_extension_not_exist", "扩展不存在"),//
    EXTENSION_NOT_EXIST_F("sea_extension_not_exist", "扩展不存在[{},{}]"),//
    EXTENSION_BIZ_SCENARIO_NOT_EXIST("sea_extension_biz_scenario_not_exist", "extension biz scenario not exist."),//
    EXTENSION_DUPLICATE_REGISTER_F("sea_extension_duplicate_register", "extension[{}] duplicated registered."),//
    EXTENSION_NEED_POINT_INTERFACE_F("sea_extension_need_point_interface", "Please assign a extension point interface for []"),//
    EXTENSION_END_NAME_NOT_VALID_F("sea_extension_end_name_not_valid", "Your name of ExtensionPoint for {} is not valid, must be end of {}"),//

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