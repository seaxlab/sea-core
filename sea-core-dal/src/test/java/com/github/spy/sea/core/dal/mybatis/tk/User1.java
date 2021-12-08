package com.github.spy.sea.core.dal.mybatis.tk;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`card_no`")
    private String cardNo;

    @Column(name = "`age`")
    private Integer age;

    @Column(name = "`email`")
    private String email;

    @Column(name = "`create_time`")
    private String createTime;

    @Version
    @Column(name = "`version`")
    private Integer version;
}
