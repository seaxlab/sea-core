package com.github.spy.sea.core.model;

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
    // 出生年月字符串(yyyy-dd-dd)
    private String birthdayStr;


    // 年龄
    private int age;

    // 性别
    private int sex;
    private String sexLabel;


}
