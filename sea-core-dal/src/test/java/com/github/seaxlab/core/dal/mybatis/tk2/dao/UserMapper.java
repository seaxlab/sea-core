package com.github.seaxlab.core.dal.mybatis.tk2.dao;

import com.github.seaxlab.core.dal.mybatis.tk2.domain.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/18
 * @since 1.0
 */
public interface UserMapper extends Mapper<User> {
  List<User> queryAll();

  User queryById(Long id);
}
