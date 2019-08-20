package com.github.spy.sea.core.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 星期枚举
 *
 * @author spy
 * @version 1.0 2019-08-20
 * @since 1.0
 */
public enum WeekEnum {
    /**
     * 周一
     */
    Monday(1, "周一"),
    /**
     * 周一
     */
    Tuesday(2, "周二"),
    /**
     * 周三
     */
    Wednesday(3, "周三"),
    /**
     * 周四
     */
    Thursday(4, "周四"),
    /**
     * 周五
     */
    Friday(5, "周五"),
    /**
     * 周六
     */
    Saturday(6, "周六"),
    /**
     * 周日
     */
    Sunday(0, "周日");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    WeekEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(int type) {

        for (WeekEnum result : WeekEnum.values()) {
            if (result.getType() == type) {
                return result.desc;
            }
        }
        return StringUtils.EMPTY;
    }
}
