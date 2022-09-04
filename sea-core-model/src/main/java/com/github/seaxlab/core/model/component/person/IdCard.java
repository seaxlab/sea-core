package com.github.seaxlab.core.model.component.person;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
@Data
public class IdCard implements Serializable {

    // 大陆身份证
    private boolean mainlandFlag;
    // 台湾身份证
    private boolean twFlag;
    // 香港身份证
    private boolean hkFlag;

    //籍贯
    private NativePlace nativePlace;

    // 出生年月
    private Date birthday;

    // 出生年月字符串(yyyy-MM-dd)
    private String birthdayStr;

    /**
     * 死亡日期
     */
    private Date deathDay;

    /**
     * 死亡日期字符串(yyyy-MM-dd)
     */
    private String deathDayStr;


    /**
     * 年龄
     */
    private int age;

    /**
     * 出生月份到当前月份个数
     */
    private long monthsOfAge;

    /**
     * 从出生年月日到当前天数
     */
    private long daysOfAge;

    /**
     * 性别
     */
    private int sex;

    /**
     * 性别文案
     */
    private String sexLabel;


}
