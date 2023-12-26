package com.github.seaxlab.core.example.biz.service.impl;

import com.github.seaxlab.core.example.biz.bo.UserAdd1BO;
import com.github.seaxlab.core.example.biz.bo.UserAdd2BO;
import com.github.seaxlab.core.example.biz.service.UserService;
import com.github.seaxlab.core.spring.annotation.LogCost;
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
@Service("user1Service")
public class User1Service implements UserService {

  @Override
  public String queryName() {
    log.info("user1");
    return "user1";
  }

  @Override
  @LogCost
  public String queryName2() {
    log.info("user1 service name2");
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
