package com.github.seaxlab.core.support.notify.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * fei shu msg type enum
 *
 * @author spy
 * @version 1.0 2022-09-30
 * @since 1.0
 */
public enum FeiShuMsgTypeEnum {


    TEXT("text"), //
    MARKDOWN("markdown"),

    ;

    @Getter
    @Setter
    private String key;

    FeiShuMsgTypeEnum(String key) {
        this.key = key;
    }

}
