package com.github.seaxlab.core.dal.mybatis.tk2.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

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

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`update_time`")
    private Date updateTime;

    @Version
    @Column(name = "`version`")
    private Integer version;

    @Column(name = "`is_deleted`")
    private Boolean isDeleted;
}
