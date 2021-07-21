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
@Getter
public enum ActionEnum implements Serializable {
    UNKNOWN("unknown", "未知"),

    ADD("add", "新增"),
    DELETE("delete", "删除"),
    UPDATE("update", "更新"),
    QUERY("query", "查询"),

    ENABLE("enable", "启用"),
    DISABLE("disable", "停用"),

    BATCH_ADD("batch_add", "批量新增"),
    BATCH_DELETE("batch_delete", "批量删除"),
    BATCH_UPDATE("batch_update", "批量更新"),
    BATCH_QUERY("batch_query", "批量查询"),

    BATCH_ENABLE("batch_enable", "批量启用"),
    BATCH_DISABLE("batch_disable", "批量停用"),


    ;

    private String key;
    private String desc;

    ActionEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
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
