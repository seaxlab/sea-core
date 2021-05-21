package com.github.spy.sea.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 星期枚举
 *
 * <p>
 * 1：周一
 * .
 * 7：周日
 * </p>
 *
 * @author spy
 * @version 1.0 2019-08-20
 * @since 1.0
 */
public enum WeekChineseEnum {
    UNKNOWN(-1, "未知"),
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
    Sunday(7, "周日");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    WeekChineseEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static WeekChineseEnum of(int week) {
        for (WeekChineseEnum item : WeekChineseEnum.values()) {
            if (item.getType() == week) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public static String getDescByType(int type) {

        for (WeekChineseEnum item : values()) {
            if (item.getType() == type) {
                return item.desc;
            }
        }
        return StringUtils.EMPTY;
    }
}
