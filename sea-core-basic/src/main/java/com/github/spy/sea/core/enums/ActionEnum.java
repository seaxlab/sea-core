package com.github.spy.sea.core.enums;

import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Action enum
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
public enum ActionEnum implements Serializable {
    ADD("ADD"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    QUERY("QUERY"),

    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    @Getter
    @Setter
    private String key;

    ActionEnum(String key) {
        this.key = key;
    }

    public static ActionEnum of(String action) {
        if (StringUtil.isEmpty(action)) {
            return null;
        }

        ActionEnum[] values = ActionEnum.values();
        for (int i = 0; i < values.length; i++) {
            ActionEnum actionEnum = values[i];
            if (EqualUtil.isEq(action, actionEnum.getKey(), false)) {
                return actionEnum;
            }
        }
        return null;
    }

}
