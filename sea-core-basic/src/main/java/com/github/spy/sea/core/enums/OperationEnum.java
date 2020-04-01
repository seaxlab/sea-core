package com.github.spy.sea.core.enums;

import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Operation enum
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
public enum OperationEnum {
    ADD("ADD"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    QUERY("QUERY"),

    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    @Getter
    @Setter
    private String key;

    OperationEnum(String key) {
        this.key = key;
    }

    public static OperationEnum of(String operation) {
        if (StringUtil.isEmpty(operation)) {
            return null;
        }

        OperationEnum[] values = OperationEnum.values();
        for (int i = 0; i < values.length; i++) {
            OperationEnum operationEnum = values[i];
            if (EqualUtil.isEq(operation, operationEnum.getKey(), false)) {
                return operationEnum;
            }
        }
        return null;
    }

}
