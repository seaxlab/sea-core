package com.github.spy.sea.core.mybatis.domain;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/18
 * @since 1.0
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String address;
}
