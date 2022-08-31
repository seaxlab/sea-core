package com.github.seaxlab.core.component.tracer.enums;

import com.github.seaxlab.core.enums.IBaseEnum;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * tracer provider enum
 *
 * @author spy
 * @version 1.0 2022/08/31
 * @since 1.0
 */
@Slf4j
@Getter
public enum TracerProviderEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "unknown"), //
    SKYWALKING("skywalking", "skywalking"), //
    SOFA("sofa", "sofa tracer"),
    ;

    private String code;
    private String desc;

    TracerProviderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final TracerProviderEnum[] VALUES;

    static {
        VALUES = values();
    }

    public static TracerProviderEnum of(String code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (TracerProviderEnum item : VALUES) {
            if (EqualUtil.isEq(code, item.code)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }
}