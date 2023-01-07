package com.github.seaxlab.core.example.service;

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
}
