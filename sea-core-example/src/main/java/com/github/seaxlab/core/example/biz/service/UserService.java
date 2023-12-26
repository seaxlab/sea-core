package com.github.seaxlab.core.example.biz.service;

import com.github.seaxlab.core.example.biz.bo.UserAdd1BO;
import com.github.seaxlab.core.example.biz.bo.UserAdd2BO;
import com.github.seaxlab.core.spring.annotation.LogCost;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/16
 * @since 1.0
 */
public interface UserService {

  @LogCost
  String queryName();

  String queryName2();

  String add(UserAdd1BO bo);

  String add(UserAdd1BO bo, UserAdd2BO bo2);
}
