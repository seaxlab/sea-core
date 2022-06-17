package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 审批状态
 *
 * @author spy
 * @version 1.0 2021/10/18
 * @since 1.0
 */
@Slf4j
@Getter
public enum ReviewEnum implements IBaseEnum<Integer> {
    UNKNOWN(0, "未知"),

    INIT(10, "初始化"), // 一种不可见状态
    PENDING(20, "审批中"),
    APPROVED(30, "通过"),
    REJECTED(40, "拒绝"),
    ;

    private Integer code;
    private String desc;

    ReviewEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ReviewEnum of(Integer code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (ReviewEnum item : values()) {
            if (item.code.intValue() == code.intValue()) {
                return item;
            }
        }
        log.warn("unknown code={}", code);

        return UNKNOWN;
    }

}
