package com.github.seaxlab.core.example.service.impl;

import com.github.seaxlab.core.example.service.UserService;
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
}
