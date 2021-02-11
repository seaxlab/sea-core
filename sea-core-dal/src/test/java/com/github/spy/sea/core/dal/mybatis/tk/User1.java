package com.github.spy.sea.core.dal.mybatis.tk;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
@Data
@Table(name = "user1")
public class User1 {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`name`")
    private String name;


    @Column(name = "`age`")
    private Integer age;

    @Column(name = "`email`")
    private String email;
}
