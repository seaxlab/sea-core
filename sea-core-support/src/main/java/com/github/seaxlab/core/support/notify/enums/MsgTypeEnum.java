package com.github.seaxlab.core.support.notify.enums;

import com.github.seaxlab.core.enums.IBaseEnum;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * msg type enum
 *
 * @author spy
 * @version 1.0 2022/10/01
 * @since 1.0
 */
@Slf4j
@Getter
public enum MsgTypeEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "unknown"),

    //add here
    TEXT("text", "text"), //
    MARKDOWN("markdown", "markdown"),
    ;

    private final String code;
    private final String desc;

    MsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final MsgTypeEnum[] VALUES;

    static {
        VALUES = values();
    }

    public static MsgTypeEnum of(String code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (MsgTypeEnum item : VALUES) {
            if (EqualUtil.isEq(code, item.code)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);
        return UNKNOWN;
    }
}