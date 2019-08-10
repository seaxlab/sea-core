package com.github.spy.sea.core.support.notify.manager.dingding;

import lombok.Getter;
import lombok.Setter;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
public enum DingDingMsgTypeEnum {


    TEXT("text");

    @Getter
    @Setter
    private String key;

    DingDingMsgTypeEnum(String key) {
        this.key = key;
    }

}
