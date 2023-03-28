package com.github.seaxlab.core.enums;

import lombok.extern.slf4j.Slf4j;

/**
 * operator enum
 *
 * @author spy
 * @version 1.0 2021/5/26
 * @since 1.0
 */
@Slf4j
public enum OperatorEnum implements IBaseEnum<Integer> {
  UNKNOWN(0, "未知", "??", "{}??{}"),

  EQUAL(1, "等于", "=", "{}={}"),
  NOT_EQUAL(2, "不等于", "!=", "{}!={}"),
  GREATER_THAN(3, "大于", ">", "{}>{}"),
  GREATER_THAN_OR_EQUAL(4, "大于等于", ">=", "{}>={}"),
  LESS_THAN(5, "小于", "<", "{}<{}"),
  LESS_THAN_OR_EQUAL(6, "小于等于", "<=", "{}<={}"),
  CONTAINS(7, "包含", "contains", "{} contains {}"),
  NOT_CONTAINS(8, "不包含", "not contains", "{} not contains {}"),
  //
  RANGE(20, "在此范围（含边界）", "[,]", "[{},{}]"),
  RANGE_OPEN_OPEN(21, "在此范围（左开右开）", "(,)", "({},{})"),
  RANGE_OPEN_CLOSE(22, "在此范围（左开右闭）", "(,]", "({},{}]"),
  RANGE_CLOSE_OPEN(23, "在此范围（左闭右开）", "[,)", "[{},{})"),

  ;
  private final Integer code;
  private final String name;
  private final String symbol;
  private final String format;

  OperatorEnum(Integer code, String name, String symbol, String format) {
    this.code = code;
    this.name = name;
    this.symbol = symbol;
    this.format = format;
  }

  @Override
  public Integer getCode() {
    return this.code;
  }

  @Override
  public String getDesc() {
    return this.name;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public static OperatorEnum of(Integer code) {
    if (code == null) {
      log.warn("code is null");
      return UNKNOWN;
    }

    for (OperatorEnum item : values()) {
      if (item.code == code) {
        return item;
      }
    }
    log.warn("unknown code={}", code);

    return UNKNOWN;
  }
}
