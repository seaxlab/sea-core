package com.github.seaxlab.core.example.biz.user.service.impl;

import com.github.seaxlab.core.example.biz.user.bo.UserAdd1BO;
import com.github.seaxlab.core.example.biz.user.bo.UserAdd2BO;
import com.github.seaxlab.core.example.biz.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/16
 * @since 1.0
 */
@Slf4j
@Service("user2Service")
public class User2Service implements UserService {
  @Override
  public String queryName() {
    log.info("user2");
    return "user2";
  }

  @Override
  public String queryName2() {
    log.info("query name2");
    return "query name2";
  }

  @Override
  public String add(UserAdd1BO bo) {
    log.info("add1 bo={}", bo);
    return "add1";
  }

  @Override
  public String add(UserAdd1BO bo, UserAdd2BO bo2) {
    log.info("add bo1={},bo2={}", bo, bo2);
    return "add2";
  }
}
