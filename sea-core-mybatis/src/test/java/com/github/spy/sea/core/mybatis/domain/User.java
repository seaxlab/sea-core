package com.github.spy.sea.core.mybatis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/18
 * @since 1.0
 */
@Data
@Table(name = "`user`")
public class User {
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`age`")
    private Integer age;

    @Column(name = "`address`")
    private String address;
}
