package com.github.spy.sea.core.enums;

import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * Action enum
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
public enum ActionEnum implements Serializable {
    UNKNOWN("unknown"),

    ADD("ADD"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    QUERY("QUERY"),

    ENABLE("ENABLE"),
    DISABLE("DISABLE"),

    BATCH_ADD("BATCH_ADD"),
    BATCH_DELETE("BATCH_DELETE"),
    BATCH_UPDATE("BATCH_UPDATE"),
    BATCH_QUERY("BATCH_QUERY"),

    BATCH_ENABLE("BATCH_ENABLE"),
    BATCH_DISABLE("BATCH_DISABLE"),


    ;

    @Getter
    private String key;

    ActionEnum(String key) {
        this.key = key;
    }

    public static ActionEnum of(String action) {
        if (StringUtil.isEmpty(action)) {
            return UNKNOWN;
        }

        ActionEnum[] values = ActionEnum.values();
        for (int i = 0; i < values.length; i++) {
            ActionEnum item = values[i];
            if (EqualUtil.isEq(action, item.getKey(), false)) {
                return item;
            }
        }
        return UNKNOWN;
    }

}
