package com.github.seaxlab.core.enums;

import com.github.seaxlab.core.util.EqualUtil;
import lombok.Getter;

/**
 * Date type enum.
 *
 * @author spy
 * @version 1.0 2020/4/2
 * @since 1.0
 */
public enum DataTypeEnum {

    BOOLEAN("BOOLEAN"),
    NUMBER("NUMBER"),
    TEXT("TEXT"),
    JSON("JSON"),
    MOBILE("mobile"),


    ;

    @Getter
    private String key;

    DataTypeEnum(String key) {
        this.key = key;
    }

    public static DataTypeEnum of(String str) {
        DataTypeEnum[] values = values();

        for (int i = 0; i < values.length; i++) {
            DataTypeEnum item = values[i];
            if (EqualUtil.isEq(item.getKey(), str, false)) {
                return item;
            }
        }

        return null;
    }
}
