package com.github.spy.sea.core.component.condition.enums;

import com.github.spy.sea.core.enums.IBaseEnum;
import com.github.spy.sea.core.util.EqualUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * operator enum
 *
 * @author spy
 * @version 1.0 2021/5/26
 * @since 1.0
 */
@Slf4j
public enum OperatorEnum implements IBaseEnum<String> {
    UNKNOWN("unknown", "未知", "??", "{}??{}"),

    // string
    EQUAL("equals", "等于", "=", "{}={}"),
    NOT_EQUAL("exclude", "不等于", "!=", "{}!={}"),

    //number
    GREATER_THAN("greaterThan", "大于", ">", "{}>{}"),
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual", "大于等于", ">=", "{}>={}"),
    LESS_THAN("lessThan", "小于", "<", "{}<{}"),
    LESS_THAN_OR_EQUAL("lessThanOrEqual", "小于等于", "<=", "{}<={}"),

    NUMBER_RANGE("numberRange", "在此范围（含边界）", "[,]", "[{},{}]"),

    // date
    DATE_RANGE("dateRange", "在此范围（含边界）", "[,]", "[{},{}]"),
    DATE_BEFORE("dateBefore", "在此之前", "(∞,)", "(∞,{})"),
    DATE_AFTER("dateAfter", "在此之后", "(,∞)", "({},∞)"),

    TIME_RANGE("timeRange", "在此范围（含边界）", "[,]", "[{},{}]"),
    TIME_BEFORE("timeBefore", "在此之前", "(∞,)", "(∞,{})"),
    TIME_AFTER("timeAfter", "在此之后", "(,∞)", "({},∞)"),

    DATETIME_RANGE("dateTimeRange", "在此范围（含边界）", "[,]", "[{},{}]"),
    DATETIME_BEFORE("dateTimeBefore", "在此之前", "(∞,)", "(∞,{})"),
    DATETIME_AFTER("dateTimeAfter", "在此之后", "(,∞)", "({},∞)"),

    //非规则匹配
    MATCH("match", "Ant match匹配", "", ""),
    REGEX("regex", "正则匹配", "", ""),
    SPRING_EXPRESSION("spEL", "spring表达式匹配", "", ""),
    GROOVY("groovy", "groovy动态表达式匹配", "", ""),

    ;


    @Getter
    private String code;
    @Getter
    private String desc;
    private String symbol;
    private String format;

    OperatorEnum(String code, String desc, String symbol, String format) {
        this.code = code;
        this.desc = desc;
        this.symbol = symbol;
        this.format = format;
    }


    public static OperatorEnum of(String code) {
        if (code == null) {
            log.warn("code is null");
            return UNKNOWN;
        }

        for (OperatorEnum item : values()) {
            if (EqualUtil.isEq(item.getCode(), code)) {
                return item;
            }
        }
        log.warn("unknown code={}", code);

        return UNKNOWN;
    }
}
