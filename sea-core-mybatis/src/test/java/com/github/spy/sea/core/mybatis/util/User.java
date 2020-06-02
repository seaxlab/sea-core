package com.github.spy.sea.core.mybatis.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private int int1;
    private long long1;
    private boolean boolean1;

    private Integer intObj;
    public Long longObj;
    private Boolean booleanObj;

    public String username;

}
