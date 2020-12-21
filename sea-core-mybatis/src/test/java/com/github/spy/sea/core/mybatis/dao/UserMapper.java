package com.github.spy.sea.core.mybatis.dao;

import com.github.spy.sea.core.mybatis.domain.User;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/18
 * @since 1.0
 */
public interface UserMapper {
    List<User> queryAll();

    User queryById(Long id);
}
