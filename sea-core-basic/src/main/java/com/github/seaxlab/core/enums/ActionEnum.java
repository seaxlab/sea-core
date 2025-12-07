package com.github.seaxlab.core.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Action enum
 *
 * @author spy
 * @version 1.0 2020/4/1
 * @since 1.0
 */
@Getter
@Slf4j
public enum ActionEnum implements Serializable {
  UNKNOWN("unknown", "未知"),

  ADD("add", "新增"),
  DELETE("delete", "删除"),
  DELETE_LOGIC("delete_logic", "逻辑删除"),
  DELETE_PHYSICS("delete_physics", "物理删除"),

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

  private final String key;
  private final String desc;

  private static final ActionEnum[] VALUES;

  static {
    VALUES = values();
  }


  ActionEnum(String key, String desc) {
    this.key = key;
    this.desc = desc;
  }

  public static ActionEnum of(String action) {
    if (action == null || action.trim().isEmpty()) {
      log.warn("action is empty");
      return UNKNOWN;
    }

    for (ActionEnum item : VALUES) {
      if (item.getKey().equalsIgnoreCase(action)) {
        return item;
      }
    }

    log.warn("unknown code={}", action);
    return UNKNOWN;
  }

  public boolean equals(String code) {
    if (code == null) {
      log.warn("code is null");
      return false;
    }
    return this.key.equalsIgnoreCase(code);
  }

}
